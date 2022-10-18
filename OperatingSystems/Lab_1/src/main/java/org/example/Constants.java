package org.example;

import java.io.File;

public final class Constants {
    private Constants() {}

    public static final String HOST = "localhost";
    public static final int PORT = 8888;
    public static final String PROCESS_F_PATH = "org.example.processF.ProcessF";
    public static final String PROCESS_G_PATH = "org.example.processG.ProcessG";
    public static final String PATH_TO_JAR =
            String.join(File.separator, "out", "artifacts", "Lab_1_jar", "Lab_1.jar");
}
