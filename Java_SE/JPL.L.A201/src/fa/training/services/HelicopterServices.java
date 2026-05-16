package fa.training.services;

import fa.training.entities.Airport;
import fa.training.entities.Helicopter;
import fa.training.utils.Validator;

import java.util.List;
import java.util.Scanner;

public class HelicopterServices {
    public static void inputHelicopter(Scanner sc, List<Helicopter> helicopters) {
        String id, model;
        do {
            System.out.print("Helicopter ID (RWxxxxx): ");
            id = sc.nextLine();
        } while (!Validator.isValidHelicopterId(id));

        System.out.print("Model: ");
        model = sc.nextLine();
        System.out.print("Cruise Speed: ");
        double speed = Double.parseDouble(sc.nextLine());
        System.out.print("Empty Weight: ");
        double empty = Double.parseDouble(sc.nextLine());

        double takeoff;
        do {
            System.out.print("Max Takeoff Weight (<= 1.5 * Empty): ");
            takeoff = Double.parseDouble(sc.nextLine());
            if (!Validator.isValidHeliWeight(empty, takeoff))
                System.out.println("Weight exceeds 1.5x empty weight!");
        } while (!Validator.isValidHeliWeight(empty, takeoff));

        System.out.print("Range: ");
        double range = Double.parseDouble(sc.nextLine());

        helicopters.add(new Helicopter(id, model, speed, empty, takeoff, range));
        System.out.println("Helicopter added to storage.");
    }

    public static void addRWToAirport(Scanner sc, List<Helicopter> helicopters, List<Airport> airports) {
        System.out.print("Airport ID: ");
        String apId = sc.nextLine();
        Airport ap = AirportServices.findAirport(apId, airports);
        if (ap == null) return;

        System.out.print("Helicopter ID: ");
        String rwId = sc.nextLine();
        Helicopter rw = helicopters.stream().filter(f -> f.getId().equals(rwId)).findFirst().orElse(null);
        if(rw != null) {
            if (ap.getHelicopterIds().size() < ap.getMaxRW()) {
                ap.getHelicopterIds().add(rwId);
                System.out.println("Helicopter parked.");
            } else {
                System.out.println("Airport full for helicopters!");
            }
        }

    }
}
