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

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.HashSet;
import java.util.Set;

public class BukkitMapCanvasRenderer extends MapRenderer {
	private final byte[][] imageMap = new byte[128][128];
	private final boolean[][] dirtyMap = new boolean[128][128];
	private final WrappedMapPalette mapPalette = new WrappedMapPalette();

	private final Set<Player> cleanPlayer = new HashSet<>();

	@Override
	public void render(MapView map, MapCanvas canvas, Player player) {
		for (int x = 0; x < 128; x ++) {
			for (int y = 0; y < 128; y ++) {
				if (dirtyMap[x][y] || !cleanPlayer.contains(player)) {
					canvas.setPixel(x, y, imageMap[x][y]);
				}
			}
		}
	}

	public void setRenderImage(int[][] subBuffer) {
		for (int x = 0; x < 128; x ++) {
			for (int y = 0; y < 128; y ++) {
				int index = mapPalette.getPaletteIndex(subBuffer[x][y]);
				byte color = (byte) (index < 128 ? index : -129 + (index - 127));
				if (imageMap[x][y] != color) {
					cleanPlayer.clear();
					dirtyMap[x][y] = true;
					imageMap[x][y] = color;
				}
			}
		}
	}
}
