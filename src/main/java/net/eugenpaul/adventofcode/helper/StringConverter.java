package net.eugenpaul.adventofcode.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringConverter {
    private StringConverter() {

    }

    public static List<Long> toNumberArrayList(String data) {
        return toNumberList(data, ArrayList::new, false);
    }

    public static List<Long> toNumberLinkedList(String data) {
        return toNumberList(data, LinkedList::new, false);
    }

    public static List<Long> toNumberArrayListSorted(String data) {
        return toNumberList(data, ArrayList::new, true);
    }

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

    public static Long[] toLongArray(String data) {
        return toNumberArrayList(data).toArray(new Long[0]);
    }

    public static long[] digitsToLongArray(String data) {
        return data.chars().mapToLong(v -> v - '0').toArray();
    }

    public static Stream<Character> toCharStream(String data) {
        return data.chars().mapToObj(c -> (char) c);
    }

}
