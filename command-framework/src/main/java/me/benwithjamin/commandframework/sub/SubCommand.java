package me.benwithjamin.commandframework.sub;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Project: me.benwithjamin.minigames.command.sub | Author: BenWithJamIn#4547
 * Created: 14/02/2023 at 16:03
 */
@Getter
public abstract class SubCommand {
    private final String name;
    private final String[] aliases;
    @Nullable private final String permission;
    private final String description;
    private final List<Integer> args;

    public SubCommand(String name, String[] aliases, String description, int[] args) {
        this(name, aliases, null, description, args);
    }

    public SubCommand(String name, String[] aliases, @Nullable String permission, String description, int[] args) {
        this.name = name;
        this.aliases = aliases;
        this.permission = permission;
        this.description = description;
        this.args = Arrays.stream(args).boxed().collect(Collectors.toList());
    }

    /**
     * Called when the command is executed
     *
     * @see org.bukkit.command.Command#execute(CommandSender, String, String[])
     */
    public abstract void execute(@NotNull CommandSender commandSender, @NotNull String[] strings);

    /**
     * Called when a player tab completes this command
     *
     * @see org.bukkit.command.Command#execute(CommandSender, String, String[])
     */
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return null;
    }

    /**
     * Checks if a given command name equals the command name or any of the aliases
     *
     * @param name The command name to check
     *
     * @return True if the command name equals the command name or any of the aliases
     */
    public boolean checkName(String name) {
        for (String alias : this.aliases) {
            if (name.equalsIgnoreCase(alias)) {
                return true;
            }
        }
        return name.equalsIgnoreCase(this.name);
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
