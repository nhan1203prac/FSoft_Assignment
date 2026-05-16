package fa.training.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Airport implements Serializable {
    private String id;
    private String name;
    private double runwaySize;
    private int maxFW, maxRW;
    private List<String> fixedWingIds = new ArrayList<>();
    private List<String> helicopterIds = new ArrayList<>();

    public Airport(String id, String name, double runwaySize, int maxFW, int maxRW) {
        this.id = id;
        this.name = name;
        this.runwaySize = runwaySize;
        this.maxFW = maxFW;
        this.maxRW = maxRW;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getRunwaySize() { return runwaySize; }
    public List<String> getFixedWingIds() { return fixedWingIds; }
    public List<String> getHelicopterIds() { return helicopterIds; }
    public int getMaxFW() { return maxFW; }
    public int getMaxRW() { return maxRW; }
}