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

package moe.orangemc.fishlib.reflection;

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;

/**
 * A mapping between Minecraft and Bukkit classes.
 */
@ShouldNotBeImplemented
public interface Mapping {
	/**
	 * Get the Minecraft class by its full name.
	 *
	 * @param fullName the full name of the class
	 * @return the class in Minecraft
	 * @throws ClassNotFoundException if the class is not found
	 */
	Class<?> getMinecraftClass(String fullName) throws ClassNotFoundException;

	/**
	 * Get the Bukkit class by its name.
	 *
	 * @param name the name of the class
	 * @return the class in Bukkit
	 * @throws ClassNotFoundException if the class is not found
	 */
	Class<?> getBukkitClass(String name) throws ClassNotFoundException;
}
