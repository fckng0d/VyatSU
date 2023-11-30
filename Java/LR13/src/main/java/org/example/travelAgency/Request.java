package org.example.travelAgency;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
//@Scope("prototype")
public class Request {
    @Getter
    @Setter
    private Trip trip;

    @Getter
    @Setter
    private LocalDate dateOfRegistration;

    @Autowired
    private TripService tripService;

    public void addTrip(String date, String arrival) {
        if (tripService.findByDate(date, arrival) != null) {
            this.trip = tripService.findByDate(date, arrival);
            this.dateOfRegistration = LocalDate.now();

            System.out.println("Дата оформления заявки: " + this.dateOfRegistration);
        } else {
            System.out.println("Такого рейса нет");
        }
    }
}
