package de.frinshhd.core.database;

import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.j256.ormlite.dao.Dao;
import de.frinshhd.core.Ban;
import de.frinshhd.core.CoreMain;
import de.frinshhd.core.database.sql.MysqlManager;
import de.frinshhd.core.database.sql.entities.BanSQL;
import de.frinshhd.core.database.sql.entities.PlayerSQL;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.SimpleTimeZone;
import java.util.UUID;

public class DatabaseManager {
    // Todo: mongo stuff


    public void init() {

    }

    public Ban banPlayer(String playerName, UUID playerUUID, long banTime, long unbanTime, String reason, UUID banner) throws SQLException {
        if (isPlayerBanned(getPlayerDatabaseID(playerName, playerUUID))) {
            return null;
        }

        switch (CoreMain.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<BanSQL, Long> bansDao = null;
                bansDao = MysqlManager.getBansDao();
                BanSQL banSQL = new BanSQL();
                UUID playerID = getPlayerDatabaseID(playerName);
                UUID bannerID = getPlayerDatabaseID(banner);

                if (playerID == null) {
                    playerID = createPlayerDatabase(playerName, playerUUID);
                }

                banSQL.create(playerID, banTime, unbanTime, reason, bannerID);
                bansDao.create(banSQL);

                return sqlBantoBan(banSQL);
            case MONGODB:
                break;
        }

        return null;
    }

    public void unbanPlayer(BigInteger banID) throws SQLException {
        switch (CoreMain.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<BanSQL, Long> bansDao = null;
                bansDao = MysqlManager.getBansDao();
                BanSQL banSQL = bansDao.queryForEq("id", banID).stream().toList().get(0);
                banSQL.setDisabled();
                bansDao.update(banSQL);
                break;
            case MONGODB:
                break;
        }
    }

    public ArrayList<Ban> getPlayerBans(UUID playerID) throws SQLException {
        ArrayList<Ban> bans = new ArrayList<>();

        switch (CoreMain.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<BanSQL, Long> bansDao = null;
                bansDao = MysqlManager.getBansDao();

                try {
                    for (BanSQL banSQL : bansDao.queryForEq("playerID", playerID)) {
                        bans.add(sqlBantoBan(banSQL));
                        System.out.println("2");
                    }
                } catch (SQLException e) {
                    System.out.println("3");
                    return bans;
                }

                return bans;
            case MONGODB:
                return bans;
        }

        return bans;
    }

    public Ban getBan(BigInteger banID) throws SQLException {
        switch (CoreMain.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<BanSQL, Long> bansDao = null;
                bansDao = MysqlManager.getBansDao();
                return sqlBantoBan(bansDao.queryForEq("id", banID).stream().toList().get(0));
            case MONGODB:
                return null;
        }

        return null;
    }

    public boolean isPlayerBanned(UUID playerID) throws SQLException {
        switch (CoreMain.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                ArrayList<Ban> bans = getPlayerBans(playerID);
                Ban latestBan = null;
                try {
                    latestBan = bans.get(bans.size() - 1);
                } catch (IndexOutOfBoundsException e) {
                    return false;
                }

                return !latestBan.disabled;
            case MONGODB:
                return false;
        }

        return false;
    }

    public Ban sqlBantoBan(BanSQL banSQL) {
        Ban ban = new Ban();
        ban.id = banSQL.id;
        ban.banTime = banSQL.banTime;
        ban.unbanTime = banSQL.unbanTime;
        ban.uuid = banSQL.playerID;
        ban.banner = banSQL.bannerID;
        ban.disabled = banSQL.disabled;
        ban.reason = banSQL.reason;

        return ban;
    }

    //PlayersDatabase
    public UUID getPlayerDatabaseID(String playerName, UUID playerUUID) throws SQLException {
        switch (CoreMain.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<PlayerSQL, Long> playerDao = null;
                playerDao = MysqlManager.getPlayerDao();

                try {
                    return playerDao.queryForEq("playerUUID", playerUUID).stream().toList().get(0).uuid;
                } catch (SQLException | ArrayIndexOutOfBoundsException e) {
                    try {
                        return playerDao.queryForEq("playerName", playerName.toLowerCase()).stream().toList().get(0).uuid;
                    } catch (SQLException | ArrayIndexOutOfBoundsException e2) {
                        return null;
                    }
                }
            case MONGODB:
                break;
        }

        return null;
    }
    public UUID getPlayerDatabaseID(UUID playerUUID) throws SQLException {
        switch (CoreMain.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<PlayerSQL, Long> playerDao = null;
                playerDao = MysqlManager.getPlayerDao();

                try {
                    return playerDao.queryForEq("playerUUID", playerUUID).stream().toList().get(0).uuid;
                } catch (SQLException | ArrayIndexOutOfBoundsException e) {
                    return null;
                }
            case MONGODB:
                break;
        }

        return null;
    }

    public UUID getPlayerDatabaseID(String playerName) throws SQLException {
        switch (CoreMain.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<PlayerSQL, Long> playerDao = null;
                playerDao = MysqlManager.getPlayerDao();

                try {
                    return playerDao.queryForEq("playerName", playerName.toLowerCase()).stream().toList().get(0).uuid;
                } catch (SQLException | ArrayIndexOutOfBoundsException e) {
                    return null;
                }
            case MONGODB:
                break;
        }

        return null;
    }

    public UUID createPlayerDatabase(String playerName, UUID playerUUID) throws SQLException {
        switch (CoreMain.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<PlayerSQL, Long> playerDao = null;
                playerDao = MysqlManager.getPlayerDao();
                PlayerSQL player = new PlayerSQL();
                player.create(playerName, playerUUID);
                playerDao.create(player);

                return player.uuid;
            case MONGODB:
                break;
        }

        return null;
    }

    public void updatePlayerDatabaseName(UUID playerUUID, String playerName) throws SQLException {
        switch (CoreMain.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<PlayerSQL, Long> playerDao = null;
                playerDao = MysqlManager.getPlayerDao();
                PlayerSQL player = playerDao.queryForEq("playerUUID", playerUUID).stream().toList().get(0);
                player.updatePlayerName(playerName);
                playerDao.update(player);

                break;
            case MONGODB:
                break;
        }
    }

    public void setPlayerDatabaseUUID(String playerName, UUID playerUUID) throws SQLException {
        switch (CoreMain.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<PlayerSQL, Long> playerDao = null;
                playerDao = MysqlManager.getPlayerDao();
                PlayerSQL player = playerDao.queryForEq("playerName", playerName.toLowerCase()).stream().toList().get(0);
                player.setPlayerUUID(playerUUID);
                playerDao.update(player);

                break;
            case MONGODB:
                break;
        }
    }

    public void checkPlayerDatabaseRegistered(String playerName, UUID playerUUID) throws SQLException {
        UUID playerID = getPlayerDatabaseID(playerUUID);

        //if now playerUUID found
        if (playerID == null) {
            playerID = getPlayerDatabaseID(playerName);

            //if name also not registered create new entry
            // if name registered set the correct uuid
            if (playerID == null) {
                createPlayerDatabase(playerName, playerUUID);
            } else {
                setPlayerDatabaseUUID(playerName, playerUUID);
            }

            return;
        }

        updatePlayerDatabaseName(playerUUID, playerName);
    }
}
