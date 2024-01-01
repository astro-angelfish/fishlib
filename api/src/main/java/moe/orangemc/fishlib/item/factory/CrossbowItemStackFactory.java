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
import org.bukkit.inventory.meta.CrossbowMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory for crossbows.
 *
 * @see CrossbowMeta
 */
public class CrossbowItemStackFactory extends AbstractItemStackFactory<CrossbowItemStackFactory> {
	private List<ItemStack> chargedProjectiles = new ArrayList<>();

	protected CrossbowItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the charged projectiles of the crossbow.
	 *
	 * @param chargedProjectiles The charged projectiles
	 * @return The factory itself
	 */
	public CrossbowItemStackFactory withChargedProjectiles(List<ItemStack> chargedProjectiles) {
		this.chargedProjectiles = chargedProjectiles;
		return this;
	}

	/**
	 * Add a charged projectile to the crossbow.
	 *
	 * @param chargedProjectile The charged projectile to add
	 * @return The factory itself
	 */
	public CrossbowItemStackFactory addChargedProjectile(ItemStack chargedProjectile) {
		this.chargedProjectiles.add(chargedProjectile);
		return this;
	}

	@Override
	public ItemStackFactory toGeneric() {
		return super.toGeneric().addItemMetaPostHook("crossbow_charged_projectiles", (im) -> ((CrossbowMeta) im).setChargedProjectiles(chargedProjectiles));
	}

	@Override
	public ItemStack build() {
		this.addItemMetaPostHook("crossbow_charged_projectiles", (im) -> ((CrossbowMeta) im).setChargedProjectiles(chargedProjectiles));
		return super.build();
	}
}
