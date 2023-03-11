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

package moe.orangemc.fishlib.command.selector.mode;

import moe.orangemc.fishlib.command.selector.SelectMode;
import moe.orangemc.fishlib.command.selector.Selector;
import moe.orangemc.fishlib.command.selector.context.LocationContext;
import moe.orangemc.fishlib.command.selector.context.SelectedEntitiesContext;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NearestPlayer implements SelectMode {
	@Override
	public char getSymbol() {
		return 's';
	}

	@Override
	public List<Entity> preSelectEntities(Selector selector) {
		return new ArrayList<>(Bukkit.getOnlinePlayers());
	}

	@Override
	public List<Entity> postSelectEntities(Selector selector) {
		Entity nearest = null;
		double lastDistance = Double.MAX_VALUE;

		Location location = selector.getContext(LocationContext.class).getBaseLocation();
		List<Entity> currentEntities =selector.getContext(SelectedEntitiesContext.class).getSelectedEntities();

		for (Entity e : currentEntities) {
			if (e.getLocation().distanceSquared(location) < lastDistance) {
				nearest = e;
				lastDistance = e.getLocation().distanceSquared(location);
			}
		}
		if (nearest == null) {
			return new ArrayList<>();
		}

		return Collections.singletonList(nearest);
	}
}
