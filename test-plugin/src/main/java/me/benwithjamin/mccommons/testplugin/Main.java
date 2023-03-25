package me.benwithjamin.mccommons.testplugin;

import me.benwithjamin.mccommons.command.CommandManager;
import me.benwithjamin.mccommons.menu.MenuManager;
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