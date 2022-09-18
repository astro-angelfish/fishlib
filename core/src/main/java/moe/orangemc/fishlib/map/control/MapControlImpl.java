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

package moe.orangemc.fishlib.map.control;

import moe.orangemc.fishlib.map.MapRenderContext;
import moe.orangemc.fishlib.map.handler.DefaultControlRenderer;
import moe.orangemc.fishlib.map.handler.MapControlRenderer;
import moe.orangemc.fishlib.utils.Vector2i;
import org.apache.commons.lang3.Validate;

public class MapControlImpl implements MapControl {
	private MapControlRenderer renderer = new DefaultControlRenderer();

	private Vector2i location;
	private Vector2i size = new Vector2i(0, 0);

	public MapControlImpl(Vector2i location) {
		Validate.notNull(location, "Location cannot be null.");
		this.location = location;
	}

	public void render(MapRenderContext ctx) {
		this.renderer.render(this, ctx);
	}

	@Override
	public Vector2i getLocation() {
		return location.clone();
	}

	@Override
	public Vector2i getSize() {
		return size.clone();
	}

	@Override
	public void moveTo(Vector2i location) {
		this.location = location.clone();
	}

	@Override
	public void resize(Vector2i newSize) {
		this.size = newSize.clone();
	}

	@Override
	public void setRenderer(MapControlRenderer renderer) {
		Validate.notNull(renderer, "Renderer cannot be null");
		this.renderer = renderer;
	}
}
