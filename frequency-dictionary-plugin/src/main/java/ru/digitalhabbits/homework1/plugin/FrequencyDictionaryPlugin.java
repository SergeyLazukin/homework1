package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrequencyDictionaryPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        // TODO: NotImplemented
        String regex = "(\\b[a-zA-Z][a-zA-Z.0-9]*\\b)";

        Map<String, Integer> map = new TreeMap<>();
        StringBuilder builder = new StringBuilder();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text.toLowerCase());
        while(matcher.find()) {
            String key = matcher.group();
            if(map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
                continue;
            }
            map.put(key, 1);
        }

        for(Map.Entry<String, Integer> pair : map.entrySet()) {
            builder.append(pair.getKey()).append(" ").append(pair.getValue()).append("\n");
        }

        return builder.toString();
    }
}
