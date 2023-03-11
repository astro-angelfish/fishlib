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

public class OfficialMapping implements Mapping {
	@Override
	public Class<?> getMinecraftClass(String fullName) throws ClassNotFoundException {
		return Class.forName(fullName);
	}

	@Override
	public Class<?> getBukkitClass(String name) throws ClassNotFoundException {
		return Class.forName("org.bukkit.craftbukkit." + ReflectionUtil.getServerVersion() + "." + name);
	}
}
