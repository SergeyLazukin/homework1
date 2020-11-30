package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        // TODO: NotImplemented
        String textLowerCase = text.toLowerCase();
        int lines = textLowerCase.split("\\n").length;
        int words = textLowerCase.split("\\s").length;
        int letters = textLowerCase.toCharArray().length;
        return lines + ";" + words + ";" + letters;
    }
}
