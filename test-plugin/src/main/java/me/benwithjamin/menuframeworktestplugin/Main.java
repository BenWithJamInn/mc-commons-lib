package me.benwithjamin.menuframeworktestplugin;

import me.benwithjamin.commandframework.CommandManager;
import me.benwithjamin.menuframework.MenuManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        new CommandManager(this);
        new MenuManager(this);

        CommandManager.registerCommand(new TestCommand());
    }
}