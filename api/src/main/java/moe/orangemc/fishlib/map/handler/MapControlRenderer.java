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
import moe.orangemc.fishlib.map.MapRenderContext;
import moe.orangemc.fishlib.map.control.MapControl;

/**
 * A renderer for map controls.
 */
@CanImplement
public interface MapControlRenderer {
	/**
	 * Render the control.
	 * <br>
	 * The context has been mapped to the control's location.
	 *
	 * @param control the control to render
	 * @param context the context to render in
	 */
	void render(MapControl control, MapRenderContext context);
}
