package de.frinshhd.core.database;

import com.j256.ormlite.dao.Dao;
import de.frinshhd.core.Ban;
import de.frinshhd.core.CoreMain;
import de.frinshhd.core.database.sql.MysqlManager;
import de.frinshhd.core.database.sql.entities.BanSQL;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class DatabaseManager {
    // Todo: mongo stuff


    public void init() {

    }

    public Ban banPlayer(UUID playerUUID, long banTime, long unbanTime, String reason, UUID banner) throws SQLException {
        switch (CoreMain.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<BanSQL, Long> bansDao = null;
                bansDao = MysqlManager.getBansDao();
                BanSQL banSQL = new BanSQL();
                banSQL.create(playerUUID, banTime, unbanTime, reason, banner);
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

    public ArrayList<BanSQL> getPlayerBans(UUID playerUUID) throws SQLException {
        ArrayList<BanSQL> bans = new ArrayList<>();

        switch (CoreMain.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<BanSQL, Long> bansDao = null;
                bansDao = MysqlManager.getBansDao();
                bans.addAll(bansDao.queryForEq("uuid", playerUUID));

                return bans;
            case MONGODB:
                return bans;
        }

        return bans;
    }

    public BanSQL getBan(BigInteger banID) throws SQLException {
        switch (CoreMain.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                Dao<BanSQL, Long> bansDao = null;
                bansDao = MysqlManager.getBansDao();
                return bansDao.queryForEq("id", banID).stream().toList().get(0);
            case MONGODB:
                return null;
        }

        return null;
    }

    public Ban sqlBantoBan(BanSQL banSQL) {
        Ban ban = new Ban();
        ban.id = banSQL.id;
        ban.banTime = banSQL.banTime;
        ban.unbanTime = banSQL.unbanTime;
        ban.uuid = banSQL.uuid;
        ban.banner = banSQL.banner;
        ban.disabled = banSQL.disabled;
        ban.reason = banSQL.reason;

        return ban;
    }
}
