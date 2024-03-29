package de.frinshhd.spigot.utils;

import de.frinshhd.core.utils.TranslatorPlaceholder;
import de.frinshhd.core.utils.Translator;
import org.bukkit.ChatColor;

public class SpigotTranslator extends Translator {

    public static String build(String messageKey, TranslatorPlaceholder... translatorPlaceholders) {
        return ChatColor.translateAlternateColorCodes('&', buildRaw(messageKey, translatorPlaceholders));
    }
}
