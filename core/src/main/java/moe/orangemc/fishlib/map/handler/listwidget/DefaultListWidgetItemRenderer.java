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

package moe.orangemc.fishlib.map.handler.listwidget;

import moe.orangemc.fishlib.map.MapRenderContext;
import moe.orangemc.fishlib.map.control.MapControl;
import moe.orangemc.fishlib.map.control.MapListWidgetItemImpl;
import moe.orangemc.fishlib.map.handler.MapControlRenderer;
import moe.orangemc.fishlib.util.Vector2i;

import java.awt.*;

public class DefaultListWidgetItemRenderer implements MapControlRenderer {
	@Override
	public void render(MapControl control, MapRenderContext context) {
		if (!(control instanceof MapListWidgetItemImpl listWidgetItem)) {
			throw new IllegalArgumentException("The control to be rendered is not a widget item");
		}
		context.drawText(new Vector2i(0, 0), "Arial", listWidgetItem.getText(), Color.BLACK, 10);
	}
}
