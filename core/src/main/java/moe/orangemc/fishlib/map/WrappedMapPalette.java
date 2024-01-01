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

import moe.orangemc.fishlib.reflection.ReflectionUtil;
import org.apache.commons.imaging.palette.Palette;

import org.bukkit.map.MapPalette;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WrappedMapPalette implements Palette {
	private final Map<Integer, Integer> colorMap = new HashMap<>();
	private boolean init = false;
	private Color[] colors;

	public WrappedMapPalette() {
		try {
			Object field = ReflectionUtil.getField(MapPalette.class, null, "colors");
			if (field instanceof Color[] argColors) {
				this.colors = new Color[argColors.length];
				System.arraycopy(argColors, 0, this.colors, 0, argColors.length);
				init = true;
			} else {
				throw new IllegalStateException("Expected colors is not a Color array");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getPaletteIndex(int rgb) {
		if (!init) {
			return 0;
		}
		Integer mappedIndex = colorMap.get(rgb);
		if (mappedIndex != null) {
			return mappedIndex;
		}

		int index = 0;
		double best = Double.MAX_VALUE;

		for (int i = 4; i < colors.length; i++) {
			double distance = getDistance(new Color(rgb, false), colors[i]);
			if (distance < best) {
				best = distance;
				index = i;
			}
		}
		colorMap.put(rgb, index);

		return index;
	}

	private double getDistance(Color c1, Color c2) {
		return toXyzVector(c1).distance(toXyzVector(c2));
	}

	private Vector toXyzVector(Color c) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		return new Vector(2.7688 * r + 1.7517 * g + 1.1301 * b, r + 4.5906 * g + 0.0601 * b, 0.0565 * g + 5.5942 * b);
	}

	@Override
	public int getEntry(int index) {
		if (!init) {
			return 0;
		}
		return colors[index].getRGB();
	}

	@Override
	public int length() {
		if (!init) {
			return 0;
		}
		return colors.length;
	}
}
