package org.example.travelAgency;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Trip {
    private int id;
    private LocalDate date;
    private String departure;
    private String arrival;
}
