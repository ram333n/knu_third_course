package org.example.vacations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.example.transport.Transport;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public abstract class AbstractVacation {
    protected final String description;
    protected final BigDecimal price;
    protected final Transport transport;
    protected final LocalDate startDate;
    protected final int duration;
    protected final boolean isNutritionIncluded;

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