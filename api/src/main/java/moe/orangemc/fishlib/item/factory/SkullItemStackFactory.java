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
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

/**
 * A factory for skulls.
 *
 * @see SkullMeta
 */
public class SkullItemStackFactory extends AbstractItemStackFactory<SkullItemStackFactory> {
	protected SkullItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the owner of the skull.
	 * @param owner The owner
	 * @return The factory itself
	 */
	public SkullItemStackFactory withOwner(OfflinePlayer owner) {
		this.addItemMetaPostHook("skull_owner", (im) -> ((SkullMeta) im).setOwningPlayer(owner));
		return this;
	}

	/**
	 * Set the owner of the skull.
	 * @param profile The owner profile
	 * @return The factory itself
	 */
	public SkullItemStackFactory withOwner(PlayerProfile profile) {
		this.addItemMetaPostHook("skull_owner", (im) -> ((SkullMeta) im).setOwnerProfile(profile));
		return this;
	}
}
