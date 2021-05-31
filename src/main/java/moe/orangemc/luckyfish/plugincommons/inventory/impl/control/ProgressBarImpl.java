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

import moe.orangemc.luckyfish.plugincommons.inventory.control.ProgressBar;
import moe.orangemc.luckyfish.plugincommons.inventory.control.handler.ProgressBarUpdater;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * The progress bar
 */
public class ProgressBarImpl extends ControlImpl implements ProgressBar {
    private final int length;
    private ItemStack emptyItem;
    private ItemStack filledItem;
    private final ProgressBarUpdater updater;

    public ProgressBarImpl(int length, ItemStack emptyItem, ItemStack filledItem, ProgressBarUpdater updater) {
        this.length = length;
        this.emptyItem = emptyItem;
        this.filledItem = filledItem;
        this.updater = updater;
    }

    @Override
    public void onAdd(Inventory inventory, int startIndex) {
        super.onAdd(inventory, startIndex);
        if (startIndex % 9 + length > 9) {
            throw new IllegalArgumentException("Progress bar is too long");
        }

        for (int a = 0; a < length; a ++) {
            inventory.setItem(a + startIndex, emptyItem);
        }
    }

    @Override
    public void update() {
        if (getInventoryPutIn() == null) {
            return;
        }
        double rate = 0;
        if (updater != null) {
            rate = updater.update((Player) getInventoryPutIn().getHolder(), this);
        }
        for (int i = 0; i < length; i ++) {
            double slotRate = (i + 0.0) / length;
            if (slotRate < rate) {
                getInventoryPutIn().setItem(getStartIndex() + i, filledItem);
            } else {
                getInventoryPutIn().setItem(getStartIndex() + i, emptyItem);
            }
        }
    }

    @Override
    public ItemStack getEmptyItem() {
        return emptyItem;
    }

    @Override
    public ItemStack getFilledItem() {
        return filledItem;
    }

	@Override
	public void setEmptyItem(ItemStack item) {
		Validate.notNull(item, "emptyItem cannot be null");
		this.emptyItem = item.clone();
		this.update();
	}

	@Override
	public void setFilledItem(ItemStack item) {
		Validate.notNull(item, "filledItem cannot be null");
		this.filledItem = item.clone();
		this.update();
	}
}
