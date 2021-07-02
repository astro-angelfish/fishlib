# plugin-common
Fish's plugin common utils. It was developed because the fish is too lazy to do the same thing on the different plugins.

# Feature
This library provides a common command with auto-complete and child commands.

It also provides tools to make reflecting classes, creating custom gui and communicating with bungee easier.

# How to use
put
```xml
<dependency>
    <groupId>moe.orangemc.luckyfish</groupId>
    <artifactId>PluginCommons</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```
and
```xml
<repository>
    <id>luckyfish-repo</id>
    <url>https://raw.githubusercontent.com/Lucky-fish/mvn-repo/master/</url>
</repository>
```
into your ```pom.xml```. You would know where to put them.

For command, there is an example

```java
import CommonCommand;
import SubCommandBase;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MyPlugin extends JavaPlugin {
	private final CommonCommand cmd = new CommonCommand("[MyPlugin]");

	public void onEnable() {
		Objects.requireNonNull(getCommand("mycmd")).setExecutor(cmd);
		Objects.requireNonNull(getCommand("mycmd")).setTabCompleter(cmd);

		// This line can be put in anywhere you want.
		cmd.registerCommand(new CustomCommand());
	}
}

public class CustomCommand implements SubCommandBase {
	@Override
	public String getName() {
		return "custom";
	}

	@Override
	public String getDescription() {
		return "Custom subCommand";
	}

	@Override
	public String getUsage() {
		return "custom";
	}

	@Override
	public boolean execute(CommandSender sender, Command command, String[] strings) {
		sender.sendMessage("Hello :P");
		return true;
	}
}
```
For reflecting and others, I'll make a tutorial soon.
