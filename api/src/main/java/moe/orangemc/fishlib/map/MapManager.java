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

package moe.orangemc.fishlib.map;

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;
import moe.orangemc.fishlib.util.Vector2i;

import org.bukkit.entity.ItemFrame;

import java.util.Map;

/**
 * A manager for managing map ui.
 *
 * @see MapUI
 * @see ItemFrame
 */
@ShouldNotBeImplemented
public interface MapManager {
	/**
	 * Create a map ui with the given frames.
	 * @param framesToDisplay the frames to display, with matching coordinates
	 * @return the created map ui
	 */
	MapUI createMapUI(Map<Vector2i, ItemFrame> framesToDisplay);

	/**
	 * Create a map ui with the given start frame and max size.
	 * <br>
	 * This method would automatically calculate the frames to display, until the max size is reached.
	 *
	 * @param start the start frame to display
	 * @param maxSize the max size of the map ui
	 * @return the created map ui
	 */
	MapUI createMapUI(ItemFrame start, Vector2i maxSize);

	/**
	 * Destroy the given map ui.
	 * @param ui the map ui to destroy
	 */
	void destroyMapUI(MapUI ui);

	/**
	 * Destroy the map ui of the given part.
	 * @param part the part to destroy
	 */
	void destroyMapUI(ItemFrame part);
}
