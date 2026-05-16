package fa.training.utils;

public class Validator {

    public static boolean isValidAirportId(String id) {
        return id != null && id.matches("^AP\\d{5}$");
    }

    public static boolean isValidFixedWingId(String id) {
        return id != null && id.matches("^FW\\d{5}$");
    }

    public static boolean isValidHelicopterId(String id) {
        return id != null && id.matches("^RW\\d{5}$");
    }

    public static boolean isValidModel(String model) {
        return model != null && model.length() <= 40;
    }

    public static boolean isValidPlaneType(String type) {
        return type != null && type.matches("^(CAG|LGR|PRV)$");
    }

    public static boolean isValidHeliWeight(double empty, double takeoff) {
        return takeoff <= 1.5 * empty;
    }

    public static boolean isPositive(double value) {
        return value > 0;
    }
}