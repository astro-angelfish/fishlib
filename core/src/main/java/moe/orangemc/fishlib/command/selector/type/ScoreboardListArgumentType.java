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

import moe.orangemc.fishlib.command.selector.util.EntityHelper;

import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Objective;

import java.util.List;

public class ScoreboardListArgumentType implements ComplexSelectorArgumentType<Entity> {
	private final List<ScoreboardArgumentType> scoreboardArgumentTypes;

	public ScoreboardListArgumentType(List<ScoreboardArgumentType> scoreboardArgumentTypes) {
		this.scoreboardArgumentTypes = scoreboardArgumentTypes;
	}

	@Override
	public boolean matches(Entity val) {
		for (ScoreboardListArgumentType.ScoreboardArgumentType arg : scoreboardArgumentTypes) {
			if (!arg.matches(val)) {
				return false;
			}
		}
		return true;
	}

	public static class ScoreboardArgumentType implements ComplexSelectorArgumentType<Entity> {
		private final Objective scoreboardObjective;
		private final IntegerRangeArgumentType rangeArgumentType;

		public ScoreboardArgumentType(Objective scoreboardObjective, IntegerRangeArgumentType rangeArgumentType) {
			this.scoreboardObjective = scoreboardObjective;
			this.rangeArgumentType = rangeArgumentType;
		}

		@Override
		public boolean matches(Entity val) {
			return rangeArgumentType.matches(scoreboardObjective.getScore(EntityHelper.getEntityName(val)).getScore());
		}
	}
}
