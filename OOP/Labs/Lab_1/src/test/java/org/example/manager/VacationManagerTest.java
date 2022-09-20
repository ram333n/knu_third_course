package org.example.manager;

import org.example.transport.Bus;
import org.example.transport.Plane;
import org.example.transport.Ship;
import org.example.vacations.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VacationManagerTest {
    private boolean isSorted(List<AbstractVacation> list, Comparator<AbstractVacation> comparator) {
        for(int i = 0; i < list.size() - 1; i++) {
            if(comparator.compare(list.get(i), list.get(i + 1)) > 0) {
                return false;
            }
        }

        return true;
    }
    private VacationManager initTestInput() {
        AbstractVacation[] data = {
            new Cruise("Cruise to moskva cruiser",
                       BigDecimal.valueOf(1500),
                       new Ship("Hetman Sahaydachnyi"),
                       LocalDate.now(),
                       5,
                       true),

            new Excursion("Excursion to Zhytomyr",
                          BigDecimal.valueOf(899.99),
                          new Bus("Luaz"),
                          LocalDate.now().plusDays(25),
                          1,
                          false),

            new Shopping("Globus mall",
                         BigDecimal.valueOf(2000),
                         null,
                         LocalDate.of(2023, 10, 3),
                         1,
                         false),

            new SickLeave("Sanatorium Truscavetc",
                          BigDecimal.valueOf(5000),
                          new Plane("An-225 Mriya"),
                          LocalDate.of(2022, 12, 23),
                          10,
                          true),

            new Vacation("Just vacation",
                          BigDecimal.valueOf(0),
                          null,
                          LocalDate.now(),
                          14,
                          false),

            new Excursion("Excursion to burnt moskva",
                          BigDecimal.valueOf(0),
                          new Plane("An-225 Mriya"),
                          LocalDate.now(),
                          1,
                          true),
        };

        return new VacationManager(Arrays.asList(data));
    }

    @Test
    void testLab() {
        VacationManager manager = initTestInput();

        System.out.println("----------------------INPUT----------------------\n\n");
        manager.print();

        List<AbstractVacation> vacationsList = manager.getVacationStorage();

        System.out.println("----------------------SORTED BY DATE----------------------\n\n");
        manager.sort(SortBy.DATE);
        assertTrue(isSorted(vacationsList, Comparator.comparing(AbstractVacation::getStartDate)));
        manager.print();

        System.out.println("----------------------SORTED BY DURATION----------------------\n\n");
        manager.sort(SortBy.DURATION);
        assertTrue(isSorted(vacationsList, Comparator.comparing(AbstractVacation::getDuration)));
        manager.print();

        System.out.println("----------------------SORTED BY PRICE----------------------\n\n");
        manager.sort(SortBy.PRICE);
        assertTrue(isSorted(vacationsList, Comparator.comparing(AbstractVacation::getPrice)));
        manager.print();

        //According to print, the cruise index is 3
        AbstractVacation expectedCruise = manager.buyVacation(3);

        assertTrue(expectedCruise instanceof Cruise);
        assertEquals("Hetman Sahaydachnyi", expectedCruise.getTransport().getModel());

        try {
            manager.buyVacation(6);
            fail();
        } catch (IllegalArgumentException e) {}
    }
}