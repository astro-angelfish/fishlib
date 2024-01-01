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

package moe.orangemc.fishlib.scoreboard.util;

import org.bukkit.scoreboard.Objective;

public class AntiFlickeringUtil {
	public static String getEntryFromScore(Objective o, int score) {
		if (o == null) return null;
		if (!hasScoreTaken(o, score)) return null;
		for (String s : o.getScoreboard().getEntries()) {
			if (o.getScore(s).getScore() == score) {
				return o.getScore(s).getEntry();
			}
		}
		return null;
	}

	public static boolean hasScoreTaken(Objective o, int score) {
		for (String s : o.getScoreboard().getEntries()) {
			if (o.getScore(s).getScore() == score) {
				return true;
			}
		}
		return false;
	}

	public static void replaceScore(Objective o, int score, String name) {
		if (hasScoreTaken(o, score)) {
			if (getEntryFromScore(o, score).equals(name)) {
				return;
			}
			if (!(getEntryFromScore(o, score).equals(name))) {
				o.getScoreboard().resetScores(getEntryFromScore(o, score));
			}
		}
		o.getScore(name).setScore(score);
	}
}
