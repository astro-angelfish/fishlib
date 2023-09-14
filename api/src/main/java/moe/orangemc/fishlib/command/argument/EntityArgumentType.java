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

package moe.orangemc.fishlib.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import moe.orangemc.fishlib.FishLibrary;
import moe.orangemc.fishlib.command.selector.Selector;
import moe.orangemc.fishlib.command.util.FishLibCommandReader;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EntityArgumentType implements ArgumentType<Entity[]>, SuggestionProvider<CommandSender> {
	private static final List<String> EXAMPLES = Arrays.asList("Astro_angelfish", "11451419-1981-4011-4514-19198101", "@a", "@e[type=fish]");

	@Override
	public Entity[] parse(StringReader reader) throws CommandSyntaxException {
		if (!(reader instanceof FishLibCommandReader fishReader)) {
			return new Entity[0];
		}

		Selector selector = FishLibrary.getCommandHelper(fishReader.getOwner()).getSelectorManager().createSelector(fishReader.getSender());
		selector.updatePrompt(reader);
		return selector.selectEntities().toArray(new Entity[0]);
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().startsWith(builder.getRemaining().toLowerCase())) {
				builder.suggest(p.getName());
			}
		}
		return builder.buildFuture();
	}

	@Override
	public Collection<String> getExamples() {
		return EXAMPLES;
	}

	@Override
	public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSender> context, SuggestionsBuilder builder) {
		return listSuggestions(context, builder);
	}
}
