package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter output;
    private ObjectInputStream input;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                clientSocket = serverSocket.accept();
                output = new PrintWriter(clientSocket.getOutputStream(), true);
                input = new ObjectInputStream(clientSocket.getInputStream());

                System.out.println("Client connected");

                try {
                    processDeserializing();
                } catch (SocketException e) {
                    System.out.println("Socket reset");
                } catch (ClassNotFoundException e) {
                    System.out.println("Class not found");
                } catch (EOFException e) {
                    System.out.println("Serialization is finished");
                }

                System.out.println("Client disconnected");
            }
        } catch (IOException e) {
            System.out.println("Something happened on server side");
            e.printStackTrace();
        }
    }

    private void processDeserializing() throws IOException, ClassNotFoundException, EOFException {
        while (!clientSocket.isClosed()) {
            TestClass testClass = (TestClass) input.readObject();

            if (testClass == null) {
                System.out.println("Received null object");
                output.println("Error, received null object!");
            } else {
                System.out.println(testClass);
                output.println("Object is deserialized");
            }
        }
    }

    public void stop() throws IOException {
        input.close();
        output.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(Constants.PORT);
    }
}
