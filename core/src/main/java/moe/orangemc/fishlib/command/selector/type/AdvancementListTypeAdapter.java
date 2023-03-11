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

package moe.orangemc.fishlib.command.selector.type;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import moe.orangemc.fishlib.command.CommandFailException;
import moe.orangemc.fishlib.command.selector.Selector;
import moe.orangemc.fishlib.command.util.CommandSyntaxExceptionBuilder;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancementListTypeAdapter implements SelectorArgumentTypeAdapter<AdvancementListArgumentType> {
	@Override
	public AdvancementListArgumentType fromStringReader(Selector selector, StringReader prompt) throws CommandSyntaxException {
		List<AdvancementListArgumentType.AdvancementArgumentType> advancementArgumentTypes = new ArrayList<>();

		prompt.expect('{');
		char lastPeek;
		do {
			String namespace = "";
			StringBuilder name = new StringBuilder(prompt.readUnquotedString());
			if (prompt.peek() == ':') {
				namespace = name.toString();
				prompt.skip();
				name = new StringBuilder(prompt.readUnquotedString());
			}
			while (prompt.peek() == '/') {
				prompt.skip();
				name.append("/").append(prompt.readUnquotedString());
			}
			if (namespace.isBlank()) {
				namespace = NamespacedKey.MINECRAFT;
			}

			NamespacedKey achievementKey = NamespacedKey.fromString(namespace + ":" + name);
			if (achievementKey == null) {
				throw new CommandFailException(selector.getOwner(), "command.notfound", "Target not found.");
			}
			Advancement advancement = Bukkit.getAdvancement(achievementKey);
			if (advancement == null) {
				CommandSyntaxExceptionBuilder.raise(selector.getOwner(), selector.getSender(), "command.advancement.notfound", "Advancement " + namespace + ":" + name + " is not found.", namespace, name);
			}

			if (prompt.peek() == '{') {
				Map<String, Boolean> criteriaMap = new HashMap<>();
				while (prompt.peek() != '}') {
					String criteriaName = prompt.readUnquotedString();
					prompt.expect('=');
					boolean invertCriteria = !prompt.readBoolean();

					criteriaMap.put(criteriaName, invertCriteria);

					if (prompt.peek() != '}') {
						prompt.expect(',');
					}
				}
				prompt.expect('}');

				advancementArgumentTypes.add(new AdvancementListArgumentType.AdvancementArgumentType(advancement, criteriaMap));
			} else {
				advancementArgumentTypes.add(new AdvancementListArgumentType.AdvancementArgumentType(advancement, !prompt.readBoolean()));
			}
		} while ((lastPeek = prompt.read()) == ',');

		if (lastPeek != '}') {
			throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedSymbol().createWithContext(prompt, "}");
		}
		return new AdvancementListArgumentType(advancementArgumentTypes);
	}

	@Override
	public Class<AdvancementListArgumentType> getProvidingClass() {
		return AdvancementListArgumentType.class;
	}
}
