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

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import moe.orangemc.fishlib.util.StringUtil;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class FishUTFStringArgumentType implements ArgumentType<String> {
	@Override
	public String parse(StringReader reader) throws CommandSyntaxException {
		return readUTFString(reader);
	}

	public static String readUTFString(StringReader reader, Predicate<Character> matcher) throws CommandSyntaxException {
		if (reader.peek() == '"' || reader.peek() == '\'') {
			return reader.readQuotedString();
		}

		int start = reader.getCursor();
		while (matcher.test(reader.peek()) && reader.canRead()) {
			reader.skip();
		}

		return reader.getString().substring(start, reader.getCursor());
	}

	public static String readUTFString(StringReader reader) throws CommandSyntaxException {
		return readUTFString(reader, c -> !StringUtil.isBlankChar(c));
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
		return ArgumentType.super.listSuggestions(context, builder);
	}

	@Override
	public Collection<String> getExamples() {
		return ArgumentType.super.getExamples();
	}
}
