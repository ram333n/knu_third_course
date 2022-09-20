package org.example.vacations;

import org.example.transport.Transport;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Shopping extends AbstractVacation {
    public Shopping(String description,
                    BigDecimal price,
                    Transport transport,
                    LocalDate startDate,
                    int duration,
                    boolean isNutritionIncluded) {
        super(description, price, transport, startDate, duration, isNutritionIncluded);
    }

    @Override
    public String toString() {
        return String.format("Type : %s%n%s", "shopping", super.toString());
    }
}
