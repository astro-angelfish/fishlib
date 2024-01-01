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

package moe.orangemc.fishlib.messaging;

import moe.orangemc.fishlib.annotation.CanImplement;

import org.bukkit.entity.Player;

/**
 * The callback of bungee message or player message
 *
 * @see org.bukkit.plugin.messaging.PluginMessageListener
 */
@CanImplement
public interface MessageCallback<T> {
	/**
	 * Calls when bungee/player sends plugin message
	 *
	 * @param player player who send this message, null if this is a bungee channel.
	 * @param target message object
	 */
	void call(Player player, T target);

	/**
	 * Fetch the acceptable class of plugin message
	 *
	 * @return message class which is acceptable
	 */
	Class<T> getAcceptableClass();
}
