package org.example;

public final class Constants {
    private Constants() {}

    public static final String HOST = "localhost";
    public static final String REMOTE_OBJECT = "server";
    public static final int PORT = 9999;
    public static final String URL = String.format("//%s:%d/%s", HOST, PORT, REMOTE_OBJECT);
}
