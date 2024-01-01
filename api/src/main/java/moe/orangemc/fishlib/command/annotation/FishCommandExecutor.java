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

package moe.orangemc.fishlib.command.annotation;

import com.mojang.brigadier.arguments.ArgumentType;
import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Indicates that a method under {@link moe.orangemc.fishlib.command.SubCommandBase} is a command executor.
 * <br>
 *
 * The method should always have a {@link org.bukkit.command.CommandSender} as the first parameter.
 * <br>
 * The rest of parameters should be annotated with {@link FishCommandParameter}, and their types should be registered with {@link moe.orangemc.fishlib.command.argument.ArgumentTypeManager#registerCommandArgumentType(ArgumentType, Class)}.
 * <br>
 * Otherwise, you'll receive an {@link IllegalArgumentException} with corresponding message.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ShouldNotBeImplemented
public @interface FishCommandExecutor {
	/**
	 * The permission required to execute this command.
	 * <br>
	 * If empty, no permission is required.
	 * <br>
	 * Fishlib uses {@link org.bukkit.permissions.Permissible#hasPermission(String)} to check whether the sender has the permission.
	 *
	 * @see org.bukkit.permissions.Permissible#hasPermission(String)
	 * @return the permission required to execute this command
	 */
	String permission() default "";
}
