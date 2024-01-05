package de.frinshhd.bungee.utils;

import de.frinshhd.core.utils.Translator;
import de.frinshhd.core.utils.TranslatorPlaceholder;
import net.md_5.bungee.api.ChatColor;

import java.util.Map;

public class BungeeTranslator extends Translator {

    public static String build(String messageKey, TranslatorPlaceholder... translatorPlaceholders) {
        return ChatColor.translateAlternateColorCodes('&', buildRaw(messageKey, translatorPlaceholders));
    }
}
