package Entities;

public class ElectricVan extends Vehicle {
    private double batteryRange;

    public ElectricVan(String id, double capacity, int driverCount, int maintenanceNum,
                       String region, boolean hasGps, double price, String status,
                       double batteryRange) {
        super(id, "ELECTRIC_VAN", capacity, driverCount, maintenanceNum, region, hasGps, price, status);
        this.batteryRange = batteryRange;
    }

    public double getBatteryRange() {
        return batteryRange;
    }

    public void setBatteryRange(double batteryRange) {
        this.batteryRange = batteryRange;
    }

    @Override
    public void displayVehicleInfo() {
        System.out.printf("[EV_VAN] ID: %s | Range: %.1f km | Status: %s%n",
                getId(), batteryRange, getStatus());
    }
}
