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

import moe.orangemc.fishlib.map.listener.MapClickListener;
import moe.orangemc.fishlib.util.Vector2i;
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
		return addMapUI(new MapUIImpl(itemFrames, corner), itemFrames);
	}

	@Override
	public MapUI createMapUI(ItemFrame start, Vector2i maxSize) {
		Validate.notNull(start, "Start item frame cannot be null");
		Validate.notNull(maxSize, "Max size cannot be null");
		Validate.isTrue(maxSize.getX() > 0 && maxSize.getY() > 0, "Illegal size");

		ItemFrameFinder itemFrameFinder = new ItemFrameFinder(start, maxSize).startFinding();

		Vector2i actualSize = itemFrameFinder.getActualSize();
		ItemFrame[][] itemFrames = new ItemFrame[actualSize.getX() + 1][actualSize.getY() + 1];
		itemFrameFinder.getResult().forEach((loc, itemFrame) -> itemFrames[loc.getX()][loc.getY()] = itemFrame);

		return addMapUI(new MapUIImpl(itemFrames, actualSize), itemFrames);
	}

	@Override
	public void destroyMapUI(MapUI ui) {
		Validate.notNull(ui, "The map ui cannot be null");
		Validate.isInstanceOf(MapUIImpl.class, ui, "The map ui must be managed by fishlib");

		MapUIImpl mapUI = (MapUIImpl) ui;
		mapUIs.remove(mapUI);
		for (ItemFrame frame : mapUI.getItemFrames()) {
			itemFrameMap.remove(frame);
			frame.remove();
		}
	}

	@Override
	public void destroyMapUI(ItemFrame part) {
		Validate.notNull(part, "The frame cannot be null");
		MapUIImpl mapUI = itemFrameMap.get(part);
		Validate.notNull(mapUI, "The item frame is not part of map ui");
		Validate.isInstanceOf(MapUIImpl.class, mapUI, "The map ui must be managed by fishlib");

		destroyMapUI(mapUI);
	}

	private MapUI addMapUI(MapUIImpl mapUI, ItemFrame[][] itemFrames) {
		this.mapUIs.add(mapUI);
		for (ItemFrame[] if1 : itemFrames) {
			if (if1 == null) {
				continue;
			}
			for (ItemFrame if2 : if1) {
				if (if2 == null) {
					continue;
				}
				itemFrameMap.put(if2, mapUI);
			}
		}
		this.mapUIs.add(mapUI);
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
