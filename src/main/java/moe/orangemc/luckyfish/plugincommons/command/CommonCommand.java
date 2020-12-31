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

package moe.orangemc.luckyfish.plugincommons.command;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Common commands.
 * @see org.bukkit.command.CommandExecutor
 * @see org.bukkit.command.TabCompleter
 */
public final class CommonCommand implements CommandExecutor, TabCompleter {
    private final String prefix;

    private final Map<String, SubCommandBase> commandBaseMap = new HashMap<>();
    private final Map<String, SubCommandBase> aliasesMap = new HashMap<>();

    public CommonCommand() {
        this("");
    }

    public CommonCommand(String prefix) {
    	if (prefix == null) {
    		prefix = "";
	    }

        this.prefix = prefix;

        // help command
        registerCommand(new HelpCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return onCommand(sender, command, label, new String[]{"help"});
        }

        String commandName = args[0];

        SubCommandBase subCommandBase;
        if (commandBaseMap.containsKey(commandName)) {
            subCommandBase = commandBaseMap.get(commandName);
        } else if (aliasesMap.containsKey(commandName)) {
            subCommandBase = aliasesMap.get(commandName);
        } else {
            return onCommand(sender, command, label, new String[]{"help"});
        }

        if (subCommandBase.getPermissionRequired() != null) {
            if (!sender.hasPermission(subCommandBase.getPermissionRequired())) {
                sender.sendMessage(prefix + ChatColor.RED + "你没有使用此命令的权限(┙>∧<)┙へ┻┻");
                return true;
            }
        }

        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, subArgs.length);

        if (!subCommandBase.execute(sender, command, subArgs)) {
            return onCommand(sender, command, label, new String[]{"help", commandName});
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length > 1) {
            String commandName = args[0];

            SubCommandBase subCommandBase;
            if (commandBaseMap.containsKey(commandName)) {
                subCommandBase = commandBaseMap.get(commandName);
            } else if (aliasesMap.containsKey(commandName)) {
                subCommandBase = aliasesMap.get(commandName);
            } else {
                return new ArrayList<>();
            }

            if (subCommandBase.getPermissionRequired() != null) {
                if (!sender.hasPermission(subCommandBase.getPermissionRequired())) {
                    return new ArrayList<>();
                }
            }

            String[] subArgs = new String[args.length - 1];
            System.arraycopy(args, 1, subArgs, 0, subArgs.length);

            return subCommandBase.tabComplete(sender, subArgs);
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

    /**
     * Register a sub-command
     * @param commandBase the sub-command to be registered.
     */
    public void registerCommand(SubCommandBase commandBase) {
	    Validate.notNull(commandBase, "commandBase cannot be null");

        this.commandBaseMap.put(commandBase.getName(), commandBase);
        for (String alias : commandBase.getAliases()) {
            this.aliasesMap.put(alias, commandBase);
        }
    }

    private class HelpCommand implements SubCommandBase {
        @Override
        public String getName() {
            return "help";
        }

        @Override
        public String getDescription() {
            return "获取帮助信息，也就是你眼前的这条消息";
        }

        @Override
        public String getUsage() {
            return "[命令]";
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String[] args) {
            if (args.length > 1) {
                return new ArrayList<>();
            }

            return getCommandForTabComplete(args);
        }

        @Override
        public boolean execute(CommandSender sender, Command command, String[] args) {
            if (args.length == 0) {
                commandBaseMap.forEach((name, cmd) -> {
                    if (cmd.getPermissionRequired() == null || sender.hasPermission(cmd.getPermissionRequired())) {
                        this.execute(sender, command, new String[]{name});
                    }
                });
                return true;
            }

            String name = args[0];

            SubCommandBase commandBase = null;
            if (commandBaseMap.containsKey(name)) {
                commandBase = commandBaseMap.get(name);
            } else if (aliasesMap.containsKey(name)) {
                commandBase = aliasesMap.get(name);
            }
            if (commandBase == null) {
                return execute(sender, command, new String[0]);
            }

            sender.sendMessage(prefix + ChatColor.YELLOW + "/" + command.getName() + " " + commandBase.getName() + " " + commandBase.getUsage() + ChatColor.GREEN + " - " + ChatColor.GOLD + commandBase.getDescription());

            return true;
        }
    }
}
