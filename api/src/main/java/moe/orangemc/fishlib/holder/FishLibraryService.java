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

package moe.orangemc.fishlib.holder;

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;
import moe.orangemc.fishlib.inventory.PluginInventoryManager;
import moe.orangemc.fishlib.language.LanguageManager;
import moe.orangemc.fishlib.map.MapManager;
import moe.orangemc.fishlib.messaging.MessagingManager;
import moe.orangemc.fishlib.scoreboard.ScoreboardListManager;

import org.bukkit.plugin.Plugin;

@ShouldNotBeImplemented
public interface FishLibraryService {
	/**
	 * Get a {@see PluginInventoryManager} of the plugin
	 * @param plugin the plugin.
	 * @return the {@see PluginInventoryManager} of the plugin.
	 */
	PluginInventoryManager getPluginInventoryManager(Plugin plugin);

	/**
	 * Get a {@see MessagingManager} of the plugin
	 * @param plugin the plugin.
	 * @return the {@see MessagingManager} of the plugin.
	 */
	MessagingManager getMessagingManager(Plugin plugin);

	/**
	 * Get a {@see ScoreboardListManager} of the plugin
	 * @param plugin the plugin.
	 * @return the {@see ScoreboardListManager} of the plugin.
	 */
	ScoreboardListManager getScoreboardListManager(Plugin plugin);

	/**
	 * Get a {@see LanguageManager} of the plugin
	 * @param plugin the plugin
	 * @return the {@see LanguageManager} of the plugin, or null if failed to initialize language manager.
	 */
	LanguageManager getLanguageManager(Plugin plugin);

	/**
	 * Get a {@see MapManager} of the plugin
	 * @param plugin the plugin
	 * @return the {@see MapManager} of the plugin.
	 */
	MapManager getMapManager(Plugin plugin);

	/**
	 * Get the instance of the standalone plugin
	 */
	Plugin getStandalonePlugin();
}
