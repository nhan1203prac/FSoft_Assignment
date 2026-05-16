package Exceptions;

public class VehicleNumberException extends Exception {
    private String itemName;

    public VehicleNumberException(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String getMessage() {
        // Trả về phần: occurs at item <Item Name>
        return "occured at item " + itemName;
    }
}