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
        if (data.getCity1().compareToIgnoreCase(data.getCity2()) > 0) {
            cityDistances.compute(data.getCity1(), //
                    (k, v) -> {
                        if (null == v) {
                            Map<String, Integer> distances = new HashMap<>();
                            distances.put(data.getCity2(), data.getDistance());
                            return distances;
                        } else {
                            v.put(data.getCity2(), data.getDistance());
                            return v;
                        }
                    });
            cityDistances.computeIfAbsent(data.getCity2(), k -> new HashMap<>());
        } else {
            cityDistances.compute(data.getCity2(), //
                    (k, v) -> {
                        if (null == v) {
                            Map<String, Integer> distances = new HashMap<>();
                            distances.put(data.getCity1(), data.getDistance());
                            return distances;
                        } else {
                            v.put(data.getCity1(), data.getDistance());
                            return v;
                        }
                    });
            cityDistances.computeIfAbsent(data.getCity1(), k -> new HashMap<>());
        }

    }

    public List<String> getLocationList() {
        return new ArrayList<>(cityDistances.keySet());
    }

    public int getDistance(String[] route) {
        int fullDistance = 0;
        for (int i = 0; i <= route.length - 2; i++) {
            if (route[i].compareToIgnoreCase(route[i + 1]) > 0) {
                fullDistance += cityDistances.get(route[i]).get(route[i + 1]);
            } else {
                fullDistance += cityDistances.get(route[i + 1]).get(route[i]);
            }
        }
        return fullDistance;
    }
}
