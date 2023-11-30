package org.example.travelAgency;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class TravelersCard {
    @Getter
    public Map<LocalDate, List<Trip>> tripMap = new LinkedHashMap<>();

    @Getter
    @Setter
    private LocalDate dateOfRegistration;

    public void printTravelersCard() {
        System.out.println("Дорожная карта: ");
        for (Map.Entry<LocalDate, List<Trip>> entry : tripMap.entrySet()) {
            System.out.println(entry);
        }
        System.out.println();
    }

    public void addTrip(LocalDate date, Trip trip) {
        if (tripMap.containsKey(date)) {
            tripMap.get(date).add(trip);
        } else {
            tripMap.put(date, new ArrayList<>());
            tripMap.get(date).add(trip);
        }
    }

    public int getSize() {
        int count = 0;
        for (Map.Entry<LocalDate, List<Trip>> entry : tripMap.entrySet()) {
            count += entry.getValue().size();
        }
        return count;
    }
}

