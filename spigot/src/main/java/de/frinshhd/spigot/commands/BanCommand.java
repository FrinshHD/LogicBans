package de.frinshhd.spigot.commands;

import com.j256.ormlite.table.TableUtils;
import de.frinshhd.core.Ban;
import de.frinshhd.core.CoreMain;
import de.frinshhd.core.database.sql.entities.BanSQL;
import de.frinshhd.spigot.SpigotMain;
import de.frinshhd.spigot.utils.SpigotCommandExecutor;
import de.frinshhd.spigot.utils.SpigotTranslator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BanCommand extends SpigotCommandExecutor {


    public BanCommand() {
        super("ban");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender.hasPermission("anturniabans.ban")) {

            if (args.length >= 1) {
                String playerName;
                UUID playerUUID = null;
                Player player = SpigotMain.getInstance().getServer().getPlayer(args[0]);
                UUID bannerUUID = null;
                String reason = null;

                if (player == null) {
                    playerName = args[0];
                } else {
                    playerName = player.getName();
                    playerUUID = player.getUniqueId();
                }

                if (sender instanceof Player) {
                    bannerUUID = ((Player) sender).getUniqueId();
                }


                Ban ban = CoreMain.getBanManager().banPlayer(playerName, playerUUID, bannerUUID, -1, args, 1);

                //Todo: tell admin that he banned the player with the reason
                if (ban == null) {
                    sender.sendMessage(SpigotTranslator.build("ban.alreadyBanned"));
                    return false;
                } else {
                    sender.sendMessage(SpigotTranslator.build("ban.success"));
                    return true;
                }
            }

            // ToDo message to show the user how the command works
            sender.sendMessage(SpigotTranslator.build("ban.helpMessage"));
            return false;
        }

        sender.sendMessage(SpigotTranslator.build("noPermission"));
        return false;
    }
}
