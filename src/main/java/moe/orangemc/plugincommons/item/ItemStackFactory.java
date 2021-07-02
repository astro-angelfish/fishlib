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

package moe.orangemc.plugincommons.item;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemStackFactory {
	private final Material material;
	private int amount = 1;
	private final Map<Enchantment, Integer> enchantmentMap = new HashMap<>();
	private String displayName = null;
	private List<String> lore = null;
	private List<ItemFlag> itemFlags = null;
	private boolean unbreakable = false;
	private final Multimap<Attribute, AttributeModifier> attributeModifierMap = ArrayListMultimap.create();

	public ItemStackFactory(Material material) {
		Validate.notNull(material, "material cannot be null");

		this.material = material;
	}

	public ItemStackFactory withAmount(int amount) {
		Validate.isTrue(amount > 0, "amount cannot be less than 1");

		this.amount = amount;
		return this;
	}

	public ItemStackFactory addEnchantment(Enchantment enchantment, int level) {
		Validate.notNull(enchantment, "enchantment cannot be null");
		Validate.isTrue(level > 0, "level cannot be less than 1");

		enchantmentMap.put(enchantment, level);
		return this;
	}

	public ItemStackFactory withDisplayName(String name) {
		this.displayName = name;
		return this;
	}

	public ItemStackFactory withLore(List<String> lore) {
		this.lore = lore;
		return this;
	}

	public ItemStackFactory addLore(String lore) {
		Validate.notNull(lore, "lore cannot be null");

		if (this.lore == null) {
			this.lore = new ArrayList<>();
		}
		this.lore.add(lore);
		return this;
	}

	public ItemStackFactory withItemFlags(ItemFlag ... itemFlags) {
		Validate.notNull(itemFlags, "itemFlags cannot be null");
		Validate.noNullElements(itemFlags, "all elements in itemFlags cannot be null");

		if (this.itemFlags == null) {
			this.itemFlags = new ArrayList<>();
		}

		Collections.addAll(this.itemFlags, itemFlags);
		return this;
	}

	public ItemStackFactory withAttributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
		Validate.notNull(attribute, "attribute cannot be null");
		Validate.notNull(attributeModifier, "attributeModifier cannot be null");

		attributeModifierMap.put(attribute, attributeModifier);

		return this;
	}

	public ItemStackFactory setUnbreakable(boolean unbreakable) {
		this.unbreakable = unbreakable;
		return this;
	}

	public ItemStack build() {
		ItemStack targetItemStack = new ItemStack(material, amount);

		targetItemStack.addUnsafeEnchantments(enchantmentMap);

		ItemMeta im = targetItemStack.getItemMeta();
		if (im == null) {
			im = Bukkit.getItemFactory().getItemMeta(material);
		}

		if (im != null) {
			if (displayName != null) {
				im.setDisplayName(displayName);
			}
			if (lore != null) {
				im.setLore(lore);
			}
			if (itemFlags != null) {
				im.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
			}
			im.setAttributeModifiers(attributeModifierMap);
			im.setUnbreakable(unbreakable);
			targetItemStack.setItemMeta(im);
		}

		return targetItemStack;
	}
}
