package fa.training.entities;

public class FixedWing extends Airplane{
    private PlaneType planeType;
    private double minNeededRunwaySize;

    public FixedWing(String id, String model, double cruiseSpeed, double emptyWeight, double maxTakeOffWeight, PlaneType planeType, double minNeededRunwaySize) {
        super(id, model, cruiseSpeed, emptyWeight, maxTakeOffWeight);
        this.planeType = planeType;
        this.minNeededRunwaySize = minNeededRunwaySize;
    }

    public PlaneType getPlaneType() {
        return planeType;
    }

    public void setPlaneType(PlaneType planeType) {
        this.planeType = planeType;
    }

    public double getMinNeededRunwaySize() {
        return minNeededRunwaySize;
    }

    public void setMinNeededRunwaySize(double minNeededRunwaySize) {
        this.minNeededRunwaySize = minNeededRunwaySize;
    }

    @Override
    public String fly() {
        return "Fixed wing";
    }
}
