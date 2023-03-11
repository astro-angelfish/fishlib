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

package moe.orangemc.fishlib.command.selector.argument.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import moe.orangemc.fishlib.command.selector.Selector;
import moe.orangemc.fishlib.command.selector.SelectorArgument;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;

public class TypeArgument implements SelectorArgument<NamespacedKey> {
	@Override
	public String getName() {
		return "type";
	}

	@Override
	public boolean matchEntity(Selector selector, Entity entity, NamespacedKey value, Boolean parallelResult) throws CommandSyntaxException {
		if (parallelResult != null) {
			return false;
		}

		return entity.getType().getKey().equals(value);
	}

	@Override
	public Class<NamespacedKey> getAcceptableClass() {
		return NamespacedKey.class;
	}
}
