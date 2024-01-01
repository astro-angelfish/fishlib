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

package moe.orangemc.fishlib.command.selector.context;

import moe.orangemc.fishlib.command.selector.Selector;
import moe.orangemc.fishlib.command.selector.util.WorldHelper;

import org.bukkit.Location;

public class LocationContextImpl implements LocationContext {
	private final Location baseLocation;
	private double x = Double.NaN;
	private double y = Double.NaN;
	private double z = Double.NaN;

	public LocationContextImpl(Selector selector) {
		baseLocation = WorldHelper.getLocation(selector.getSender());
	}

	private void checkAndUpdate() {
		if (!Double.isNaN(x) && !Double.isNaN(y) && !Double.isNaN(z)) {
			baseLocation.setX(x);
			baseLocation.setY(y);
			baseLocation.setZ(z);
		}
	}

	@Override
	public void setX(double x) {
		this.x = x;
		this.checkAndUpdate();
	}

	@Override
	public void setY(double y) {
		this.y = y;
		this.checkAndUpdate();
	}

	@Override
	public void setZ(double z) {
		this.z = z;
		this.checkAndUpdate();
	}

	@Override
	public Location getBaseLocation() {
		return baseLocation;
	}
}
