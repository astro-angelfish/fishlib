# FishLib

Fish's plugin common utils. It was developed because the fish is too lazy to do the same things on the different
plugins.

Still under construction. No compatibility is guaranteed. But feel free to contribute if you have encountered a bug and
sure it is not a compatibility issue.

## Get Started

You might need to clone this repository directly because we don't have a maven repository yet.

```shell
git clone https://github.com/astro-angelfish/fishlib.git
cd fishlib
mvn clean install
```

Then add the dependency to your pom.xml:

```xml
<dependency>
    <groupId>moe.orangemc</groupId>
    <artifactId>fishlib</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```

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
import moe.orangemc.fishlib.command.FishBaseCommand;
import moe.orangemc.fishlib.command.CommandHelper;
import moe.orangemc.fishlib.FishLibrary;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		FishBaseCommand cmd = FishLibrary.getCommandHelper(this).buildAndRegisterCommand(getCommand("test"));
		cmd.registerCommand(new TestCommand());
	}
}
```

Others you could dive into the source code and explore them. I'll create a document for them.
