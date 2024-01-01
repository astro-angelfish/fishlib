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

package moe.orangemc.fishlib.item.factory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory for bundles.
 *
 * @see BundleMeta
 */
public class BundleItemStackFactory extends AbstractItemStackFactory<BundleItemStackFactory> {
	private List<ItemStack> items = new ArrayList<>();

	protected BundleItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the items in the bundle.
	 *
	 * @param items The items
	 * @return The factory itself
	 */
	public BundleItemStackFactory withItems(List<ItemStack> items) {
		this.items = items;
		return this;
	}

	/**
	 * Add an item to the bundle.
	 *
	 * @param item The item to add
	 * @return The factory itself
	 */
	public BundleItemStackFactory addItem(ItemStack item) {
		this.items.add(item);
		return this;
	}

	@SuppressWarnings("UnstableApiUsage")
	@Override
	public ItemStackFactory toGeneric() {
		return super.toGeneric().addItemMetaPostHook("bundle_items", (im) -> ((BundleMeta) im).setItems(items));
	}

	@SuppressWarnings("UnstableApiUsage")
	@Override
	public ItemStack build() {
		this.addItemMetaPostHook("bundle_items", (im) -> ((BundleMeta) im).setItems(items));
		return super.build();
	}
}
