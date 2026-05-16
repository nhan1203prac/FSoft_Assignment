package Manager;

import DAO.VehicleDAO;
import Entities.*;
import Service.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VehicleManager {
    public static void main(String[] args) {
        CSVReader reader = new CSVReader();
        List<Vehicle> vehicles = reader.readFromCSV("vehicles.csv");

        System.out.println("--- DỮ LIỆU ĐỌC TỪ FILE CSV ---");
        for (Vehicle v : vehicles) {
            v.displayVehicleInfo();
        }

        VehicleDAO dao = new VehicleDAO();
        dao.importVehicles(vehicles);

        VehicleSearchService searchService = new VehicleSearchService();

        searchService.searchActiveVehicles(vehicles, 10.0, 2, "NORTH", 3, true, 180000000 );
    }



}
