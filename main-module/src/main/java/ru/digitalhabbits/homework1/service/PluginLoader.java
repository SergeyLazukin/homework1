package ru.digitalhabbits.homework1.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class PluginLoader {
    private static final Logger LOGGER = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = "jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin.";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName) {
        // TODO: NotImplemented
        String pathPlugins = System.getProperty("user.dir") + "\\" + pluginDirName + "\\";

        List<Class<? extends PluginInterface>> plugins = new ArrayList<>();

        List<String> namePlugins;
        List<Path> pathList = new ArrayList<>();
        List<String> nameClassPlugins;
        try {
            pathList = Files.list(new File(pathPlugins).toPath()).collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }

        namePlugins = pathList.stream().map(path -> path.toFile().getName()).collect(Collectors.toList());
        nameClassPlugins = takeClassnameFromJar(pathList);

        URL[] resources = new URL[namePlugins.size()];
        try {
            for (int i = 0; i < resources.length; i++) {
                resources[i] = new File(pathPlugins + namePlugins.get(i)).toURI().toURL();
                URLClassLoader childClassloader = new URLClassLoader(new URL[]{resources[i]}, PluginLoader.class.getClassLoader());
                Class<? extends PluginInterface> clazz = (Class<? extends PluginInterface>)
                        Class.forName(nameClassPlugins.get(i), true, childClassloader);
                plugins.add(clazz);
            }
        } catch (ClassNotFoundException | MalformedURLException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }
        return plugins;
    }

    public static List<String> takeClassnameFromJar(List<Path> pathList) {
        List<String> result = new ArrayList<>();

        try {
            for (Path pathPlugin : pathList) {
                JarFile jarFile = new JarFile(String.valueOf(pathPlugin));
                Enumeration<JarEntry> enumeration = jarFile.entries();
                while (enumeration.hasMoreElements()) {
                    String file = enumeration.nextElement().getName().replaceAll("/", "\\.");
                    if (file.endsWith(".class") && file.startsWith(PACKAGE_TO_SCAN)) {
                        result.add(file.substring(0, file.length() - 6));
                    }
                }
            }
        } catch(IOException e){
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }
        return result;
    }
}
