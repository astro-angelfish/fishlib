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

package moe.orangemc.fishlib.command;

import moe.orangemc.fishlib.FishLibrary;
import moe.orangemc.fishlib.language.LanguageManager;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CommandFailException extends RuntimeException {
	private final Plugin owner;
	private final String languageKey;

	private final Object[] args;

	public CommandFailException(Plugin owner, String languageKey, String rawMessage, Object... args) {
		super(rawMessage, null, true, false);
		this.owner = owner;
		this.languageKey = languageKey;
		this.args = args;
	}

	public CommandFailException(Plugin owner, String languageKey, String rawMessage, Throwable cause, Object... args) {
		super(rawMessage, cause, true, true);
		this.owner = owner;
		this.languageKey = languageKey;
		this.args = args;
	}

	public String toMessage(CommandSender sender) {
		if (this.owner == null) {
			return this.getMessage();
		}
		LanguageManager lm = FishLibrary.getLanguageManager(this.owner);
		return lm.getTranslationBySender(sender, this.languageKey, args);
	}
}
