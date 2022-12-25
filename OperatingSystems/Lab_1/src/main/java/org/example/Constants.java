package org.example;

import java.io.File;

public final class Constants {
    private Constants() {}

    public static final String HOST = "localhost";
    public static final int PORT = 9999;
    public static final String PROCESS_F_PATH = "org.example.process.ProcessF";
    public static final String PROCESS_G_PATH = "org.example.process.ProcessG";
    public static final String PATH_TO_JAR =
            String.join(File.separator, ".", "Lab_1.jar");
}
