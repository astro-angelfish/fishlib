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

package moe.orangemc.fishlib.command.selector.type;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import moe.orangemc.fishlib.command.selector.Selector;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Team;

public class TeamTypeAdapter implements SelectorArgumentTypeAdapter<Team> {
	@Override
	public Team fromStringReader(Selector selector, StringReader sr) throws CommandSyntaxException {
		return Bukkit.getScoreboardManager().getMainScoreboard().getTeam(sr.readString());
	}

	@Override
	public Class<Team> getProvidingClass() {
		return Team.class;
	}
}
