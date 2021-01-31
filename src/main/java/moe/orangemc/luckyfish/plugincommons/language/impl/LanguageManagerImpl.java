/*
 * Plugin Commons, a Bukkit development library
 * Copyright (C) Lucky_fish0w0
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package moe.orangemc.luckyfish.plugincommons.language.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import moe.orangemc.luckyfish.plugincommons.language.LanguageException;
import moe.orangemc.luckyfish.plugincommons.language.LanguageManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LanguageManagerImpl implements LanguageManager {
	private final Map<String, Map<String, String>> languageMap = new HashMap<>();
	private final File languageDirectory;

	public LanguageManagerImpl(Plugin plugin) throws LanguageException {
		try {
			languageDirectory = new File(plugin.getDataFolder(), "lang");
			if (!languageDirectory.exists()) {
				languageDirectory.mkdir();
			}

			loadLanguageFiles(plugin);
		} catch (Exception e) {
			throw new LanguageException("Failed to initialize language manager.", e);
		}
	}

	private void loadLanguageFiles(Plugin plugin) throws IOException {
		for (Locale locale : Locale.getAvailableLocales()) {
			String localeString = locale.toString().replaceAll("_?#[a-zA-Z0-9\\-_]+$", "").toLowerCase();
			File targetLanguageFile = new File(languageDirectory, localeString + ".json");

			if (!targetLanguageFile.exists()) {
				InputStream inputStream = plugin.getClass().getResourceAsStream("lang/" + localeString + ".json");
				if (inputStream == null) {
					continue;
				}
				FileUtils.copyToFile(inputStream, new File(languageDirectory, localeString + ".json"));
			}

			readLanguageFile(localeString, targetLanguageFile);
		}
	}

	private void readLanguageFile(String language, File languageFile) throws IOException {
		if (!languageFile.exists()) {
			return;
		}

		Gson gson = new Gson();
		try {
			Map<String, String> languageKeyMap = gson.fromJson(new FileReader(languageFile), new TypeToken<Map<String, String>>() {
			}.getType());
			this.languageMap.put(language, languageKeyMap);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTranslationBySender(CommandSender commandSender, String key, Object... args) {
		Validate.notNull(commandSender, "commandSender cannot be null");
		Validate.notNull(key, "key cannot be null");

		if (commandSender instanceof ProxiedCommandSender) {
			return getTranslationBySender(((ProxiedCommandSender) commandSender).getCaller(), key, args);
		}
		String locale;
		if (commandSender instanceof Player) {
			locale = ((Player) commandSender).getLocale().toLowerCase();
		} else {
			locale = "en_us";
		}

		return getTranslation(locale, key, args);
	}

	@Override
	public String getTranslation(String locale, String key, Object... args) {
		Validate.notNull(locale, "locale cannot be null");
		Validate.notNull(key, "key cannot be null");

		if (!locale.equals("en_us")) {
			if (!languageMap.containsKey(locale) || !languageMap.get(locale).containsKey(key)) {
				return getTranslation("en_us", key, args);
			}
		} else if (!languageMap.containsKey(locale) || !languageMap.get(locale).containsKey(key)) {
			return key;
		}

		return String.format(languageMap.get(locale).get(key), args);
	}
}
