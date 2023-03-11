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

package moe.orangemc.fishlib.command.selector;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;
import moe.orangemc.fishlib.command.CommandFailException;
import moe.orangemc.fishlib.command.argument.type.EntityList;
import moe.orangemc.fishlib.command.selector.context.SelectorContext;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.CompletableFuture;

@ShouldNotBeImplemented
public interface Selector {
	Plugin getOwner();
	CommandSender getSender();

	void updatePrompt(StringReader prompt);

	EntityList selectEntities() throws CommandSyntaxException, CommandFailException;

	CompletableFuture<Suggestions> generateSuggestions() throws CommandSyntaxException, CommandFailException;


	<T extends SelectorContext, U extends T> T getContext(Class<T> clazz, Class<U> defaultImplementation);
	<T extends SelectorContext> T getContext(Class<T> clazz);
}
