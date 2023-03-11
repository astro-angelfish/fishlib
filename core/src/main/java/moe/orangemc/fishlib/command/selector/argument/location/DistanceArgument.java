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
import moe.orangemc.fishlib.command.selector.type.IntegerRangeArgumentType;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.Objects;

public class DistanceArgument implements SelectorArgument<IntegerRangeArgumentType> {
	@Override
	public String getName() {
		return "distance";
	}

	@Override
	public boolean matchEntity(Selector selector, Entity entity, IntegerRangeArgumentType value, Boolean parallelResult) {
		if (Objects.equals(parallelResult, true)) {
			return false;
		}

		Location baseLocation = selector.getContext(LocationContext.class).getBaseLocation();
		return baseLocation.getWorld() == entity.getWorld() && value.matches((int) Math.ceil(baseLocation.distance(entity.getLocation())));
	}

	@Override
	public Class<IntegerRangeArgumentType> getAcceptableClass() {
		return IntegerRangeArgumentType.class;
	}
}
