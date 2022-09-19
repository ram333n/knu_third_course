package org.example.trips;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.example.transport.Transport;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractTrip {
    protected String description;
    protected BigDecimal price;
    protected Transport transport;
    protected LocalDate startDate;
    protected int duration;
    protected boolean isNutritionIncluded;

    @Override
    public String toString() {
        return String.format("Description : %s%n" +
                                    "Price : %s%n" +
                                    "Transport : %s%n" +
                                    "Start date : %s%n" +
                                    "Duration(days) : %d%n" +
                                    "Nutrition included : %s%n",
                                    description,
                                    price,
                                    transport,
                                    startDate,
                                    duration,
                                    isNutritionIncluded
                             );
    }
}