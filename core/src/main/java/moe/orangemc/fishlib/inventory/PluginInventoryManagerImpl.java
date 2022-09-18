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

import moe.orangemc.fishlib.inventory.control.InventoryProgressBar;
import moe.orangemc.fishlib.inventory.listener.InventoryListener;
import org.apache.commons.lang3.Validate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The inventory manager
 */
public class PluginInventoryManagerImpl implements PluginInventoryManager {
	private final List<PluginInventory> inventoryList = new ArrayList<>();
    private final List<InventoryProgressBar> inventoryProgressBarsToUpdate = new ArrayList<>();

    public PluginInventoryManagerImpl(Plugin plugin) {
	    Bukkit.getScheduler().runTaskTimer(plugin, this::updateProgressBar, 0, 0);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), plugin);
    }

    @Override
    public PluginInventory createInventory(Player player, String title, int height) {
	    Validate.notNull(player, "player cannot be null");
	    Validate.notNull(title, "title cannot be null");

        PluginInventory inventory = new PluginInventoryImpl(this, title, player, height);
        inventoryList.add(inventory);
        return inventory;
    }

    @Override
    public void destroyInventory(PluginInventory inventory) {
	    Validate.notNull(inventory, "inventory cannot be null");

        inventoryList.remove(inventory);
	    ((PluginInventoryImpl) inventory).onDestroy();
    }

    @Override
    public List<PluginInventory> getInventoryList() {
        return Collections.unmodifiableList(inventoryList);
    }

    public void registerProgressBar(InventoryProgressBar inventoryProgressBar) {
        inventoryProgressBarsToUpdate.add(inventoryProgressBar);
    }

    public void unregisterProgressBar(InventoryProgressBar inventoryProgressBar) {
        inventoryProgressBarsToUpdate.remove(inventoryProgressBar);
    }

    private void updateProgressBar() {
        for (InventoryProgressBar inventoryProgressBar : inventoryProgressBarsToUpdate) {
            inventoryProgressBar.update();
        }
    }

    @Override
    public PluginInventory getInventoryByBukkitInventory(Inventory inventory) {
        for (PluginInventory pluginInventory : inventoryList) {
            if (pluginInventory.checkInventory(inventory)) {
                return pluginInventory;
            }
        }

        return null;
    }
}
