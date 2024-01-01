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

import com.mojang.brigadier.arguments.ArgumentType;
import moe.orangemc.fishlib.annotation.CanImplement;

/**
 * The base of the sub-command, basic element of command in fishlib
 * <br>
 * Use {@link moe.orangemc.fishlib.command.annotation.FishCommandExecutor} and {@link moe.orangemc.fishlib.command.annotation.FishCommandParameter} on a method
 * to define a behavior of the command with specified argument.
 * <br>
 * The first parameter of the method should be {@link org.bukkit.command.CommandSender}
 * <br>
 * Rest of the parameters should be annotated with {@link moe.orangemc.fishlib.command.annotation.FishCommandParameter} and
 * their types should be registered using {@link moe.orangemc.fishlib.command.argument.ArgumentTypeManager#registerCommandArgumentType(ArgumentType, Class)}
 * <br>
 * The method should have a void return type.
 * <br>
 * The following is an example of proper definition of a command
 * <pre>
 *   {@literal @}FishCommandExecutor public void testCommand(CommandSender sender, {@literal @}FishCommandParameter("entity") EntityList entities)
 *   { /* implementation *&#47 }
 * </pre>
 *
 * @see org.bukkit.command.CommandExecutor
 * @see org.bukkit.command.TabCompleter
 * @see org.bukkit.permissions.Permissible
 */
@CanImplement
public interface SubCommandBase {
	/**
	 * Get the name of the sub-command
	 *
	 * @return name of the sub-command
	 */
	String getName();

	/**
	 * Get the description of the sub-command
	 *
	 * @return description of the sub-command
	 */
	String getDescription();

	/**
	 * Get the aliases of the sub-command. Very similar to aliases in plugin.yml
	 *
	 * @return aliases of the sub-command
	 */
	default String[] getAliases() {
		return new String[0];
	}

	/**
	 * Get the permission of the sub-command.
	 * <p>
	 *
	 * Will be checked with {@link org.bukkit.permissions.Permissible#hasPermission(String)}
	 *
	 * @see org.bukkit.permissions.Permissible
	 * @return permission of the sub-command, null if no permission is required.
	 */
	default String getPermissionRequired() {
		return null;
	}
}
