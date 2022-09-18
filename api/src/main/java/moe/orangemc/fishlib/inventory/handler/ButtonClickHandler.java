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

package moe.orangemc.fishlib.inventory.handler;

import moe.orangemc.fishlib.annotation.CanImplement;
import moe.orangemc.fishlib.inventory.control.InventoryButton;

import org.bukkit.entity.Player;

/**
 * The handler handles the button events.
 */
@CanImplement
public interface ButtonClickHandler {
    /**
     * Calls when a player clicks that button
     * @param whoClicked the player clicked the button
     * @param inventoryButtonClicked the button clicked.
     */
    void onClick(Player whoClicked, InventoryButton inventoryButtonClicked, boolean leftClick);
}
