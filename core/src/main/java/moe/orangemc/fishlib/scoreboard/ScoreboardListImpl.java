/*
 * FishLib, a Bukkit development library
 * Copyright (C) Lucky_fish0w0
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

package moe.orangemc.fishlib.scoreboard;

import moe.orangemc.fishlib.scoreboard.util.AntiFlickeringUtil;
import org.apache.commons.lang3.Validate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * An organized scoreboard based on {@link ArrayList}
 * Its display will change automatically when its content changes.
 * @see ArrayList
 * @see Scoreboard
 */
public class ScoreboardListImpl extends ArrayList<String> implements ScoreboardList {
	private final Scoreboard scoreboard;
	private final Objective objective;

	private final List<Player> playerDisplayedTo = new ArrayList<>();

	public ScoreboardListImpl(String title) {
		Validate.notNull(title, "title cannot be null");

		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = scoreboard.registerNewObjective(Integer.toHexString(hashCode()), "dummy", title, RenderType.INTEGER);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	@Override
	public boolean add(String s) {
		boolean result = super.add(s);
		updateScoreboard();
		return result;
	}

	@Override
	public boolean remove(Object o) {
		boolean result = super.remove(o);
		updateScoreboard();
		return result;
	}

	@Override
	public void clear() {
		super.clear();
		updateScoreboard();
	}

	@Override
	public String set(int i, String s) {
		if (i >= this.size()) {
			while (this.size() < i) {
				super.add("");
			}
			super.add(s);
			updateScoreboard();
			return "";
		}

		String result = super.set(i, s);
		updateScoreboard();
		return result;
	}

	@Override
	public void add(int i, String s) {
		super.add(i, s);
		updateScoreboard();
	}

	@Override
	public String remove(int i) {
		String result = super.remove(i);
		updateScoreboard();
		return result;
	}

	@Override
	protected void removeRange(int i, int i1) {
		super.removeRange(i, i1);
		updateScoreboard();
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean result = super.removeAll(collection);
		updateScoreboard();
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		boolean result = super.retainAll(collection);
		updateScoreboard();
		return result;
	}

	@Override
	public void replaceAll(UnaryOperator<String> unaryOperator) {
		super.replaceAll(unaryOperator);
		updateScoreboard();
	}

	@Override
	public void sort(Comparator<? super String> comparator) {
		super.sort(comparator);
		updateScoreboard();
	}

	@Override
	public boolean removeIf(Predicate<? super String> predicate) {
		boolean result = super.removeIf(predicate);
		updateScoreboard();
		return result;
	}

	@Override
	public void updateScoreboard() {
		Set<String> scoreEntries = scoreboard.getEntries();
		for (String entry : scoreEntries) {
			scoreboard.resetScores(entry);
		}

		int index = size() / 2;
		int emptyCounter = 0;
		for (String entry : this) {
			String place = entry;
			if (place.trim().isEmpty()) {
				emptyCounter ++;
				place = " ".repeat(Math.max(0, emptyCounter));
			}
			try {
				AntiFlickeringUtil.replaceScore(objective, index, place);
			} catch (IllegalArgumentException iae) {
				objective.getScore("A long entry.").setScore(index);
			}
			index --;
		}
	}

	@Override
	public void displayToPlayer(Player player) {
		Validate.notNull(player, "player cannot be null");

		if (playerDisplayedTo.contains(player)) {
			return;
		}
		player.setScoreboard(this.scoreboard);
		playerDisplayedTo.add(player);
	}

	@Override
	public void resetPlayerDisplay(Player player) {
		Validate.notNull(player, "player cannot be null");

		if (!playerDisplayedTo.contains(player)) {
			return;
		}
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		playerDisplayedTo.remove(player);
	}

	@Override
	public Object clone() {
		throw new UnsupportedOperationException();
	}
}
