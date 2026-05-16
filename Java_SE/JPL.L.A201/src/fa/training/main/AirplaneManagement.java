package fa.training.main;

import fa.training.entities.*;
import fa.training.services.AirportServices;
import fa.training.services.FixedWingServices;
import fa.training.services.HelicopterServices;
import fa.training.utils.Validator;

import java.io.*;
import java.util.*;

public class AirplaneManagement {
    private static List<Airport> airports = new ArrayList<>();
    private static List<FixedWing> fixedWings = new ArrayList<>();
    private static List<Helicopter> helicopters = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("airports.txt"));
            DataStore data = (DataStore) ois.readObject();
            ois.close();

            airports = data.getAirports();
            fixedWings = data.getFixedWings();
            helicopters = data.getHelicopters();
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        while (true) {
            System.out.println("\n=== AIRPORT MANAGEMENT ===");
            System.out.println("1. Input Data (Airport/Plane)");
            System.out.println("2. Display All Airports (Sorted by ID)");
            System.out.println("3. Display One Airport Status");
            System.out.println("4. Add Fixed Wing Plane to Airport");
            System.out.println("5. Add Helicopter to Airport");
            System.out.println("6. Display All Fixed Wings");
            System.out.println("7. Display All Helicopters");
            System.out.println("8. Exit");
            System.out.print("Select: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1: inputMenu(); break;
                    case 2: displayAllAirports(); break;
                    case 3: displayOneAirport(); break;
                    case 4: FixedWingServices.addFWToAirport(sc, fixedWings, airports); break;
                    case 5: HelicopterServices.addRWToAirport(sc, helicopters, airports); break;
                    case 6: displayAllFW(); break;
                    case 7: displayAllRW(); break;
                    case 8:
                        saveData();
                        System.out.println("Data saved");
                        System.exit(0);
                    default: System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a number.");
            }
        }
    }


    private static void inputMenu() {
        System.out.println("1. Input Airport | 2. Input Fixedwing | 3. Input Helicopter");
        int type = Integer.parseInt(sc.nextLine());
        if (type == 1) AirportServices.inputAirport(sc, airports);
        else if (type == 2) FixedWingServices.inputFixedwing(sc, fixedWings);
        else if (type == 3) HelicopterServices.inputHelicopter(sc, helicopters);
    }

    private static void displayAllAirports() {
        airports.sort(Comparator.comparing(Airport::getId));
        for (Airport a : airports) {
            System.out.println("ID: " + a.getId() + " | Name: " + a.getName() + " | FW: " + a.getFixedWingIds().size() + "/" + a.getMaxFW()
            + " | RW: " + a.getHelicopterIds().size() + "/" + a.getMaxRW());
        }
    }

    private static void displayOneAirport() {
        System.out.print("Airport ID: ");
        String id = sc.nextLine();
        Airport ap = AirportServices.findAirport(id, airports);
        if (ap != null) {
            System.out.println("--- Airport Details ---");
            System.out.println("Name: " + ap.getName());
            System.out.println("Fixedwings parked: " + ap.getFixedWingIds());
            System.out.println("Helicopters parked: " + ap.getHelicopterIds());
        }
    }


    private static void saveData() {
        try {
            DataStore data = new DataStore(helicopters, airports, fixedWings);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("airports.txt"));

            oos.writeObject(data);
            oos.close();
        }catch (IOException e){
            System.out.println("Error saving file");
            e.printStackTrace();
        }
    }
    private static void displayAllFW() {
        fixedWings.forEach(System.out::println);
    }

    private static void displayAllRW() {
        helicopters.forEach(System.out::println);
    }
}