package net.eugenpaul.adventofcode.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConvertHelper {

    public static long toLong(char d) {
        return (d - '0');
    }

    public static long toLong(String d) {
        return Long.parseLong(d);
    }

    public static int toInt(String d) {
        return Integer.parseInt(d);
    }

    public static int toInt(char d) {
        return d - '0';
    }

    public static List<List<String>> asListList(List<String> eventData) {
        List<List<String>> response = new LinkedList<>();
        List<String> subList = new LinkedList<>();
        for (var line : eventData) {
            if (line.isBlank()) {
                if (subList.isEmpty()) {
                    continue;
                }
                response.add(subList);
                subList = new LinkedList<>();
            } else {
                subList.add(line);
            }
        }
        if (!subList.isEmpty()) {
            response.add(subList);
        }
        return response;
    }

    public static <T> T[][] turnRight(T[][] in) {
        if (in == null) {
            return null;
        }
        int rows = in.length;
        if (rows == 0) {
            @SuppressWarnings("unchecked")
            T[][] empty = (T[][]) java.lang.reflect.Array.newInstance(in.getClass().getComponentType().getComponentType(), 0, 0);
            return empty;
        }
        int cols = in[0].length;
        @SuppressWarnings("unchecked")
        T[][] out = (T[][]) java.lang.reflect.Array.newInstance(in.getClass().getComponentType().getComponentType(), cols, rows);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                out[c][rows - 1 - r] = in[r][c];
            }
        }
        return out;
    }

    public static List<String> turnRightStrings(List<String> in) {
        if (in == null) {
            return null;
        }
        if (in.isEmpty()) {
            return Collections.emptyList();
        }
        int rows = in.size();
        int cols = in.get(0).length();
        List<StringBuilder> outTmp = new ArrayList<>(rows);
        for (int c = 0; c < cols; c++) {
            outTmp.add(new StringBuilder());
        }
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                outTmp.get(c).append(in.get(r).charAt(c));
            }
        }
        List<String> out = new ArrayList<>();
        for (var r : outTmp) {
            out.add(r.reverse().toString());
        }
        return out;
    }

    public static <T> List<List<T>> turnRight(List<List<T>> in) {
        if (in == null) {
            return null;
        }

        if (in.isEmpty()) {
            return Collections.emptyList();
        }

        int rows = in.size();
        int cols = in.get(0).size();
        List<List<T>> outTmp = new ArrayList<>(rows);
        for (int c = 0; c < cols; c++) {
            outTmp.add(new LinkedList<>());
        }
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                outTmp.get(c).addFirst(in.get(r).get(c));
            }
        }
        List<List<T>> out = new ArrayList<>();
        for (var r : outTmp) {
            out.add(r.stream().toList());
        }
        return out;
    }
}
