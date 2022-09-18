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

package moe.orangemc.fishlib.map.listener;

import moe.orangemc.fishlib.map.MapManagerImpl;
import moe.orangemc.fishlib.map.MapUIImpl;
import moe.orangemc.fishlib.utils.Vector2i;

import org.bukkit.Location;
import org.bukkit.Rotation;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.List;

public record MapClickListener(MapManagerImpl mapManager) implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		event.setCancelled(executePlayerInteract(event.getPlayer(), event.getAction()));
	}
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerInteractEntity(PlayerInteractAtEntityEvent event) {
		event.setCancelled(executePlayerInteract(event.getPlayer(), Action.RIGHT_CLICK_AIR));
	}
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		event.setCancelled(executePlayerInteract(event.getPlayer(), Action.RIGHT_CLICK_AIR));
	}
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof ItemFrame)) {
			return;
		}
		if (!(event.getDamager() instanceof Player p)) {
			return;
		}
		event.setCancelled(executePlayerInteract(p, Action.LEFT_CLICK_AIR));
	}

	private boolean executePlayerInteract(Player player, Action action) {
		ItemFrame selected = null;
		double distance = Double.MAX_VALUE;
		Vector2i selectedLoc = null;

		List<ItemFrame> itemFrames = player.getNearbyEntities(4, 4, 4).stream().filter((e) -> e instanceof ItemFrame).map((e) -> (ItemFrame) e).filter(e -> e.isFixed() && e.getRotation() == Rotation.NONE).toList();
		for (ItemFrame itemFrame : itemFrames) {
			Vector2i result = locatePlayerSight(player, itemFrame);

			if (result != null) {
				double checkDis = itemFrame.getLocation().distance(player.getEyeLocation());
				if (checkDis < distance) {
					distance = checkDis;
					selected = itemFrame;
					selectedLoc = result;
				}
			}
		}

		if (selected == null) {
			return false;
		}

		MapUIImpl mapUI = (MapUIImpl) mapManager.getMapUIViaItemFrame(selected);
		mapUI.processButtonClick(player, action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK, selected, selectedLoc);
		return true;
	}

	private Vector2i locatePlayerSight(Player player, ItemFrame itemFrame) {
		Location location = itemFrame.getLocation();

		Vector planeNormalVector = itemFrame.getFacing().getDirection();
		Vector playerEyeVector = player.getEyeLocation().toVector();
		Vector playerDirectionVector = player.getLocation().getDirection().normalize();
		Vector itemFrameCenterVector = location.toVector();

		// Algorithm from https://blog.csdn.net/abcjennifer/article/details/6688080
		// Calculate the vector length between the player's eye and the surface of the itemFrame located.
		double distance = itemFrameCenterVector.clone().subtract(playerEyeVector).dot(planeNormalVector) / planeNormalVector.dot(playerDirectionVector);

		// Doesn't seem to have point... Maybe is too far away
		if (!Double.isFinite(distance) || distance > 4) {
			return null;
		}

		Vector hit = playerEyeVector.clone().add(playerDirectionVector.clone().multiply(distance)).subtract(itemFrameCenterVector);
		// The hit point is too far away.
		if (Math.abs(hit.getX()) > 0.5 || Math.abs(hit.getY()) > 0.5 || Math.abs(hit.getZ()) > 0.5) {
			return null;
		}

		return switch (itemFrame.getFacing()) {
			case NORTH -> new Vector2i((int) ((0.5 - hit.getX()) * 128), (int) ((0.5 - hit.getY()) * 128));
			case SOUTH -> new Vector2i((int) (0.5 + hit.getX() * 128), (int) ((0.5 - hit.getY()) * 128));
			case WEST -> new Vector2i((int) ((0.5 + hit.getZ()) * 128), (int) ((0.5 - hit.getY()) * 128));
			case EAST -> new Vector2i((int) ((0.5 - hit.getZ()) * 128), (int) ((0.5 - hit.getY()) * 128));
			case UP -> new Vector2i((int) ((0.5 + hit.getX()) * 128), (int) ((0.5 + hit.getZ()) * 128));
			case DOWN -> new Vector2i((int) ((0.5 + hit.getX()) * 128), (int) ((0.5 - hit.getZ()) * 0.5));
			default -> null;
		};
	}
}
