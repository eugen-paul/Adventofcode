package net.eugenpaul.adventofcode.y2015.day9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullMap {
    private Map<String, Map<String, Integer>> cityDistances;

    public FullMap() {
        cityDistances = new HashMap<>();
    }

    public void addRoute(Route data) {
        cityDistances.computeIfAbsent(data.getCity1(), k->new HashMap<>()).put(data.getCity2(), data.getDistance());
        cityDistances.computeIfAbsent(data.getCity2(), k->new HashMap<>()).put(data.getCity1(), data.getDistance());
    }

    public List<String> getLocationList() {
        return new ArrayList<>(cityDistances.keySet());
    }

    public int getDistance(String[] route) {
        int fullDistance = 0;
        for (int i = 0; i <= route.length - 2; i++) {
            fullDistance += cityDistances.get(route[i]).get(route[i + 1]);
        }
        return fullDistance;
    }
}
