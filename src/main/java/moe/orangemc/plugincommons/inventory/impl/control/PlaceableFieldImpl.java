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

package moe.orangemc.plugincommons.inventory.impl.control;

import moe.orangemc.plugincommons.inventory.control.PlaceableField;
import moe.orangemc.plugincommons.inventory.control.handler.PlaceableFieldHandler;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * A field that player can put items in.
 */
public class PlaceableFieldImpl extends ControlImpl implements PlaceableField {
    private final int width;
    private final int height;
    private final PlaceableFieldHandler handler;

    private final Map<Integer, ItemStack> content = new HashMap<>();

    public PlaceableFieldImpl(int width, int height, PlaceableFieldHandler handler) {
        this.width = width;
        this.height = height;
        this.handler = handler;
    }

	@Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public PlaceableFieldHandler getHandler() {
        return handler;
    }

    @Override
    public Map<Integer, ItemStack> getContent() {
        return new HashMap<>(content);
    }

    @Override
    public void setContent(Map<Integer, ItemStack> content, boolean needSynchronize) {
	    Validate.notNull(content, "content cannot be null");

        this.content.clear();
        this.content.putAll(content);

        if (needSynchronize) {
            synchronizeToInventory();
        }
    }

    @Override
    public void setContent(Map<Integer, ItemStack> content) {
	    Validate.notNull(content, "content cannot be null");

        this.setContent(content, true);
    }

    public void onAdd(Inventory inventoryPlacedIn, int startIndex) {
        int startX = getStartIndex() % 9;
        int startY = getStartIndex() / 9;
        if (startX + width > 9 || startY + height > inventoryPlacedIn.getSize() / 9) {
            throw new IllegalArgumentException("Inventory is too small to fit in");
        }
        super.onAdd(inventoryPlacedIn, startIndex);
        synchronizeToInventory();
    }

	@Override
	public void clear() {
    	content.clear();
    	synchronizeToInventory();
    }

    private void synchronizeToInventory() {
        if (getInventoryPutIn() == null) {
            return;
        }
        int startX = getStartIndex() % 9;
        int startY = getStartIndex() / 9;
        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                getInventoryPutIn().setItem(startX + x + (startY + y) * 9, new ItemStack(Material.AIR));
            }
        }
        this.content.forEach((index, content) -> {
            int targetX = index % width + startX;
            int targetY = index / width + startY;

            getInventoryPutIn().setItem(targetX + targetY * 9, content);
        });
    }

    @Override
    public boolean isSlotInsideField(int slot) {
        int offset = slot - getStartIndex();
        int x = offset % 9;
        int y = offset / 9;

        return x >= 0 && x < width && y >= 0 && y < height;
    }

    @Override
    public int locateRelativeOffsetOfSlot(int slot) {
        if (!isSlotInsideField(slot)) {
            return -1;
        }

        int startX = getStartIndex() % 9;
        int startY = getStartIndex() / 9;
        int offsetX = (slot % 9 - startX) % width;
        int offsetY = slot / 9 - startY;

        return offsetY * width + offsetX;
    }
}
