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

package moe.orangemc.fishlib.command.selector.context;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectedEntitiesContextImpl implements SelectedEntitiesContext {
	private List<Entity> selectedEntities = new ArrayList<>();

	public SelectedEntitiesContextImpl() {
		Collections.addAll(selectedEntities, Bukkit.getWorlds().stream().flatMap((w) -> w.getEntities().stream()).toList().toArray(new Entity[0]));
	}

	@Override
	public List<Entity> getSelectedEntities() {
		return new ArrayList<>(selectedEntities);
	}

	@Override
	public void setSelectedEntities(List<Entity> entities) {
		this.selectedEntities = entities;
	}
}
