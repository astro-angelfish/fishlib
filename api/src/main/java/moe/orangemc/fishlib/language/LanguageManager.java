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

package moe.orangemc.fishlib.language;

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;

import org.bukkit.command.CommandSender;

/**
 * Language manager written in mind.
 */
@ShouldNotBeImplemented
public interface LanguageManager {
	/**
	 * Get translation string from a command sender
	 *
	 * @param commandSender command sender to get translation
	 * @param key           translation key
	 * @param args          translation arguments
	 * @return the translated string
	 */
	String getTranslationBySender(CommandSender commandSender, String key, Object... args);

	/**
	 * Get translation string by direct locale string
	 *
	 * @param locale locale string
	 * @param key    translation key
	 * @param args   translation arguments
	 * @return the translated string
	 */
	String getTranslation(String locale, String key, Object... args);
}
