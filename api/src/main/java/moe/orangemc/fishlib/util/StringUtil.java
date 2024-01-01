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

package moe.orangemc.fishlib.util;

public class StringUtil {
	private StringUtil() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks if the character is a blank character.
	 * @param c the character
	 * @return true if the character is a blank character
	 */
	public static boolean isBlankChar(char c) {
		return Character.isWhitespace(c) || c == '\ufeff' || c == '\u202a' || c == '\0' || c == '\u3164' || c == '\u2800' || c == '\u180e';
	}

	/**
	 * Checks if the string is blank.
	 * @param s the string
	 * @return true if the string is blank
	 */
	public static boolean isBlank(String s) {
		if (s == null) return true;
		for (char c : s.toCharArray()) {
			if (!isBlankChar(c)) return false;
		}
		return true;
	}
}
