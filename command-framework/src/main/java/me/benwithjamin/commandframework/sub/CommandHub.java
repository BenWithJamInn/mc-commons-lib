package me.benwithjamin.commandframework.sub;

import me.benwithjamin.commandframework.MyCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Project: me.benwithjamin.minigames.command.sub | Author: BenWithJamIn#4547
 * Created: 14/02/2023 at 16:00
 */

/**
 * @see MyCommand for constructors
 */
public abstract class CommandHub extends MyCommand {
    private final List<SubCommand> subCommands = new ArrayList<>();

    /**
     * @param name The command hub name
     */
    public CommandHub(String name) {
        super(name, "");
    }

    /**
     * @param name The command hub name
     * @param aliases List of aliases of the command hub
     */
    public CommandHub(String name, String[] aliases) {
        super(name, aliases, "", new int[0]);
    }

    /**
     * @param name The command hub name
     * @param aliases List of aliases of the command hub
     * @param permission The permission required to execute the command hub
     */
    public CommandHub(String name, String[] aliases, String permission) {
        super(name, aliases, permission, "", new int[0]);
    }

    @Override
    public void execute(@NotNull CommandSender commandSender, @NotNull String[] args) {
        if (args.length == 0) {
            this.sendHelp(commandSender);
            return;
        }
        for (SubCommand subCommand : this.subCommands) {
            if (!subCommand.getName().equalsIgnoreCase(args[0]) || !subCommand.getName().contains(args[0])) {
                continue;
            }
            if (subCommand.getPermission() != null && !commandSender.hasPermission(subCommand.getPermission())) {
                commandSender.sendMessage("§cYou do not have permission to execute this command");
                return;
            }
            List<String> newArgs = Arrays.stream(args).collect(Collectors.toList());
            newArgs.remove(0);
            if (subCommand.getArgs().size() != 0 && !subCommand.getArgs().contains(newArgs.size())) {
                subCommand.sendHelp(commandSender);
                return;
            }
            subCommand.execute(commandSender, newArgs.toArray(new String[0]));
        }
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (args.length > 1) {
            for (SubCommand subCommand : this.subCommands) {
                if (!subCommand.checkName(args[0])) {
                    continue;
                }
                return subCommand.tabComplete(sender, alias, args);
            }
            return null;
        } else {
            List<String> subCommandNames = new ArrayList<>();
            for (SubCommand subCommand : this.subCommands) {
                if (subCommand.getPermission() == null || sender.hasPermission(subCommand.getPermission())) {
                    subCommandNames.add(subCommand.getName());
                }
            }
            return subCommandNames;
        }
    }

    @Override
    public void sendHelp(CommandSender sender) {
        List<String> helpMessages = new ArrayList<>();
        for (SubCommand subCommand : this.subCommands) {
            if (subCommand.getPermission() == null || sender.hasPermission(subCommand.getPermission())) {
                helpMessages.add(subCommand.getDescription());
            }
        }
        sender.sendMessage(helpMessages.toArray(new String[0]));
    }

    /**
     * Adds a sub command to the command hub
     *
     * @param subCommand The sub command to add
     */
    public void registerSubCommand(SubCommand subCommand) {
        this.subCommands.add(subCommand);
    }

    /**
     * Removes a sub command from the command hub
     *
     * @param subCommand The sub command to remove
     */
    public void unregisterSubCommand(SubCommand subCommand) {
        this.subCommands.remove(subCommand);
    }
}
