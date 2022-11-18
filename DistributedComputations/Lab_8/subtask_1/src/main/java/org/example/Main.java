package org.example;

import org.example.client.Client;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Client client = new Client(Constants.HOST, Constants.PORT);
        try {
            client.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}