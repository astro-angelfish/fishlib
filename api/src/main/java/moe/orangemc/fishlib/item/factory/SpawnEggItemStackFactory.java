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
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.inventory.meta.SpawnEggMeta;

/**
 * A factory for spawn eggs.
 *
 * @see SpawnEggMeta
 */
public class SpawnEggItemStackFactory extends AbstractItemStackFactory<SpawnEggItemStackFactory> {
	protected SpawnEggItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the entity type of the spawn egg.
	 *
	 * @param entitySnapshot The entity type
	 * @return The factory itself
	 */
	public SpawnEggItemStackFactory withEntity(EntitySnapshot entitySnapshot) {
		this.addItemMetaPostHook("spawn_egg_entity_type", (im) -> ((SpawnEggMeta) im).setSpawnedEntity(entitySnapshot));
		return this;
	}
}
