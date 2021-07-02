/*
 * Plugin Commons, a Bukkit development library
 * Copyright (C) Lucky_fish0w0
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package moe.orangemc.plugincommons.scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

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
}
