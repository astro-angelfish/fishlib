/*
 * FishLib, a Bukkit development library
 * Copyright (C) Astro angelfish
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package moe.orangemc.fishlib.command.selector;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import moe.orangemc.fishlib.command.argument.FishUTFStringArgumentType;
import moe.orangemc.fishlib.command.argument.type.EntityList;
import moe.orangemc.fishlib.command.selector.argument.trait.LimitArgument;
import moe.orangemc.fishlib.command.selector.context.*;
import moe.orangemc.fishlib.command.selector.type.SelectorArgumentTypeAdapter;
import moe.orangemc.fishlib.command.selector.util.CharFilterUtil;
import moe.orangemc.fishlib.command.selector.util.Sorter;
import moe.orangemc.fishlib.command.util.CommandFailException;
import moe.orangemc.fishlib.command.util.CommandSyntaxExceptionBuilder;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SelectorImpl implements Selector {
	private final Plugin owner;
	private final CommandSender sender;

	private final Map<Class<? extends SelectorContext>, SelectorContext> contextMap = new HashMap<>();
	private final Map<Character, SelectMode> selectModeMap;
	@SuppressWarnings("rawtypes")
	private final Map<String, SelectorArgument> argumentNameMap;
	@SuppressWarnings("rawtypes")
	private final Map<Class, SelectorArgumentTypeAdapter> argumentAdapterMap;
	private final Map<String, Sorter> sorterMap;
	private StringReader prompt;
	private String entitySelectionCachedPrompt;

	@SuppressWarnings("rawtypes")
	public SelectorImpl(Plugin owner, CommandSender sender, Map<Character, SelectMode> selectModeMap, Map<String, SelectorArgument> argumentNameMap, Map<Class, SelectorArgumentTypeAdapter> argumentAdapterMap, Map<String, Sorter> sorterMap) {
		this.owner = owner;
		this.sender = sender;
		this.selectModeMap = selectModeMap;
		this.argumentNameMap = argumentNameMap;
		this.argumentAdapterMap = argumentAdapterMap;
		this.sorterMap = sorterMap;

		contextMap.put(SelectedEntitiesContext.class, new SelectedEntitiesContextImpl());

		contextMap.put(LocationContext.class, new LocationContextImpl(this));
	}

	@Override
	public Plugin getOwner() {
		return owner;
	}

	@Override
	public CommandSender getSender() {
		return sender;
	}

	@Override
	public void updatePrompt(StringReader prompt) {
		this.prompt = prompt;
	}

	@Override
	public EntityList selectEntities() throws CommandSyntaxException, CommandFailException {
		SelectedEntitiesContext selectedEntitiesContext = getContext(SelectedEntitiesContext.class);

		if (!prompt.getString().equals(entitySelectionCachedPrompt)) {
			int start = prompt.getCursor();

			Player p = Bukkit.getPlayer(prompt.readUnquotedString());
			if (p != null) {
				selectedEntitiesContext.setSelectedEntities(new ArrayList<>(Collections.singleton(p)));
			} else {
				prompt.setCursor(start);
				try {
					UUID uuid = UUID.fromString(prompt.readString());
					Entity e = Bukkit.getEntity(uuid);
					if (e == null) {
						CommandSyntaxExceptionBuilder.raise(owner, sender, "command.entity.notfound", "Entity not found");
					}
					selectedEntitiesContext.setSelectedEntities(new ArrayList<>(Collections.singleton(e)));
				} catch (IllegalArgumentException e) {
					prompt.setCursor(start);
					parsePrompt(prompt, selectedEntitiesContext);
				}
			}
			entitySelectionCachedPrompt = this.prompt.getString().substring(start, this.prompt.getCursor());
		}

		return new EntityList(selectedEntitiesContext.getSelectedEntities());
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void parsePrompt(StringReader promptReader, SelectedEntitiesContext selectedEntitiesContext) throws CommandSyntaxException {
		promptReader.expect('@');
		SelectMode selectMode = selectModeMap.get(promptReader.read());
		if (selectMode == null) {
			CommandSyntaxExceptionBuilder.raise(owner, sender, "command.invalid.args", "Invalid argument");
			return;
		}

		List<Entity> preSelect = selectMode.preSelectEntities(this);
		selectedEntitiesContext.setSelectedEntities(preSelect);

		char lastRead;

		if (promptReader.getRemainingLength() > 0) {
			promptReader.expect('[');

			class ArgumentContext {
				final SelectorArgument argument;
				final Object argumentValue;
				final boolean invert;

				public ArgumentContext(SelectorArgument argument, Object argumentValue, boolean invert) {
					this.argument = argument;
					this.argumentValue = argumentValue;
					this.invert = invert;
				}
			}

			List<ArgumentContext> argumentContextList = new ArrayList<>();

			do {
				String key = FishUTFStringArgumentType.readUTFString(promptReader, CharFilterUtil::charNotSelectorSpecial);
				promptReader.expect('=');
				boolean invert = prompt.peek() == '!';
				if (invert) {
					prompt.skip();
				}

				SelectorArgument argument = argumentNameMap.get(key);
				if (argument == null) {
					CommandSyntaxExceptionBuilder.raise(owner, sender, "command.invalid.args", "Invalid argument");
					return;
				}
				SelectorArgumentTypeAdapter adapter = argumentAdapterMap.get(argument.getAcceptableClass());
				if (adapter == null) {
					throw new CommandFailException(owner, "command.generic", "Incomplete plugin, contact server administrator for a fix.");
				}
				Object argumentValue = adapter.fromStringReader(this, promptReader);
				argumentContextList.add(new ArgumentContext(argument, argumentValue, invert));
			} while ((lastRead = promptReader.read()) == ',');

			if (lastRead != ']') {
				throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedSymbol().createWithContext(promptReader, "]");
			}

			Map<SelectorArgument, Boolean> parallelResults = new HashMap<>();

			// We need to move the limit argument to the last.
			argumentContextList.sort((a, b) -> {
				if (a.argument instanceof LimitArgument) {
					return 1;
				}
				if (b.argument instanceof LimitArgument) {
					return -1;
				}
				return 0;
			});

			try {
				for (ArgumentContext argumentContext : argumentContextList) {
					preSelect.removeIf((e) -> {
						boolean result;
						try {
							result = argumentContext.argument.matchEntity(this, e, argumentContext.argumentValue, parallelResults.get(argumentContext.argument));
						} catch (CommandSyntaxException ex) {
							throw new RuntimeException(ex);
						}
						parallelResults.put(argumentContext.argument, result);
						return argumentContext.invert == result;
					});
					selectedEntitiesContext.setSelectedEntities(preSelect);
				}
			} catch (RuntimeException re) {
				if (re.getCause() instanceof CommandSyntaxException e) {
					throw e;
				}
				throw re;
			}
		}
		List<Entity> postSelect = selectMode.postSelectEntities(this);
		if (postSelect != null) {
			selectedEntitiesContext.setSelectedEntities(postSelect);
		}
	}

	@Override
	public CompletableFuture<Suggestions> generateSuggestions() throws CommandSyntaxException, CommandFailException {
		return Suggestions.empty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends SelectorContext> T getContext(Class<T> clazz) {
		return (T) contextMap.get(clazz);
	}

	@Override
	public <T extends SelectorContext, U extends T> T getContext(Class<T> clazz, Class<U> defaultImplementation) {
		if (!contextMap.containsKey(clazz)) {
			try {
				U context = defaultImplementation.getConstructor(Selector.class).newInstance(this);
				contextMap.put(clazz, context);
			} catch (InvocationTargetException | InstantiationException e) {
				throw new IllegalStateException("Failed to initialize " + defaultImplementation + ", please check if there is anything wrong and report them to the developers first.", e);
			} catch (IllegalAccessException | NoSuchMethodException e) {
				throw new IllegalArgumentException("No constructor accepts selector in the context has been found. Make sure it is public and the class is not an interface or abstract class", e);
			}
		}
		return getContext(clazz);
	}

	public Sorter getSorter(String name) {
		return sorterMap.get(name);
	}
}
