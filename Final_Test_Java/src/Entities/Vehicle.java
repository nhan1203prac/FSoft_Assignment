package Entities;

public abstract class Vehicle {
    private String id;
    private String type;
    private double capacity;
    private int driverCount;
    private int maintenanceNum;
    private String region;
    private boolean hasGpsTracker;
    private double purchasePrice;
    private String status;

    public Vehicle(String id, String type, double capacity, int driverCount, int maintenanceNum, String region, boolean hasGpsTracker, double purchasePrice, String status) {
        this.id = id;
        this.type = type;
        this.capacity = capacity;
        this.driverCount = driverCount;
        this.maintenanceNum = maintenanceNum;
        this.region = region;
        this.hasGpsTracker = hasGpsTracker;
        this.purchasePrice = purchasePrice;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public int getDriverCount() {
        return driverCount;
    }

    public void setDriverCount(int driverCount) {
        this.driverCount = driverCount;
    }

    public int getMaintenanceNum() {
        return maintenanceNum;
    }

    public void setMaintenanceNum(int maintenanceNum) {
        this.maintenanceNum = maintenanceNum;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public boolean isHasGpsTracker() {
        return hasGpsTracker;
    }

    public void setHasGpsTracker(boolean hasGpsTracker) {
        this.hasGpsTracker = hasGpsTracker;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract void displayVehicleInfo();

}
