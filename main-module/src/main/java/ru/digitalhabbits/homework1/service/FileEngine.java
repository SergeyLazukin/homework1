package ru.digitalhabbits.homework1.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.util.Arrays.stream;
import static org.slf4j.LoggerFactory.getLogger;

public class FileEngine {
    private static final Logger logger = getLogger(FileEngine.class);
    private static final String RESULT_FILE_PATTERN = "results-%s.txt";
    private static final String RESULT_DIR = "results";
    private static final String RESULT_EXT = "txt";

    public boolean writeToFile(@Nonnull String text, @Nonnull String pluginName) {
        // TODO: NotImplemented
        String path = String.format(System.getProperty("user.dir") + "\\" + RESULT_DIR + "\\" + RESULT_FILE_PATTERN, pluginName);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)))) {
            writer.write(text);
        } catch (IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return true;
    }

    public void cleanResultDir() {
        final String currentDir = System.getProperty("user.dir");
        final File resultDir = new File(currentDir + "/" + RESULT_DIR);
        stream(resultDir.list((dir, name) -> name.endsWith(RESULT_EXT)))
                .forEach(fileName -> new File(resultDir + "/" + fileName).delete());
    }
}
