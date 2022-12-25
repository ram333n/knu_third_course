package org.example;

import org.example.manager.Manager;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter argument : ");
            int argument = scanner.nextInt();
            new Manager(Constants.PORT, argument).compute();
        }
    }
}