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
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;

import java.util.List;

/**
 * A factory for suspicious stews.
 *
 * @see SuspiciousStewMeta
 */
public class SuspiciousStewItemStackFactory extends AbstractItemStackFactory<SuspiciousStewItemStackFactory> {
	private List<PotionEffect> customEffects;

	protected SuspiciousStewItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the custom effects of the suspicious stew.
	 *
	 * @param customEffects The custom effects
	 * @return The factory itself
	 */
	public SuspiciousStewItemStackFactory withCustomEffects(List<PotionEffect> customEffects) {
		this.customEffects = customEffects;
		return this;
	}

	/**
	 * Add a custom effect to the suspicious stew.
	 *
	 * @param customEffect The custom effect to add
	 * @return The factory itself
	 */
	public SuspiciousStewItemStackFactory addCustomEffect(PotionEffect customEffect) {
		this.customEffects.add(customEffect);
		return this;
	}

	@Override
	public ItemStackFactory toGeneric() {
		return super.toGeneric().addItemMetaPostHook("suspicious_stew_custom_effects", (im) -> {
			SuspiciousStewMeta meta = (SuspiciousStewMeta) im;
			meta.clearCustomEffects();
			for (PotionEffect effect : customEffects) {
				meta.addCustomEffect(effect, true);
			}
		});
	}

	@Override
	public ItemStack build() {
		this.addItemMetaPostHook("suspicious_stew_custom_effects", (im) -> {
			SuspiciousStewMeta meta = (SuspiciousStewMeta) im;
			meta.clearCustomEffects();
			for (PotionEffect effect : customEffects) {
				meta.addCustomEffect(effect, true);
			}
		});
		return super.build();
	}
}
