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
import moe.orangemc.fishlib.map.handler.MapControlRenderer;
import moe.orangemc.fishlib.util.Vector2i;

/**
 * A control on the map.
 */
@ShouldNotBeImplemented
public interface MapControl {
	/**
	 * Get the location of the control.
	 * @return the location of the control
	 */
	Vector2i getLocation();

	/**
	 * Get the size of the control.
	 * @return the size of the control
	 */
	Vector2i getSize();

	/**
	 * Move the control to the given location.
	 * @param location the location to move to
	 */
	void moveTo(Vector2i location);

	/**
	 * Resize the control to the given size.
	 * @param newSize the new size to resize to
	 */
	void resize(Vector2i newSize);

	/**
	 * Set the renderer for this control.
	 * @param handler the renderer to set
	 */
	void setRenderer(MapControlRenderer handler);
}
