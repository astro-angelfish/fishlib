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
 * The parameter of a command.
 * <br>
 * The command parameter should be annotated with this annotation.
 * <br>
 * The parameter type should be registered with {@link moe.orangemc.fishlib.command.argument.ArgumentTypeManager#registerCommandArgumentType(ArgumentType, Class)}.
 * <br>
 * If no valid parameter type found, an {@link IllegalArgumentException} will be thrown with corresponding message.
 * <br>
 * Built-in types:
 * <ul>
 *     <li>{@link org.bukkit.entity.Player}</li>
 *     <li>int</li>
 *     <li>double</li>
 *     <li>float</li>
 *     <li>long</li>
 *     <li>boolean</li>
 *     <li>{@link String}</li>
 *     <li>{@link moe.orangemc.fishlib.command.argument.type.EntityList}</li>
 *     <li>{@link String}[] for greedy parameter receptors</li>
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@ShouldNotBeImplemented
public @interface FishCommandParameter {
	String languageKey() default "";

	/**
	 * The name of the parameter.
	 * <br>
	 * Parameter name in class are replaced by JVM stack operators and cannot be retrieved by reflection.
	 * @return the name of the parameter
	 */
	String name();
}
