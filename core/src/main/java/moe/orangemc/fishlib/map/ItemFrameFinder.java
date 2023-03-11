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

package moe.orangemc.fishlib.map;

import moe.orangemc.fishlib.util.Vector2i;

import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ItemFrameFinder {
	private final ItemFrame startItemFrame;
	private final Vector2i maxSize;

	private final Map<Vector2i, ItemFrame> result = new HashMap<>();

	public ItemFrameFinder(ItemFrame startItemFrame, Vector2i maxSize) {
		this.startItemFrame = startItemFrame;
		this.maxSize = maxSize.clone();
	}

	public ItemFrameFinder startFinding() {
		ItemFrame current = startItemFrame;

		for (int i = 0; i < maxSize.getY(); i ++) {
			findHorizontally(current, new Vector2i(0, i));
			Collection<Entity> entityList = current.getWorld().getNearbyEntities(current.getLocation(), 1, 1, 1, (e) -> e instanceof ItemFrame);
			boolean found = false;
			for (Entity next : entityList) {
				if (!(next instanceof ItemFrame nextItemFrame)) {
					throw new IllegalStateException("The predicate has lost its function");
				}
				if (nextItemFrame.getLocation().equals(current.getLocation().add(getVerticalDirection()))) {
					current = nextItemFrame;
					found = true;
					break;
				}
			}
			if (!found) {
				maxSize.setY(i);
				break;
			}
		}

		return this;
	}

	private Vector getVerticalDirection() {
		return switch (startItemFrame.getFacing()) {
			case UP -> new Vector(0, 0, 1);
			case DOWN -> new Vector(0, 0, -1);
			case EAST, WEST, NORTH, SOUTH -> new Vector(0, -1, 0);

			default -> throw new IllegalStateException("Unexpected value: " + startItemFrame.getFacing());
		};
	}

	private void findHorizontally(ItemFrame start, Vector2i currentHorizontal) {
		result.put(currentHorizontal, start);
		int i = 0;
		ItemFrame current = start;
		result.put(currentHorizontal.clone(), start);
		Vector2i currentLocation = currentHorizontal.clone();
		while (i < maxSize.getX()) {
			Collection<Entity> entityList = current.getWorld().getNearbyEntities(current.getLocation(), 1, 1, 1, (e) -> e instanceof ItemFrame);
			boolean found = false;
			for (Entity next : entityList) {
				if (!(next instanceof ItemFrame nextItemFrame)) {
					throw new IllegalStateException("The predicate has lost its function");
				}

				if (nextItemFrame.getLocation().equals(current.getLocation().add(getHorizontalDirection()))) {
					current = nextItemFrame;
					result.put(currentLocation.add(1, 0).clone(), current);
					found = true;
					break;
				}
			}
			if (!found) {
				maxSize.setX(i);
				return;
			}
			i ++;
		}
	}

	private Vector getHorizontalDirection() {
		return switch (startItemFrame.getFacing()) {
			case UP, DOWN, SOUTH -> new Vector(1, 0, 0);
			case WEST -> new Vector(0, 0, 1);
			case EAST -> new Vector(0, 0, -1);
			case NORTH -> new Vector(-1, 0, 0);
			default -> throw new IllegalStateException("Unexpected value: " + startItemFrame.getFacing());
		};
	}

	public Map<Vector2i, ItemFrame> getResult() {
		return Collections.unmodifiableMap(this.result);
	}

	public Vector2i getActualSize() {
		return this.maxSize.clone();
	}
}
