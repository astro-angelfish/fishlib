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

package moe.orangemc.fishlib.command.selector.argument.player;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import moe.orangemc.fishlib.command.selector.Selector;
import moe.orangemc.fishlib.command.selector.SelectorArgument;
import moe.orangemc.fishlib.command.selector.type.IntegerRangeArgumentType;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class LevelArgument implements SelectorArgument<IntegerRangeArgumentType> {
	@Override
	public String getName() {
		return "level";
	}

	@Override
	public boolean matchEntity(Selector selector, Entity entity, IntegerRangeArgumentType value, Boolean parallelResult) throws CommandSyntaxException {
		if (!(entity instanceof Player p)) {
			return false;
		}

		return value.matches(p.getLevel()) && (parallelResult == null || parallelResult);
	}

	@Override
	public Class<IntegerRangeArgumentType> getAcceptableClass() {
		return null;
	}
}
