package org.example.trips;

import org.example.transport.Transport;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Excursion extends AbstractTrip {
    public Excursion(String description,
                     BigDecimal price,
                     Transport transport,
                     LocalDate startDate,
                     int duration,
                     boolean isNutritionIncluded) {
        super(description, price, transport, startDate, duration, isNutritionIncluded);
    }

    @Override
    public String toString() {
        return String.format("Type : %s%n%s", "excursion", super.toString());
    }
}
