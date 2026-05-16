package Exceptions;

public class VehicleTypeException extends Exception {
    private String itemName;

    public VehicleTypeException(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String getMessage() {
        return "occured at item " + itemName;
    }
}