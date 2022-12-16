package org.example.rmi;

import org.example.config.ConfigParams;
import org.example.dao.CarDao;
import org.example.model.Car;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerRmiTask8 extends UnicastRemoteObject implements RmiServer {
    private final CarDao carDao;
    private final ReadWriteLock lock;

    public ServerRmiTask8() throws RemoteException {
        this.carDao = new CarDao();
        this.lock = new ReentrantReadWriteLock();
    }

    public ServerRmiTask8(Car... initValues) throws RemoteException {
        this.carDao = new CarDao(initValues);
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public List<Car> findByBrand(String brand) throws RemoteException {
        try {
            lock.readLock().lock();
            return carDao.findByBrand(brand);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Car> findByYearsOfExploitationExclusiveThreshold(int threshold) throws RemoteException {
        try {
            lock.readLock().lock();
            return carDao.findByYearsOfExploitationExclusiveThreshold(threshold);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Car> findByYearOfProductionAndPriceExclusiveThreshold(int yearOfProduction, BigDecimal priceThreshold)
            throws RemoteException {
        try {
            lock.readLock().lock();
            return carDao.findByYearOfProductionAndPriceExclusiveThreshold(yearOfProduction, priceThreshold);
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(ConfigParams.PORT);
        RmiServer server = new ServerRmiTask8(
                new Car(0L, "Skoda", "Oktavia", 2015, "Yellow", BigDecimal.valueOf(1000L), "AM 1111 AM"),
                new Car(1L, "Renault", "Kangoo", 2018, "Green", BigDecimal.valueOf(1500L), "AA 2222 AM"),
                new Car(2L, "BMW", "X5", 2022, "Black", BigDecimal.valueOf(2200L), "AA 3333 AM"),
                new Car(3L, "Volkswagen", "Passat", 2018, "Blue", BigDecimal.valueOf(1000L), "AA 4444 AM")
        );

        registry.rebind(ConfigParams.REMOTE_OBJECT, server);
        System.out.println("Server started");
    }
}
