package org.example.config;

public final class ConfigParams {
    private ConfigParams() {}

    public static final int PORT = 9999;
    public static final int SERVER_THREADS_COUNT = 4;
    public static final String HOST = "localhost";
    public static final String REMOTE_OBJECT = "server";
    public static final String REMOTE_OBJECT_URL = String.format("//%s:%d/%s", HOST, PORT, REMOTE_OBJECT);
}
