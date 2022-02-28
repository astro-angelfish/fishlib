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

package moe.orangemc.fishlib.command;

import moe.orangemc.fishlib.annotation.CanImplement;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

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
     * Get the usage of the sub-command
     * @return usage of the sub-command
     */
    String getUsage();

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
     * Called when the command is executed
     * @param sender the sender who issued this command
     * @param command the command to be executed
     * @param args command arguments.
     * @return true if the argument is correct, otherwise the usage will be displayed to sender.
     */
    boolean execute(CommandSender sender, Command command, String[] args);

    /**
     * Called when someone tries to tab-complete
     * @param sender the sender who tries to tab-complete
     * @param args command arguments.
     * @return available complements.
     */
    default List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

	/**
	 * Called when the plugin is needed.
	 * @return the plugin provided the command.
	 */
	Plugin getProvidingPlugin();
}
