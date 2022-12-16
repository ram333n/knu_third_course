package org.example.rmi;

import org.example.config.ConfigParams;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientRmiTask8 {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        RmiServer server = (RmiServer) Naming.lookup(ConfigParams.REMOTE_OBJECT_URL);
        System.out.println("Connected");

        System.out.println(server.findByBrand("Renault"));
        System.out.println(server.findByYearsOfExploitationExclusiveThreshold(5));
        System.out.println(server.findByYearOfProductionAndPriceExclusiveThreshold(2022, BigDecimal.valueOf(2000L)));
        System.out.println(server.findByYearOfProductionAndPriceExclusiveThreshold(2015, BigDecimal.valueOf(500L)));
    }
}
