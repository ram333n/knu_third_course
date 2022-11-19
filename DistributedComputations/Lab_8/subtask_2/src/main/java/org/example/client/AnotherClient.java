package org.example.client;

import org.example.Constants;
import org.example.rmi.RmiServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class AnotherClient {
    public static void main(String[] args)
            throws MalformedURLException, NotBoundException, RemoteException, InterruptedException {
        RmiServer server = (RmiServer) Naming.lookup(Constants.URL);
        System.out.println("Connected");

        Thread.sleep(2000L);

        System.out.println(server.findAllTeams());
    }
}
