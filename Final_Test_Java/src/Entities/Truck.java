package Entities;

public class Truck extends Vehicle {
    private int axleCount;
    private boolean hasRefrigeration;

    public Truck(String id, double capacity, int driverCount, int maintenanceNum,
                 String region, boolean hasGps, double price, String status,
                 int axleCount, boolean hasRefrigeration) {
        super(id, "TRUCK", capacity, driverCount, maintenanceNum, region, hasGps, price, status);
        this.axleCount = axleCount;
        this.hasRefrigeration = hasRefrigeration;
    }

    public int getAxleCount() {
        return axleCount;
    }

    public void setAxleCount(int axleCount) {
        this.axleCount = axleCount;
    }

    public boolean isHasRefrigeration() {
        return hasRefrigeration;
    }

    public void setHasRefrigeration(boolean hasRefrigeration) {
        this.hasRefrigeration = hasRefrigeration;
    }

    @Override
    public void displayVehicleInfo() {
        System.out.printf("[TRUCK] ID: %s | Axles: %d | Refrigo: %b | Status: %s%n",
                getId(), axleCount, hasRefrigeration, getStatus());
    }
}
