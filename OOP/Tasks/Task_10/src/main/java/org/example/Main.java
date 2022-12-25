package org.example;

public class Main {

    public static void main(String[] args) {
        CustomThreadPool pool = new CustomThreadPool(4);

        pool.execute(new CustomTask("1", 700L));
        pool.execute(new CustomTask("2", 700L));
        pool.execute(new CustomTask("3", 900L));
        pool.execute(new CustomTask("4", 1000L));
        pool.execute(new CustomTask("5", 1000L));

        pool.shutdown();

        pool.execute(new CustomTask("6", 1000L));
    }
}