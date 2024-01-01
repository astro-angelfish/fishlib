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

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * The manager of the custom inventory
 * <br>
 * Fetch this instance with {@link moe.orangemc.fishlib.FishLibrary#getPluginInventoryManager(Plugin)}
 *
 * @see PluginInventory
 */
@ShouldNotBeImplemented
public interface PluginInventoryManager {
	/**
	 * Creates an inventory
	 *
	 * @param player the owner
	 * @param height height of the inventory
	 * @return the inventory created
	 */
	PluginInventory createInventory(Player player, String title, int height);

	/**
	 * Destroies an inventory
	 *
	 * @param inventory the inventory to be destoried
	 */
	void destroyInventory(PluginInventory inventory);

	/**
	 * Get a list of inventories
	 *
	 * @return a list of inventories
	 */
	List<PluginInventory> getInventoryList();

	/**
	 * Get a PluginInventory by bukkit inventory
	 *
	 * @param inventory the bukkit inventory
	 * @return the PluginInventory instance
	 */
	PluginInventory getInventoryByBukkitInventory(Inventory inventory);
}
