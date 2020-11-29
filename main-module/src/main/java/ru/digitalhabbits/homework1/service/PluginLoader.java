package ru.digitalhabbits.homework1.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class PluginLoader {
    private static final Logger logger = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = "jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName) {
        // TODO: NotImplemented
        String path = System.getProperty("user.dir") + "\\" + pluginDirName + "\\";

        List<Class<? extends PluginInterface>> plugins = new ArrayList<>();

        List<String> namePlugins = Arrays.asList("counter-plugin", "frequency-dictionary-plugin");
        List<String> nameClassPlugins = Arrays.asList("CounterPlugin", "FrequencyDictionaryPlugin");

        URL[] resources = new URL[namePlugins.size()];
        try {
            for (int i = 0; i < resources.length; i++) {
                resources[i] = new File(path + namePlugins.get(i) + "." + PLUGIN_EXT).toURI().toURL();
                URLClassLoader childClassloader = new URLClassLoader(new URL[]{resources[i]}, PluginLoader.class.getClassLoader());
                Class<? extends PluginInterface> clazz = (Class<? extends PluginInterface>)
                        Class.forName(PACKAGE_TO_SCAN + "." + nameClassPlugins.get(i), true, childClassloader);
                plugins.add(clazz);
            }
        } catch (ClassNotFoundException | MalformedURLException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return plugins;
    }
}
