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

package moe.orangemc.fishlib.command.argument;

import com.mojang.brigadier.arguments.*;
import org.apache.commons.lang3.Validate;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ArgumentTypeManagerImpl implements ArgumentTypeManager {
	@SuppressWarnings("rawtypes")
	private final Map<Class, ArgumentType> argumentTypeMap = new HashMap<>();

	public ArgumentTypeManagerImpl() {
		registerDefaultArgumentTypes();
	}

	@Override
	public <T> void registerCommandArgumentType(ArgumentType<T> argumentType, Class<T> clazz) {
		Validate.notNull(argumentType, "argument type cannot be null");
		Validate.notNull(clazz, "argument class cannot be null");
		Validate.isTrue(!argumentTypeMap.containsKey(clazz), "duplicated argument type");
		Validate.isTrue(!Collection.class.isAssignableFrom(clazz) && !Map.class.isAssignableFrom(clazz) && !Set.class.isAssignableFrom(clazz), "collections or sets should not be used as argument type. use a wrapper class or array instead");

		argumentTypeMap.put(clazz, argumentType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ArgumentType<T> getCommandArgumentType(Class<T> clazz) {
		Validate.notNull(clazz, "argument class cannot be null");

		return argumentTypeMap.get(clazz);
	}

	private void registerDefaultArgumentTypes() {
		registerCommandArgumentType(new EntityArgumentType(), Entity[].class);
		registerCommandArgumentType(new PlayerArgumentType(), Player.class);
		registerCommandArgumentType(new StringArrayArgumentType(), String[].class);

		registerCommandArgumentType(IntegerArgumentType.integer(), int.class);
		registerCommandArgumentType(DoubleArgumentType.doubleArg(), double.class);
		registerCommandArgumentType(BoolArgumentType.bool(), boolean.class);
		registerCommandArgumentType(FloatArgumentType.floatArg(), float.class);
		registerCommandArgumentType(LongArgumentType.longArg(), long.class);
		registerCommandArgumentType(new FishUTFStringArgumentType(), String.class);
	}
}
