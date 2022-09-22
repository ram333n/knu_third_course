package org.example;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;

public class Client {
    private Socket clientSocket;
    private ObjectOutputStream output;
    private BufferedReader input;

    public void connect(String host, int port) throws IOException {
        clientSocket = new Socket(host, port);
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendSerializedObject(TestClass testClass) throws IOException {
        output.writeObject(testClass);
        output.flush();
        return input.readLine();
    }

    public void stop() throws IOException {
        input.close();
        output.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.connect(Constants.HOST, Constants.PORT);
            TestClass testClass = new TestClass("Testname", 18, LocalDate.now());
            String response = client.sendSerializedObject(testClass);
            System.out.println(response);
            client.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
