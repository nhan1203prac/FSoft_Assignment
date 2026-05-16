create database Vehicle_Management;
use Vehicle_Management;


CREATE TABLE Vehicles (
    VehicleID VARCHAR(5) PRIMARY KEY,
    VehicleType VARCHAR(20) NOT NULL,
    Capacity DECIMAL(10, 2),
    DriverCount INT,
    MaintenanceNum INT,
    Region NVARCHAR(20),
    HasGpsTracker BIT,
    PurchasePrice DECIMAL(18, 2),
    VehicleStatus NVARCHAR(20),
    
    -- Thuộc tính riêng của Truck
    TruckAxleCount INT NULL,
    HasRefrigeration BIT NULL,
    
    -- Thuộc tính riêng của Electric Van
    BatteryRange DECIMAL(10, 2) NULL
);
GO