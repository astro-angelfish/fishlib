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

package moe.orangemc.fishlib.map.control;

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;
import moe.orangemc.fishlib.map.handler.MapClickHandler;

/**
 * A control that can be clicked on the map.
 * <br>
 * Fishlib uses ray-tracing to detect the click on the map.
 */
@ShouldNotBeImplemented
public interface MapClickable extends MapControl {
	/**
	 * Set the click handler for this clickable.
	 *
	 * @param clickHandler the click handler to set
	 */
	void setClickHandler(MapClickHandler clickHandler);
}
