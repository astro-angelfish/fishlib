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

import moe.orangemc.fishlib.map.control.MapControl;
import moe.orangemc.fishlib.map.control.MapListWidgetImpl;
import moe.orangemc.fishlib.map.control.MapListWidgetItem;
import moe.orangemc.fishlib.map.control.MapListWidgetItemImpl;
import moe.orangemc.fishlib.map.handler.MapClickHandler;
import moe.orangemc.fishlib.utils.Vector2i;

import org.bukkit.entity.Player;

import java.util.List;

public class DefaultListWidgetClickHandler implements MapClickHandler {
	@Override
	public void onClick(Player player, MapControl clickedControl, boolean rightClick, Vector2i clickedLocation) {
		if (clickedControl instanceof MapListWidgetImpl widget) {
			List<MapListWidgetItem> items = widget.getItems();

			boolean found = false;
			for (int i = 0; i < items.size(); i++) {
				MapListWidgetItem item = items.get(i);
				if (!found && item.getLocation().getY() < clickedLocation.getY() && item.getLocation().getY() + item.getSize().getY() > clickedLocation.getY()) {
					if (item instanceof MapListWidgetItemImpl implementedItem) {
						implementedItem.onClick(player, clickedControl, rightClick, clickedLocation.clone().subtract(item.getLocation()));
						widget.setSelectedIndex(i);
						item.setClicked(true);
						found = true;
					} else {
						throw new IllegalStateException("List widget contains non-widget item: " + item + ", Is any plugin trying to implement the classes should not be implemented?");
					}
				} else {
					item.setClicked(false);
				}
			}
		} else {
			throw new IllegalArgumentException("Unable to render a control that is not ListWidget");
		}
	}
}
