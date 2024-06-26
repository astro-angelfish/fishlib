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

package moe.orangemc.fishlib.map.handler;

import moe.orangemc.fishlib.annotation.CanImplement;
import moe.orangemc.fishlib.map.control.MapControl;
import moe.orangemc.fishlib.util.Vector2i;

import org.bukkit.entity.Player;

/**
 * A handler for map click events.
 */
@CanImplement
public interface MapClickHandler {
	/**
	 * Called when a player clicks on a control.
	 *
	 * @param player the player who clicked
	 * @param clickedControl the control that was clicked
	 * @param rightClick whether the player right-clicked
	 * @param clickedLocation the location on the map that was clicked
	 */
	void onClick(Player player, MapControl clickedControl, boolean rightClick, Vector2i clickedLocation);
}
