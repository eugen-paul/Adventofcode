package net.eugenpaul.adventofcode.y2018.day6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day6 extends SolutionTemplate {

    @Getter
    private long largestArea;
    @Getter
    private long areaPuzzle2;

    @Setter
    private int maxDistance = 10000;

    public static void main(String[] args) {
        Day6 puzzle = new Day6();
        puzzle.doPuzzleFromFile("y2018/day6/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<SimplePos> coordinates = eventData.stream()//
                .map(this::fromString)//
                .collect(Collectors.toList());

        largestArea = doPuzzle1(coordinates);
        areaPuzzle2 = doPuzzle2(coordinates);

        logger.log(Level.INFO, () -> "largestArea  : " + getLargestArea());
        logger.log(Level.INFO, () -> "areaPuzzle2  : " + getAreaPuzzle2());

        return true;
    }

    private int doPuzzle1(List<SimplePos> coordinates) {
        Map<SimplePos, Integer> fullArea = new HashMap<>();

        for (int i = 0; i < coordinates.size(); i++) {
            fullArea.put(coordinates.get(i), i);
        }

        // hopefully after 100 steps all finite areas will stop growing
        int steps = 100;

        for (int radius = 1; radius <= steps; radius++) {
            doStep(coordinates, fullArea, radius);
        }

        Map<Integer, Integer> areaSizeAfter100 = new HashMap<>();

        for (int i = 0; i < coordinates.size(); i++) {
            int number = i;
            long size = fullArea.values().stream().filter(v -> v == number).count();
            areaSizeAfter100.put(number, (int) size);
        }

        doStep(coordinates, fullArea, 101);

        Map<Integer, Integer> areaSizeAfter101 = new HashMap<>();

        for (int i = 0; i < coordinates.size(); i++) {
            int number = i;
            long size = fullArea.values().stream().filter(v -> v == number).count();
            areaSizeAfter101.put(number, (int) size);
        }

        // Assumption: all areas grown at step 101 are infinite areas.
        List<Integer> finiteAreas = new ArrayList<>();
        for (Entry<Integer, Integer> entry : areaSizeAfter100.entrySet()) {
            if (entry.getValue().equals(areaSizeAfter101.get(entry.getKey()))) {
                finiteAreas.add(entry.getValue());
            }
        }

        return finiteAreas.stream().sorted((a, b) -> b - a).findFirst().orElseThrow();
    }

    private void doStep(List<SimplePos> coordinates, Map<SimplePos, Integer> fullArea, int radius) {
        for (int i = 0; i < coordinates.size(); i++) {
            addRadius(i, coordinates.get(i), radius, fullArea);
        }

        for (Entry<SimplePos, Integer> entry : fullArea.entrySet()) {
            if (entry.getValue() >= 100) {
                entry.setValue(entry.getValue() - 100);
            }
        }
    }

    private int doPuzzle2(List<SimplePos> coordinates) {
        List<SimplePos> coordinatesSortedX = coordinates.stream().sorted((a, b) -> a.getX() - b.getX()).collect(Collectors.toList());

        int minX = coordinatesSortedX.get(0).getX();
        int maxX = coordinatesSortedX.get(coordinates.size() - 1).getX();

        int minY = coordinatesSortedX.get(0).getY();
        int maxY = coordinatesSortedX.get(coordinates.size() - 1).getY();

        int response = 0;
        for (int x = minX - 1000; x <= maxX + 1000; x++) {
            for (int y = minY - 1000; y <= maxY + 1000; y++) {
                if (getDistanceToAllCoordinates(new SimplePos(x, y), coordinates) < maxDistance) {
                    response++;
                }
            }
        }

        return response;
    }

    private long getDistanceToAllCoordinates(SimplePos pos, List<SimplePos> coordinates) {
        AtomicLong response = new AtomicLong(0L);

        coordinates.stream().forEach(v -> response.addAndGet((long) Math.abs(pos.getX() - v.getX()) + Math.abs(pos.getY() - v.getY())));

        return response.longValue();
    }

    private SimplePos fromString(String data) {
        String[] elements = data.split(",");
        return new SimplePos(//
                Integer.parseInt(elements[0].trim()), //
                Integer.parseInt(elements[1].trim())//
        );
    }

    private void addRadius(int coordinateNumber, SimplePos center, int radius, Map<SimplePos, Integer> fullArea) {
        for (int i = 0; i < radius; i++) {
            SimplePos currentPosition1 = new SimplePos(radius - i + center.getX(), -i + center.getY());
            SimplePos currentPosition2 = new SimplePos(-i + center.getX(), i - radius + center.getY());
            SimplePos currentPosition3 = new SimplePos(-radius + i + center.getX(), i + center.getY());
            SimplePos currentPosition4 = new SimplePos(i + center.getX(), radius - i + center.getY());
            checkAndAddPosition(coordinateNumber, fullArea, currentPosition1);
            checkAndAddPosition(coordinateNumber, fullArea, currentPosition2);
            checkAndAddPosition(coordinateNumber, fullArea, currentPosition3);
            checkAndAddPosition(coordinateNumber, fullArea, currentPosition4);
        }
    }

    private void checkAndAddPosition(int coordinateNumber, Map<SimplePos, Integer> fullArea, SimplePos currentPosition) {
        Integer value = fullArea.getOrDefault(currentPosition, -10);
        if (value == -10) {
            fullArea.put(currentPosition, coordinateNumber + 100);
        } else if (value >= 100 && value != coordinateNumber + 100) {
            fullArea.put(currentPosition, -1);
        }
    }

}
