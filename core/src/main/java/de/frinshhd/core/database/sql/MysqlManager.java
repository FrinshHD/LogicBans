package de.frinshhd.core.database.sql;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import de.frinshhd.core.database.sql.entities.Quests;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class MysqlManager {

    public static JdbcConnectionSource connectionSource;

    public static Dao<Quests, Long> getQuestDao() throws SQLException {
        return DaoManager.createDao(connectionSource, Quests.class);
    }

    public static void connect() throws SQLException {
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:plugins/AnturniaQuests/sqlite.db");
        } catch (SQLException e) {
            createNewDatabase();
            connect();
        }

        TableUtils.createTableIfNotExists(connectionSource, Quests.class);
    }

    public static Quests getQuestPlayer(UUID uuid) {
        Dao<Quests, Long> questsDao = null;
        try {
            questsDao = getQuestDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<Quests> quests = null;
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
        if (connectionSource.isOpen("Quests")) {
            return;
        }

        disconnect();
        connect();
    }

    public static void createNewDatabase() {

        String url = "jdbc:sqlite:plugins/AnturniaQuests/sqlite.db";

        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
