package org.example;

import org.example.manager.Manager;
import os.lab1.compfuncs.basic.IntOps;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter value : ");
            int argument = scanner.nextInt();
            new Manager(argument, Constants.PORT).compute();
        }
    }
}