package me.benwithjamin.commandframework;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

/*
 * Project: me.benwithjamin.minigames.command | Author: BenWithJamIn#4547
 * Created: 14/02/2023 at 15:21
 */
public class CommandManager {
    private static JavaPlugin plugin;
    private static CommandMap commandMap;

    public CommandManager(JavaPlugin javaPlugin) {
        plugin = javaPlugin;

        try {
           Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
           bukkitCommandMap.setAccessible(true);
           commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch(Exception e) {
           e.printStackTrace();
        }
    }

    public static void registerCommand(Command command) {
        commandMap.register(plugin.getName().toLowerCase(), new BukkitCommand(command));
    }
}
