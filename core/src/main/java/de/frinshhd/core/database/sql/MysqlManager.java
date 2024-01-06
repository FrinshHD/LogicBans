package de.frinshhd.core.database.sql;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.table.TableUtils;
import de.frinshhd.core.CoreMain;
import de.frinshhd.core.database.sql.entities.BanSQL;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class MysqlManager {

    public static JdbcConnectionSource connectionSource;

    public static Dao<BanSQL, Long> getBansDao() throws SQLException {
        return DaoManager.createDao(connectionSource, BanSQL.class);
    }

    public static void connect(String url) throws SQLException {
        connect(url, null, null);
    }

    public static void connect(String url, String userName, String password) throws SQLException {
        if (userName == null && password == null) {
            try {
                connectionSource = new JdbcConnectionSource(url);
            } catch (SQLException e) {
                createNewSqliteDatabase();
                connect(url,userName,password);
            }
        } else {
            try {
                connectionSource = new JdbcConnectionSource(url, userName, password);
            } catch (SQLException e) {
                //Todo: logging
            }
        }

        TableUtils.createTableIfNotExists(connectionSource, BanSQL.class);
    }

    public static BanSQL getBanPlayer(UUID uuid) {
        Dao<BanSQL, Long> questsDao = null;
        try {
            questsDao = getBansDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<BanSQL> quests = null;
        try {
            quests = questsDao.queryForEq("uuid", uuid);
        } catch (SQLException e) {
            return null;
        }

        if (quests.isEmpty()) {
            return null;
        }

        return quests.get(0);
    }

    public static void disconnect() throws Exception {
        connectionSource.close();
    }

    public static void checkConnection() throws Exception {
        if (connectionSource.isOpen("Bans")) {
            return;
        }

        disconnect();
        CoreMain.getConfigsManager().connectToDatabase();
    }

    public static void createNewSqliteDatabase() {

        String url = "jdbc:sqlite:plugins/AnturniaBans/sqlite.db";

        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                //Todo: logging
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
