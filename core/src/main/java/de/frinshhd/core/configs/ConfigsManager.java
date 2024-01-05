package de.frinshhd.core.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.frinshhd.core.Main;
import de.frinshhd.core.configs.models.Config;
import de.frinshhd.core.database.sql.MysqlManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class ConfigsManager {

    public Config config;

    public ConfigsManager() {
        load();

        // connect to database
        switch (Main.getConfigsManager().config.database.getType()) {
            case MYSQL:
            case SQLITE:
                try {
                    MysqlManager.connect();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case MONGODB:
                break;
            default:
        }
    }

    public void load() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            //this.config = mapper.readValue(new FileInputStream("plugins/AnturniaQuests/config.yml"), Config.class);
            this.config = mapper.readValue(Main.class.getResource("config.yml"), Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}