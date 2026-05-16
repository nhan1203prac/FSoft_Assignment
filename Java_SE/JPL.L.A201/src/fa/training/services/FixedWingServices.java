package fa.training.services;

import fa.training.entities.Airport;
import fa.training.entities.FixedWing;
import fa.training.entities.PlaneType;
import fa.training.utils.Validator;

import java.util.List;
import java.util.Scanner;

public class FixedWingServices {
    public static void inputFixedwing(Scanner sc, List<FixedWing> fixedWings) {
        String id, model, type;
        do {
            System.out.print("Enter Fixedwing ID (FWxxxxx): ");
            id = sc.nextLine();
        } while (!Validator.isValidFixedWingId(id));

        do {
            System.out.print("Enter Model (max 40 chars): ");
            model = sc.nextLine();
        } while (!Validator.isValidModel(model));

        do {
            System.out.print("Enter Type (CAG/LGR/PRV): ");
            type = sc.nextLine().toUpperCase();
        } while (!Validator.isValidPlaneType(type));

        System.out.print("Cruise Speed: ");
        double speed = Double.parseDouble(sc.nextLine());
        System.out.print("Empty Weight: ");
        double empty = Double.parseDouble(sc.nextLine());
        System.out.print("Max Takeoff Weight: ");
        double takeoff = Double.parseDouble(sc.nextLine());
        System.out.print("Min Needed Runway Size: ");
        double runway = Double.parseDouble(sc.nextLine());

        fixedWings.add(new FixedWing(id, model, speed, empty, takeoff, PlaneType.valueOf(type), runway));
        System.out.println("Fixedwing added to storage.");
    }

    public static void addFWToAirport(Scanner sc, List<FixedWing> fixedWings, List<Airport> airports) {
        System.out.print("Enter Airport ID: ");
        String apId = sc.nextLine();
        Airport ap = AirportServices.findAirport(apId, airports);
        if (ap == null) return;

        System.out.print("Enter Fixedwing ID: ");
        String fwId = sc.nextLine();
        FixedWing fw = fixedWings.stream().filter(f -> f.getId().equals(fwId)).findFirst().orElse(null);

        if (fw != null) {
            if (fw.getMinNeededRunwaySize() > ap.getRunwaySize()) {
                System.out.println("Error: Airport runway too small for this plane!");
            } else if (ap.getFixedWingIds().size() >= ap.getMaxFW()) {
                System.out.println("Error: Airport full for fixed wings!");
            } else {
                ap.getFixedWingIds().add(fwId);
                System.out.println("Plane parked successfully.");
            }
        } else {
            System.out.println("Plane not found in storage.");
        }
    }
}
