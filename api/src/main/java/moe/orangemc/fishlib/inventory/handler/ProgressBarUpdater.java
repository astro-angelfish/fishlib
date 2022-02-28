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

package moe.orangemc.fishlib.inventory.handler;

import moe.orangemc.fishlib.annotation.CanImplement;
import moe.orangemc.fishlib.inventory.control.InventoryProgressBar;

import org.bukkit.entity.Player;

/**
 * The updater of progress bar
 */
@CanImplement
public interface ProgressBarUpdater {
    /**
     * Calls when a progress bar is being updated
     * @param who the owner of the inventory which the progress bar have been put in
     * @param inventoryProgressBar the progressbar updated
     * @return rate of the progress bar, 0 is empty and 1 is full
     */
    double update(Player who, InventoryProgressBar inventoryProgressBar);
}
