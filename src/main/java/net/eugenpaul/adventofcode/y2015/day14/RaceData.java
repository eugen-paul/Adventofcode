package net.eugenpaul.adventofcode.y2015.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaceData {
    Map<Reindeer, Integer> distance;
    Map<Reindeer, Integer> points;
    List<Reindeer> reindeers;

    public RaceData(List<Reindeer> reindeers) {
        this.reindeers = reindeers;
    }

    public void doRace(int time) {
        initRace(reindeers);

        for (int i = 1; i <= time; i++) {
            int stepTime = i;
            distance.replaceAll((k, v) -> k.computeDistance(stepTime));
            setPoints();
        }
    }

    private void initRace(List<Reindeer> reindeers) {
        distance = new HashMap<>();
        points = new HashMap<>();
        for (Reindeer reindeer : reindeers) {
            distance.put(reindeer, 0);
            points.put(reindeer, 0);
        }
    }

    private void setPoints() {
        int[] maxDistance = { 0 };
        distance.forEach((k, v) -> {
            if (maxDistance[0] < v) {
                maxDistance[0] = v.intValue();
            }
        });
        points.replaceAll((k, v) -> (maxDistance[0] == distance.get(k)) ? v + 1 : v);
    }

    public int getWinnerPoints() {
        int[] maxPoints = { 0 };
        points.forEach((k, v) -> {
            if (maxPoints[0] < v) {
                maxPoints[0] = v.intValue();
            }
        });
        return maxPoints[0];
    }
}
