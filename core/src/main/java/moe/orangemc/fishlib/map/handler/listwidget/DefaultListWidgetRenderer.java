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

package moe.orangemc.fishlib.map.handler.listwidget;

import moe.orangemc.fishlib.map.MapRenderContext;
import moe.orangemc.fishlib.map.control.MapControl;
import moe.orangemc.fishlib.map.control.MapListWidgetImpl;
import moe.orangemc.fishlib.map.control.MapListWidgetItem;
import moe.orangemc.fishlib.map.control.MapListWidgetItemImpl;
import moe.orangemc.fishlib.map.handler.MapControlRenderer;

public class DefaultListWidgetRenderer implements MapControlRenderer {
	@Override
	public void render(MapControl control, MapRenderContext context) {
		if (control instanceof MapListWidgetImpl widget) {
			for (MapListWidgetItem widgetItem : widget.getItems()) {
				if (widgetItem instanceof MapListWidgetItemImpl item) {
					item.render(context);
				} else {
					throw new IllegalStateException("List widget contains non-widget item: " + widgetItem + ", Is any plugin trying to implement the classes should not be implemented?");
				}
			}
		} else {
			throw new IllegalArgumentException("Unable to render a control that is not ListWidget");
		}
	}
}
