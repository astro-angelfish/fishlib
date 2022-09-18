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

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;

import org.bukkit.entity.Player;

@ShouldNotBeImplemented
public interface MessagingManager {
	/**
	 * Registers a type adapter
	 * @param target target class to be registered
	 * @param typeAdapter target type adapter to be registered
	 * @throws IllegalArgumentException when target class is already registered
	 */
	<T> void registerTypeAdapter(Class<T> target, TypeAdapter<T> typeAdapter);

	/**
	 * Registers a communication channel
	 * @param channel the channel to be registered
	 * @param callback callback when receive the message
	 * @param bungeeOnly true if the channel should be only used in bungee mode.
	 * @throws IllegalStateException when target channel can only be used in bungee mode but the server is not configured well/is not covered under bungee.
	 * @throws IllegalArgumentException when target channel is already registered
	 */
	<T> void registerChannel(String channel, MessageCallback<T> callback, boolean bungeeOnly);

	/**
	 * Sends a plugin message to player/bungee
	 * @param channel the channel to send message on
	 * @param targetPlayer the player to send message
	 * @param messageObject the message object to send message (Maybe you need to register a type adapter for it)
	 * @throws IllegalArgumentException when target channel is not registered
	 */
	void sendMessage(String channel, Player targetPlayer, Object messageObject);

	/**
	 * Checks if the message channel is registered
	 * @param channel the channel to be checked
	 * @return true if channel is registered, false on otherwise
	 */
	boolean isChannelRegistered(String channel);
}
