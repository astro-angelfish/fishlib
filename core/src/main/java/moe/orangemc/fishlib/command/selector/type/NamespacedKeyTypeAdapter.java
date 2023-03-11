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
import moe.orangemc.fishlib.command.selector.Selector;

import org.bukkit.NamespacedKey;

public class NamespacedKeyTypeAdapter implements SelectorArgumentTypeAdapter<NamespacedKey> {
	@Override
	public NamespacedKey fromStringReader(Selector selector, StringReader prompt) throws CommandSyntaxException {
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

		return NamespacedKey.fromString(namespace + ":" + name);
	}

	@Override
	public Class<NamespacedKey> getProvidingClass() {
		return NamespacedKey.class;
	}
}
