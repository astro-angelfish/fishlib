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

package moe.orangemc.fishlib.command.selector.argument.trait.sorter;

import moe.orangemc.fishlib.command.selector.Selector;
import moe.orangemc.fishlib.command.selector.util.Sorter;

public class ArbitrarySorter implements Sorter {
	@Override
	public String getName() {
		return "arbitrary";
	}

	@Override
	public void sort(Selector selector) {
		// Do nothing.
	}
}
