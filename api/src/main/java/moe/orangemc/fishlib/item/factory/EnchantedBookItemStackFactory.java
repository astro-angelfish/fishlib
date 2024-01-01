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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * A factory for enchanted book items.
 *
 * @see EnchantmentStorageMeta
 */
public class EnchantedBookItemStackFactory extends AbstractItemStackFactory<EnchantedBookItemStackFactory> {
	private Map<Enchantment, Integer> storedEnchantments = new HashMap<>();

	protected EnchantedBookItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the stored enchantments of the book.
	 *
	 * @param storedEnchantments The stored enchantments
	 * @return The factory itself
	 */
	public EnchantedBookItemStackFactory withStoredEnchantments(Map<Enchantment, Integer> storedEnchantments) {
		this.storedEnchantments = storedEnchantments;
		return this;
	}

	/**
	 * Add a stored enchantment to the book.
	 *
	 * @param enchantment The enchantment
	 * @param level       The level of the enchantment
	 * @return The factory itself
	 */
	public EnchantedBookItemStackFactory addStoredEnchantment(Enchantment enchantment, int level) {
		this.storedEnchantments.put(enchantment, level);
		return this;
	}

	@Override
	public ItemStackFactory toGeneric() {
		return super.toGeneric().addItemMetaPostHook("enchantment_book_enchants", (im) -> {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) im;
			new HashMap<>(meta.getStoredEnchants()).forEach((enc, lvl) -> meta.removeStoredEnchant(enc));
			storedEnchantments.forEach((enchantment, i) -> meta.addStoredEnchant(enchantment, i, true));
		});
	}

	@Override
	public ItemStack build() {
		this.addItemMetaPostHook("enchantment_book_enchants", (im) -> {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) im;
			new HashMap<>(meta.getStoredEnchants()).forEach((enc, lvl) -> meta.removeStoredEnchant(enc));
			storedEnchantments.forEach((enchantment, i) -> meta.addStoredEnchant(enchantment, i, true));
		});
		return super.build();
	}
}
