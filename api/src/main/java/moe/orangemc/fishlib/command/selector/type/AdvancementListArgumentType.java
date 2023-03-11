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

import org.apache.commons.lang3.Validate;

import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdvancementListArgumentType implements ComplexSelectorArgumentType<Entity> {
	private final List<AdvancementArgumentType> advancementArgumentTypeList;

	public AdvancementListArgumentType(List<AdvancementArgumentType> advancementArgumentTypeList) {
		this.advancementArgumentTypeList = advancementArgumentTypeList;
	}

	@Override
	public boolean matches(Entity val) {
		if (!(val instanceof Player p)) {
			return false;
		}

		for (AdvancementArgumentType arg : advancementArgumentTypeList) {
			if (!arg.matches(p)) {
				return false;
			}
		}
		return true;
	}

	public static class AdvancementArgumentType implements ComplexSelectorArgumentType<Entity> {
		private final Advancement advancement;
		private final Map<String, Boolean> criteriaMap;
		private final boolean invert;

		public AdvancementArgumentType(Advancement advancement, boolean invert) {
			this(advancement, null, invert);
		}
		public AdvancementArgumentType(Advancement advancement, Map<String, Boolean> criteriaMap) {
			this(advancement, criteriaMap, false);
		}

		public AdvancementArgumentType(Advancement advancement, Map<String, Boolean> criteriaMap, boolean invert) {
			Validate.notNull(advancement, "Advancement cannot be null");
			this.advancement = advancement;
			Validate.notNull(advancement, "Cannot find advancement");

			this.criteriaMap = criteriaMap;
			this.invert = invert;
		}

		@Override
		public boolean matches(Entity val) {
			if (!(val instanceof Player p)) {
				return !invert;
			}

			AdvancementProgress ap = p.getAdvancementProgress(advancement);
			if (criteriaMap == null) {
				return ap.isDone() ^ invert;
			}
			AtomicBoolean match = new AtomicBoolean(true);
			criteriaMap.forEach((name, inverted) -> match.set(match.get() && (inverted ^ ap.getAwardedCriteria().contains(name))));

			return match.get();
		}
	}
}
