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

import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory for firework items.
 *
 * @see FireworkMeta
 */
public class FireworkItemStackFactory extends AbstractItemStackFactory<FireworkItemStackFactory> {
	private List<FireworkEffect> effects = new ArrayList<>();

	protected FireworkItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the effects of the firework.
	 *
	 * @param effects The effects
	 * @return The factory itself
	 */
	public FireworkItemStackFactory withEffects(List<FireworkEffect> effects) {
		this.effects = effects;
		return this;
	}

	/**
	 * Add an effect to the firework.
	 *
	 * @param effect The effect to add
	 * @return The factory itself
	 */
	public FireworkItemStackFactory addEffect(FireworkEffect effect) {
		this.effects.add(effect);
		return this;
	}

	/**
	 * Set the power of the firework.
	 *
	 * @param power The power
	 * @return The factory itself
	 */
	public FireworkItemStackFactory withPower(int power) {
		this.addItemMetaPostHook("firework_power", (im) -> ((FireworkMeta) im).setPower(power));
		return this;
	}

	@Override
	public ItemStackFactory toGeneric() {
		return super.toGeneric().addItemMetaPostHook("firework_effects", (im) -> {
			FireworkMeta meta = (FireworkMeta) im;
			meta.clearEffects();
			meta.addEffects(effects);
		});
	}

	@Override
	public ItemStack build() {
		this.addItemMetaPostHook("firework_effects", (im) -> {
			FireworkMeta meta = (FireworkMeta) im;
			meta.clearEffects();
			meta.addEffects(effects);
		});
		return super.build();
	}
}
