package de.frinshhd.core.database;

import com.j256.ormlite.dao.Dao;
import de.frinshhd.core.Main;
import de.frinshhd.core.database.sql.MysqlManager;
import de.frinshhd.core.database.sql.entities.Ban;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class DatabaseManager {
    // Todo: mongo stuff


    public void init() {

    }

    public void banPlayer(UUID playerUUID, long banTime, long unbanTime, String reason, UUID banner) throws SQLException {
        switch (Main.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<Ban, Long> bansDao = null;
                bansDao = MysqlManager.getBansDao();
                Ban ban = new Ban();
                ban.create(playerUUID, banTime, unbanTime, reason, banner);
                bansDao.create(ban);

                break;
            case MONGODB:
                break;
        }
    }

    public void unbanPlayer(BigInteger banID) throws SQLException {
        switch (Main.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<Ban, Long> bansDao = null;
                bansDao = MysqlManager.getBansDao();
                Ban ban = bansDao.queryForEq("id", banID).stream().toList().get(0);
                ban.setDisabled();
                bansDao.update(ban);
                break;
            case MONGODB:
                break;
        }
    }

    public ArrayList<Ban> getPlayerBans(UUID playerUUID) throws SQLException {
        ArrayList<Ban> bans = new ArrayList<>();

        switch (Main.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<Ban, Long> bansDao = null;
                bansDao = MysqlManager.getBansDao();
                bans.addAll(bansDao.queryForEq("uuid", playerUUID));

                return bans;
            case MONGODB:
                return bans;
        }

        return bans;
    }

    public Ban getBan(BigInteger banID) throws SQLException {
        switch (Main.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<Ban, Long> bansDao = null;
                bansDao = MysqlManager.getBansDao();
                return bansDao.queryForEq("id", banID).stream().toList().get(0);
            case MONGODB:
                return null;
        }

        return null;
    }
}
