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

import org.apache.commons.lang3.Validate;

public class IntegerRangeArgumentType implements ComplexSelectorArgumentType<Integer> {
	private final int min;
	private final int max;

	public IntegerRangeArgumentType(int min, int max) {
		Validate.isTrue(min <= max, "The minimum value should be smaller than max value.");

		this.min = min;
		this.max = max;
	}

	@Override
	public boolean matches(Integer val) {
		return val >= min && val <= max;
	}
}
