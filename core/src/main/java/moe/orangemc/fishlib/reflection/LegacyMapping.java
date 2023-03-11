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

package moe.orangemc.fishlib.reflection;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public class LegacyMapping implements Mapping {
	private static final String MODERN_NMS_PREFIX = "net.minecraft";
	private final String LEGACY_NMS_PREFIX = "net.minecraft.server." + ReflectionUtil.getServerVersion();

	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private final Map<String, String> officialToLegacyMapping = new HashMap<>();

	{
		officialToLegacyMapping.put("net.minecraft.advancements.critereon.ItemInteractWithBlockTrigger", "CriterionTriggerInteractBlock");
		officialToLegacyMapping.put("net.minecraft.advancements.critereon.PickedUpItemTrigger", "CriterionTriggerThrownItemPickedUpByEntity");
		officialToLegacyMapping.put("net.minecraft.advancements.critereon.PlayerTrigger", "CriterionTriggerLocation");

		officialToLegacyMapping.put("net.minecraft.commands.synchronization.ArgumentTypeInfo", "ArgumentSerializer");

		officialToLegacyMapping.put("net.minecraft.commands.synchronization.brigadier.DoubleArgumentInfo", "ArgumentSerializerDouble");
		officialToLegacyMapping.put("net.minecraft.commands.synchronization.brigadier.FloatArgumentInfo", "ArgumentSerializerFloat");
		officialToLegacyMapping.put("net.minecraft.commands.synchronization.brigadier.IntegerArgumentInfo", "ArgumentSerializerInteger");
		officialToLegacyMapping.put("net.minecraft.commands.synchronization.brigadier.LongArgumentInfo", "ArgumentSerializerLong");

		officialToLegacyMapping.put("net.minecraft.commands.synchronization.ArgumentTypeInfos", "ArgumentSerializers");

		// TODO: Core mappings.

		// TODO: Data mappings.

		// TODO: Locale mappings.

		// TODO: NBT mappings.

		// TODO: Network mappings.

		// TODO: RecipeBook mappings.

		// TODO: Resources mappings.

		// TODO: Server mappings.

		// TODO: Sounds mappings.

		// TODO: Stats mappings.

		// TODO: Tags mappings.

		// TODO: Util mappings.

		// TODO: World mappings.

	}

	@Override
	public Class<?> getMinecraftClass(String fullName) throws ClassNotFoundException {
		throw new ClassNotFoundException("If you are interested in helping me, you can request a PR in https://github.com/astro-angelfish/fishlib", new UnsupportedOperationException("The offical to legacy mapping is currently incomplete."));
		// return Class.forName(LEGACY_NMS_PREFIX + "." + legacyAndOfficalMapping.get(fullName));
	}

	@Override
	public Class<?> getBukkitClass(String name) throws ClassNotFoundException {
		return Class.forName("org.bukkit.craftbukkit." + ReflectionUtil.getServerVersion() + "." + name);
	}
}
