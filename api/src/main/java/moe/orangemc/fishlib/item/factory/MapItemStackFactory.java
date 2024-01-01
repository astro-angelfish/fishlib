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

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

/**
 * A factory for maps.
 *
 * @see MapMeta
 */
public class MapItemStackFactory extends AbstractItemStackFactory<MapItemStackFactory> {
	protected MapItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set whether the map is scaling.
	 *
	 * @param scaling Whether the map is scaling
	 * @return The factory itself
	 */
	public MapItemStackFactory withScaling(boolean scaling) {
		this.addItemMetaPostHook("map_scaling", (im) -> ((MapMeta) im).setScaling(scaling));
		return this;
	}

	/**
	 * Set the map view of the map.
	 *
	 * @param mapView The map view
	 * @return The factory itself
	 */
	public MapItemStackFactory withMapView(MapView mapView) {
		this.addItemMetaPostHook("map_view", (im) -> ((MapMeta) im).setMapView(mapView));
		return this;
	}

	/**
	 * Set the color of the map.
	 *
	 * @param color The color
	 * @return The factory itself
	 */
	public MapItemStackFactory withColor(Color color) {
		this.addItemMetaPostHook("map_color", (im) -> ((MapMeta) im).setColor(color));
		return this;
	}
}
