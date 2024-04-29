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

import java.util.List;

/**
 * A list widget on the map.
 */
@ShouldNotBeImplemented
public interface MapListWidget extends MapClickable {
	/**
	 * Add an item to the list.
	 * @param item the item to add
	 */
	void addItem(String item);

	/**
	 * Add an item to the list.
	 * @param item the item to add
	 * @param handler the click handler for the item
	 */
	void addItem(String item, MapClickHandler handler);

	/**
	 * Get the items in the list.
	 * @return the items in the list
	 */
	List<MapListWidgetItem> getItems();

	/**
	 * Get the selected index.
	 * @return the selected index
	 */
	int getSelectedIndex();

	/**
	 * <b>Should for internal usage</b>
	 * <br>
	 * Set the selected index.
	 *
	 * @param idx the index to set
	 */
	void setSelectedIndex(int idx);
}
