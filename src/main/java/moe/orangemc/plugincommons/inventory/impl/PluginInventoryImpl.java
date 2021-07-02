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
import moe.orangemc.luckyfish.plugincommons.inventory.control.*;
import moe.orangemc.plugincommons.inventory.control.handler.ButtonClickHandler;
import moe.orangemc.plugincommons.inventory.control.handler.InventoryHandler;
import moe.orangemc.plugincommons.inventory.control.handler.PlaceableFieldHandler;
import moe.orangemc.plugincommons.inventory.control.handler.ProgressBarUpdater;
import moe.orangemc.plugincommons.inventory.impl.control.ButtonImpl;
import moe.orangemc.plugincommons.inventory.impl.control.ControlImpl;
import moe.orangemc.plugincommons.inventory.impl.control.PlaceableFieldImpl;
import moe.orangemc.plugincommons.inventory.impl.control.ProgressBarImpl;
import moe.orangemc.plugincommons.inventory.control.*;
import org.apache.commons.lang.Validate;
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
    private Border border = null;
    private InventoryHandler handler = null;

    private final List<Control> controls = new ArrayList<>();
    private final List<Integer> borderSlots = new ArrayList<>();

    public PluginInventoryImpl(PluginInventoryManagerImpl manager, String title, Player player, int height) {
        this.manager = manager;
        this.player = player;
        internalInventory = Bukkit.createInventory(player, height * 9, title);
    }

    @Override
    public void setBorder(Border border) {
	    Validate.notNull(border, "border cannot be null");
        this.border = border;
        borderSlots.clear();
        borderSlots.addAll(border.putBorder(internalInventory));
    }

    @Override
    public Border getBorder() {
        return border;
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
	public Button putButton(int x, int y, ItemStack buttonItem, ButtonClickHandler handler) {
    	Validate.notNull(buttonItem, "buttonItem cannot be null");

    	ButtonImpl button = new ButtonImpl(buttonItem, handler);
	    add(x, y, button);
	    return button;
    }

	@Override
	public Button putButton(int x, int y, ItemStack buttonItem) {
		Validate.notNull(buttonItem, "buttonItem cannot be null");

		ButtonImpl button = new ButtonImpl(buttonItem, null);
		add(x, y, button);
		return button;
	}

	@Override
	public ProgressBar putProgressBar(int x, int y, int length, ItemStack emptyItem, ItemStack filledItem, ProgressBarUpdater updater) {
    	Validate.notNull(emptyItem, "emptyItem cannot be null");
    	Validate.notNull(filledItem, "filledItem cannot be null");

    	ProgressBarImpl progressBar = new ProgressBarImpl(length, emptyItem, filledItem, updater);
    	add(x, y, progressBar);
    	return progressBar;
    }

	@Override
	public PlaceableField putPlaceableField(int x, int y, int width, int height, PlaceableFieldHandler handler) {
	    PlaceableFieldImpl placeableField = new PlaceableFieldImpl(width, height, handler);
	    add(x, y, placeableField);
	    return placeableField;
    }

	@Override
	public PlaceableField putPlaceableField(int x, int y, int width, int height) {
		PlaceableFieldImpl placeableField = new PlaceableFieldImpl(width, height, null);
		add(x, y, placeableField);
		return placeableField;
	}

    private void add(int x, int y, ControlImpl control) {
        if (borderSlots.contains(x + y * 9)) {
            borderSlots.remove(Integer.valueOf(x + y * 9));
        }

        controls.add(control);
        if (control instanceof ProgressBarImpl) {
            manager.registerProgressBar((ProgressBar) control);
        }
        control.onAdd(internalInventory, x + y * 9);
    }

    @Override
    public List<Integer> getBorderSlots() {
        return Collections.unmodifiableList(borderSlots);
    }

    @Override
    public List<Control> getControls() {
        return Collections.unmodifiableList(controls);
    }

    public void onDestroy() {
        for (Control control : controls) {
            if (control instanceof ProgressBarImpl) {
                manager.unregisterProgressBar((ProgressBar) control);
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
