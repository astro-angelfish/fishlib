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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.meta.CompassMeta;

/**
 * A factory for compasses.
 *
 * @see CompassMeta
 */
public class CompassItemStackFactory extends AbstractItemStackFactory<CompassItemStackFactory> {
	protected CompassItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set whether the compass is tracking lodestone.
	 *
	 * @param lodestoneTracked Whether the compass is tracking lodestone
	 * @return The factory itself
	 */
	public CompassItemStackFactory withLodestoneTracked(boolean lodestoneTracked) {
		this.addItemMetaPostHook("lodestone_tracked", (im) -> ((CompassMeta) im).setLodestoneTracked(lodestoneTracked));
		return this;
	}

	/**
	 * Set the lodestone of the compass.
	 *
	 * @param lodestone The lodestone
	 * @return The factory itself
	 */
	public CompassItemStackFactory withLodestone(Location lodestone) {
		this.addItemMetaPostHook("lodestone", (im) -> ((CompassMeta) im).setLodestone(lodestone));
		return this;
	}
}
