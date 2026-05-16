package Manager;

import Entities.ElectricVan;
import Entities.Truck;
import Entities.Vehicle;
import java.util.List;

public class VehicleSearchService {

    public void searchActiveVehicles(List<Vehicle> vehicleList, double minCapacity, int exactDriverCount, String region, int axlesCount, boolean hasGPS, double purchasePrice) {
        System.out.println("\n=== KẾT QUẢ TÌM KIẾM XE ===");
        boolean found = false;

        for (Vehicle v : vehicleList) {
            if (!"ACTIVE".equalsIgnoreCase(v.getStatus())) {
                continue;
            }
            boolean matchGPS = v.isHasGpsTracker() == hasGPS;
            boolean matchPurchasePrice = v.getPurchasePrice() == purchasePrice;
            boolean matchCapacity = v.getCapacity() >= minCapacity;
            boolean matchDrivers = v.getDriverCount() == exactDriverCount;
            boolean matchRegion = v.getRegion().equalsIgnoreCase(region);

            if (matchCapacity && matchDrivers && matchRegion && matchGPS && matchPurchasePrice) {
                if (v instanceof Truck) {
                    Truck t = (Truck) v;
                    if (t.getAxleCount() == axlesCount) {
                        v.displayVehicleInfo();
                        found = true;
                    }
                }
                else if (v instanceof ElectricVan) {
                    v.displayVehicleInfo();
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy phương tiện nào thỏa mãn điều kiện.");
        }
    }
}