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
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import moe.orangemc.fishlib.FishLibrary;
import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;
import moe.orangemc.fishlib.command.annotation.FishCommandExecutor;
import moe.orangemc.fishlib.command.annotation.FishCommandParameter;
import moe.orangemc.fishlib.command.argument.ArgumentTypeManager;
import moe.orangemc.fishlib.language.LanguageManager;
import moe.orangemc.fishlib.util.SneakyExceptionRaiser;
import org.apache.commons.lang.Validate;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;

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
	private String lastLabel;

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
		    commandDispatcher.execute(String.join(" ", args).strip(), sender);
	    } catch (CommandSyntaxException e) {
			if (CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				subCommand.commandBase.getProvidingPlugin().getLogger().warning(sw.toString());
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

    private final class HelpCommand implements SubCommandBase {
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

			StringBuilder paramStringBuilder = new StringBuilder(ChatColor.YELLOW + "/").append(CommonCommand.this.lastLabel).append(" ").append(subCommand.commandBase.getName()).append(" ");
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

	private final class GeneratedSubCommand {
		private final Map<Method, List<String>> nameMap = new HashMap<>();

		private final SubCommandBase commandBase;

		public GeneratedSubCommand(SubCommandBase commandBase) {
			this.commandBase = commandBase;

			ArgumentTypeManager argumentTypeManager = FishLibrary.getCommandHelper(commandBase.getProvidingPlugin()).getArgumentTypeManager();

			List<LiteralArgumentBuilder<CommandSender>> builders = new ArrayList<>();
			builders.add(LiteralArgumentBuilder.literal(commandBase.getName()));
			for (String alias : commandBase.getAliases()) {
				builders.add(LiteralArgumentBuilder.literal(alias));
			}

			// Arguments become immutable after concatenating to each other, so we had to collect them as a list first.
			Map<String, ArgumentBuilder<CommandSender, ?>> argumentBuilderMap = new HashMap<>();
			Map<ArgumentBuilder<CommandSender, ?>, List<List<ArgumentBuilder<CommandSender, ?>>>> roots = new HashMap<>();

			for (LiteralArgumentBuilder<CommandSender> builder : builders) {
				// Iterate all executor methods as options
				for (Method method : commandBase.getClass().getMethods()) {
					if (!method.isAnnotationPresent(FishCommandExecutor.class)) {
						continue;
					}
					if (method.isSynthetic() || (method.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
						continue;
					}
					if (method.getReturnType() != void.class || method.getParameterTypes()[0] != CommandSender.class) {
						throw new IllegalArgumentException("Invalid argument type or return type of method called " + method.getName() + " in " + commandBase);
					}
					FishCommandExecutor executorAnnotation = method.getAnnotation(FishCommandExecutor.class);

					nameMap.put(method, new ArrayList<>());

					// Collect arguments, but not register them to argument chain directly. Instead we need to put them into a list.
					Parameter[] parameters = method.getParameters();
					boolean first = true;
					List<ArgumentBuilder<CommandSender, ?>> argumentChain = new LinkedList<>();

					for (Parameter parameter : parameters) {
						ArgumentType<?> type = argumentTypeManager.getCommandArgumentType(parameter.getType());
						if (parameter.getType() != CommandSender.class) {
							if (type == null) {
								throw new IllegalArgumentException("Unknown argument type: " + parameter.getType().getName() + ", you might want to register it with " + ArgumentTypeManager.class + " first.");
							}
							if (parameter.getAnnotation(FishCommandParameter.class) == null) {
								throw new IllegalArgumentException("Missing @CommandParameter annotation on parameter " + parameter.getName());
							}
						} else if (!first) {
							throw new IllegalArgumentException("CommandSender must be the first parameter of method " + method.getName() + " in " + commandBase);
						} else {
							first = false;
							continue;
						}

						FishCommandParameter annotation = parameter.getAnnotation(FishCommandParameter.class);
						if (annotation.name().isBlank()) {
							throw new IllegalArgumentException("Missing name in @CommandParameter annotation on parameter " + parameter.getName() + " of method " + method.getName() + " in " + commandBase);
						}

						if (annotation.languageKey().isBlank() || commandBase.getProvidingPlugin() == null) {
							nameMap.get(method).add(annotation.name());
						} else {
							nameMap.get(method).add(annotation.languageKey());
						}

						if (argumentBuilderMap.containsKey(annotation.name())) {
							argumentChain.add(argumentBuilderMap.get(annotation.name()));
							continue;
						}

						RequiredArgumentBuilder<CommandSender, ?> next = RequiredArgumentBuilder.argument(annotation.name(), type);
						if (type instanceof SuggestionProvider<?> sp) {
							next.suggests((SuggestionProvider<CommandSender>) sp);
						}

						argumentBuilderMap.put(annotation.name(), next);
						argumentChain.add(next);
					}

					// Just redirect to our own function. MethodHandle can be faster than reflection (for the first few times? Reflections seem to generate dynamic classes when they are called frequently.)
					MethodHandle mh;
					try {
						mh = MethodHandles.lookup().unreflect(method);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					}

					if (argumentChain.isEmpty()) {
						builder.executes((context) -> {
							SneakyExceptionRaiser.anyCall(() -> mh.invokeWithArguments(commandBase, context.getSource()));
							return 0;
						});
					} else {
						for (Iterator<ArgumentBuilder<CommandSender, ?>> iterator = argumentChain.iterator(); iterator.hasNext(); ) {
							ArgumentBuilder<CommandSender, ?> argumentBuilder = iterator.next();
							if (!iterator.hasNext()) {
								argumentBuilder.executes((context) -> {
									SneakyExceptionRaiser.anyCall(() -> {
										List<Object> argumentList = new ArrayList<>();
										argumentList.add(commandBase);
										argumentList.add(context.getSource());

										for (int i = 1; i < parameters.length; i++) {
											Parameter parameter = parameters[i];
											FishCommandParameter commandParameter = parameter.getAnnotation(FishCommandParameter.class);
											argumentList.add(context.getArgument(commandParameter.name(), parameter.getType()));
										}
										mh.invokeWithArguments(argumentList);
									});
									return 0;
								}).requires((sender) -> {
									if (executorAnnotation.permission() == null) {
										return true;
									}
									return sender.hasPermission(executorAnnotation.permission());
								});
							}
						}

						if (!roots.containsKey(argumentChain.get(0))) {
							roots.put(argumentChain.get(0), new ArrayList<>());
						}

						// We do not want the disorder of the list which leads to registering unexpected null argument
						// And if there is a list in our collection is a sublist of our new collected arguments, we need to remove the older one
						// to avoid immutable argument issues.
						List<List<ArgumentBuilder<CommandSender, ?>>> content = roots.get(argumentChain.get(0));
						for (Iterator<List<ArgumentBuilder<CommandSender, ?>>> iterator = content.iterator(); iterator.hasNext(); ) {
							List<ArgumentBuilder<CommandSender, ?>> chain = iterator.next();
							if (Collections.indexOfSubList(argumentChain, chain) > -1) {
								iterator.remove();
								break;
							}
						}
						content.add(argumentChain);
					}

				}

				// Register all collected arguments.
				roots.forEach((r, list) -> {
					for (List<ArgumentBuilder<CommandSender, ?>> chain : list) {
						for (int i = chain.size() - 1; i >= 1; i--) {
							chain.get(i - 1).then(chain.get(i));
						}
					}
					builder.then(r.requires((sender) -> {
						if (commandBase.getPermissionRequired() == null) {
							return true;
						}
						return sender.hasPermission(commandBase.getPermissionRequired());
					}));
				});

				commandDispatcher.register(builder);
			}
		}
	}
}
