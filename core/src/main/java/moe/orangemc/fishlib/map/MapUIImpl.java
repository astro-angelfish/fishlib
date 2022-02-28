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

import moe.orangemc.fishlib.map.control.*;
import moe.orangemc.fishlib.map.handler.DefaultRenderer;
import moe.orangemc.fishlib.map.handler.MapRenderer;
import moe.orangemc.fishlib.map.render.MapRenderContextImpl;
import moe.orangemc.fishlib.map.render.MappedMapRenderContextImpl;
import moe.orangemc.fishlib.utils.MathUtil;
import moe.orangemc.fishlib.utils.Vector2i;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.palette.Dithering;
import org.apache.commons.lang3.Validate;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Rotation;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class MapUIImpl implements MapUI {
	private final BukkitMapCanvasRenderer[][] bukkitMapCanvasRenderers;
	private final Map<ItemFrame, Vector2i> itemFrameMap = new HashMap<>();
	private final Vector2i corner;

	private final BufferedImage buffer;

	private final List<MapControlImpl> controls = new LinkedList<>();
	private MapRenderer renderer = new DefaultRenderer();
	private final WrappedMapPalette palette = new WrappedMapPalette();

	private final AtomicBoolean ticking = new AtomicBoolean(false);

	public MapUIImpl(ItemFrame[][] frames, Vector2i corner) {
		bukkitMapCanvasRenderers = new BukkitMapCanvasRenderer[corner.getX() + 1][corner.getY() + 1];
		this.corner = corner;
		buffer = new BufferedImage(corner.getX() * 128 + 128, corner.getY() * 128 + 128, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x <= corner.getX(); x ++) {
			for (int y = 0; y <= corner.getY(); y ++) {
				ItemFrame frame = frames[x][y];
				if (frame == null) {
					bukkitMapCanvasRenderers[x][y] = null;
				} else {
					ItemStack mapItem = new ItemStack(Material.FILLED_MAP, 1);
					MapMeta mm = (MapMeta) mapItem.getItemMeta();
					if (mm == null) {
						mm = (MapMeta) Bukkit.getItemFactory().getItemMeta(Material.FILLED_MAP);
					}
					if (mm == null) {
						throw new RuntimeException("Unable to get item meta of item: " + mapItem);
					}
					MapView mv = Bukkit.createMap(Bukkit.getWorlds().get(0));

					BukkitMapCanvasRenderer canvasRenderer = new BukkitMapCanvasRenderer();
					bukkitMapCanvasRenderers[x][y] = canvasRenderer;

					mv.addRenderer(canvasRenderer);
					mm.setMapView(mv);
					mapItem.setItemMeta(mm);
					itemFrameMap.put(frame, new Vector2i(x, y));
					frame.setItem(mapItem);
					frame.setFixed(true);
					frame.setRotation(Rotation.NONE);
				}
			}
		}
	}

	private void swapBuffer() {
		for (int x = 0; x <= corner.getX(); x ++) {
			for (int y = 0; y <= corner.getY(); y ++) {
				BukkitMapCanvasRenderer md = bukkitMapCanvasRenderers[x][y];
				if (md == null) {
					continue;
				}
				int[][] subBuffer = new int[128][128];
				for (int subX = 0; subX < 128; subX ++) {
					for (int subY = 0; subY < 128; subY ++) {
						subBuffer[subX][subY] = buffer.getRGB(128 * x + subX, 128 * y + subY);
					}
				}
				md.setRenderImage(subBuffer);
			}
		}
	}

	private void render() {
		MapRenderContextImpl mapRenderContext = new MapRenderContextImpl(this.buffer);

		this.renderer.render(mapRenderContext);

		for (MapControlImpl control : this.controls) {
			Vector2i from = control.getLocation();
			Vector2i to = from.clone().add(control.getSize());

			MappedMapRenderContextImpl mappedContext = mapRenderContext.createMappingContext(from, to);

			control.render(mappedContext);
		}

		this.renderer.postRender(mapRenderContext);
		try {
			Dithering.applyFloydSteinbergDithering(buffer, palette);
		} catch (ImageWriteException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		ticking.set(true);
		render();
		swapBuffer();
		ticking.set(false);
	}

	public boolean isTicking() {
		return ticking.get();
	}

	@Override
	public void setRenderer(MapRenderer renderer) {
		Validate.notNull(renderer, "Renderer cannot be null.");
		this.renderer = renderer;
	}

	public void processButtonClick(Player player, boolean rightClick, ItemFrame frame, Vector2i location) {
		Vector2i clickedLocation = location.clone().add(itemFrameMap.get(frame).getX() * 128, itemFrameMap.get(frame).getY() * 128);
		for (MapControlImpl control : this.controls) {
			if (control instanceof MapClickableImpl clickable && MathUtil.isInRange(clickedLocation.getX(), clickable.getLocation().getX(), clickable.getLocation().getX() + clickable.getSize().getX()) && MathUtil.isInRange(clickedLocation.getY(), clickable.getLocation().getY(), clickable.getLocation().getY() + clickable.getLocation().getY())) {
				clickable.onClick(player, control, rightClick, location);
			}
		}
	}

	@Override
	public MapControl addControl(Vector2i location, Vector2i size) {
		MapControlImpl control = new MapClickableImpl(location);
		control.resize(size);
		this.controls.add(control);
		return control;
	}

	@Override
	public MapClickable addClickable(Vector2i location, Vector2i size) {
		MapClickableImpl clickableControl = new MapClickableImpl(location);
		clickableControl.resize(size);
		this.controls.add(clickableControl);
		return clickableControl;
	}

	@Override
	public MapListWidget addListWidget(Vector2i location, Vector2i size) {
		MapListWidgetImpl listWidget = new MapListWidgetImpl(location);
		listWidget.resize(size);
		this.controls.add(listWidget);
		return listWidget;
	}

	@Override
	public Vector2i getSize() {
		return corner.clone().add(1, 1).multiply(128);
	}
}
