package me.benwithjamin.mccommons.testplugin;

import me.benwithjamin.mccommons.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/*
 * Project: me.benwithjamin.menuframeworktestplugin | Author: BenWithJamIn#4547
 * Created: 09/03/2023 at 21:27
 */
public class TestCommand extends Command {
    public TestCommand() {
        super("testmenu", "This is the test menu");
    }

    @Override
    public void execute(@NotNull CommandSender commandSender, @NotNull String[] args) {
        Player player = (Player) commandSender;
        new TestMenu(Bukkit.createInventory(player, 9*3), player, "first menu").open();
    }
}
