package net.eugenpaul.adventofcode.y2015.day3;

import java.util.HashMap;
import java.util.Map;

public class CityMap {
    private Map<Integer, Map<Integer, Integer>> innermap = new HashMap<>();

    public boolean addVisitedHouse(int x, int y) {
        Map<Integer, Integer> yLine = innermap.computeIfAbsent(x, s -> new HashMap<>());
        yLine.compute(y, (s, alt) -> (null == alt) ? 1 : alt++);
        return true;
    }

    public int getHouseCount() {
        int responseCounter = 0;
        for (Map.Entry<Integer, Map<Integer, Integer>> entry : innermap.entrySet()) {
            responseCounter += entry.getValue().size();
        }
        return responseCounter;
    }
}
