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

package moe.orangemc.fishlib.inventory.control;

import org.bukkit.inventory.Inventory;

/**
 * Custom control of inventory
 */
public abstract class InventoryControlImpl implements InventoryControl {
    private Inventory inventoryPutIn = null;
    private int startIndex = -1;

    public void onAdd(Inventory inventory, int startIndex) {
        inventoryPutIn = inventory;
        this.startIndex = startIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    protected Inventory getInventoryPutIn() {
        return inventoryPutIn;
    }
}
