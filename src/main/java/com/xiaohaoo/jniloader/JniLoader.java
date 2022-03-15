package com.xiaohaoo.jniloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaohao
 * @version 1.0
 * @date 2022/3/14 11:08 PM
 */
public class JniLoader {
    private static final String TMP_DIR = System.getProperty("java.io.tmpdir", ".");
    private static final String CACHE_DIR = "com/xiaohaoo/jni/";

    private static final String SUFFIX = ".jni";

    private static final Map<String, String> LIBRARY_CACHE = new ConcurrentHashMap<>();

    static {
        cleanDir();
    }

    public static void loadLibrary(String path) {
        if (!LIBRARY_CACHE.containsKey(path)) {
            try {
                Path newFile = createNewFile(path);
                System.load(newFile.toString());
                LIBRARY_CACHE.put(path, newFile.toAbsolutePath().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadLibrary(URL url) {
        if (!LIBRARY_CACHE.containsKey(url.toString())) {
            try {
                Path newFile = createNewFile(url.getPath());
                System.load(newFile.toString());
                LIBRARY_CACHE.put(url.getPath(), newFile.toAbsolutePath().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getFileName(String path) {
        return path.substring(path.lastIndexOf(File.separator) + 1);
    }

    private static Path createNewFile(String path) throws IOException {
        File parentDir = Path.of(TMP_DIR, CACHE_DIR).toFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        return Files.copy(Path.of(path), Path.of(parentDir.getAbsolutePath(), getFileName(path), SUFFIX), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void cleanDir() {
        Path.of(TMP_DIR, CACHE_DIR).toFile().deleteOnExit();
    }
}
