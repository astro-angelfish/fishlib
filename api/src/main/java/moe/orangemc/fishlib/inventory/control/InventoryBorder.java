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

package moe.orangemc.fishlib.inventory.control;

import org.apache.commons.lang3.Validate;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Border of the inventory
 */
public class InventoryBorder {
	private final ItemStack borderStyle;

	public InventoryBorder(ItemStack borderStyle) {
		Validate.notNull(borderStyle, "borderStyle cannot be null");

		this.borderStyle = borderStyle;
	}

	/**
	 * Calls when the border is being put.
	 * Override this to customize border.
	 *
	 * @param internalInventory the inventory to be put border
	 * @return Slots that used to put border.
	 */
	public List<Integer> putBorder(Inventory internalInventory) {
		List<Integer> slotPut = new ArrayList<>();
		for (int x = 0; x < 9; x++) {
			internalInventory.setItem(x, borderStyle);
			slotPut.add(x);
			internalInventory.setItem(internalInventory.getSize() - x - 1, borderStyle);
			slotPut.add(internalInventory.getSize() - x - 1);
		}
		for (int y = 0; y < internalInventory.getSize() / 9; y++) {
			internalInventory.setItem(y * 9, borderStyle);
			slotPut.add(y * 9);
			internalInventory.setItem(y * 9 + 8, borderStyle);
			slotPut.add(y * 9 + 8);
		}

		return slotPut;
	}

	/**
	 * Gets the border style item
	 *
	 * @return the border style item
	 */
	public final ItemStack getBorderStyle() {
		return borderStyle.clone();
	}
}
