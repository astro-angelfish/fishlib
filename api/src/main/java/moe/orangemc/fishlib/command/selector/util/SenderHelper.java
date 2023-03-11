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

package moe.orangemc.fishlib.command.selector.util;

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ProxiedCommandSender;

@ShouldNotBeImplemented
public final class SenderHelper {
	public static CommandSender getRealSender(CommandSender sender) {
		if (!(sender instanceof ProxiedCommandSender pcs)) {
			return sender;
		}
		return getRealSender(pcs.getCaller());
	}

	public static CommandSender getExecutedSender(CommandSender sender) {
		if (!(sender instanceof ProxiedCommandSender pcs)) {
			return sender;
		}
		return getExecutedSender(pcs.getCallee());
	}
}
