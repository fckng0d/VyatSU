package org.example;

import org.example.travelAgency.TravelService;
import org.example.travelAgency.TravelersCard;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Array;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext(
//                "applicationContext.xml");

        ApplicationContext context = new AnnotationConfigApplicationContext(
                ApplicationConfig.class);

        TravelService travelService = context.getBean("travelService", TravelService.class);


        travelService.order("2020-11-10", "Киров");
        travelService.order("2022-03-17", "Токио");
//
//        System.out.println();
//        travelService.printAllTrips();

    }
}