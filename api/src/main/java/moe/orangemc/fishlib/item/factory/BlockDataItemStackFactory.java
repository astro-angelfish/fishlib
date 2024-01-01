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
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.meta.BlockDataMeta;

/**
 * A factory for block items.
 *
 * @see BlockDataMeta
 */
public class BlockDataItemStackFactory extends AbstractItemStackFactory<BlockDataItemStackFactory> {
	protected BlockDataItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the block data of the item.
	 *
	 * @param blockData The block data
	 * @return The factory itself
	 */
	public BlockDataItemStackFactory withBlockData(BlockData blockData) {
		this.addItemMetaPostHook("block_data", (im) -> ((BlockDataMeta) im).setBlockData(blockData));
		return this;
	}
}
