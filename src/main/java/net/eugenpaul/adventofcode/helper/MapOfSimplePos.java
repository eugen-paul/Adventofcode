package net.eugenpaul.adventofcode.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class MapOfSimplePos {
    private static Logger logger = Logger.getLogger(MapOfSimplePos.class.getName());

    private MapOfSimplePos() {
    }

    public static void printList(List<String> data) {
        for (String line : data) {
            logger.log(Level.INFO, line::toString);
        }
    }

    public static List<String> mapToPrintList(Map<SimplePos, Boolean> map) {
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;

        for (var entry : map.entrySet()) {
            xMin = Math.min(xMin, entry.getKey().getX());
            xMax = Math.max(xMax, entry.getKey().getX());
            yMin = Math.min(yMin, entry.getKey().getY());
            yMax = Math.max(yMax, entry.getKey().getY());
        }

        List<String> response = new LinkedList<>();

        for (int y = yMin; y <= yMax; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = xMin; x <= xMax; x++) {
                var value = map.getOrDefault(new SimplePos(x, y), false);
                line.append(value.booleanValue() ? '#' : '.');
            }
            response.add(line.toString());
        }

        return response;
    }

    public static <T> List<String> mapToPrintList(Map<SimplePos, T> map, Function<T, Character> tToChar) {
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;

        for (var entry : map.entrySet()) {
            xMin = Math.min(xMin, entry.getKey().getX());
            xMax = Math.max(xMax, entry.getKey().getX());
            yMin = Math.min(yMin, entry.getKey().getY());
            yMax = Math.max(yMax, entry.getKey().getY());
        }

        List<String> response = new LinkedList<>();

        for (int y = yMin; y <= yMax; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = xMin; x <= xMax; x++) {
                var value = tToChar.apply(map.get(new SimplePos(x, y)));
                line.append(value);
            }
            response.add(line.toString());
        }

        return response;
    }

    public static <T> List<String> mapToPrintList(Map<SimplePos, T> map, Function<T, Character> tToChar, SimplePos minPos, SimplePos maxPos) {
        int xMin = minPos.getX();
        int xMax = maxPos.getX();
        int yMin = minPos.getY();
        int yMax = maxPos.getY();

        List<String> response = new LinkedList<>();

        for (int y = yMin; y <= yMax; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = xMin; x <= xMax; x++) {
                var value = tToChar.apply(map.get(new SimplePos(x, y)));
                line.append(value);
            }
            response.add(line.toString());
        }

        return response;
    }
}