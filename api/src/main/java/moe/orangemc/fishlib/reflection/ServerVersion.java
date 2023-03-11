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

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class ServerVersion implements Comparable<ServerVersion> {
	private final String serverVersionString;

	private final int minecraftMajorVersion;
	private final int minecraftMinorVersion;
	private final int bukkitVersion;

	public ServerVersion(String serverVersionString) {
		this.serverVersionString = serverVersionString;

		StringReader sr = new StringReader(serverVersionString);
		try {
			sr.expect('v');
			minecraftMajorVersion = sr.readInt();
			sr.expect('_');
			minecraftMinorVersion = sr.readInt();
			sr.expect('_');
			sr.expect('R');
			bukkitVersion = sr.readInt();
		} catch (CommandSyntaxException e) {
			throw new IllegalArgumentException("Invalid version string.", e);
		}
	}

	@Override
	public String toString() {
		return serverVersionString;
	}

	@Override
	public int compareTo(ServerVersion o) {
		if (o.minecraftMajorVersion > this.minecraftMajorVersion) {
			return -1;
		} else if (o.minecraftMajorVersion < this.minecraftMajorVersion) {
			return 1;
		}

		if (o.minecraftMinorVersion > this.minecraftMinorVersion) {
			return -1;
		} else if (o.minecraftMinorVersion < this.minecraftMinorVersion) {
			return 1;
		}

		if (o.bukkitVersion > this.bukkitVersion) {
			return -1;
		} else if (o.bukkitVersion < this.bukkitVersion) {
			return 1;
		}

		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ServerVersion that = (ServerVersion) o;

		if (minecraftMajorVersion != that.minecraftMajorVersion) return false;
		if (minecraftMinorVersion != that.minecraftMinorVersion) return false;
		return bukkitVersion == that.bukkitVersion;
	}

	public boolean newer(ServerVersion serverVersion) {
		return this.compareTo(serverVersion) > 0;
	}
	public boolean older(ServerVersion serverVersion) {
		return this.compareTo(serverVersion) < 0;
	}

	@Override
	public int hashCode() {
		int result = minecraftMajorVersion;
		result = 31 * result + minecraftMinorVersion;
		result = 31 * result + bukkitVersion;
		return result;
	}
}
