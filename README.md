# FishLib

Fish's plugin common utils. It was developed because the fish is too lazy to do the same things on the different
plugins.

Still under construction. No compatibility is guaranteed. But feel free to contribute if you have encountered a bug and
sure it is not a compatibility issue.

## Maven

```xml
<dependency>
	<groupId>moe.orangemc</groupId>
	<artifactId>fishlib-api</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<scope>provided</scope>
</dependency>
```

in `dependencies` tag of pom.xml

```xml
		<repository>
			<id>bunnytime-public</id>
			<url>http://maven.bunnytime.top/repository/bunnytime-public/</url>
		</repository>
```

in `repositories` tag of pom.xml. We are temporarily borrowing the repository of *BunnyTime*.

## Usage

### Command

```java
import moe.orangemc.fishlib.command.SubCommandBase;
import moe.orangemc.fishlib.command.annotation.FishCommandExecutor;
import moe.orangemc.fishlib.command.annotation.FishCommandParameter;


public class TestCommand implements SubCommandBase {
		@Override
	public String getName() {
		return "test";
	}

	@Override
	public String getPermission() {
		return "test";
	}

	@Override
	public String getUsage() {
		return "/test";
	}

	@Override
	public String getDescription() {
		return "test";
	}
	
	@FishCommandExecutor
	public void onCommand(CommandSender sender) {
		sender.sendMessage("test");
	}
	
	@FishCommandExecutor
	public void onCommand(CommandSender sender, @FishCommandParameter("arg1") String arg1) {
		sender.sendMessage("test " + arg1);
	}
}
```

And in the main class:

```java
import moe.orangemc.fishlib.command.CommonCommand;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		CommonCommand cmd = new CommonCommand("test");
		cmd.registerCommand(new TestCommand());
		getCommand("test").setExecutor(cmd);
	}
}
```

Others you could dive into the source code and explore them. I'll create a document for them.
