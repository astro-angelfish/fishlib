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

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;

/**
 * A factory for armor items.
 *
 * @see ArmorMeta
 */
@ShouldNotBeImplemented
public class AbstractArmorItemStackFactory<T extends AbstractArmorItemStackFactory<T>> extends AbstractItemStackFactory<T> {
	protected AbstractArmorItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the armor trim.
	 * @param armorTrim The armor trim
	 * @return The factory itself
	 */
	@SuppressWarnings("unchecked")
	public T withArmorTrim(ArmorTrim armorTrim) {
		this.addItemMetaPostHook("armor_trim", (im) -> ((ArmorMeta) im).setTrim(armorTrim));

		return (T) this;
	}
}
