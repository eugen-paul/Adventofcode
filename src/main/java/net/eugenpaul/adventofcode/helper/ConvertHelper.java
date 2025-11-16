package net.eugenpaul.adventofcode.helper;

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

    public static Double toDouble(String d) {
        return Double.parseDouble(d);
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
}
