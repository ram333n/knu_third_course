package org.example.server;

import org.example.Constants;
import org.example.rmi.RmiServer;
import org.example.rmi.RmiServerImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(Constants.PORT);
        RmiServer server = new RmiServerImpl();
        registry.rebind(Constants.REMOTE_OBJECT, server);
        System.out.println("Server started");
    }
}
