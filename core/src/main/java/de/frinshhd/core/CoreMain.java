package de.frinshhd.core;

import de.frinshhd.core.configs.ConfigsManager;
import de.frinshhd.core.database.DatabaseManager;
import de.frinshhd.core.utils.Translator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CoreMain {
    public static String latestVersion;
    public static String currentVersion;

    private static ConfigsManager configsManager;
    private static DatabaseManager databaseManager;
    private static BanManager banManager;

    public static ConfigsManager getConfigsManager() {
        return configsManager;
    }
    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
    public static BanManager getBanManager() {
        return banManager;
    }

    public static void init() {
        //create files
        new File("plugins/AnturniaBans").mkdir();

        List<String> files = new ArrayList<>();
        files.addAll(List.of("config.yml", "messages.properties"));

        for (String fileRaw : files) {
            File file = new File("plugins/AnturniaBans/" + fileRaw);
            if (file.exists()) {
                continue;
            }

            InputStream link = (CoreMain.class.getClassLoader().getResourceAsStream(fileRaw));
            try {
                Files.copy(link, file.getAbsoluteFile().toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        configsManager = new ConfigsManager();
        databaseManager = new DatabaseManager();
        banManager = new BanManager();

        // register messages
        try {
            Translator.register("plugins/AnturniaBans/messages.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void reload() {
        try {
            getConfigsManager().load();
            Translator.register("plugins/AnturniaBans/messages.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}