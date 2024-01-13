package de.frinshhd.spigot;

import de.frinshhd.core.CoreMain;
import de.frinshhd.spigot.utils.DynamicCommands;
import de.frinshhd.spigot.utils.DynamicListeners;
import de.frinshhd.spigot.utils.Metrics;
import de.frinshhd.spigot.utils.SpigotTranslator;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.Set;

public final class SpigotMain extends JavaPlugin {

    private static JavaPlugin instance;

    public static JavaPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        CoreMain.init();

        //Bstats
        int pluginId = 1234;
        Metrics metrics = new Metrics(this, pluginId);

        // Find plugin class names for dynamic loading
        String fullCanonicalName = SpigotMain.getInstance().getClass().getCanonicalName();
        String canonicalName = fullCanonicalName.substring(0, fullCanonicalName.lastIndexOf("."));

        Reflections reflections = new Reflections(canonicalName, new SubTypesScanner(false));
        Set<String> classNames = reflections.getAll(new SubTypesScanner(false));

        DynamicCommands.load(classNames, canonicalName);
        DynamicListeners.load(classNames, canonicalName);
    }

    @Override
    public void onDisable() {

    }
}
