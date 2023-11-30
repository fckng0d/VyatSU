package org.example.travelAgency;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class TripService {
    private List<Trip> tripList = new ArrayList<>();

    @PostConstruct
    private void initMethod() {
        tripList.add(new Trip(1, LocalDate.of(2020, 11, 10), "Москва", "Киров"));
        tripList.add(new Trip(2, LocalDate.of(2022, 06, 02), "Питер", "Казань"));
        tripList.add(new Trip(3, LocalDate.of(2023, 12, 17), "Киров", "Москва"));
        tripList.add(new Trip(4, LocalDate.of(2021, 01, 17), "Ямайка", "Пермь"));
        tripList.add(new Trip(5, LocalDate.of(2020, 02, 17), "Пекин", "Токио"));
        tripList.add(new Trip(6, LocalDate.of(2022, 03, 17), "Пекин", "Токио"));
        tripList.add(new Trip(7, LocalDate.of(2022, 04, 17), "Токио", "Сингапур"));
        tripList.add(new Trip(8, LocalDate.of(2021, 05, 17), "Иерусалим", "Москва"));
        tripList.add(new Trip(9, LocalDate.of(2023, 06, 17), "Москва", "Париж"));
        tripList.add(new Trip(10, LocalDate.of(2019, 07, 17), "Стокгольм", "Окинава"));
    }

    public Trip findByDate(String date, String arrival) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date2 = LocalDate.parse(date, formatter);
        for (Trip trip : tripList) {
            if (trip.getDate().equals(date2) && trip.getArrival().equals(arrival)) {
                return trip;
            }
        }
        return null;
    }

    public void printAll() {
        System.out.println("Список всех туров: ");
        for (Trip trip : tripList) {
            System.out.println(trip);
        }
        System.out.println();
    }
}
