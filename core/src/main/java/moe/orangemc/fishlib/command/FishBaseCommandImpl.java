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

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import moe.orangemc.fishlib.FishLibrary;
import moe.orangemc.fishlib.command.annotation.FishCommandExecutor;
import moe.orangemc.fishlib.command.annotation.FishCommandParameter;
import moe.orangemc.fishlib.command.util.FishLibCommandReader;
import moe.orangemc.fishlib.language.LanguageManager;
import org.apache.commons.lang.Validate;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FishBaseCommandImpl implements CommandExecutor, TabCompleter, FishBaseCommand {
	private final String prefix;

	private final Plugin clientPlugin;
	private final Map<String, GeneratedSubCommand> commandBaseMap = new HashMap<>();
	private final Map<String, GeneratedSubCommand> aliasesMap = new HashMap<>();

	private final CommandDispatcher<CommandSender> commandDispatcher;
	private String lastLabel;

	public FishBaseCommandImpl(Plugin client, String prefix) {
		this.prefix = prefix;
		this.clientPlugin = client;
		this.commandDispatcher = new CommandDispatcher<>();

		// help command
		registerCommand(new HelpCommand());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			return onCommand(sender, command, label, new String[]{"help"});
		}
		if (label != null) {
			this.lastLabel = label;
		}

		String commandName = args[0];

		GeneratedSubCommand subCommand;
		if (commandBaseMap.containsKey(commandName)) {
			subCommand = commandBaseMap.get(commandName);
		} else if (aliasesMap.containsKey(commandName)) {
			subCommand = aliasesMap.get(commandName);
		} else {
			return onCommand(sender, command, label, new String[]{"help"});
		}

		if (subCommand.getDelegate().getPermissionRequired() != null) {
			if (!sender.hasPermission(subCommand.getDelegate().getPermissionRequired())) {
				if (FishLibrary.getStandalonePlugin() == null) {
					sender.sendMessage(prefix + ChatColor.RED + "你没有使用此命令的权限(┙>∧<)┙へ┻┻");
				} else {
					LanguageManager languageManager = FishLibrary.getLanguageManager(FishLibrary.getStandalonePlugin());
					sender.sendMessage(languageManager.getTranslationBySender(sender, "command.no_permission"));
				}
				return true;
			}
		}

		try {
			commandDispatcher.execute(new FishLibCommandReader(String.join(" ", args).strip(), clientPlugin, sender), sender);
		} catch (CommandSyntaxException e) {
			if (CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				clientPlugin.getLogger().warning(sw.toString());
			}
			return onCommand(sender, command, label, new String[]{"help", args[0]});
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length > 1) {
			String commandName = args[0];

			GeneratedSubCommand generatedSubCommand;
			if (commandBaseMap.containsKey(commandName)) {
				generatedSubCommand = commandBaseMap.get(commandName);
			} else if (aliasesMap.containsKey(commandName)) {
				generatedSubCommand = aliasesMap.get(commandName);
			} else {
				return new ArrayList<>();
			}

			if (generatedSubCommand.getDelegate().getPermissionRequired() != null) {
				if (!sender.hasPermission(generatedSubCommand.getDelegate().getPermissionRequired())) {
					return new ArrayList<>();
				}
			}

			String[] subArgs = new String[args.length - 1];
			System.arraycopy(args, 1, subArgs, 0, subArgs.length);
		}

		return getCommandForTabComplete(args);
	}

	private List<String> getCommandForTabComplete(String[] args) {
		List<String> commandList = new ArrayList<>();
		commandBaseMap.forEach((name, commandBase) -> commandList.add(name));

		if (args.length == 1 && !args[0].isEmpty()) {
			commandList.removeIf((name) -> !name.startsWith(args[0]));
		}

		return commandList;
	}


	public void registerCommand(SubCommandBase commandBase) {
		Validate.notNull(commandBase, "commandBase cannot be null");

		GeneratedSubCommand command = new GeneratedSubCommand(this, commandBase);

		for (String alias : commandBase.getAliases()) {
			this.aliasesMap.put(alias, command);
		}

		this.commandBaseMap.put(commandBase.getName(), command);
	}

	private final class HelpCommand implements SubCommandBase {
		@Override
		public String getName() {
			return "help";
		}

		@Override
		public String getDescription() {
			if (FishBaseCommandImpl.this.clientPlugin == null) {
				return "获取帮助信息，也就是你眼前的这条消息.";
			}
			return "command.help.description";
		}

		@FishCommandExecutor
		public void execute(CommandSender sender) {
			commandBaseMap.forEach((name, cmd) -> {
				if (cmd.getDelegate().getPermissionRequired() == null || sender.hasPermission(cmd.getDelegate().getPermissionRequired())) {
					this.execute(sender, name);
				}
			});
		}

		@FishCommandExecutor
		public void execute(CommandSender sender, @FishCommandParameter(languageKey = "command.help.arg1", name = "command") String name) {

			GeneratedSubCommand subCommand = null;
			if (commandBaseMap.containsKey(name)) {
				subCommand = commandBaseMap.get(name);
			} else if (aliasesMap.containsKey(name)) {
				subCommand = aliasesMap.get(name);
			}
			if (subCommand == null) {
				execute(sender);
				return;
			}

			StringBuilder paramStringBuilder = new StringBuilder(ChatColor.YELLOW + "/").append(FishBaseCommandImpl.this.lastLabel).append(" ").append(subCommand.getDelegate().getName()).append(" ");
			String description;
			if (clientPlugin != null) {
				LanguageManager manager = FishLibrary.getLanguageManager(clientPlugin);

				subCommand.getNameMap().forEach((m, params) -> {
					for (String paramName : params) {
						paramStringBuilder.append("<").append(manager.getTranslationBySender(sender, paramName)).append("> ");
					}
				});

				description = manager.getTranslationBySender(sender, subCommand.getDelegate().getDescription());
			} else {
				subCommand.getNameMap().forEach((m, params) -> {
					for (String paramName : params) {
						paramStringBuilder.append("<").append(paramName).append("> ");
					}
				});

				description = subCommand.getDelegate().getDescription();
			}

			paramStringBuilder.append(ChatColor.GREEN).append("- ").append(ChatColor.GOLD).append(description);
			sender.sendMessage(paramStringBuilder.toString());
		}
	}

	public Plugin getClientPlugin() {
		return clientPlugin;
	}

	public CommandDispatcher<CommandSender> getCommandDispatcher() {
		return commandDispatcher;
	}
}
