package net.eugenpaul.adventofcode.helper;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    public static List<String> mapOfIntsToPrintList(Map<SimplePos, Integer> map) {
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
                var value = map.getOrDefault(new SimplePos(x, y), 10);
                line.append(value == 10 ? '.' : value);
            }
            response.add(line.toString());
        }

        return response;
    }

    public static List<String> mapToPrintList(Set<SimplePos> set) {
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;

        for (var entry : set) {
            xMin = Math.min(xMin, entry.getX());
            xMax = Math.max(xMax, entry.getX());
            yMin = Math.min(yMin, entry.getY());
            yMax = Math.max(yMax, entry.getY());
        }

        List<String> response = new LinkedList<>();

        for (int y = yMin; y <= yMax; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = xMin; x <= xMax; x++) {
                if (set.contains(new SimplePos(x, y))) {
                    line.append('#');
                } else {
                    line.append('.');
                }
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

    public static <T, E> List<String> mapToPrintList(Map<SimplePos, T> map, Function<T, Character> tToChar, Map<SimplePos, E> mapObj,
            Function<E, Character> eToChar) {
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
                SimplePos pos = new SimplePos(x, y);
                if (mapObj.get(pos) != null) {
                    var value = eToChar.apply(mapObj.get(pos));
                    line.append(value);
                } else {
                    var value = tToChar.apply(map.get(pos));
                    line.append(value);
                }
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

    public static <T> Map<SimplePos, T> initMap(List<String> eventData, Function<Character, T> toT) {
        Map<SimplePos, T> responseMap = new HashMap<>();

        int y = 0;
        for (String scan : eventData) {
            int x = 0;
            for (char c : scan.toCharArray()) {
                T value = toT.apply(c);
                if (value != null) {
                    responseMap.put(new SimplePos(x, y), toT.apply(c));
                }
                x++;
            }
            y++;
        }

        return responseMap;
    }

    public static Map<SimplePos, Integer> initMapOfDigits(List<String> eventData) {
        return initMap(eventData, v -> v - '0');
    }
}
