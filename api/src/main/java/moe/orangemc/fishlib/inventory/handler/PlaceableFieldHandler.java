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

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * The handler handles placeable field events.
 */
@CanImplement
public interface PlaceableFieldHandler {
	/**
	 * Calls when player tries to put item into the field
	 *
	 * @param index the relative slot the item being put into
	 * @param who   the player who want to put the item
	 * @param item  the item being put into
	 * @return true if callee agrees to put the item
	 */
	boolean onPlace(int index, Player who, ItemStack item);

	/**
	 * Calls when player tries to take away an item
	 *
	 * @param index the relative slot item being taken
	 * @param who   the player who want to take the item
	 * @param item  the item being taken away
	 * @return true if callee agrees to take away the item
	 */
	boolean onTake(int index, Player who, ItemStack item);

	/**
	 * Calls when player tries to swap item
	 *
	 * @param index   the relative slot item being swapped
	 * @param who     the player who want to swap the item
	 * @param oldItem the item being taken away
	 * @param newItem the item being put into
	 * @return true if callee agrees to swap the item
	 */
	boolean onSwap(int index, Player who, ItemStack oldItem, ItemStack newItem);

	/**
	 * Calls when the field is being removed
	 * Default it ignores everything and consumes everything in the field
	 *
	 * @param visitors the players who are visiting the inventory
	 */
	default void onRemove(Set<Player> visitors) {
	}
}
