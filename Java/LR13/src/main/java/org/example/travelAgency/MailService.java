package org.example.travelAgency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MailService {
    @Autowired
    private Request request;

    public void sendMailOfRequest(LocalDate date) {
        System.out.println("Отправлено сообщение на почту:");
        System.out.println("\"Оформлена заявка на тур: ");
        System.out.println(request.getTrip());
        System.out.println("Дата оформления: " + date + "\"\n\n");
    }
}
