package Service;

import Entities.*;
import Exceptions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public List<Vehicle> readFromCSV(String filePath) {
        List<Vehicle> list = new ArrayList<>();
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (lineNumber == 0) {
                    lineNumber++;
                    continue;
                }

                try {
                    String[] data = line.split(",", -1);

                    String id = data[0].trim();
                    String type = data[1].trim();
                    double capacity = parseDouble(data[2]);
                    int drivers = parseInt(data[3]);
                    int maintenance = parseInt(data[4]);
                    String region = data[5].trim();
                    int axles = parseInt(data[6]);
                    String gpsRaw = data[7].trim();
                    System.out.println(gpsRaw);
                    String refrigRaw = data[8].trim();
                    double range = parseDouble(data[9]);
                    double price = parseDouble(data[10]);
                    String status = data[11].trim();

                    if (capacity <= 5) throw new VehicleNumberException("VEHICLE_CAPACITY");

                    if (drivers <= 0) throw new VehicleNumberException("DRIVER_COUNT");
                    if (maintenance <= 0) throw new VehicleNumberException("MAINTAINANCE_NUM");
                    if ("TRUCK".equalsIgnoreCase(type) && axles <= 0) throw new VehicleNumberException("TRUCK_AXLE_COUNT");

                    if (!gpsRaw.equals("0") && !gpsRaw.equals("1")) throw new VehicleNumberException("HAS_GPS_TRACKER");
                    if ("TRUCK".equalsIgnoreCase(type) && !refrigRaw.equals("0") && !refrigRaw.equals("1"))
                        throw new VehicleNumberException("HAS_REFRIGERATION");

                    if (price <= 50000000) throw new VehicleNumberException("VEHICLE_PURCHASE_PRICE");

                    if ("ELECTRIC_VAN".equalsIgnoreCase(type) && data[9].trim().isEmpty())
                        throw new VehicleTypeException("BATTERY_RANGE");
                    if ("TRUCK".equalsIgnoreCase(type) && !data[9].trim().isEmpty())
                        throw new VehicleTypeException("BATTERY_RANGE");

                    boolean hasGps = gpsRaw.equals("1");
                    boolean hasRefrig = refrigRaw.equals("1");

                    if ("TRUCK".equalsIgnoreCase(type)) {
                        list.add(new Truck(id, capacity, drivers, maintenance, region, hasGps, price, status, axles, hasRefrig));
                    } else if ("ELECTRIC_VAN".equalsIgnoreCase(type)) {
                        list.add(new ElectricVan(id, capacity, drivers, maintenance, region, hasGps, price, status, range));
                    }

                    System.out.println("Successfully read data from line " + lineNumber);

                } catch (VehicleNumberException | VehicleTypeException e) {
                    System.out.println("Error in data line " + lineNumber + ":"
                            + e.getClass().getSimpleName() + " " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Error in data line " + lineNumber + ":InvalidDataException occured at item " + e.getClass().getSimpleName());
                    e.printStackTrace();
                }

                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        }
        return list;
    }

    private double parseDouble(String s) {
        return (s == null || s.trim().isEmpty()) ? 0 : Double.parseDouble(s.trim());
    }

    private int parseInt(String s) {
        return (s == null || s.trim().isEmpty()) ? 0 : Integer.parseInt(s.trim());
    }
}