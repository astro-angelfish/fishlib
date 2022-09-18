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

package moe.orangemc.fishlib.scoreboard;

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;

import org.bukkit.entity.Player;

import java.util.List;

@ShouldNotBeImplemented
public interface ScoreboardList extends List<String> {
	/**
	 * Display the scoreboard to player
	 * @param player the player to display the scoreboard
	 */
	void displayToPlayer(Player player);

	/**
	 * Reset the displaying scoreboard of player
	 * @param player the player to reset scoreboard
	 * @throws IllegalArgumentException when player is not displaying the scoreboard that belongs to the list.
	 */
	void resetPlayerDisplay(Player player);

	/**
	 * Set the title of the scoreboard
	 * @param title the new title of scoreboard
	 */
	void setTitle(String title);

	/**
	 * INTERNAL USE ONLY, DO NOT CALL IT.
	 */
	@SuppressWarnings("DeprecatedIsStillUsed")
	@Deprecated
	void updateScoreboard();
}
