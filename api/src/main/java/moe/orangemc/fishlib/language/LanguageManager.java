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
import org.bukkit.entity.Player;

/**
 * Language manager written in mind.
 *
 * <br>
 * To use this, you need to place a language file named <b><i>xx_xx.json</i></b> (like <i>en_us.json</i>) into <b><i>lang</i></b> folder under
 * the data folder of your plugin.
 * <br>
 * The format of language file is very similar to <a href="https://minecraft.fandom.com/wiki/Resource_pack#Language">original language file of Minecraft</a>.
 * <br>
 * It uses key-pair values to store the translation data. The key is the translation key, and the value is the translated string.
 * <br>
 * An example is following:
 * <pre><code>
 * {
 *     "fishlib.command.no_permission": "You don't have permission to do this!",
 *     "fishlib.command.no_player": "You must be a player to do this!",
 *     "fishlib.command.no_subcommand": "No such sub-command!",
 *     "fishlib.command.no_subcommand_permission": "You don't have permission to do this sub-command!",
 * }
 * </code></pre>
 *
 * @see Player#getLocale()
 */
@ShouldNotBeImplemented
public interface LanguageManager {
	/**
	 * Get translation string using the locale from the command sender
	 * <br>
	 * If the command sender is not a player or the locale file is not found, en_us will be used
	 * <br>
	 * If the command sender is a player, the locale will be fetched by using {@link Player#getLocale()}
	 *
	 * @param commandSender command sender to get translation
	 * @param key           translation key
	 * @param args          translation arguments
	 * @return the translated string
	 */
	String getTranslationBySender(CommandSender commandSender, String key, Object... args);

	/**
	 * Get translation string by direct locale string.
	 * <br>
	 * If the locale file is not found, en_us will be used.
	 *
	 * @param locale locale string
	 * @param key    translation key
	 * @param args   translation arguments
	 * @return the translated string
	 */
	String getTranslation(String locale, String key, Object... args);
}
