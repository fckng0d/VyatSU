package example;

import java.util.*;

public class Shedule {
    private final LinkedHashMap<String, TreeSet<String>> shedule = new LinkedHashMap<>();

    public void add(String day, String event) {
        if (shedule.containsKey(day)) {
            shedule.get(day).add(event);
        } else {
            TreeSet<String> events = new TreeSet<>((o2, o1) -> o1.trim().compareTo(o2.trim()));
            events.add(event);
            shedule.put(day, events);
        }
    }

    public void getEvents(String day) {
        if (shedule.containsKey(day)) {
            System.out.printf("На %s запланировано:\n", day);
            for (String event : shedule.get(day)) {
                System.out.println("— " + event);
            }
            System.out.println();
        } else {
            System.out.printf("На %s ничего не запланировано \n\n", day);
        }
    }

    public void getDate(String event) {
        List<String> days = new ArrayList<>();
        for (Map.Entry<String, TreeSet<String>> entry : shedule.entrySet()) {
            if (entry.getValue().contains(event)) {
                days.add(entry.getKey());
            }
        }
        if (days.isEmpty()) {
            System.out.printf("В расписании нет мероприятия \"%s\"\n\n", event);
        } else {
            System.out.printf("Мероприятие \"%s\" запланировано на:\n", event);
            for (String day : days) {
                System.out.println("— " + day);
            }
            System.out.println();
        }
    }

    public void printShedule() {
        System.out.println("Расписание:\n" + shedule.entrySet());
    }
}
