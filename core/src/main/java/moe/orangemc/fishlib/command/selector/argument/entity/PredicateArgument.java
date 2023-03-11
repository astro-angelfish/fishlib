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

package moe.orangemc.fishlib.command.selector.argument.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import moe.orangemc.fishlib.command.CommandFailException;
import moe.orangemc.fishlib.command.selector.Selector;
import moe.orangemc.fishlib.command.selector.SelectorArgument;
import moe.orangemc.fishlib.reflection.ReflectionUtil;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.function.Predicate;

public class PredicateArgument implements SelectorArgument<NamespacedKey> {
	@Override
	public String getName() {
		return "predicate";
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public boolean matchEntity(Selector selector, Entity entity, NamespacedKey value, Boolean parallelResult) throws CommandSyntaxException {
		try {
			Object handle = ReflectionUtil.invokeMethod(entity, "getHandle");
			Object server = ReflectionUtil.invokeMethod(Bukkit.getServer(), "getServer");
			Object predicateManager = ReflectionUtil.invokeMethod(server.getClass().getSuperclass() ,server, "aH");

			Class<?> craftKeyClass = ReflectionUtil.getServerMapping().getBukkitClass("util.CraftNamespacedKey");
			Object predicate = ReflectionUtil.invokeMethod(predicateManager, "a", ReflectionUtil.invokeMethod(craftKeyClass, (Object) null, "toMinecraft", value));
			if (!(predicate instanceof Predicate p)) {
				return false;
			}

			World world = entity.getWorld();
			Object nmsWorld = ReflectionUtil.invokeMethod(world, "getHandle");

			Object lootTableBuilder = ReflectionUtil.newInstance(ReflectionUtil.getServerMapping().getMinecraftClass("net.minecraft.world.level.storage.loot.LootTableInfo.Builder"), nmsWorld);

			Class<?> lootContextParameters = ReflectionUtil.getServerMapping().getMinecraftClass("net.minecraft.world.level.storage.loot.parameters.LootContextParameters");
			Object entityParameter = ReflectionUtil.getField(lootContextParameters, null, "a");

			ReflectionUtil.invokeMethod(lootTableBuilder, "a", new Class[]{entityParameter.getClass(), Object.class}, entityParameter, handle);

			Object vec3dParameter = ReflectionUtil.getField(lootContextParameters, null, "f");
			Object vec3d = ReflectionUtil.invokeMethod(handle, "dd");
			ReflectionUtil.invokeMethod(lootTableBuilder, "a", new Class[]{entityParameter.getClass(), Object.class}, vec3dParameter, vec3d);

			Class<?> parameterSets = ReflectionUtil.getServerMapping().getMinecraftClass("net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets");
			Class<?> parameterSet = ReflectionUtil.getServerMapping().getMinecraftClass("net.minecraft.world.level.storage.loot.parameters.LootContextParameterSet");
			Object lootTableInfo = ReflectionUtil.invokeMethod(lootTableBuilder, "a", new Class[]{parameterSet}, ReflectionUtil.getField(parameterSets, null, "d"));

			return p.test(lootTableInfo) && (parallelResult == null || parallelResult);
		} catch (Exception e) {
			throw new CommandFailException(selector.getOwner(), "command.generic", "Failed to execute command", e);
		}
	}

	@Override
	public Class<NamespacedKey> getAcceptableClass() {
		return NamespacedKey.class;
	}
}
