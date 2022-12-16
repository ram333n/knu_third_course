package org.example.socket;

import org.example.config.ConfigParams;
import org.example.dao.CarDao;
import org.example.model.Car;
import org.example.util.IoUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSocketTask8 {
    private ServerSocket serverSocket;
    private final int port;
    private final CarDao carDao;
    private final ExecutorService executorService;

    public ServerSocketTask8(int port) {
        this.port = port;
        this.carDao = new CarDao();
        this.executorService = Executors.newFixedThreadPool(ConfigParams.SERVER_THREADS_COUNT);
    }

    public ServerSocketTask8(int port, Car... initValues) {
        this.port = port;
        this.carDao = new CarDao(initValues);
        this.executorService = Executors.newFixedThreadPool(ConfigParams.SERVER_THREADS_COUNT);
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);

        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("Client accepted");

            executorService.execute(() -> {
                try {
                    processQuery(client);
                } catch (SocketException e) {
                    System.out.println("Client disconnected");
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            });
        }
    }

    private void processQuery(Socket client) throws IOException {
        DataInputStream in = new DataInputStream(client.getInputStream());
        DataOutputStream out = new DataOutputStream(client.getOutputStream());

        while (true) {
            int command = in.readInt();

            switch (command) {
                case 0 -> {
                    String brand = IoUtils.readString(in);
                    IoUtils.writeListOfCars(out, carDao.findByBrand(brand));
                }

                case 1 -> {
                    int threshold = in.readInt();
                    IoUtils.writeListOfCars(out, carDao.findByYearsOfExploitationExclusiveThreshold(threshold));
                }

                case 2 -> {
                    int yearOfProduction = in.readInt();
                    BigDecimal threshold = new BigDecimal(IoUtils.readString(in));
                    IoUtils.writeListOfCars(out, carDao.findByYearOfProductionAndPriceExclusiveThreshold(
                            yearOfProduction,
                            threshold)
                    );
                }

                default -> System.out.println("Unexpected command");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocketTask8 server = new ServerSocketTask8(
              ConfigParams.PORT,
              new Car(0L, "Skoda", "Oktavia", 2015, "Yellow", BigDecimal.valueOf(1000L), "AM 1111 AM"),
              new Car(1L, "Renault", "Kangoo", 2018, "Green", BigDecimal.valueOf(1500L), "AA 2222 AM"),
              new Car(2L, "BMW", "X5", 2022, "Black", BigDecimal.valueOf(2200L), "AA 3333 AM"),
              new Car(3L, "Volkswagen", "Passat", 2018, "Blue", BigDecimal.valueOf(1000L), "AA 4444 AM")
        );

        server.start();
    }
}
