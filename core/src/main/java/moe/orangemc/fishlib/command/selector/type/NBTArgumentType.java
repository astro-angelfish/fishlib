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

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.tr7zw.changeme.nbtapi.NBTReflectionUtil;
import moe.orangemc.fishlib.command.selector.Selector;
import moe.orangemc.fishlib.command.util.CommandSyntaxExceptionBuilder;

import org.bukkit.entity.Entity;

import java.util.Arrays;
import java.util.Set;

public class NBTArgumentType implements ComplexSelectorArgumentType<Entity> {
	private final NBTContainer nbt;

	private NBTArgumentType(NBTContainer nbt) {
		this.nbt = nbt;
	}

	public static NBTArgumentType from(Selector selector, StringReader prompt) throws CommandSyntaxException {
		int start = prompt.getCursor();
		int stack = 0;
		try {
			do {
				if (prompt.read() == '{') {
					stack++;
				}
				if (prompt.read() == '}') {
					stack--;
				}
				// Is this legal?
				if ((prompt.read() == ']' || prompt.read() == ',') && stack == 0) {
					break;
				}
			} while (stack > 0);
		} catch (Exception e) {
			CommandSyntaxExceptionBuilder.raise(selector.getOwner(), selector.getSender(), "command.nbt.invalid", "Invalid NBT format", prompt);
		}

		if (stack < 0) {
			CommandSyntaxExceptionBuilder.raise(selector.getOwner(), selector.getSender(), "command.nbt.invalid", "Invalid NBT format", prompt);
		}

		int end = prompt.getCursor();
		String nbtString = prompt.getString().substring(start, end);
		NBTContainer nbt = null;
		try {
			nbt = new NBTContainer(nbtString);
		} catch (Exception e) {
			CommandSyntaxExceptionBuilder.raise(selector.getOwner(), selector.getSender(), "command.nbt.invalid", "Invalid NBT format");
		}
		return new NBTArgumentType(nbt);
	}

	private static boolean isEqual(NBTCompound compA, NBTContainer compB, String key) {
		if (compA.getType(key) != compB.getType(key)) return false;
		return switch (compA.getType(key)) {
			case NBTTagByte -> compA.getByte(key).equals(compB.getByte(key));
			case NBTTagByteArray -> Arrays.equals(compA.getByteArray(key), compB.getByteArray(key));
			case NBTTagCompound -> compA.getCompound(key).equals(compB.getCompound(key));
			case NBTTagDouble -> compA.getDouble(key).equals(compB.getDouble(key));
			case NBTTagEnd -> true; // ??
			case NBTTagFloat -> compA.getFloat(key).equals(compB.getFloat(key));
			case NBTTagInt -> compA.getInteger(key).equals(compB.getInteger(key));
			case NBTTagIntArray -> Arrays.equals(compA.getIntArray(key), compB.getIntArray(key));
			case NBTTagList ->
					NBTReflectionUtil.getEntry(compA, key).toString().equals(NBTReflectionUtil.getEntry(compB, key).toString()); // Just string compare the 2 lists
			case NBTTagLong -> compA.getLong(key).equals(compB.getLong(key));
			case NBTTagShort -> compA.getShort(key).equals(compB.getShort(key));
			case NBTTagString -> compA.getString(key).equals(compB.getString(key));
		};
	}

	@Override
	public boolean matches(Entity val) {
		Set<String> keys = this.nbt.getKeys();
		NBTEntity ne = new NBTEntity(val);

		for (String key : keys) {
			if (!isEqual(ne, nbt, key)) {
				return false;
			}
		}

		return true;
	}
}
