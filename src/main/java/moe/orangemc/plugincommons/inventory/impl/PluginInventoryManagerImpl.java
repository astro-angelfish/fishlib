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

package moe.orangemc.plugincommons.inventory.impl;

import moe.orangemc.plugincommons.inventory.PluginInventory;
import moe.orangemc.plugincommons.inventory.PluginInventoryManager;
import moe.orangemc.plugincommons.inventory.control.ProgressBar;
import moe.orangemc.plugincommons.inventory.impl.listener.InventoryListener;
import org.apache.commons.lang.Validate;
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
    private final List<ProgressBar> progressBarsToUpdate = new ArrayList<>();

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

    public void registerProgressBar(ProgressBar progressBar) {
        progressBarsToUpdate.add(progressBar);
    }

    public void unregisterProgressBar(ProgressBar progressBar) {
        progressBarsToUpdate.remove(progressBar);
    }

    private void updateProgressBar() {
        for (ProgressBar progressBar : progressBarsToUpdate) {
            progressBar.update();
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
