package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        // TODO: NotImplemented
//        String result = "";

        int lines = text.split("\\n").length;
        int words = text.split("\\s").length;
        int letters = text.toCharArray().length;

//        result = lines + ";" + words + ";" + letters;
//        return result;
        return lines + ";" + words + ";" + letters;
    }
}
