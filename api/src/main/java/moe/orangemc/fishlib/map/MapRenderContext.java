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

@ShouldNotBeImplemented
public interface MapRenderContext {
	void drawText(Vector2i location, String fontName, String text, Color color, int size);
	void drawImage(Vector2i location, Image image);
	void setColor(Vector2i location2I, Color color);
	void setColor(Vector2i from, Vector2i to, Color color);
}
