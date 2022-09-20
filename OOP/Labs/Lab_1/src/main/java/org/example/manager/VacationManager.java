package org.example.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.vacations.AbstractVacation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Getter //just for test
public class VacationManager {
    private final List<AbstractVacation> vacationStorage;

    public VacationManager() {
        vacationStorage = new ArrayList<>();
    }

    public void addVacation(AbstractVacation vacation) {
        vacationStorage.add(vacation);
    }

    public void print() {
        for(int i = 0; i < vacationStorage.size(); i++) {
            System.out.printf("Variant : %d%n%s", i, vacationStorage.get(i));
            System.out.println("------------------------------------");
        }
    }

    public void sort(SortBy filter) {
        vacationStorage.sort(getComparator(filter));
    }

    public AbstractVacation buyVacation(int index) {
        if(index < 0 || index > vacationStorage.size() - 1) {
            throw new IllegalArgumentException("Wrong variant provided");
        }

        return vacationStorage.get(index);
    }

    private Comparator<AbstractVacation> getComparator(SortBy filter) {
        switch (filter) {
            case PRICE -> {
                return Comparator.comparing(AbstractVacation::getPrice);
            }

            case DATE -> {
                return Comparator.comparing(AbstractVacation::getStartDate);
            }

            case DURATION -> {
                return Comparator.comparing(AbstractVacation::getDuration);
            }
        }

        return null; // TODO : think how to replace it
    }
}
