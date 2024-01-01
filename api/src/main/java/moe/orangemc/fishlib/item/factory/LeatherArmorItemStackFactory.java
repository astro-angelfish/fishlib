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
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * A factory for leather armor.
 *
 * @see LeatherArmorMeta
 */
public class LeatherArmorItemStackFactory extends AbstractArmorItemStackFactory<LeatherArmorItemStackFactory> {
	protected LeatherArmorItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the color of the leather armor.
	 *
	 * @param color The color
	 * @return The factory itself
	 */
	public LeatherArmorItemStackFactory withColor(int color) {
		this.addItemMetaPostHook("leather_color", (im) -> ((LeatherArmorMeta) im).setColor(Color.fromRGB(color)));
		return this;
	}

	/**
	 * Set the color of the leather armor.
	 *
	 * @param color The color
	 * @return The factory itself
	 */
	public LeatherArmorItemStackFactory withColor(Color color) {
		this.addItemMetaPostHook("leather_color", (im) -> ((LeatherArmorMeta) im).setColor(color));
		return this;
	}

	/**
	 * Set the color of the leather armor.
	 *
	 * @param red   The red value
	 * @param green The green value
	 * @param blue  The blue value
	 * @return The factory itself
	 */
	public LeatherArmorItemStackFactory withColor(int red, int green, int blue) {
		this.addItemMetaPostHook("leather_color", (im) -> ((LeatherArmorMeta) im).setColor(Color.fromRGB(red, green, blue)));
		return this;
	}

	/**
	 * Set the color of the leather armor.
	 *
	 * @param color The color
	 * @return The factory itself
	 */
	public LeatherArmorItemStackFactory withColor(java.awt.Color color) {
		this.addItemMetaPostHook("leather_color", (im) -> ((LeatherArmorMeta) im).setColor(Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue())));
		return this;
	}
}
