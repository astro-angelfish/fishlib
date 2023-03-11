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
import moe.orangemc.fishlib.command.selector.context.SelectedEntitiesContext;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class RandomEntity implements SelectMode {
	@Override
	public char getSymbol() {
		return 'r';
	}

	@Override
	public List<Entity> preSelectEntities(Selector selector) {
		return Bukkit.getWorlds().stream().flatMap((w) -> w.getEntities().stream()).collect(Collectors.toList());
	}

	@Override
	public List<Entity> postSelectEntities(Selector selector) {
		List<Entity> currentEntities =selector.getContext(SelectedEntitiesContext.class).getSelectedEntities();

		if (currentEntities.stream().anyMatch((e) -> e instanceof Player)) {
			currentEntities.removeIf((e) -> !(e instanceof Player));
		}

		return currentEntities;
	}
}
