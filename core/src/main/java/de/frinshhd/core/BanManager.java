package de.frinshhd.core;

import java.sql.SQLException;
import java.util.SimpleTimeZone;
import java.util.UUID;

public class BanManager {

    public Ban banPlayer(String playerName, UUID playerUUID, UUID bannerUUID, long duration, String[] args, int argsToSkip) {
        String reason = null;
        long unbanTime = -1;

        if (duration > -1) {
            unbanTime = System.currentTimeMillis() + duration;
        }

        if (args.length > argsToSkip) {
            StringBuilder stringBuilderReason = new StringBuilder();
            for (int i = argsToSkip; i < args.length; i++) {
                stringBuilderReason.append(args[i]).append(" ");
            }

            reason = stringBuilderReason.toString();
        }

        Ban ban;
        try {
            ban = CoreMain.getDatabaseManager().banPlayer(playerName, playerUUID, System.currentTimeMillis(), unbanTime, reason, bannerUUID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ban;
    }

    public boolean unbanPlayer() {
        return false;
    }


}
