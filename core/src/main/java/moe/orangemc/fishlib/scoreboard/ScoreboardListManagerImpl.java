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

package moe.orangemc.fishlib.scoreboard;

import org.apache.commons.lang3.Validate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ScoreboardListManagerImpl implements ScoreboardListManager {
	private final Plugin holder;
	private final Map<String, ScoreboardList> scoreboardMap = new HashMap<>();

	public ScoreboardListManagerImpl(Plugin plugin) {
		Objects.requireNonNull(plugin);

		holder = plugin;

		Bukkit.getScheduler().runTaskTimer(plugin, () -> scoreboardMap.forEach((name, scbList) -> {
			if (scbList != null) {
				scbList.updateScoreboard();
			}
		}), 10, 10);
	}

	@Override
	public ScoreboardList createScoreboard(String name, String title) {
		Validate.notNull(name, "name cannot be null");
		Validate.notNull(title, "title cannot be null");

		ScoreboardList scoreboardList;
		try {
			scoreboardList = new ScoreboardListImpl(title);
		} catch (NoSuchMethodError e) {
			scoreboardList = new LegacyScoreboardListImpl(title);
		}
		this.scoreboardMap.put(name, scoreboardList);
		return scoreboardList;
	}

	@Override
	public void destroyScoreboard(String name) {
		Validate.notNull(name, "name cannot be null");

		ScoreboardList scoreboardList = scoreboardMap.get(name);
		if (scoreboardList == null) {
			return;
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			scoreboardList.resetPlayerDisplay(p);
		}
		scoreboardMap.remove(name);
	}

	@Override
	public void destroyScoreboard(ScoreboardList scoreboardList) {
		Validate.notNull(scoreboardList, "scoreboardList cannot be null");

		AtomicReference<String> nameReference = new AtomicReference<>();
		scoreboardMap.forEach((name, list) -> {
			if (list == scoreboardList) {
				nameReference.set(name);
			}
		});

		if (nameReference.get() == null) {
			return;
		}

		destroyScoreboard(nameReference.get());
	}

	@Override
	public ScoreboardList getScoreboard(String name) {
		Validate.notNull(name, "name cannot be null");

		return scoreboardMap.get(name);
	}
}
