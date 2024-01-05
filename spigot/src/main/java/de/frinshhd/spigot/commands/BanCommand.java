package de.frinshhd.spigot.commands;

import de.frinshhd.core.Ban;
import de.frinshhd.core.CoreMain;
import de.frinshhd.spigot.SpigotMain;
import de.frinshhd.spigot.utils.SpigotTranslator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender.hasPermission("anturniabans.ban")) {

            if (args.length >= 1) {
                Player player = SpigotMain.getInstance().getServer().getPlayer(args[0]);
                UUID bannerUUID = null;
                String reason = null;

                if (player == null) {
                    //Todo: tell admin that this player isn't online
                    return false;
                }

                if (sender instanceof Player) {
                    bannerUUID = ((Player) sender).getUniqueId();
                }


                Ban ban = CoreMain.getBanManager().banPlayer(player.getUniqueId(), bannerUUID, -1, args, 1);
                //Todo: tell admin that he banned the player with the reason
                return true;
            }

            // ToDo message to show the user how the command works
            return false;
        }

        sender.sendMessage(SpigotTranslator.build("noPermission"));
        return false;
    }
}
