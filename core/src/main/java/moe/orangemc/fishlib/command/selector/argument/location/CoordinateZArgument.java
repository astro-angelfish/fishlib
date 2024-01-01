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

package moe.orangemc.fishlib.command.selector.argument.location;

import moe.orangemc.fishlib.command.selector.Selector;
import moe.orangemc.fishlib.command.selector.SelectorArgument;
import moe.orangemc.fishlib.command.selector.context.LocationContext;

import org.bukkit.entity.Entity;

import java.util.Objects;

public class CoordinateZArgument implements SelectorArgument<Double> {
	@Override
	public String getName() {
		return "z";
	}

	@Override
	public boolean matchEntity(Selector selector, Entity entity, Double value, Boolean parallelResult) {
		if (Objects.equals(parallelResult, true)) {
			return false;
		}

		LocationContext locationContext = selector.getContext(LocationContext.class);

		if (entity.getWorld() != locationContext.getBaseLocation().getWorld()) {
			return false;
		}

		locationContext.setZ(value);

		if (Math.round(value) == value) {
			return entity.getLocation().getBlockZ() == (int) Math.round(value);
		}
		return Math.abs(entity.getLocation().getZ() - value) < 0.0001;
	}

	@Override
	public Class<Double> getAcceptableClass() {
		return Double.class;
	}
}
