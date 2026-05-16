package fa.training.entities;

import java.io.Serializable;
import java.util.List;

public class DataStore implements Serializable {
    private List<Helicopter> helicopters;
    private List<Airport> airports;
    private List<FixedWing> fixedWings;

    public DataStore() {}

    public DataStore(List<Helicopter> helicopters, List<Airport> airports, List<FixedWing> fixedWings) {
        this.helicopters = helicopters;
        this.airports = airports;
        this.fixedWings = fixedWings;
    }

    public List<Helicopter> getHelicopters() {
        return helicopters;
    }

    public void setHelicopters(List<Helicopter> helicopters) {
        this.helicopters = helicopters;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    public List<FixedWing> getFixedWings() {
        return fixedWings;
    }

    public void setFixedWings(List<FixedWing> fixedWings) {
        this.fixedWings = fixedWings;
    }
}
