package me.benwithjamin.mccommons.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/*
 * Project: me.benwithjamin.minigames.command | Author: BenWithJamIn#4547
 * Created: 14/02/2023 at 15:32
 */

/**
 * An implementation of bukkit commands, handles permissions, args handling and parsing function to {@link Command )
 */
public class BukkitCommand extends org.bukkit.command.Command {
    private final Command command;

    protected BukkitCommand(Command command) {
        super(command.getName(), command.getDescription(), command.getDescription(), Arrays.asList(command.getAliases()));
        this.command = command;
        this.setPermission(command.getPermission());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (this.command.getPermission() != null && !this.testPermissionSilent(sender)) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return true;
        }
        if (this.command.getArgs().size() != 0 && !this.command.getArgs().contains(args.length)) {
            this.command.sendHelp(sender);
            return true;
        }
        this.command.execute(sender, args);
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        List<String> returnList = this.command.tabComplete(sender, alias, args);
        if (returnList == null) {
            return super.tabComplete(sender, alias, args);
        }
        return returnList;
    }
}
