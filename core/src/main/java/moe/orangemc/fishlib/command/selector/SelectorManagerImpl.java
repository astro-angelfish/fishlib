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

package moe.orangemc.fishlib.command.selector;

import moe.orangemc.fishlib.command.selector.argument.entity.NameArgument;
import moe.orangemc.fishlib.command.selector.argument.entity.PredicateArgument;
import moe.orangemc.fishlib.command.selector.argument.entity.TypeArgument;
import moe.orangemc.fishlib.command.selector.argument.entity.data.NBTArgument;
import moe.orangemc.fishlib.command.selector.argument.entity.data.PitchRotationArgument;
import moe.orangemc.fishlib.command.selector.argument.entity.data.YawRotationArgument;
import moe.orangemc.fishlib.command.selector.argument.location.*;
import moe.orangemc.fishlib.command.selector.argument.player.AdvancementArgument;
import moe.orangemc.fishlib.command.selector.argument.player.GameModeArgument;
import moe.orangemc.fishlib.command.selector.argument.player.LevelArgument;
import moe.orangemc.fishlib.command.selector.argument.scoreboard.ScoresArgument;
import moe.orangemc.fishlib.command.selector.argument.scoreboard.TagArgument;
import moe.orangemc.fishlib.command.selector.argument.scoreboard.TeamArgument;
import moe.orangemc.fishlib.command.selector.argument.trait.LimitArgument;
import moe.orangemc.fishlib.command.selector.argument.trait.SortArgument;
import moe.orangemc.fishlib.command.selector.argument.trait.sorter.ArbitrarySorter;
import moe.orangemc.fishlib.command.selector.argument.trait.sorter.FurthrestSorter;
import moe.orangemc.fishlib.command.selector.argument.trait.sorter.NearestSorter;
import moe.orangemc.fishlib.command.selector.argument.trait.sorter.RandomSorter;
import moe.orangemc.fishlib.command.selector.mode.*;
import moe.orangemc.fishlib.command.selector.type.*;
import moe.orangemc.fishlib.command.selector.util.Sorter;
import org.apache.commons.lang3.Validate;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SelectorManagerImpl implements SelectorManager {
	private final Plugin owner;

	private final Map<Character, SelectMode> selectModeMap = new HashMap<>();
	@SuppressWarnings("rawtypes")
	private final Map<String, SelectorArgument> argumentNameMap = new HashMap<>();
	@SuppressWarnings("rawtypes")
	private final Map<Class, SelectorArgumentTypeAdapter> argumentAdapterMap = new HashMap<>();
	private final Map<String, Sorter> sorterMap = new HashMap<>();

	public SelectorManagerImpl(Plugin owner) {
		this.owner = owner;

		registerDefaultArguments();
		registerDefaultSorters();
		registerDefaultSelectModes();
		registerDefaultTypeAdapters();
	}

	@Override
	public Selector createSelector(CommandSender commandSender) {
		Validate.notNull(commandSender, "sender cannot be null");

		return new SelectorImpl(owner, commandSender, selectModeMap, argumentNameMap, argumentAdapterMap, sorterMap);
	}

	@Override
	public <T> void registerArgument(SelectorArgument<T> argumentToBeRegistered) {
		Validate.notNull(argumentToBeRegistered, "argument cannot be null");
		Validate.isTrue(!argumentNameMap.containsKey(argumentToBeRegistered.getName()), "duplicated argument name");
		Class<T> clazz = argumentToBeRegistered.getAcceptableClass();
		Validate.isTrue(!Collection.class.isAssignableFrom(clazz) && !Map.class.isAssignableFrom(clazz) && !Set.class.isAssignableFrom(clazz), "collections or sets should not be used as argument type. use a wrapper class instead");

		argumentNameMap.put(argumentToBeRegistered.getName(), argumentToBeRegistered);
	}

	@Override
	public void registerSorter(Sorter sorter) {
		Validate.notNull(sorter, "sorter cannot be null");
		Validate.isTrue(!sorterMap.containsKey(sorter.getName()), "duplicated sorter name");

		sorterMap.put(sorter.getName(), sorter);
	}

	@Override
	public void registerSelectMode(SelectMode mode) {
		Validate.notNull(mode, "mode cannot be null");
		Validate.isTrue(!selectModeMap.containsKey(mode.getSymbol()), "duplicated mode symbol: " + mode.getSymbol() + " with mode: " + mode + ", already registered to: " + selectModeMap.get(mode.getSymbol()));

		selectModeMap.put(mode.getSymbol(), mode);
	}

	@Override
	public <T> void registerTypeAdapter(SelectorArgumentTypeAdapter<T> typeAdapter) {
		Validate.notNull(typeAdapter, "type adapter cannot be null");
		Class<T> clazz = typeAdapter.getProvidingClass();
		Validate.isTrue(!argumentAdapterMap.containsKey(clazz), "duplicated type adapter");
		Validate.isTrue(!Collection.class.isAssignableFrom(clazz) && !Map.class.isAssignableFrom(clazz) && !Set.class.isAssignableFrom(clazz), "collections or sets should not be used as argument type. use a wrapper class instead");

		argumentAdapterMap.put(typeAdapter.getProvidingClass(), typeAdapter);
	}

	private void registerDefaultArguments() {
		registerArgument(new NBTArgument());
		registerArgument(new PitchRotationArgument());
		registerArgument(new YawRotationArgument());

		registerArgument(new NameArgument());
		registerArgument(new PredicateArgument());
		registerArgument(new TypeArgument());

		registerArgument(new CoordinateXArgument());
		registerArgument(new CoordinateYArgument());
		registerArgument(new CoordinateZArgument());

		registerArgument(new DistanceArgument());

		registerArgument(new VolumeDistanceXArgument());
		registerArgument(new VolumeDistanceYArgument());
		registerArgument(new VolumeDistanceZArgument());

		registerArgument(new AdvancementArgument());
		registerArgument(new GameModeArgument());
		registerArgument(new LevelArgument());

		registerArgument(new ScoresArgument());
		registerArgument(new TagArgument());
		registerArgument(new TeamArgument());

		registerArgument(new LimitArgument());
		registerArgument(new SortArgument());
	}

	private void registerDefaultTypeAdapters() {
		registerTypeAdapter(new DoubleTypeAdapter());
		registerTypeAdapter(new GameModeTypeAdapter());
		registerTypeAdapter(new IntegerRangeTypeAdapter());
		registerTypeAdapter(new IntegerTypeAdapter());
		registerTypeAdapter(new NamespacedKeyTypeAdapter());
		registerTypeAdapter(new NBTTypeAdapter());
		registerTypeAdapter(new SorterTypeAdapter());
		registerTypeAdapter(new StringReaderAdapter());
		registerTypeAdapter(new TeamTypeAdapter());
		registerTypeAdapter(new ScoreboardListArgumentTypeAdapter());
		registerTypeAdapter(new AdvancementListTypeAdapter());
	}

	private void registerDefaultSelectModes() {
		registerSelectMode(new AllEntity());
		registerSelectMode(new AllPlayer());
		registerSelectMode(new NearestPlayer());
		registerSelectMode(new RandomEntity());
		registerSelectMode(new Self());
	}

	private void registerDefaultSorters() {
		registerSorter(new ArbitrarySorter());
		registerSorter(new FurthrestSorter());
		registerSorter(new NearestSorter());
		registerSorter(new RandomSorter());
	}
}
