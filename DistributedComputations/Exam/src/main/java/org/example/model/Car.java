package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car implements Serializable {
    private Long id;
    private String brand;
    private String model;
    private int yearOfProduction;
    private String color;
    private BigDecimal price;
    private String plateNumber;
}
