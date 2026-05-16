package DAO;

import Common.DbConnection;
import Entities.ElectricVan;
import Entities.Truck;
import Entities.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class VehicleDAO {

    public void importVehicles(List<Vehicle> vehicleList) {
        String sql = "INSERT INTO Vehicles (VehicleID, VehicleType, Capacity, DriverCount, " +
                "MaintenanceNum, Region, HasGpsTracker, PurchasePrice, VehicleStatus, " +
                "TruckAxleCount, HasRefrigeration, BatteryRange) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Vehicle v : vehicleList) {
                pstmt.setString(1, v.getId());
                pstmt.setString(2, v.getType());
                pstmt.setDouble(3, v.getCapacity());
                pstmt.setInt(4, v.getDriverCount());
                pstmt.setInt(5, v.getMaintenanceNum());
                pstmt.setString(6, v.getRegion());
                pstmt.setBoolean(7, v.isHasGpsTracker());
                pstmt.setDouble(8, v.getPurchasePrice());
                pstmt.setString(9, v.getStatus());

                if (v instanceof Truck) {
                    Truck t = (Truck) v;
                    pstmt.setInt(10, t.getAxleCount());
                    pstmt.setBoolean(11, t.isHasRefrigeration());
                    pstmt.setNull(12, java.sql.Types.DECIMAL);
                } else if (v instanceof ElectricVan) {
                    ElectricVan ev = (ElectricVan) v;
                    pstmt.setNull(10, java.sql.Types.INTEGER);
                    pstmt.setNull(11, java.sql.Types.BIT);
                    pstmt.setDouble(12, ev.getBatteryRange());
                }

                pstmt.executeUpdate();
            }

            System.out.println(">>> Thêm thành công " + vehicleList.size() + " phương tiện vào SQL Server.");

        } catch (SQLException e) {
            System.err.println("Lỗi kết nối hoặc thực thi SQL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}