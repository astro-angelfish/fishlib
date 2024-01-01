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

import moe.orangemc.fishlib.command.argument.ArgumentTypeManager;
import moe.orangemc.fishlib.command.argument.ArgumentTypeManagerImpl;
import moe.orangemc.fishlib.command.selector.SelectorManager;
import moe.orangemc.fishlib.command.selector.SelectorManagerImpl;
import org.apache.commons.lang.Validate;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

public class CommandHelperImpl implements CommandHelper {
	private final Plugin owner;
	private final SelectorManager selectorManager;
	private final ArgumentTypeManager argumentTypeManager;

	public CommandHelperImpl(Plugin owner) {
		this.owner = owner;

		this.selectorManager = new SelectorManagerImpl(owner);
		this.argumentTypeManager = new ArgumentTypeManagerImpl();
	}

	@Override
	public SelectorManager getSelectorManager() {
		return selectorManager;
	}

	@Override
	public ArgumentTypeManager getArgumentTypeManager() {
		return argumentTypeManager;
	}

	@Override
	public FishBaseCommand buildAndRegisterCommand(PluginCommand command) {
		return buildAndRegisterCommand(command, "");
	}

	@Override
	public FishBaseCommand buildAndRegisterCommand(PluginCommand command, String prefix) {
		Validate.notNull(command, "command cannot be null");

		FishBaseCommandImpl result = new FishBaseCommandImpl(owner, prefix);
		command.setExecutor(result);
		command.setTabCompleter(result);

		return result;
	}
}
