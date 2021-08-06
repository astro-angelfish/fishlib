/*
 * Plugin Commons, a Bukkit development library
 * Copyright (C) Lucky_fish0w0
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package moe.orangemc.plugincommons.scoreboard.impl;

import moe.orangemc.plugincommons.scoreboard.ScoreboardList;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * An organized scoreboard based on {@link LinkedList}
 * Its display will change automatically when its content changes.
 * @see LinkedList
 * @see Scoreboard
 */
public class ScoreboardListImpl extends LinkedList<String> implements ScoreboardList {
	private final Object holder = new Object();

	private final Plugin plugin;

	private final Scoreboard scoreboard;
	private final Scoreboard backupScoreboard;
	private final Objective objective;
	private final Objective backupObjective;

	private final List<Player> playerDisplayedTo = new ArrayList<>();

	private int requests = 0;
	private boolean finished = true;

	public ScoreboardListImpl(Plugin plugin, String title) {
		Validate.notNull(title, "title cannot be null");
		Validate.notNull(plugin, "plugin cannot be null");

		this.plugin = plugin;

		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = scoreboard.registerNewObjective(Integer.toHexString(hashCode()), "dummy", title, RenderType.INTEGER);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		backupScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		backupObjective = backupScoreboard.registerNewObjective(Integer.toHexString(hashCode()), "dummy", title, RenderType.INTEGER);
		backupObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		scheduleUpdateScoreboard();
		Bukkit.getScheduler().runTaskTimer(plugin, this::updateScoreboard, 0, 0);
	}

	@Override
	public String removeFirst() {
		String result = super.removeFirst();
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public String removeLast() {
		String result = super.removeLast();
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public boolean add(String s) {
		boolean result = super.add(s);
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public boolean remove(Object o) {
		boolean result = super.remove(o);
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public void clear() {
		super.clear();
		scheduleUpdateScoreboard();
	}

	@Override
	public String set(int i, String s) {
		if (i >= this.size()) {
			while (this.size() < i) {
				super.add("");
			}
			super.add(s);
			scheduleUpdateScoreboard();
			return "";
		}

		String result = super.set(i, s);
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public void add(int i, String s) {
		super.add(i, s);
		scheduleUpdateScoreboard();
	}

	@Override
	public String remove(int i) {
		String result = super.remove(i);
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public String poll() {
		String result = super.poll();
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public String remove() {
		String result = super.remove();
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public String peekFirst() {
		String result = super.peekFirst();
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public String peekLast() {
		String result = super.peekLast();
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public String pollFirst() {
		String result = super.pollFirst();
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public String pollLast() {
		String result = super.pollLast();
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public void push(String s) {
		super.push(s);
		scheduleUpdateScoreboard();
	}

	@Override
	public String pop() {
		String result = super.pop();
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		boolean result = super.removeFirstOccurrence(o);
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		boolean result = super.removeLastOccurrence(o);
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	protected void removeRange(int i, int i1) {
		super.removeRange(i, i1);
		scheduleUpdateScoreboard();
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean result = super.removeAll(collection);
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		boolean result = super.retainAll(collection);
		scheduleUpdateScoreboard();
		return result;
	}

	@Override
	public void replaceAll(UnaryOperator<String> unaryOperator) {
		super.replaceAll(unaryOperator);
		scheduleUpdateScoreboard();
	}

	@Override
	public void sort(Comparator<? super String> comparator) {
		super.sort(comparator);
		scheduleUpdateScoreboard();
	}

	@Override
	public boolean removeIf(Predicate<? super String> predicate) {
		boolean result = super.removeIf(predicate);
		scheduleUpdateScoreboard();
		return result;
	}

	protected void scheduleUpdateScoreboard() {
		requests ++;
	}

	private void updateScoreboard() {
		// Use two scoreboards to avoid blinking.
		if (!finished) {
			return;
		}
		if (requests > 0) {
			finished = false;
			for (Player player : playerDisplayedTo) {
				player.setScoreboard(backupScoreboard);
			}
			Bukkit.getScheduler().runTaskLater(plugin, () -> {
				updateTargetScoreboard(scoreboard, objective);
				Bukkit.getScheduler().runTaskLater(plugin, () -> {
					for (Player player : playerDisplayedTo) {
						player.setScoreboard(scoreboard);
					}
					Bukkit.getScheduler().runTaskLater(plugin, () -> {
						updateTargetScoreboard(backupScoreboard, backupObjective);
						finished = true;
					}, 1);
				}, 1);
			}, 1);
			requests --;
		}
	}

	private void updateTargetScoreboard(Scoreboard scoreboard, Objective objective) {
		Set<String> scoreEntries = scoreboard.getEntries();
		for (String entry : scoreEntries) {
			scoreboard.resetScores(entry);
		}

		int index = size() / 2;
		int emptyCounter = 0;
		for (String entry : this) {
			String place = entry;
			if (place.trim().isEmpty()) {
				StringBuilder emptyPlaceHolderBuilder = new StringBuilder();
				for (int i = 0; i < emptyCounter; i ++) {
					emptyPlaceHolderBuilder.append(" ");
				}
				emptyCounter ++;
				place = emptyPlaceHolderBuilder.toString();
			}
			try {
				objective.getScore(place).setScore(index);
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

	@Override
	public int hashCode() {
		return holder.hashCode() ^ 114514;
	}
}
