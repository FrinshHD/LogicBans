package de.frinshhd.spigot;

import de.frinshhd.spigot.utils.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpigotMain extends JavaPlugin {

    private static JavaPlugin instance;

    public static JavaPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        //Bstats
        int pluginId = 1234;
        Metrics metrics = new Metrics(this, pluginId);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
