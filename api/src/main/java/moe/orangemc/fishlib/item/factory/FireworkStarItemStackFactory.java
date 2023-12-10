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
import org.bukkit.inventory.meta.FireworkEffectMeta;

/**
 * A factory for firework stars.
 *
 * @see FireworkEffectMeta
 */
public class FireworkStarItemStackFactory extends AbstractItemStackFactory<FireworkStarItemStackFactory> {
	protected FireworkStarItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the effect of the firework star.
	 * @param effect The effect
	 * @return The factory itself
	 */
	public FireworkStarItemStackFactory withEffect(FireworkEffect effect) {
		this.addItemMetaPostHook("firework_star_effect", (im) -> ((FireworkEffectMeta) im).setEffect(effect));
		return this;
	}
}
