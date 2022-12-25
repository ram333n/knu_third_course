package org.example.util;

import org.example.model.Car;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class IoUtils {
    private IoUtils() {}

    public static void writeString(DataOutputStream out, String str) throws IOException {
        out.writeInt(str.length());
        out.writeBytes(str);
    }

    public static String readString(DataInputStream in) throws IOException {
        int length = in.readInt();
        byte[] data = new byte[length];
        in.readFully(data);

        return new String(data);
    }

    public static void writeCar(DataOutputStream out, Car car) throws IOException {
        out.writeLong(car.getId());
        writeString(out, car.getBrand());
        writeString(out, car.getModel());
        out.writeInt(car.getYearOfProduction());
        writeString(out, car.getColor());
        writeString(out, car.getPrice().toString());
        writeString(out, car.getPlateNumber());
    }

    public static Car readCar(DataInputStream in) throws IOException {
        Car result = new Car();

        result.setId(in.readLong());
        result.setBrand(readString(in));
        result.setModel(readString(in));
        result.setYearOfProduction(in.readInt());
        result.setColor(readString(in));
        result.setPrice(new BigDecimal(readString(in)));
        result.setPlateNumber(readString(in));

        return result;
    }

    public static void writeListOfCars(DataOutputStream out, List<Car> cars) throws IOException {
        out.writeInt(cars.size());

        for (Car car : cars) {
            writeCar(out, car);
        }
    }

    public static List<Car> readListOfCars(DataInputStream in) throws IOException {
        List<Car> result = new ArrayList<>();
        int listSize = in.readInt();

        for (int i = 0; i < listSize; i++) {
            result.add(readCar(in));
        }

        return result;
    }
}
