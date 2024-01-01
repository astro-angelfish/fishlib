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

package moe.orangemc.fishlib.command.util;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import moe.orangemc.fishlib.FishLibrary;
import moe.orangemc.fishlib.language.LanguageManager;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CommandSyntaxExceptionBuilder {
	public static void raise(Plugin owner, CommandSender sender, String languageKey, String fallbackMessage, Object... args) throws CommandSyntaxException {
		Message errorMessage = () -> {
			if (owner == null) {
				return fallbackMessage;
			}

			LanguageManager lm = FishLibrary.getLanguageManager(owner);
			return lm.getTranslationBySender(sender, languageKey, args);
		};

		throw new CommandSyntaxException(new SimpleCommandExceptionType(errorMessage), errorMessage);
	}

	public static void raise(Plugin owner, CommandSender sender, String languageKey, String fallbackMessage, StringReader input, Object... args) throws CommandSyntaxException {
		Message errorMessage = () -> {
			if (owner == null) {
				return fallbackMessage;
			}

			LanguageManager lm = FishLibrary.getLanguageManager(owner);
			return lm.getTranslationBySender(sender, languageKey, args);
		};

		throw new CommandSyntaxException(new SimpleCommandExceptionType(errorMessage), errorMessage, input.getString(), input.getCursor());
	}
}
