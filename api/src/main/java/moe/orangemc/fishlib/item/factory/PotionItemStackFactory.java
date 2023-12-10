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

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory for potions.
 *
 * @see PotionMeta
 */
public class PotionItemStackFactory extends AbstractItemStackFactory<PotionItemStackFactory> {
	private List<PotionEffect> customEffects = new ArrayList<>();

	protected PotionItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the custom effects of the potion.
	 * @param customEffects The custom effects
	 * @return The factory itself
	 */
	public PotionItemStackFactory withCustomEffects(List<PotionEffect> customEffects) {
		this.customEffects = customEffects;
		return this;
	}

	/**
	 * Add a custom effect to the potion.
	 * @param customEffect The custom effect to add
	 * @return The factory itself
	 */
	public PotionItemStackFactory addCustomEffect(PotionEffect customEffect) {
		this.customEffects.add(customEffect);
		return this;
	}

	/**
	 * Set the base potion type of the potion.
	 * @param basePotionType The base potion type
	 * @return The factory itself
	 */
	public PotionItemStackFactory withBasePotionType(PotionType basePotionType) {
		this.addItemMetaPostHook("base_potion_type", (im) -> ((PotionMeta) im).setBasePotionType(basePotionType));
		return this;
	}

	/**
	 * Set the color of the potion.
	 * @param color The color
	 * @return The factory itself
	 */
	public PotionItemStackFactory withColor(Color color) {
		this.addItemMetaPostHook("potion_color", (im) -> ((PotionMeta) im).setColor(color));
		return this;
	}

	@Override
	public ItemStackFactory toGeneric() {
		return super.toGeneric().addItemMetaPostHook("custom_effects", (im) -> {
			PotionMeta meta = (PotionMeta) im;
			meta.clearCustomEffects();
			for (PotionEffect effect : customEffects) {
				meta.addCustomEffect(effect, true);
			}
		});
	}

	@Override
	public ItemStack build() {
		this.addItemMetaPostHook("custom_effects", (im) -> {
			PotionMeta meta = (PotionMeta) im;
			meta.clearCustomEffects();
			for (PotionEffect effect : customEffects) {
				meta.addCustomEffect(effect, true);
			}
		});
		return super.build();
	}
}
