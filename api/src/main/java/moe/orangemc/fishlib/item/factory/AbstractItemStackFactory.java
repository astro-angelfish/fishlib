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
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import java.util.*;
import java.util.function.Consumer;

/**
 * A factory to construct an item stack
 *
 * @see ItemStack
 * @see ItemMeta
 */
@ShouldNotBeImplemented
public class AbstractItemStackFactory<T extends AbstractItemStackFactory<T>> {
	private final Material material;
	private int amount = 1;
	private int damage = 0;
	private final Map<Enchantment, Integer> enchantmentMap = new HashMap<>();
	private String displayName = null;
	private List<String> lore = null;
	private List<ItemFlag> itemFlags = null;
	private boolean unbreakable = false;
	private int repairCost = 0;
	private final Multimap<Attribute, AttributeModifier> attributeModifierMap = ArrayListMultimap.create();

	private final Map<String, Consumer<ItemMeta>> postItemMetaHooks = new HashMap<>();

	protected AbstractItemStackFactory(Material material) {
		Validate.notNull(material, "material cannot be null");

		this.material = material;
	}

	/**
	 * Set the amount of the item stack to be constructed
	 * @param amount the amount of the item stack to be constructed
	 * @return the factory itself
	 */
	@SuppressWarnings("unchecked")
	public T withAmount(int amount) {
		Validate.isTrue(amount > 0, "amount cannot be less than 1");

		this.amount = amount;
		return (T) this;
	}

	/**
	 * Add an enchantment to the item stack to be constructed.
	 * @param enchantment the enchantment to be applied
	 * @param level the level of the enchantment
	 * @return the factory itself
	 */
	@SuppressWarnings("unchecked")
	public T addEnchantment(Enchantment enchantment, int level) {
		Validate.notNull(enchantment, "enchantment cannot be null");
		Validate.isTrue(level > 0, "level cannot be less than 1");

		enchantmentMap.put(enchantment, level);
		return (T) this;
	}

	/**
	 * Attach a name to the item stack to be constructed.
	 * @param name the name to be applied
	 * @return the factory itself
	 */
	@SuppressWarnings("unchecked")
	public T withDisplayName(String name) {
		this.displayName = name;
		return (T) this;
	}

	/**
	 * Attach lore to the item stack to be constructed.
	 * @param lore the lore to be applied
	 * @return the factory itself
	 */
	@SuppressWarnings("unchecked")
	public T withLore(List<String> lore) {
		this.lore = lore;
		return (T) this;
	}

	/**
	 * Add a line of lore to the item stack to be constructed.
	 * @param loreLine a line of the lore
	 * @return the factory itself
	 */
	@SuppressWarnings("unchecked")
	public T addLore(String loreLine) {
		Validate.notNull(loreLine, "loreLine cannot be null");

		if (this.lore == null) {
			this.lore = new ArrayList<>();
		}
		this.lore.add(loreLine);
		return (T) this;
	}

	/**
	 * Attach a map of attribute modifiers to the item stack to be constructed
	 * @param attributeModifierMap the map of attribute modifiers to be applied
	 * @return the factory itself
	 */
	@SuppressWarnings("unchecked")
	public T withAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifierMap) {
		this.attributeModifierMap.putAll(attributeModifierMap);
		return (T) this;
	}

	/**
	 * Attach a map of enchantments to the item stack to be constructed
	 * @param enchantmentMap the map of enchantments to be applied
	 * @return the factory itself
	 */
	@SuppressWarnings("unchecked")
	public T withEnchantments(Map<Enchantment, Integer> enchantmentMap) {
		this.enchantmentMap.putAll(enchantmentMap);
		return (T) this;
	}

	/**
	 * Attach the item flags to the item stack to be constructed
	 * @param itemFlags flags to be attached
	 * @return the factory itself
	 */
	@SuppressWarnings("unchecked")
	public T withItemFlags(ItemFlag ... itemFlags) {
		Validate.notNull(itemFlags, "itemFlags cannot be null");
		Validate.noNullElements(itemFlags, "all elements in itemFlags cannot be null");

		if (this.itemFlags == null) {
			this.itemFlags = new ArrayList<>();
		}

		Collections.addAll(this.itemFlags, itemFlags);
		return (T) this;
	}

	/**
	 * Attach an attribute modifier to the item stack to be constructed
	 * @param attribute the attribute to be modified
	 * @param attributeModifier the modifier to be applied
	 * @return the factory itself
	 */
	@SuppressWarnings("unchecked")
	public T withAttributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
		Validate.notNull(attribute, "attribute cannot be null");
		Validate.notNull(attributeModifier, "attributeModifier cannot be null");

		attributeModifierMap.put(attribute, attributeModifier);

		return (T) this;
	}

	/**
	 * Set the item stack to be constructed is unbreakable
	 * @param unbreakable the unbreakable value
	 * @return the factory itself
	 */
	@SuppressWarnings("unchecked")
	public T setUnbreakable(boolean unbreakable) {
		this.unbreakable = unbreakable;
		return (T) this;
	}

	/**
	 * Set the repair cost of the item stack to be constructed
	 * @param repairCost the repair cost
	 * @return the factory itself
	 */
	@SuppressWarnings("unchecked")
	public T withRepairCost(int repairCost) {
		this.repairCost = repairCost;
		return (T) this;
	}

	/**
	 * Set the damage value of the item stack to be constructed
	 * @param damage the damage value
	 * @return the factory itself
	 */
	@SuppressWarnings("unchecked")
	public T withDamage(int damage) {
		this.damage = damage;
		return (T) this;
	}

	/**
	 * Add a hook to the item meta of the item stack to be constructed.
	 * @param name the name of the hook
	 * @param updater the hook
	 * @return the factory itself
	 */
	@SuppressWarnings("unchecked")
	protected T addItemMetaPostHook(String name, Consumer<ItemMeta> updater) {
		postItemMetaHooks.put(name, updater);

		return (T) this;
	}

	/**
	 * Add a map of hooks to the item meta of the item stack to be constructed.
	 * @param hooks the map of hooks
	 * @return the factory itself
	 */
	@SuppressWarnings("unchecked")
	protected T addAllItemMetaPostHooks(Map<String, Consumer<ItemMeta>> hooks) {
		postItemMetaHooks.putAll(hooks);

		return (T) this;
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

			((Repairable) im).setRepairCost(repairCost);
			((Damageable) im).setDamage(damage);

			ItemMeta finalIm = im;
			postItemMetaHooks.forEach((key, hook) -> {
				hook.accept(finalIm);
			});

			targetItemStack.setItemMeta(im);
		}

		return targetItemStack;
	}

	/**
	 * Converts current item stack factory to a generic item stack factory.
	 *
	 * @return the generic item stack factory
	 */
	public ItemStackFactory toGeneric() {
		return new ItemStackFactory(this.material)
				.setUnbreakable(this.unbreakable)
				.withRepairCost(this.repairCost)
				.withDisplayName(this.displayName)
				.withLore(this.lore)
				.withItemFlags(this.itemFlags.toArray(new ItemFlag[0]))
				.withAttributeModifiers(this.attributeModifierMap)
				.withEnchantments(this.enchantmentMap)
				.withAmount(this.amount)
				.withDamage(damage)
				.addAllItemMetaPostHooks(this.postItemMetaHooks);
	}

	/**
	 * Converts current item stack factory to a generic armor item stack factory.
	 * @return the generic armor item stack factory
	 */
	public ArmorItemStackFactory toGenericArmor() {
		if (!(this instanceof AbstractArmorItemStackFactory<?>)) {
			throw new ClassCastException("Cannot cast " + this + " to generic armor item stack factory");
		}

		return new ArmorItemStackFactory(this.material)
				.setUnbreakable(this.unbreakable)
				.withRepairCost(this.repairCost)
				.withDisplayName(this.displayName)
				.withLore(this.lore)
				.withItemFlags(this.itemFlags.toArray(new ItemFlag[0]))
				.withAttributeModifiers(this.attributeModifierMap)
				.withEnchantments(this.enchantmentMap)
				.withAmount(this.amount)
				.withDamage(damage)
				.addAllItemMetaPostHooks(this.postItemMetaHooks);
	}
}
