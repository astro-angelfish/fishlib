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

/**
 * A list item in the list widget.
 */
@ShouldNotBeImplemented
public interface MapListWidgetItem extends MapClickable {
	/**
	 * Get the text of the item.
	 * @return the text of the item
	 */
	String getText();

	/**
	 * Set the text of the item.
	 * @param text the text to set
	 */
	void setText(String text);

	/**
	 * Get whether the item is clicked.
	 * @return whether the item is clicked
	 */
	boolean isClicked();

	/**
	 * <b>Should be for internal usage</b>
	 * <br>
	 * Set whether the item is clicked.
	 * @param clicked whether the item is clicked
	 */
	void setClicked(boolean clicked);
}
