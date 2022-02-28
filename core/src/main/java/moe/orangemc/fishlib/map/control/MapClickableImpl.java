/*
 * FishLib, a Bukkit development library
 * Copyright (C) Lucky_fish0w0
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

import moe.orangemc.fishlib.map.handler.DefaultClickHandler;
import moe.orangemc.fishlib.map.handler.MapClickHandler;
import moe.orangemc.fishlib.utils.Vector2i;

import org.bukkit.entity.Player;

public class MapClickableImpl extends MapControlImpl implements MapClickable {
	private MapClickHandler clickHandler = new DefaultClickHandler();

	public MapClickableImpl(Vector2i location) {
		super(location);
	}

	@Override
	public void setClickHandler(MapClickHandler clickHandler) {
		this.clickHandler = clickHandler;
	}

	public void onClick(Player player, MapControl clickedControl, boolean isRightClick, Vector2i clickedLocation) {
		clickHandler.onClick(player, clickedControl, isRightClick, clickedLocation);
	}
}
