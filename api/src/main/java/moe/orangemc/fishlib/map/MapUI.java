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
import moe.orangemc.fishlib.map.control.MapClickable;
import moe.orangemc.fishlib.map.control.MapControl;
import moe.orangemc.fishlib.map.control.MapListWidget;
import moe.orangemc.fishlib.map.handler.MapRenderer;
import moe.orangemc.fishlib.util.Vector2i;

@ShouldNotBeImplemented
public interface MapUI {
	void setRenderer(MapRenderer renderer);

	MapControl addControl(Vector2i location, Vector2i size);

	MapClickable addClickable(Vector2i location, Vector2i size);

	MapListWidget addListWidget(Vector2i location, Vector2i size);

	Vector2i getSize();
}
