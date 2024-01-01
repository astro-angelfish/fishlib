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
import org.bukkit.block.BlockState;
import org.bukkit.inventory.meta.BlockStateMeta;

/**
 * A factory for tile entity items.
 *
 * @see BlockStateMeta
 */
public class BlockStateItemStackFactory extends AbstractItemStackFactory<BlockStateItemStackFactory> {
	protected BlockStateItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the block state of the item.
	 *
	 * @param blockState The block state
	 * @return The factory itself
	 */
	public BlockStateItemStackFactory withBlockState(BlockState blockState) {
		this.addItemMetaPostHook("block_state", (im) -> ((BlockStateMeta) im).setBlockState(blockState));
		return this;
	}
}
