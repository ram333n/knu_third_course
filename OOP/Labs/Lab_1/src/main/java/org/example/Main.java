package org.example;

import org.example.transport.Bus;
import org.example.trips.Cruise;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Cruise("aboba", BigDecimal.valueOf(1500.99), new Bus("bus"), LocalDate.now(), 5, true));
    }
}