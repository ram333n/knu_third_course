package org.example.dao;

import org.example.model.Car;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CarDao {
    private final List<Car> cars;

    public CarDao() {
        this.cars = new ArrayList<>();
    }

    public CarDao(Car... initValues) {
        this();

        Collections.addAll(cars, initValues);
    }

    public List<Car> findByBrand(String brand) {
        return findByPredicate(car -> Objects.equals(car.getBrand(), brand));
    }

    public List<Car> findByYearsOfExploitationExclusiveThreshold(int threshold) {
        return findByPredicate(car -> LocalDate.now().getYear() - car.getYearOfProduction() > threshold);
    }

    public List<Car> findByYearOfProductionAndPriceExclusiveThreshold(int yearOfProduction, BigDecimal priceThreshold) {
        return findByPredicate(car -> car.getYearOfProduction() == yearOfProduction
                && car.getPrice().compareTo(priceThreshold) > 0);
    }

    private List<Car> findByPredicate(Predicate<Car> predicate) {
        return cars.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
