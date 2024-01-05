package de.frinshhd.bungee;

import de.frinshhd.bungee.utils.Metrics;
import net.md_5.bungee.api.plugin.Plugin;

public final class BungeeMain extends Plugin {

    @Override
    public void onEnable() {
        //Bstats
        int pluginId = 1234;
        Metrics metrics = new Metrics(this, pluginId);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
