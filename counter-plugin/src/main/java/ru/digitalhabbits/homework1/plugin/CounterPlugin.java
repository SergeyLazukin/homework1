package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        // TODO: NotImplemented
        String regex = "(\\b[a-zA-Z][a-zA-Z.0-9]*\\b)";
        String textLowerCase = text.toLowerCase();

        long lines = textLowerCase.lines().count();

        long words = 0;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textLowerCase);
        while(matcher.find()) {
            words++;
        }

        int letters = text.length();

        return lines + ";" + words + ";" + letters;
    }
}
