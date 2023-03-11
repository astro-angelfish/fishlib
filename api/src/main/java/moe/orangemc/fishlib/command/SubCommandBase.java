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

package moe.orangemc.fishlib.command;

import moe.orangemc.fishlib.FishLibrary;
import moe.orangemc.fishlib.annotation.CanImplement;

import org.bukkit.plugin.Plugin;

/**
 * The base of the sub-command
 * @see org.bukkit.command.CommandExecutor
 * @see org.bukkit.command.TabCompleter
 * @see org.bukkit.permissions.Permissible
 */
@CanImplement
public interface SubCommandBase {
    /**
     * Get the name of the sub-command
     * @return name of the sub-command
     */
    String getName();

    /**
     * Get the description of the sub-command
     * @return description of the sub-command
     */
    String getDescription();

    /**
     * Get the aliases of the sub-command
     * @return aliases of the sub-command
     */
    default String[] getAliases() {
        return new String[0];
    }

    /**
     * Get the permission of the sub-command
     * @return permission of the sub-command, null if no permission is required.
     */
    default String getPermissionRequired() {
        return null;
    }

	/**
	 * Called when the plugin is needed.
	 * @return the plugin provided the command.
	 */
	default Plugin getProvidingPlugin() {
		return FishLibrary.getStandalonePlugin();
	}
}
