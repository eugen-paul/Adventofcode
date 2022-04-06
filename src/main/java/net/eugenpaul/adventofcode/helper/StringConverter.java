package net.eugenpaul.adventofcode.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class StringConverter {

    private static Logger logger = Logger.getLogger(StringConverter.class.getName());
    private static final String NUMBER_SEPARATOR_REGEX = "[\t ,]";

    private StringConverter() {

    }

    /**
     * Convert Numbers in String to ArrayList: "1 2 3" => new ArrayList(List.of(1,2,3))
     * 
     * @param data
     * @return
     */
    public static List<Long> toLongArrayList(String data) {
        return toNumberList(data, Long::parseLong, ArrayList::new, false);
    }

    /**
     * Convert Numbers in String to ArrayList: "1 2 3" => new ArrayList(List.of(1,2,3))
     * 
     * @param data
     * @return
     */
    public static List<Integer> toIntegerArrayList(String data) {
        return toNumberList(data, Integer::parseInt, ArrayList::new, false);
    }

    /**
     * Convert Numbers in String to LinkedList: "1 2 3" => new LinkedList(List.of(1,2,3))
     * 
     * @param data
     * @return
     */
    public static List<Long> toLongLinkedList(String data) {
        return toNumberList(data, Long::parseLong, LinkedList::new, false);
    }

    /**
     * Convert Numbers in String to LinkedList: "1 2 3" => new LinkedList(List.of(1,2,3))
     * 
     * @param data
     * @return
     */
    public static List<Integer> toIntegerLinkedList(String data) {
        return toNumberList(data, Integer::parseInt, LinkedList::new, false);
    }

    /**
     * Convert Numbers in String to ArrayList and sort it: "2 1 3" => new ArrayList(List.of(1,2,3))
     * 
     * @param data
     * @return
     */
    public static List<Long> toLongArrayListSorted(String data) {
        return toNumberList(data, Long::parseLong, ArrayList::new, true);
    }

    /**
     * Convert Numbers in String to ArrayList and sort it: "2 1 3" => new ArrayList(List.of(1,2,3))
     * 
     * @param data
     * @return
     */
    public static List<Integer> toIntegerArrayListSorted(String data) {
        return toNumberList(data, Integer::parseInt, ArrayList::new, true);
    }

    /**
     * Convert Numbers in String to LinkedList and sort it: "2 1 3" => new LinkedList(List.of(1,2,3))
     * 
     * @param data
     * @return
     */
    public static List<Long> toLongLinkedListSorted(String data) {
        return toNumberList(data, Long::parseLong, LinkedList::new, true);
    }

    /**
     * Convert Numbers in String to LinkedList and sort it: "2 1 3" => new LinkedList(List.of(1,2,3))
     * 
     * @param data
     * @return
     */
    public static List<Integer> toIntegerLinkedListSorted(String data) {
        return toNumberList(data, Integer::parseInt, LinkedList::new, true);
    }

    public static <T extends Number & Comparable<T>> List<T> toNumberList(String data, Function<String, T> mapper, Supplier<Collection<T>> supplier,
            boolean sort) {
        var response = (List<T>) Stream.of(data.split(NUMBER_SEPARATOR_REGEX))//
                .map(mapper::apply)//
                .collect(Collectors.toCollection(supplier));

        if (sort) {
            Collections.sort(response);
        }
        return response;
    }

    /**
     * Convert Numbers in String to Array of Numbers: "2 1 3" => new long[]{2,1,3}
     * 
     * @param data
     * @return
     */
    public static long[] toLongArray(String data) {
        return Stream.of(data.split(NUMBER_SEPARATOR_REGEX))//
                .mapToLong(Long::parseLong).toArray();
    }

    /**
     * Convert digits in String to Array of Numbers: "213" => new long[]{2,1,3}
     * 
     * @param data
     * @return
     */
    public static long[] digitsToLongArray(String data) {
        return data.chars().mapToLong(v -> v - '0').toArray();
    }

    /**
     * Convert String to stream of chars
     * 
     * @param data
     * @return
     */
    public static Stream<Character> toCharStream(String data) {
        return data.chars().mapToObj(c -> (char) c);
    }

    public static Map<SimplePos, Boolean> toBoolMap(List<String> data, char trueChar) {
        Map<SimplePos, Boolean> response = new HashMap<>();
        for (int y = 0; y < data.size(); y++) {
            for (int x = 0; x < data.get(0).length(); x++) {
                var currentPos = new SimplePos(x, y);
                var value = data.get(y).charAt(x) == trueChar;
                response.put(currentPos, value);
            }
        }
        return response;
    }

    public static void printBoolMap(Map<SimplePos, Boolean> map) {
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

        for (int y = yMin; y <= yMax; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = xMin; x <= xMax; x++) {
                var value = map.getOrDefault(new SimplePos(x, y), false);
                line.append(value.booleanValue() ? '#' : '.');
            }
            logger.log(Level.INFO, line::toString);
        }
    }
}
