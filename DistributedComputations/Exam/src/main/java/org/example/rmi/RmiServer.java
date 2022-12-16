package org.example.rmi;

import org.example.model.Car;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RmiServer extends Remote {
    List<Car> findByBrand(String brand) throws RemoteException;
    List<Car> findByYearsOfExploitationExclusiveThreshold(int threshold) throws RemoteException;
    List<Car> findByYearOfProductionAndPriceExclusiveThreshold(int yearOfProduction, BigDecimal priceThreshold)
            throws RemoteException;
}
