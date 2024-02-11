package de.frinshhd.spigot.commands;

import de.frinshhd.spigot.utils.SpigotCommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BanInfoCommand extends SpigotCommandExecutor {
    public BanInfoCommand() {
        super("baninfo");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        return true;
    }
}
