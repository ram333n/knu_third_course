package org.example.socket;

import org.example.config.ConfigParams;
import org.example.model.Car;
import org.example.util.IoUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.List;

public class ClientSocketTask8 {
    private Socket socket;
    private final String host;
    private final int port;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientSocketTask8(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public List<Car> findByBrand(String brand) throws IOException {
        out.writeInt(0);
        IoUtils.writeString(out, brand);

        return IoUtils.readListOfCars(in);
    }

    public List<Car> findByYearsOfExploitationExclusiveThreshold(int threshold) throws IOException {
        out.writeInt(1);
        out.writeInt(threshold);

        return IoUtils.readListOfCars(in);
    }

    public List<Car> findByYearOfProductionAndPriceExclusiveThreshold(int yearOfProduction, BigDecimal priceThreshold)
            throws IOException {
        out.writeInt(2);
        out.writeInt(yearOfProduction);
        IoUtils.writeString(out, priceThreshold.toString());

        return IoUtils.readListOfCars(in);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ClientSocketTask8 client = new ClientSocketTask8(ConfigParams.HOST, ConfigParams.PORT);
        client.connect();

        Thread anotherThread = new Thread(() -> {
            try {
                ClientSocketTask8 anotherClient = new ClientSocketTask8(ConfigParams.HOST, ConfigParams.PORT);
                anotherClient.connect();

                System.out.println("c2: " + anotherClient.findByBrand("BMW"));
                System.out.println("c2: " + anotherClient.findByYearsOfExploitationExclusiveThreshold(2));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        anotherThread.start();

        System.out.println("c1: " + client.findByBrand("Renault"));
        System.out.println("c1: " + client.findByYearsOfExploitationExclusiveThreshold(5));
        System.out.println("c1: " + client.findByYearOfProductionAndPriceExclusiveThreshold(2022, BigDecimal.valueOf(2000L)));
        System.out.println("c1: " + client.findByYearOfProductionAndPriceExclusiveThreshold(2015, BigDecimal.valueOf(500L)));

        anotherThread.join();
    }
}
