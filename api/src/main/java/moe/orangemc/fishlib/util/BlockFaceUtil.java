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

package moe.orangemc.fishlib.util;

import org.apache.commons.lang3.Validate;

import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class BlockFaceUtil {
	private BlockFaceUtil() {
		throw new UnsupportedOperationException();
	}

	public static BlockFace fromDirection(Vector direction) {
		Validate.notNull(direction, "direction cannot be null");

		if (direction.getY() > 0) {
			return BlockFace.UP;
		} else if (direction.getY() < 0) {
			return BlockFace.DOWN;
		}

		double ratio = direction.getX() / direction.getZ();
		if (ratio > 4) {
			if (direction.getX() > 0) {
				return BlockFace.EAST;
			} else {
				return BlockFace.WEST;
			}
		} else if (ratio > 1.3333333333333333) {
			if (direction.getX() > 0) {
				return BlockFace.EAST_SOUTH_EAST;
			} else {
				return BlockFace.WEST_NORTH_WEST;
			}
		} else if (ratio > 0.75) {
			if (direction.getX() > 0) {
				return BlockFace.SOUTH_EAST;
			} else {
				return BlockFace.NORTH_WEST;
			}
		} else if (ratio > 0.25) {
			if (direction.getX() > 0) {
				return BlockFace.SOUTH_SOUTH_EAST;
			} else {
				return BlockFace.NORTH_NORTH_WEST;
			}
		} else if (ratio > -0.25) {
			if (direction.getZ() > 0) {
				return BlockFace.SOUTH;
			} else {
				return BlockFace.NORTH_NORTH_EAST;
			}
		} else if (ratio > -0.75) {
			if (direction.getZ() > 0) {
				return BlockFace.SOUTH_SOUTH_WEST;
			} else {
				return BlockFace.NORTH_NORTH_EAST;
			}
		} else if (ratio > -1.3333333333333333) {
			if (direction.getX() > 0) {
				return BlockFace.SOUTH_WEST;
			} else {
				return BlockFace.NORTH_EAST;
			}
		} else if (ratio > -4) {
			if (direction.getX() > 0) {
				return BlockFace.WEST_SOUTH_WEST;
			} else {
				return BlockFace.EAST_NORTH_EAST;
			}
		} else {
			if (direction.getX() > 0) {
				return BlockFace.WEST;
			} else {
				return BlockFace.EAST;
			}
		}
	}

	public static BlockFace fromDirectionTo8Faces(Vector direction) {
		Validate.notNull(direction, "direction cannot be null");

		if (direction.getY() > 0) {
			return BlockFace.UP;
		} else if (direction.getY() < 0) {
			return BlockFace.DOWN;
		}

		double angle = Math.atan2(direction.getZ(), direction.getX());

		double unitCheck = Math.PI / 6;
		for (BlockFace bf : BlockFace.values()) {
			if (Math.atan2(bf.getModZ(), bf.getModX()) - angle < unitCheck) {
				return bf;
			}
		}

		throw new UnsupportedOperationException();
	}

	public static BlockFace fromDirectionToCartesian(Vector direction) {
		Validate.notNull(direction, "direction cannot be null");

		if (direction.getY() > 0) {
			return BlockFace.UP;
		} else if (direction.getY() < 0) {
			return BlockFace.DOWN;
		}

		double angle = Math.atan2(direction.getZ(), direction.getX());

		double unitCheck = Math.PI / 4;
		for (BlockFace bf : BlockFace.values()) {
			if (Math.atan2(bf.getModZ(), bf.getModX()) - angle < unitCheck) {
				return bf;
			}
		}

		throw new UnsupportedOperationException();
	}
}
