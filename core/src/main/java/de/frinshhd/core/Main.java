package de.frinshhd.core;

import de.frinshhd.core.configs.ConfigsManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String latestVersion;
    public static String currentVersion;

    private static ConfigsManager configsManager;

    public static ConfigsManager getConfigsManager() {
        return configsManager;
    }

    public static void init() {
        //create files
        new File("plugins/AnturniaBans").mkdir();

        List<String> files = new ArrayList<>();
        files.addAll(List.of("config.yml"));

        for (String fileRaw : files) {
            File file = new File("plugins/AnturniaQuests/" + fileRaw);
            if (file.exists()) {
                continue;
            }

            InputStream link = (Main.class.getResourceAsStream(fileRaw));
            try {
                Files.copy(link, file.getAbsoluteFile().toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        configsManager = new ConfigsManager();
    }

    public static void reload() {
        getConfigsManager().load();
    }
}