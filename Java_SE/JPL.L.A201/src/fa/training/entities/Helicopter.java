package fa.training.entities;

public class Helicopter extends Airplane{
    private double range;

    public Helicopter(String id, String model, double cruiseSpeed, double emptyWeight, double maxTakeOffWeight, double range) {
        super(id, model, cruiseSpeed, emptyWeight, maxTakeOffWeight);
        this.range = range;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    @Override
    public String fly() {
        return "Rotated wing";
    }
}
