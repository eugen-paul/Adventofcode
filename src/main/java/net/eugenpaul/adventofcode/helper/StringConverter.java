package net.eugenpaul.adventofcode.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class StringConverter {
    private StringConverter() {

    }

    /**
     * Convert Numbers in String to ArrayList: "1 2 3" => new ArrayList(List.of(1,2,3))
     * 
     * @param data
     * @return
     */
    public static List<Long> toNumberArrayList(String data) {
        return toNumberList(data, ArrayList::new, false);
    }

    /**
     * Convert Numbers in String to LinkedList: "1 2 3" => new LinkedList(List.of(1,2,3))
     * 
     * @param data
     * @return
     */
    public static List<Long> toNumberLinkedList(String data) {
        return toNumberList(data, LinkedList::new, false);
    }

    /**
     * Convert Numbers in String to ArrayList and sort it: "2 1 3" => new ArrayList(List.of(1,2,3))
     * 
     * @param data
     * @return
     */
    public static List<Long> toNumberArrayListSorted(String data) {
        return toNumberList(data, ArrayList::new, true);
    }

    /**
     * Convert Numbers in String to LinkedList and sort it: "2 1 3" => new LinkedList(List.of(1,2,3))
     * 
     * @param data
     * @return
     */
    public static List<Long> toNumberLinkedListSorted(String data) {
        return toNumberList(data, LinkedList::new, true);
    }

    public static List<Long> toNumberList(String data, Supplier<Collection<Long>> supplier, boolean sort) {
        var response = (List<Long>) Stream.of(data.split("[\t ]"))//
                .mapToLong(Long::parseLong)//
                .boxed()//
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
        return Stream.of(data.split("[\t ]"))//
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

}
