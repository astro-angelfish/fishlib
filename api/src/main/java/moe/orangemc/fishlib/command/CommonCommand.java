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
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import moe.orangemc.fishlib.FishLibrary;
import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;
import moe.orangemc.fishlib.command.annotation.FishCommandExecutor;
import moe.orangemc.fishlib.command.annotation.FishCommandParameter;
import moe.orangemc.fishlib.command.argument.ArgumentTypeManager;
import moe.orangemc.fishlib.language.LanguageManager;
import org.apache.commons.lang.Validate;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Common commands.
 * @see org.bukkit.command.CommandExecutor
 * @see org.bukkit.command.TabCompleter
 */
@ShouldNotBeImplemented
public final class CommonCommand implements CommandExecutor, TabCompleter {
	private final String prefix;

    private final Map<String, GeneratedSubCommand> commandBaseMap = new HashMap<>();
    private final Map<String, GeneratedSubCommand> aliasesMap = new HashMap<>();

	private final CommandDispatcher<CommandSender> commandDispatcher;

    public CommonCommand() {
        this("");
    }

    public CommonCommand(String prefix) {
	    if (prefix == null) {
    		prefix = "";
	    }

        this.prefix = prefix;
		this.commandDispatcher = new CommandDispatcher<>();

        // help command
        registerCommand(new HelpCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return onCommand(sender, command, label, new String[]{"help"});
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

        if (subCommand.commandBase.getPermissionRequired() != null) {
            if (!sender.hasPermission(subCommand.commandBase.getPermissionRequired())) {
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
		    commandDispatcher.execute(String.join(" ", args), sender);
	    } catch (CommandSyntaxException e) {
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

            if (generatedSubCommand.commandBase.getPermissionRequired() != null) {
                if (!sender.hasPermission(generatedSubCommand.commandBase.getPermissionRequired())) {
                    return new ArrayList<>();
                }
            }

            String[] subArgs = new String[args.length - 1];
            System.arraycopy(args, 1, subArgs, 0, subArgs.length);

			// TODO: We'll complete the full suggestion provider after Bukkit provides the cursor position of the user input box.
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

		GeneratedSubCommand command = new GeneratedSubCommand(commandBase);

		for (String alias : commandBase.getAliases()) {
			this.aliasesMap.put(alias, command);
		}

	    this.commandBaseMap.put(commandBase.getName(), command);
    }

    private class HelpCommand implements SubCommandBase {
        @Override
        public String getName() {
            return "help";
        }

        @Override
        public String getDescription() {
        	if (getProvidingPlugin() == null) {
        		return "获取帮助信息，也就是你眼前的这条消息.";
	        }
            return "command.help.description";
        }

		@FishCommandExecutor
		public void execute(CommandSender sender) {
			commandBaseMap.forEach((name, cmd) -> {
				if (cmd.commandBase.getPermissionRequired() == null || sender.hasPermission(cmd.commandBase.getPermissionRequired())) {
					this.execute(sender, name);
				}
			});
		}

		@FishCommandExecutor
        public void execute(CommandSender sender, String name) {

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

			StringBuilder paramStringBuilder = new StringBuilder(ChatColor.YELLOW + " ").append(CommonCommand.this).append(" ").append(subCommand.commandBase.getName()).append(" ");
			String description;
			if (subCommand.commandBase.getProvidingPlugin() != null) {
				LanguageManager manager = FishLibrary.getLanguageManager(subCommand.commandBase.getProvidingPlugin());

				subCommand.nameMap.forEach((m, params) -> {
					for (String paramName : params) {
						paramStringBuilder.append("<").append(manager.getTranslationBySender(sender, paramName)).append("> ");
					}
				});

				description = manager.getTranslationBySender(sender, subCommand.commandBase.getDescription());
			} else {
				subCommand.nameMap.forEach((m, params) -> {
					for (String paramName : params) {
						paramStringBuilder.append("<").append(paramName).append("> ");
					}
				});

				description = subCommand.commandBase.getDescription();
			}

			paramStringBuilder.append(ChatColor.GREEN).append("- ").append(ChatColor.GOLD).append(description);
			sender.sendMessage(paramStringBuilder.toString());
		}
    }

	private class GeneratedSubCommand {
		private final Map<Method, List<String>> nameMap = new HashMap<>();

		private final SubCommandBase commandBase;

		public GeneratedSubCommand(SubCommandBase commandBase) {
			this.commandBase = commandBase;

			ArgumentTypeManager argumentTypeManager = FishLibrary.getCommandHelper(commandBase.getProvidingPlugin()).getArgumentTypeManager();

			LiteralArgumentBuilder<CommandSender> commandArgumentBuilder = LiteralArgumentBuilder.literal(commandBase.getName());
			CommandNode<CommandSender> node = CommonCommand.this.commandDispatcher.register(commandArgumentBuilder);

			for (Method cmdMethod : commandBase.getClass().getMethods()) {
				FishCommandExecutor annotation = cmdMethod.getAnnotation(FishCommandExecutor.class);
				if (annotation == null || cmdMethod.isSynthetic()) {
					continue;
				}
				if (cmdMethod.getReturnType() != void.class || cmdMethod.getParameterTypes()[0] != CommandSender.class) {
					throw new IllegalArgumentException("Invalid argument type or return type of method called " + cmdMethod.getName() + " in " + commandBase);
				}

				// We need to attach the root to the sub-command base.
				RequiredArgumentBuilder<CommandSender, ?> latestNode = null;
				RequiredArgumentBuilder<CommandSender, ?> root = null;

				Parameter[] parameters = cmdMethod.getParameters();
				nameMap.put(cmdMethod, new ArrayList<>());

				for (int i = 1; i < parameters.length; i++) {
					Parameter parameter = parameters[i];

					FishCommandParameter parameterAnnotation = parameter.getAnnotation(FishCommandParameter.class);
					if (parameterAnnotation.languageKey().isBlank() || commandBase.getProvidingPlugin() == null) {
						if (parameterAnnotation.defaultName().isBlank()) {
							nameMap.get(cmdMethod).add(parameter.getName());
							continue;
						}
						nameMap.get(cmdMethod).add(parameterAnnotation.defaultName());
					}
					nameMap.get(cmdMethod).add(parameterAnnotation.languageKey());

					RequiredArgumentBuilder<CommandSender, ?> currentParameterArgumentBuilder = RequiredArgumentBuilder.argument(parameter.getName(), argumentTypeManager.getCommandArgumentType(parameter.getType()));
					if (latestNode == null) {
						latestNode = currentParameterArgumentBuilder;
						root = latestNode;
					} else {
						latestNode = latestNode.then(currentParameterArgumentBuilder);
					}
				}

				MethodHandle handle;
				try {
					handle = MethodHandles.lookup().unreflect(cmdMethod);
				} catch (IllegalAccessException e) {
					throw new IllegalArgumentException("Invalid method: " + cmdMethod + " in command base: " + commandBase);
				}

				if (root == null) {
					commandArgumentBuilder.executes(context -> {
						CommandSender source = context.getSource();
						try {
							if (!annotation.permission().isEmpty() && !source.hasPermission(annotation.permission())) {
								throw new CommandFailException(commandBase.getProvidingPlugin(), "command.no_permission", "You do not have permission to perform that command.");
							}

							handle.invokeWithArguments(source);
						} catch (CommandFailException e) {
							context.getSource().sendMessage(e.toMessage(context.getSource()));
						} catch (CommandSyntaxException e) {
							throw e;
						} catch (Throwable t) {
							throw new RuntimeException(t);
						}
						return 0;
					});
				} else {
					latestNode.executes(context -> {
						try {
							CommandSender source = context.getSource();
							if (!annotation.permission().isEmpty() && !source.hasPermission(annotation.permission())) {
								throw new CommandFailException(commandBase.getProvidingPlugin(), "command.no_permission", "You do not have permission to perform that command.");
							}

							List<Object> argumentList = new ArrayList<>();
							argumentList.add(source);
							for (Parameter parameter : parameters) {
								argumentList.add(context.getArgument(parameter.getName(), parameter.getType()));
							}

							handle.invokeWithArguments(argumentList);
						} catch (CommandFailException e) {
							context.getSource().sendMessage(e.toMessage(context.getSource()));
						} catch (CommandSyntaxException e) {
							throw e;
						} catch (Throwable e) {
							throw new RuntimeException(e);
						}
						return 0;
					});
					commandArgumentBuilder.then(root);
				}
			}

			for (String alias : commandBase.getAliases()) {
				CommonCommand.this.commandDispatcher.register(LiteralArgumentBuilder.<CommandSender>literal(alias).redirect(node));
			}
		}
	}
}
