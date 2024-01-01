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

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;
import moe.orangemc.fishlib.command.argument.ArgumentTypeManager;
import moe.orangemc.fishlib.command.selector.SelectorManager;

import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

/**
 * A helper class for commands.
 */
@ShouldNotBeImplemented
public interface CommandHelper {
	/**
	 * Get the selector manager.
	 * @return the selector manager.
	 */
	SelectorManager getSelectorManager();

	/**
	 * Get the argument type manager.
	 * @return the argument type manager.
	 */
	ArgumentTypeManager getArgumentTypeManager();

	/**
	 * Build and register a command using {@link PluginCommand#setTabCompleter(TabCompleter)} and {@link PluginCommand#setExecutor(org.bukkit.command.CommandExecutor)}
	 * with an empty prefix.
	 *
	 * @param command the command to register
	 * @return the command instance.
	 */
	FishBaseCommand buildAndRegisterCommand(PluginCommand command);

	/**
	 * Build and register a command using {@link PluginCommand#setTabCompleter(TabCompleter)} and {@link PluginCommand#setExecutor(org.bukkit.command.CommandExecutor)}
	 * @param command the command to register
	 * @param prefix the prefix of the command
	 * @return the command instance.
	 */
	FishBaseCommand buildAndRegisterCommand(PluginCommand command, String prefix);
}
