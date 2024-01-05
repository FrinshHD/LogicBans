package de.frinshhd.spigot;

import org.bukkit.plugin.java.JavaPlugin;

public final class SpigotMain extends JavaPlugin {

    private static JavaPlugin instance;

    public static JavaPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        System.out.println("byeeee");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
