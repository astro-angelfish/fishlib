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

package moe.orangemc.fishlib.inventory;

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;
import moe.orangemc.fishlib.inventory.control.*;
import moe.orangemc.fishlib.inventory.handler.ButtonClickHandler;
import moe.orangemc.fishlib.inventory.handler.InventoryHandler;
import moe.orangemc.fishlib.inventory.handler.PlaceableFieldHandler;
import moe.orangemc.fishlib.inventory.handler.ProgressBarUpdater;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Customizable inventory
 */
@ShouldNotBeImplemented
public interface PluginInventory {
	/**
	 * Sets the border of the inventory
	 * @param inventoryBorder border of the inventory
	 */
	void setBorder(InventoryBorder inventoryBorder);

	/**
	 * Gets the border
	 * @return the border of the inventory
	 */
	InventoryBorder getBorder();

	/**
	 * Checks a bukkit inventory belongs to this inventory
	 * @param inventory the inventory to be checked
	 * @return true if the inventory belongs to this inventory
	 */
	boolean checkInventory(Inventory inventory);

	/**
	 * Opens inventory for player
	 * @see Player#openInventory(Inventory)
	 */
	void open();

	/**
	 * Adds a button with a handler
	 * @param x the position of the button to be placed.
	 * @param y the position of the button to be placed.
	 * @param buttonItem the icon of the button.
	 * @param handler the handler to handle button events.
	 * @return the button
	 */
	InventoryButton putButton(int x, int y, ItemStack buttonItem, ButtonClickHandler handler);

	/**
	 * Adds a button without a handler
	 * @param x the position of the button to be placed.
	 * @param y the position of the button to be placed.
	 * @param buttonItem the icon of the button
	 * @return the button
	 */
	InventoryButton putButton(int x, int y, ItemStack buttonItem);

	/**
	 * Adds a progress bar
	 * @param x the position of the progress bar to be placed.
	 * @param y the position of the progress bar to be placed.
	 * @param length the length of the progress bar
	 * @param emptyItem the item of the unfilled place in progress bar
	 * @param filledItem the item of filled place in progress bar
	 * @param updater the progress bar updater.
	 * @return the progress bar
	 */
	InventoryProgressBar putProgressBar(int x, int y, int length, ItemStack emptyItem, ItemStack filledItem, ProgressBarUpdater updater);

	/**
	 * Put a placeable field with a handler.
	 * @param x the position of the field to be placed.
	 * @param y the position of the field to be placed.
	 * @param width the width of the field.
	 * @param height the height of the field.
	 * @param handler the handler of the field.
	 * @return the placeable field.
	 */
	InventoryPlaceableField putPlaceableField(int x, int y, int width, int height, PlaceableFieldHandler handler);

	/**
	 * Put a placeable field with a handler.
	 * @param x the position of the field to be placed.
	 * @param y the position of the field to be placed.
	 * @param width the width of the field.
	 * @param height the height of the field.
	 * @return the placeable field.
	 */
	InventoryPlaceableField putPlaceableField(int x, int y, int width, int height);

	/**
	 * Get a list of slots managed by border
	 * @return a list of slots managed by border
	 */
	List<Integer> getBorderSlots();

	/**
	 * Get a list of controls
	 * @return a list of controls
	 */
	List<InventoryControl> getControls();

	/**
	 * Set the inventory handler
	 * @param handler handler of this inventory
	 */
	void setHandler(InventoryHandler handler);

	/**
	 * Find the control at the slot
	 * @param slot the slot to be checked
	 * @return the control at the slot, null if nothing found
	 */
	InventoryControl getControl(int slot);

	/**
	 * Find the control at the position
	 * @param x the x position to be checked
	 * @param y the y position to be checked
	 * @return the control at the position, null if nothing found
	 */
	InventoryControl getControl(int x, int y);

	void removeControl(InventoryControl control);
}
