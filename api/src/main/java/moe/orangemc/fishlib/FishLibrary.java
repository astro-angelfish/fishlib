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

package moe.orangemc.fishlib;

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;
import moe.orangemc.fishlib.command.CommandHelper;
import moe.orangemc.fishlib.inventory.PluginInventoryManager;
import moe.orangemc.fishlib.language.LanguageManager;
import moe.orangemc.fishlib.map.MapManager;
import moe.orangemc.fishlib.messaging.MessagingManager;
import moe.orangemc.fishlib.scoreboard.ScoreboardListManager;
import moe.orangemc.fishlib.service.FishLibraryService;

import org.bukkit.plugin.Plugin;

/**
 * General commons.
 */
@ShouldNotBeImplemented
public final class FishLibrary {
	private FishLibrary() {
		throw new UnsupportedOperationException();
	}

	private static FishLibraryService fishLibraryService = null;

	public static void setFishLibraryService(FishLibraryService fishLibraryService) {
		if (FishLibrary.fishLibraryService != null) {
			throw new IllegalArgumentException("Cannot overwrite a service.");
		}
		FishLibrary.fishLibraryService = fishLibraryService;
	}

	/**
	 * Get a {@see PluginInventoryManager} of the plugin
	 * @param plugin the plugin.
	 * @return the {@see PluginInventoryManager} of the plugin.
	 */
	public static PluginInventoryManager getPluginInventoryManager(Plugin plugin) {
		return fishLibraryService.getPluginInventoryManager(plugin);
	}

	/**
	 * Get a {@see MessagingManager} of the plugin
	 * @param plugin the plugin.
	 * @return the {@see MessagingManager} of the plugin.
	 */
	public static MessagingManager getMessagingManager(Plugin plugin) {
		return fishLibraryService.getMessagingManager(plugin);
	}

	/**
	 * Get a {@see ScoreboardListManager} of the plugin
	 * @param plugin the plugin.
	 * @return the {@see ScoreboardListManager} of the plugin.
	 */
	public static ScoreboardListManager getScoreboardListManager(Plugin plugin) {
		return fishLibraryService.getScoreboardListManager(plugin);
	}

	/**
	 * Get a {@see LanguageManager} of the plugin
	 * @param plugin the plugin
	 * @return the {@see LanguageManager} of the plugin, or null if failed to initialize language manager.
	 */
	public static LanguageManager getLanguageManager(Plugin plugin) {
		return fishLibraryService.getLanguageManager(plugin);
	}

	/**
	 * Get a {@see MapManager} of the plugin
	 * @param plugin the plugin
	 * @return the {@see MapManager} of the plugin.
	 */
	public static MapManager getMapManager(Plugin plugin) {
		return fishLibraryService.getMapManager(plugin);
	}

	public static CommandHelper getCommandHelper(Plugin plugin) {
		return fishLibraryService.getCommandHelper(plugin);
	}

	/**
	 * Get the instance of the standalone plugin
	 * @return null if this library is compressed along with the caller plugin. Otherwise, the instance of the plugin instance is returned
	 */
	public static Plugin getStandalonePlugin() {
		return fishLibraryService.getStandalonePlugin();
	}
}
