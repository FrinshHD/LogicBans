package de.frinshhd.spigot.listeners;

import de.frinshhd.core.CoreMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        try {
            CoreMain.getDatabaseManager().checkPlayerDatabaseRegistered(player.getName(), player.getUniqueId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
