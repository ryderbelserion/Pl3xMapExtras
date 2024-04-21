package com.ryderbelserion.map.util;

import com.ryderbelserion.map.Provider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileUtil {

    private static final Provider.MapExtras api = Provider.getInstance();
    private static final Logger logger = api.getLogger();

    /**
     * Extracts a single file from a directory in the jar.
     */
    public static void extract(Class<?> object, String fileName, Path output, boolean overwrite) {
        try (InputStream stream = object.getResourceAsStream("/" + fileName)) {
            if (stream == null) {
                throw new RuntimeException("Could not read file from jar! (" + fileName + ")");
            }

            Path path = output.resolve(fileName);

            if (!Files.exists(path) || overwrite) {
                Files.createDirectories(path.getParent());
                Files.copy(stream, path, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception exception) {
            logger.warning("Failed to extract " + fileName + " from the jar.");
        }
    }

    /**
     * Extracts multiple files from a directory in the jar.
     */
    public static void extracts(Class<?> object, String sourceDir, Path outDir, boolean replace) {
        try (JarFile jarFile = new JarFile(Path.of(object.getProtectionDomain().getCodeSource().getLocation().toURI()).toFile())) {
            String path = sourceDir.substring(1);
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();

                if (!name.startsWith(path)) {
                    continue;
                }

                Path file = outDir.resolve(name.substring(path.length()));

                boolean exists = Files.exists(file);

                if (!replace && exists) {
                    continue;
                }

                if (entry.isDirectory()) {
                    if (!exists) {
                        try {
                            Files.createDirectories(file);
                        } catch (IOException exception) {
                            logger.severe(name + " could not be created");
                        }
                    }

                    continue;
                }

                try (
                        InputStream inputStream = new BufferedInputStream(jarFile.getInputStream(entry));
                        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file.toFile()))
                ) {
                    byte[] buffer = new byte[4096];
                    int readCount;

                    while ((readCount = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, readCount);
                    }

                    outputStream.flush();
                } catch (IOException exception) {
                    logger.severe("Failed to extract (" + name + ") from jar!");
                }
            }
        } catch (IOException exception) {
            logger.severe("Failed to extract file (" + sourceDir + ") from jar!");
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Returns a list of files in a directory if ending in a specific extension.
     *
     * @param directory the folder to check.
     * @param extension the file extension to look for.
     * @return a list of files that meet the criteria.
     */
    public static List<String> getFiles(Path directory, String extension) {
        return getFiles(directory, null, extension, false);
    }

    /**
     * Gets a list of files with the option to recursively look.
     *
     * @param directory the folder to check.
     * @param folder the folder to check if recursion is not on.
     * @param extension the file extension to look for.
     * @param recursion recursively look through the folder.
     * @return a list of files that meet the criteria.
     */
    public static List<String> getFiles(Path directory, String folder, String extension, boolean recursion) {
        List<String> files = new ArrayList<>();

        if (recursion) {
            try (Stream<Path> iterator = Files.find(Paths.get(directory.toUri()), Integer.MAX_VALUE, (filePath, fileAttr) -> fileAttr.isRegularFile())) {
                for (Path file : iterator.toList()) {
                    String fileName = file.getFileName().toString();

                    if (!fileName.endsWith(extension)) continue;

                    files.add(fileName);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return Collections.unmodifiableList(files);
        }

        return getFiles(directory, folder, extension);
    }

    /**
     * Returns a list of files in a directory if they end in .yml
     * If the folder is null, It will use the data folder supplied.
     *
     * @param directory the folder to check.
     * @param folder the subfolder with the files you actually want.
     * @return a list of files that meet the criteria.
     */
    public static List<String> getFiles(Path directory, String folder, String extension) {
        List<String> files = new ArrayList<>();

        File dir = folder == null ? directory.toFile() : directory.resolve(folder).toFile();

        String[] file = dir.list();

        File[] iterator = dir.listFiles();

        if (iterator != null) {
            for (File key : iterator) {
                if (key.isDirectory()) {
                    String[] folderList = key.list();

                    if (folderList != null) {
                        for (String name : folderList) {
                            if (!name.endsWith(extension)) continue;

                            files.add(name.replaceAll(extension, ""));
                        }
                    }
                }
            }
        }

        if (file != null) {
            for (String name : file) {
                if (!name.endsWith(".yml")) continue;

                files.add(name.replaceAll(".yml", ""));
            }
        }

        return Collections.unmodifiableList(files);
    }

    /**
     * Use minimessage to color a message.
     *
     * @param message the message to color.
     * @return the colored component.
     */
    public static Component color(String message) {
        return MiniMessage.miniMessage().deserialize(message).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }
}