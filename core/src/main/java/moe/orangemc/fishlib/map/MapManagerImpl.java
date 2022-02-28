/*
 * FishLib, a Bukkit development library
 * Copyright (C) Lucky_fish0w0
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

import moe.orangemc.fishlib.map.listener.MapClickListener;
import moe.orangemc.fishlib.utils.Vector2i;
import org.apache.commons.lang3.Validate;

import org.bukkit.Bukkit;
import org.bukkit.entity.ItemFrame;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapManagerImpl implements MapManager {
	private final List<MapUIImpl> mapUIs = new LinkedList<>();
	private final Map<ItemFrame, MapUIImpl> itemFrameMap = new HashMap<>();

	private final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 2);

	public MapManagerImpl(Plugin plugin) {
		Bukkit.getScheduler().runTaskTimer(plugin, this::scheduleTick, 0, 0);
		Bukkit.getPluginManager().registerEvents(new MapClickListener(this), plugin);
	}

	@Override
	public MapUI createMapUI(Map<Vector2i, ItemFrame> framesToDisplay) {
		Validate.notEmpty(framesToDisplay, "You should provide more item frame");

		Vector2i corner = getActualSize(framesToDisplay.keySet());
		ItemFrame[][] itemFrames = new ItemFrame[corner.getX() + 1][corner.getY() + 1];

		framesToDisplay.forEach((loc, frame) -> itemFrames[loc.getX()][loc.getY()] = frame);
		MapUIImpl mapUI = new MapUIImpl(itemFrames, corner);
		this.mapUIs.add(mapUI);
		framesToDisplay.forEach((loc, frame) -> itemFrameMap.put(frame, mapUI));
		return mapUI;
	}

	private Vector2i getActualSize(Set<Vector2i> locations) {
		int maxX = 0;
		int maxY = 0;
		for (Vector2i location : locations) {
			Validate.isTrue(location.getX() >= 0, "The x value of one of the locations must greater than zero");
			Validate.isTrue(location.getY() >= 0, "The y value of one of the locations must greater than zero");

			maxX = Math.max(maxX, location.getX());
			maxY = Math.max(maxY, location.getY());
		}
		return new Vector2i(maxX, maxY);
	}

	private void scheduleTick() {
		for (MapUIImpl mapUI : this.mapUIs) {
			if (mapUI.isTicking()) {
				continue;
			}
			threadPoolExecutor.submit(mapUI::tick);
		}
	}

	public void onDestroy() {
		threadPoolExecutor.shutdown();
	}

	public MapUI getMapUIViaItemFrame(ItemFrame itemFrame) {
		return itemFrameMap.get(itemFrame);
	}
}
