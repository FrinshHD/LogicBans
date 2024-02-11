package de.frinshhd.spigot.commands;

import de.frinshhd.spigot.utils.SpigotCommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TempBanCommand extends SpigotCommandExecutor {
    public TempBanCommand() {
        super("tempban");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        return true;
    }
}
