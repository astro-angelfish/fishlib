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

import java.awt.*;

/**
 * A context for rendering on a map.
 */
@ShouldNotBeImplemented
public interface MapRenderContext {
	/**
	 * Draw text on the map.
	 *
	 * @param location the location to draw the text
	 * @param fontName the font name to use, must be installed on the server
	 * @param text the text to draw
	 * @param color the color of the text
	 * @param size the size of the text
	 */
	void drawText(Vector2i location, String fontName, String text, Color color, int size);

	/**
	 * Draw an image on the map.
	 *
	 * @param location the location to draw the image
	 * @param image the image to draw
	 */
	void drawImage(Vector2i location, Image image);

	/**
	 * Set the color of a pixel on the map.
	 *
 	 * @param location the location of the pixel
	 * @param color the color to set
	 */
	void setColor(Vector2i location, Color color);

	/**
	 * Set the color of a rectangle on the map.
	 *
	 * @param from the top left corner of the rectangle
	 * @param to the bottom right corner of the rectangle
	 * @param color the color to set
	 */
	void setColor(Vector2i from, Vector2i to, Color color);
}
