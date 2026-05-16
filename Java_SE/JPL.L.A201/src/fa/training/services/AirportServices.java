package fa.training.services;

import fa.training.entities.Airport;
import fa.training.utils.Validator;

import java.util.List;
import java.util.Scanner;

public class AirportServices {
    public static Airport findAirport(String id, List<Airport> airports) {
        return airports.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }

    public static void inputAirport(Scanner sc, List<Airport> airports) {
        String id, name;
        do {
            System.out.print("Airport ID (APxxxxx): ");
            id = sc.nextLine();
        } while (!Validator.isValidAirportId(id));

        System.out.print("Airport Name: ");
        name = sc.nextLine();
        System.out.print("Runway Size: ");
        double size = Double.parseDouble(sc.nextLine());
        System.out.print("Max Fixedwing Parking: ");
        int maxFW = Integer.parseInt(sc.nextLine());
        System.out.print("Helicopter Parking: ");
        int maxRW = Integer.parseInt(sc.nextLine());

        airports.add(new Airport(id, name, size, maxFW, maxRW));
        System.out.println("Airport added successfully!");
    }
}
