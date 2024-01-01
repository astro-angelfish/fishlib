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
import moe.orangemc.fishlib.command.annotation.FishCommandExecutor;
import moe.orangemc.fishlib.command.annotation.FishCommandParameter;

/**
 * Common command, a wrapper of Bukkit command with brigadier.
 *
 * @see SubCommandBase
 * @see FishCommandExecutor
 * @see FishCommandParameter
 * @see org.bukkit.command.CommandExecutor
 * @see org.bukkit.command.TabCompleter
 */
@ShouldNotBeImplemented
public interface FishBaseCommand {
	/**
	 * Register a sub-command that should be executed.
	 *
	 * @param commandBase the sub-command to be registered.
	 */
	void registerCommand(SubCommandBase commandBase);
}
