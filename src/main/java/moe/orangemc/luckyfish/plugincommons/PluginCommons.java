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

package moe.orangemc.luckyfish.plugincommons;

import moe.orangemc.luckyfish.plugincommons.holder.ManagerHolder;
import moe.orangemc.luckyfish.plugincommons.holder.impl.ManagerHolderImpl;
import moe.orangemc.luckyfish.plugincommons.inventory.PluginInventoryManager;
import moe.orangemc.luckyfish.plugincommons.language.LanguageManager;
import moe.orangemc.luckyfish.plugincommons.messaging.MessagingManager;
import moe.orangemc.luckyfish.plugincommons.scoreboard.ScoreboardListManager;
import org.bukkit.plugin.Plugin;

/**
 * General commons.
 */
public final class PluginCommons {
	private PluginCommons() {
		throw new UnsupportedOperationException();
	}

	private static ManagerHolder managerHolder = null;

	public static void setManagerHolder(ManagerHolder managerHolder) {
		if (PluginCommons.managerHolder != null) {
			throw new IllegalArgumentException("Cannot overwrite a manager holder.");
		}
		PluginCommons.managerHolder = managerHolder;
	}

	/**
	 * Get a {@see PluginInventoryManager} of the plugin
	 * @param plugin the plugin.
	 * @return the {@see PluginInventoryManager} of the plugin.
	 */
	public static PluginInventoryManager getPluginInventoryManager(Plugin plugin) {
		return managerHolder.getPluginInventoryManager(plugin);
	}

	/**
	 * Get a {@see MessagingManager} of the plugin
	 * @param plugin the plugin.
	 * @return the {@see MessagingManager} of the plugin.
	 */
	public static MessagingManager getMessagingManager(Plugin plugin) {
		return managerHolder.getMessagingManager(plugin);
	}

	/**
	 * Get a {@see ScoreboardListManager} of the plugin
	 * @param plugin the plugin.
	 * @return the {@see ScoreboardListManager} of the plugin.
	 */
	public static ScoreboardListManager getScoreboardListManager(Plugin plugin) {
		return managerHolder.getScoreboardListManager(plugin);
	}

	/**
	 * Get a {@see LanguageManager} of the plugin
	 * @param plugin the plugin
	 * @return the {@see LanguageManager} of the plugin, or null if failed to initialize language manager.
	 */
	public static LanguageManager getLanguageManager(Plugin plugin) {
		return managerHolder.getLanguageManager(plugin);
	}

	static {
		new ManagerHolderImpl();
	}
}
