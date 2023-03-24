package me.benwithjamin.mccommons.command;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Project: me.benwithjamin.minigames.command | Author: BenWithJamIn#4547
 * Created: 14/02/2023 at 15:23
 */
@Getter
public abstract class Command {
    private final String name;
    private final String[] aliases;
    @Nullable private final String permission;
    private final String description;
    private final List<Integer> args;

    /**
     * @param name The command name
     * @param description The description of the command
     */
    public Command(String name, String description) {
        this(name, new String[0], description, new int[]{0});
    }

    /**
     * @param name The command name
     * @param aliases List of aliases of the command
     * @param description The description of the command
     * @param args The number of arguments the command takes, empty if any
     */
    public Command(String name, String[] aliases, String description, int[] args) {
        this(name, aliases, null, description, args);
    }

    /**
     * @param name The command name
     * @param aliases List of aliases of the command
     * @param permission The permission required to execute the command
     * @param description The description of the command
     * @param args The number of arguments the command takes, empty if any
     */
    public Command(String name, String[] aliases, @Nullable String permission, String description, int[] args) {
        this.name = name;
        this.aliases = aliases;
        this.permission = permission;
        this.description = description;
        this.args = Arrays.stream(args).boxed().collect(Collectors.toList());

        CommandManager.registerCommand(this);
    }

    /**
     * Called when the command is executed
     *
     * @see org.bukkit.command.Command#execute(CommandSender, String, String[])
     */
    public abstract void execute(@NotNull CommandSender commandSender, @NotNull String[] args);

    /**
     * Called when a player tab completes this command
     *
     * @see org.bukkit.command.Command#execute(CommandSender, String, String[])
     */
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return null;
    }

    /**
     * Sends help text to the sender
     *
     * @param sender The command sender to send a message
     */
    public void sendHelp(CommandSender sender) {
        sender.sendMessage(this.description);
    }
}
