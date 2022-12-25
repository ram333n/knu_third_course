package org.example.util;

import org.example.classes.Flower;

import java.util.List;

public final class OrangeryPrinter {
    private OrangeryPrinter() {}

    public static void print(List<Flower> orangery) {
        for(Flower flower : orangery) {
            System.out.println(flower);
            System.out.println("----------------------------");
        }
    }
}
