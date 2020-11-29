package ru.digitalhabbits.homework1.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.slf4j.LoggerFactory.getLogger;

public class PluginEngine {

    private static final Logger logger = getLogger(PluginLoader.class);

    @Nonnull
    public  <T extends PluginInterface> String applyPlugin(@Nonnull Class<T> cls, @Nonnull String text) {
        // TODO: NotImplemented
        String result = "";
        try {
            Object instance = cls.getDeclaredConstructor().newInstance();
            Method method = cls.getDeclaredMethod("apply", String.class);
            result = (String) method.invoke(instance, text);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }
}
