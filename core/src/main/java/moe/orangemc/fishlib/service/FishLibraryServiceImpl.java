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

package moe.orangemc.fishlib.service;

import moe.orangemc.fishlib.FishLibraryPlugin;
import moe.orangemc.fishlib.command.CommandHelper;
import moe.orangemc.fishlib.command.CommandHelperImpl;
import moe.orangemc.fishlib.inventory.PluginInventoryManager;
import moe.orangemc.fishlib.inventory.PluginInventoryManagerImpl;
import moe.orangemc.fishlib.language.LanguageException;
import moe.orangemc.fishlib.language.LanguageManager;
import moe.orangemc.fishlib.language.LanguageManagerImpl;
import moe.orangemc.fishlib.map.MapManager;
import moe.orangemc.fishlib.map.MapManagerImpl;
import moe.orangemc.fishlib.messaging.MessagingManager;
import moe.orangemc.fishlib.messaging.MessagingManagerImpl;
import moe.orangemc.fishlib.scoreboard.ScoreboardListManager;
import moe.orangemc.fishlib.scoreboard.ScoreboardListManagerImpl;
import org.apache.commons.lang3.Validate;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class FishLibraryServiceImpl implements FishLibraryService {
	private final Map<Plugin, PluginInventoryManagerImpl> inventoryManagers = new HashMap<>();
	private final Map<Plugin, MessagingManagerImpl> messagingManagers = new HashMap<>();
	private final Map<Plugin, ScoreboardListManagerImpl> scoreboardListManagers = new HashMap<>();
	private final Map<Plugin, LanguageManagerImpl> languageManagers = new HashMap<>();
	private final Map<Plugin, MapManagerImpl> mapManagers = new HashMap<>();
	private final Map<Plugin, CommandHelperImpl> commandHelpers = new HashMap<>();

	@Override
	public PluginInventoryManager getPluginInventoryManager(Plugin plugin) {
		Validate.notNull(plugin);

		PluginInventoryManagerImpl pluginInventoryManager = inventoryManagers.get(plugin);
		if (pluginInventoryManager == null) {
			pluginInventoryManager = new PluginInventoryManagerImpl(plugin);
			inventoryManagers.put(plugin, pluginInventoryManager);
		}
		return pluginInventoryManager;
	}

	@Override
	public MessagingManager getMessagingManager(Plugin plugin) {
		Validate.notNull(plugin);

		MessagingManagerImpl messagingManager = messagingManagers.get(plugin);
		if (messagingManager == null) {
			messagingManager = new MessagingManagerImpl(plugin);
			messagingManagers.put(plugin, messagingManager);
		}
		return messagingManager;
	}

	@Override
	public ScoreboardListManager getScoreboardListManager(Plugin plugin) {
		Validate.notNull(plugin);

		ScoreboardListManagerImpl scoreboardListManager = scoreboardListManagers.get(plugin);
		if (scoreboardListManager == null) {
			scoreboardListManager = new ScoreboardListManagerImpl(plugin);
			scoreboardListManagers.put(plugin, scoreboardListManager);
		}
		return scoreboardListManager;
	}

	@Override
	public LanguageManager getLanguageManager(Plugin plugin) {
		Validate.notNull(plugin);

		LanguageManagerImpl languageManager = languageManagers.get(plugin);
		if (languageManager == null) {
			try {
				languageManager = new LanguageManagerImpl(plugin);
			} catch (LanguageException e) {
				return null;
			}
			languageManagers.put(plugin, languageManager);
		}
		return languageManager;
	}

	@Override
	public MapManager getMapManager(Plugin plugin) {
		Validate.notNull(plugin);

		MapManagerImpl mapManager = mapManagers.get(plugin);
		if (mapManager == null) {
			mapManager = new MapManagerImpl(plugin);
			mapManagers.put(plugin, mapManager);
		}
		return mapManager;
	}

	@Override
	public CommandHelper getCommandHelper(Plugin plugin) {
		Validate.notNull(plugin);

		CommandHelperImpl commandHelper = commandHelpers.get(plugin);
		if (commandHelper == null) {
			commandHelper = new CommandHelperImpl(plugin);
			commandHelpers.put(plugin, commandHelper);
		}
		return commandHelper;
	}

	public void clearPlugin(Plugin plugin) {
		Validate.notNull(plugin);

		inventoryManagers.remove(plugin);
		messagingManagers.remove(plugin);
		scoreboardListManagers.remove(plugin);
		languageManagers.remove(plugin);
		MapManagerImpl mapManager = mapManagers.remove(plugin);
		if (mapManager != null) {
			mapManager.onDestroy();
		}
	}

	@Override
	public Plugin getStandalonePlugin() {
		return FishLibraryPlugin.getInstance();
	}
}
