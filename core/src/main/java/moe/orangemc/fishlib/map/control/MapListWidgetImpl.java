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

import moe.orangemc.fishlib.map.handler.MapClickHandler;
import moe.orangemc.fishlib.map.handler.MapControlRenderer;
import moe.orangemc.fishlib.map.handler.listwidget.DefaultListWidgetClickHandler;
import moe.orangemc.fishlib.map.handler.listwidget.DefaultListWidgetRenderer;
import moe.orangemc.fishlib.util.Vector2i;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapListWidgetImpl extends MapClickableImpl implements MapListWidget {
	private static final MapControlRenderer WIDGET_DEFAULT_RENDERER = new DefaultListWidgetRenderer();
	private static final MapClickHandler WIDGET_DEFAULT_CLICK_HANDLER = new DefaultListWidgetClickHandler();
	private final List<MapListWidgetItemImpl> itemList = new ArrayList<>();
	private int selectedIndex = -1;

	public MapListWidgetImpl(Vector2i location) {
		super(location);

		setRenderer(WIDGET_DEFAULT_RENDERER);
		setClickHandler(WIDGET_DEFAULT_CLICK_HANDLER);
	}

	@Override
	public void addItem(String item) {
		Validate.notNull(item, "Cannot add a null thing.");

		int y = 0;
		for (MapListWidgetItemImpl mapListWidgetItem : this.itemList) {
			y += mapListWidgetItem.getSize().getY();
		}
		itemList.add(new MapListWidgetItemImpl(y, item));
	}

	@Override
	public void addItem(String item, MapClickHandler handler) {
		Validate.notNull(item, "Cannot add a null thing.");
		Validate.notNull(handler, "Click handler cannot be null.");

		int y = 0;
		for (MapListWidgetItemImpl mapListWidgetItem : this.itemList) {
			y += mapListWidgetItem.getSize().getY();
		}
		MapListWidgetItemImpl newItem = new MapListWidgetItemImpl(y, item);
		newItem.setClickHandler(handler);
		itemList.add(newItem);
	}

	@Override
	public List<MapListWidgetItem> getItems() {
		return Collections.unmodifiableList(itemList);
	}

	@Override
	public int getSelectedIndex() {
		return selectedIndex;
	}

	@Override
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
}
