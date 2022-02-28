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

package moe.orangemc.fishlib.item;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;
import org.apache.commons.lang3.Validate;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * A factory to construct an item stack
 */
@ShouldNotBeImplemented
public final class ItemStackFactory {
	private final Material material;
	private int amount = 1;
	private final Map<Enchantment, Integer> enchantmentMap = new HashMap<>();
	private String displayName = null;
	private List<String> lore = null;
	private List<ItemFlag> itemFlags = null;
	private boolean unbreakable = false;
	private final Multimap<Attribute, AttributeModifier> attributeModifierMap = ArrayListMultimap.create();

	/**
	 * Create the factory with the material
	 * @param material the material of the item stack
	 */
	public ItemStackFactory(Material material) {
		Validate.notNull(material, "material cannot be null");

		this.material = material;
	}

	/**
	 * Set the amount of the item stack to be constructed
	 * @param amount the amount of the item stack to be constructed
	 * @return the factory itself
	 */
	public ItemStackFactory withAmount(int amount) {
		Validate.isTrue(amount > 0, "amount cannot be less than 1");

		this.amount = amount;
		return this;
	}

	/**
	 * Add an enchantment to the item stack to be constructed.
	 * @param enchantment the enchantment to be applied
	 * @param level the level of the enchantment
	 * @return the factory itself
	 */
	public ItemStackFactory addEnchantment(Enchantment enchantment, int level) {
		Validate.notNull(enchantment, "enchantment cannot be null");
		Validate.isTrue(level > 0, "level cannot be less than 1");

		enchantmentMap.put(enchantment, level);
		return this;
	}

	/**
	 * Attach a name to the item stack to be constructed.
	 * @param name the name to be applied
	 * @return the factory itself
	 */
	public ItemStackFactory withDisplayName(String name) {
		this.displayName = name;
		return this;
	}

	/**
	 * Attach lore to the item stack to be constructed.
	 * @param lore the lore to be applied
	 * @return the factory itself
	 */
	public ItemStackFactory withLore(List<String> lore) {
		this.lore = lore;
		return this;
	}

	/**
	 * Add a line of lore to the item stack to be constructed.
	 * @param loreLine a line of the lore
	 * @return the factory itself
	 */
	public ItemStackFactory addLore(String loreLine) {
		Validate.notNull(loreLine, "loreLine cannot be null");

		if (this.lore == null) {
			this.lore = new ArrayList<>();
		}
		this.lore.add(loreLine);
		return this;
	}

	/**
	 * Attach the item flags to the item stack to be constructed
	 * @param itemFlags flags to be attached
	 * @return the factory itself
	 */
	public ItemStackFactory withItemFlags(ItemFlag ... itemFlags) {
		Validate.notNull(itemFlags, "itemFlags cannot be null");
		Validate.noNullElements(itemFlags, "all elements in itemFlags cannot be null");

		if (this.itemFlags == null) {
			this.itemFlags = new ArrayList<>();
		}

		Collections.addAll(this.itemFlags, itemFlags);
		return this;
	}

	/**
	 * Attach an attribute modifier to the item stack to be constructed
	 * @param attribute the attribute to be modified
	 * @param attributeModifier the modifier to be applied
	 * @return the factory itself
	 */
	public ItemStackFactory withAttributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
		Validate.notNull(attribute, "attribute cannot be null");
		Validate.notNull(attributeModifier, "attributeModifier cannot be null");

		attributeModifierMap.put(attribute, attributeModifier);

		return this;
	}

	/**
	 * Set the item stack to be constructed is unbreakable
	 * @param unbreakable the unbreakable value
	 * @return the factory itself
	 */
	public ItemStackFactory setUnbreakable(boolean unbreakable) {
		this.unbreakable = unbreakable;
		return this;
	}

	/**
	 * Build an item stack, finish all the setup
	 * @return the item stack constructed
	 */
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
