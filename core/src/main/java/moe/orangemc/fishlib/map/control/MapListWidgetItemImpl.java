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

package moe.orangemc.fishlib.map.control;

import moe.orangemc.fishlib.map.handler.MapControlRenderer;
import moe.orangemc.fishlib.map.handler.listwidget.DefaultListWidgetItemRenderer;
import moe.orangemc.fishlib.util.Vector2i;

public class MapListWidgetItemImpl extends MapClickableImpl implements MapListWidgetItem {
	private static final MapControlRenderer DEFAULT_WIDGET_ITEM_RENDERER = new DefaultListWidgetItemRenderer();

	private String text;
	private boolean clicked = false;

	public MapListWidgetItemImpl(int y, String text) {
		super(new Vector2i(0, y));
		this.setText(text);
		this.setRenderer(DEFAULT_WIDGET_ITEM_RENDERER);
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public boolean isClicked() {
		return clicked;
	}

	@Override
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
}
