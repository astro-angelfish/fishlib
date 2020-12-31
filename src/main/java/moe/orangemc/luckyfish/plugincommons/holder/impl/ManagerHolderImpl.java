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

package moe.orangemc.luckyfish.plugincommons.holder.impl;

import moe.orangemc.luckyfish.plugincommons.PluginCommons;
import moe.orangemc.luckyfish.plugincommons.holder.ManagerHolder;
import moe.orangemc.luckyfish.plugincommons.inventory.PluginInventoryManager;
import moe.orangemc.luckyfish.plugincommons.inventory.impl.PluginInventoryManagerImpl;
import moe.orangemc.luckyfish.plugincommons.language.LanguageException;
import moe.orangemc.luckyfish.plugincommons.language.LanguageManager;
import moe.orangemc.luckyfish.plugincommons.language.impl.LanguageManagerImpl;
import moe.orangemc.luckyfish.plugincommons.messaging.MessagingManager;
import moe.orangemc.luckyfish.plugincommons.messaging.impl.MessagingManagerImpl;
import moe.orangemc.luckyfish.plugincommons.scoreboard.ScoreboardListManager;
import moe.orangemc.luckyfish.plugincommons.scoreboard.impl.ScoreboardListManagerImpl;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class ManagerHolderImpl implements ManagerHolder {
	private final Map<Plugin, PluginInventoryManager> inventoryManagers = new HashMap<>();
	private final Map<Plugin, MessagingManager> messagingManagers = new HashMap<>();
	private final Map<Plugin, ScoreboardListManager> scoreboardListManagers = new HashMap<>();
	private final Map<Plugin, LanguageManager> languageManagers = new HashMap<>();

	public ManagerHolderImpl() {
		PluginCommons.setManagerHolder(this);
	}

	@Override
	public PluginInventoryManager getPluginInventoryManager(Plugin plugin) {
		Validate.notNull(plugin);

		PluginInventoryManager pluginInventoryManager = inventoryManagers.get(plugin);
		if (pluginInventoryManager == null) {
			pluginInventoryManager = new PluginInventoryManagerImpl(plugin);
			inventoryManagers.put(plugin, pluginInventoryManager);
		}
		return pluginInventoryManager;
	}

	@Override
	public MessagingManager getMessagingManager(Plugin plugin) {
		Validate.notNull(plugin);

		MessagingManager messagingManager = messagingManagers.get(plugin);
		if (messagingManager == null) {
			messagingManager = new MessagingManagerImpl(plugin);
			messagingManagers.put(plugin, messagingManager);
		}
		return messagingManager;
	}

	@Override
	public ScoreboardListManager getScoreboardListManager(Plugin plugin) {
		Validate.notNull(plugin);

		ScoreboardListManager scoreboardListManager = scoreboardListManagers.get(plugin);
		if (scoreboardListManager == null) {
			scoreboardListManager = new ScoreboardListManagerImpl(plugin);
			scoreboardListManagers.put(plugin, scoreboardListManager);
		}
		return scoreboardListManager;
	}

	@Override
	public LanguageManager getLanguageManager(Plugin plugin) {
		Validate.notNull(plugin);

		LanguageManager languageManager = languageManagers.get(plugin);
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
}
