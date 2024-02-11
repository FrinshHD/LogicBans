package de.frinshhd.core.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import de.frinshhd.core.CoreMain;
import de.frinshhd.core.configs.models.Config;
import de.frinshhd.core.database.sql.MysqlManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class ConfigsManager {

    private Config defaultConfig;
    public Config config;

    public ConfigsManager() {
        try {
            load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // connect to database
        connectToDatabase();

        setLogLevels();
    }

    public void connectToDatabase() {
        switch (this.config.database.getType()) {
            case MYSQL:
                try {
                    MysqlManager.connect("jdbc:mysql://" + this.config.database.ip + ":" + this.config.database.port + "/LogicBans", this.config.database.username, this.config.database.password);
                } catch (SQLException e) {
                    //Todo logging
                    throw new RuntimeException(e);
                }
                break;
            case SQLITE:
                try {
                    MysqlManager.connect("jdbc:sqlite:plugins/LogicBans/sqlite.db");
                } catch (SQLException e) {
                    //Todo logging
                    throw new RuntimeException(e);
                }
                break;
            case MONGODB:
                break;
            default:
        }
    }

    public void setLogLevels() {
        if (config.debug) {
            Logger.setGlobalLogLevel(Level.DEBUG);
        } else {
            Logger.setGlobalLogLevel(Level.ERROR);
        }
    }

    public void load() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        File configFile = new File("plugins/LogicBans/config.yml");

        this.config = mapper.readValue(configFile, Config.class);

        mapper.writeValue(configFile, this.config);
    }

    public void checkConfigUpdate() {

    }

}