package de.frinshhd.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Translator {

    public static Properties messages;

    public static void register(InputStream stream) throws IOException {
        messages = new Properties();
        messages.load(stream);
    }

    public static String build(String messageKey, String... args) {
        return null;
    }

    public static String buildRaw(String messageKey, String... args) {
        if (!messages.containsKey(messageKey)) {
            throw new RuntimeException(messageKey + " is not a valid message key!");
        }

        String message = messages.get(messageKey).toString();

        for (int i = 0; i < args.length; i++) {
            message = message.replace("{" + i + "}", args[i]);
        }

        return message;
    }

}
