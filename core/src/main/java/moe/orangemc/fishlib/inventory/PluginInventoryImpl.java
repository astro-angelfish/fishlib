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

package moe.orangemc.fishlib.inventory;

import moe.orangemc.fishlib.inventory.control.*;
import moe.orangemc.fishlib.inventory.handler.ButtonClickHandler;
import moe.orangemc.fishlib.inventory.handler.InventoryHandler;
import moe.orangemc.fishlib.inventory.handler.PlaceableFieldHandler;
import moe.orangemc.fishlib.inventory.handler.ProgressBarUpdater;
import org.apache.commons.lang3.Validate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PluginInventoryImpl implements PluginInventory {
    private final PluginInventoryManagerImpl manager;
    private final Inventory internalInventory;
    private final Player player;
    private InventoryBorder inventoryBorder = null;
    private InventoryHandler handler = null;

    private final List<InventoryControl> inventoryControls = new ArrayList<>();
    private final List<Integer> borderSlots = new ArrayList<>();

    public PluginInventoryImpl(PluginInventoryManagerImpl manager, String title, Player player, int height) {
        this.manager = manager;
        this.player = player;
        internalInventory = Bukkit.createInventory(player, height * 9, title);
    }

    @Override
    public void setBorder(InventoryBorder inventoryBorder) {
	    Validate.notNull(inventoryBorder, "border cannot be null");
        this.inventoryBorder = inventoryBorder;
        borderSlots.clear();
        borderSlots.addAll(inventoryBorder.putBorder(internalInventory));
    }

    @Override
    public InventoryBorder getBorder() {
        return inventoryBorder;
    }

    @Override
    public boolean checkInventory(Inventory inventory) {
        if (inventory == null) {
            return false;
        }

        return internalInventory.hashCode() == inventory.hashCode();
    }

    @Override
    public void open() {
        player.openInventory(this.internalInventory);
    }

	@Override
	public InventoryButton putButton(int x, int y, ItemStack buttonItem, ButtonClickHandler handler) {
    	Validate.notNull(buttonItem, "buttonItem cannot be null");

    	InventoryButtonImpl button = new InventoryButtonImpl(buttonItem, handler);
	    add(x, y, button);
	    return button;
    }

	@Override
	public InventoryButton putButton(int x, int y, ItemStack buttonItem) {
		Validate.notNull(buttonItem, "buttonItem cannot be null");

		InventoryButtonImpl button = new InventoryButtonImpl(buttonItem, null);
		add(x, y, button);
		return button;
	}

	@Override
	public InventoryProgressBar putProgressBar(int x, int y, int length, ItemStack emptyItem, ItemStack filledItem, ProgressBarUpdater updater) {
    	Validate.notNull(emptyItem, "emptyItem cannot be null");
    	Validate.notNull(filledItem, "filledItem cannot be null");

    	InventoryProgressBarImpl progressBar = new InventoryProgressBarImpl(length, emptyItem, filledItem, updater);
    	add(x, y, progressBar);
    	return progressBar;
    }

	@Override
	public InventoryPlaceableField putPlaceableField(int x, int y, int width, int height, PlaceableFieldHandler handler) {
	    InventoryPlaceableFieldImpl placeableField = new InventoryPlaceableFieldImpl(width, height, handler);
	    add(x, y, placeableField);
	    return placeableField;
    }

	@Override
	public InventoryPlaceableField putPlaceableField(int x, int y, int width, int height) {
		InventoryPlaceableFieldImpl placeableField = new InventoryPlaceableFieldImpl(width, height, null);
		add(x, y, placeableField);
		return placeableField;
	}

    private void add(int x, int y, InventoryControlImpl control) {
        if (borderSlots.contains(x + y * 9)) {
            borderSlots.remove(Integer.valueOf(x + y * 9));
        }

        inventoryControls.add(control);
        if (control instanceof InventoryProgressBarImpl) {
            manager.registerProgressBar((InventoryProgressBar) control);
        }
        control.onAdd(internalInventory, x + y * 9);
    }

    @Override
    public List<Integer> getBorderSlots() {
        return Collections.unmodifiableList(borderSlots);
    }

    @Override
    public List<InventoryControl> getControls() {
        return Collections.unmodifiableList(inventoryControls);
    }

    public void onDestroy() {
        for (InventoryControl inventoryControl : inventoryControls) {
            if (inventoryControl instanceof InventoryProgressBarImpl) {
                manager.unregisterProgressBar((InventoryProgressBar) inventoryControl);
            }
        }
    }

    public InventoryHandler getHandler() {
        return handler;
    }

    @Override
    public void setHandler(InventoryHandler handler) {
        this.handler = handler;
    }
}
