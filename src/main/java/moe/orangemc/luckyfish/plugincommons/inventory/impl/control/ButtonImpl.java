/*
 * Plugin Commons, a Bukkit development library
 * Copyright (C) Lucky_fish0w0
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package moe.orangemc.luckyfish.plugincommons.inventory.impl.control;

import moe.orangemc.luckyfish.plugincommons.inventory.control.Button;
import moe.orangemc.luckyfish.plugincommons.inventory.control.handler.ButtonClickHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Clickable button, triggers handler when player clicks it.
 */
public class ButtonImpl extends ControlImpl implements Button {
    private final ItemStack item;
    private final ButtonClickHandler handler;
    public ButtonImpl(ItemStack item) {
        this(item, null);
    }
    public ButtonImpl(ItemStack item, ButtonClickHandler handler) {
        this.item = item;
        this.handler = handler;
    }

    public ButtonClickHandler getHandler() {
        return handler;
    }

    @Override
    public void onAdd(Inventory inventory, int startIndex) {
        super.onAdd(inventory, startIndex);
        inventory.setItem(startIndex, item);
    }

	@Override
	public void setItem(ItemStack item) {
		getInventoryPutIn().setItem(getStartIndex(), item);
	}

	@Override
    public ItemStack getItem() {
        return item;
    }
}
