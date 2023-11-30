package org.example.travelAgency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class TravelService {
    @Autowired
    private Request request;

    @Autowired
    private TravelersCard travelersCard;

    @Autowired
    private TripService tripService;

    @Autowired
    private MailService mailService;

    public void addTripToRequest(String date, String arrival) {
        request.addTrip(date, arrival);
    }

    public void order(String date, String arrival) {
        if (tripService.findByDate(date, arrival) != null) {
            request.addTrip(date, arrival);
            travelersCard.addTrip(request.getDateOfRegistration(), request.getTrip());

            request.setDateOfRegistration(LocalDate.of(1900, 1, 1));
        } else {
            System.out.println("В заявке нет туров");
            return;
        }
        System.out.println("Количество оформленных туров: " + travelersCard.getSize());

        travelersCard.printTravelersCard();
        mailService.sendMailOfRequest(LocalDate.now());
        request.setTrip(null);
    }

    public void printAllTrips() {
        tripService.printAll();
    }

}
