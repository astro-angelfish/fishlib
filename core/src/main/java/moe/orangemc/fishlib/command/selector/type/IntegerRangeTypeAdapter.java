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
import moe.orangemc.fishlib.command.util.CommandSyntaxExceptionBuilder;

public class IntegerRangeTypeAdapter implements SelectorArgumentTypeAdapter<IntegerRangeArgumentType> {
	@Override
	public IntegerRangeArgumentType fromStringReader(Selector selector, StringReader raw) throws CommandSyntaxException {
		int min;
		if (com.mojang.brigadier.StringReader.isAllowedNumber(raw.peek()) && raw.peek() != '.') {
			min = raw.readInt();
		} else {
			min = Integer.MIN_VALUE;
		}
		if (raw.peek() != '.' && min != Integer.MIN_VALUE) {
			return new IntegerRangeArgumentType(min, min);
		}
		raw.expect('.');
		raw.expect('.');
		int max;
		if (com.mojang.brigadier.StringReader.isAllowedNumber(raw.peek()) && raw.peek() != '.') {
			max = raw.readInt();
		} else if (min == Integer.MIN_VALUE) {
			CommandSyntaxExceptionBuilder.raise(selector.getOwner(), selector.getSender(), "argument.int.expected", "Integer expected", raw);
			max = Integer.MAX_VALUE; // Unreachable code, but make compiler happy.
		} else {
			max = Integer.MAX_VALUE;
		}
		return new IntegerRangeArgumentType(min, max);
	}

	@Override
	public Class<IntegerRangeArgumentType> getProvidingClass() {
		return IntegerRangeArgumentType.class;
	}
}
