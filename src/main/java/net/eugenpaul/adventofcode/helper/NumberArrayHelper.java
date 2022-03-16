package net.eugenpaul.adventofcode.helper;

import java.util.List;

public final class NumberArrayHelper {
    private NumberArrayHelper() {

    }

    public static <T extends Number & Comparable<T>> int getPositionOfMaxElement(List<T> data) {
        int responsePosition = 0;
        int currentPosition = 0;
        T max = data.get(0);

        for (T number : data) {
            if (max.compareTo(number) < 0) {
                max = number;
                responsePosition = currentPosition;
            }
            currentPosition++;
        }

        return responsePosition;
    }

    public static <T extends Number & Comparable<T>> int getPositionOfMinElement(List<T> data) {
        int responsePosition = 0;
        int currentPosition = 0;
        T min = data.get(0);

        for (T number : data) {
            if (min.compareTo(number) > 0) {
                min = number;
                responsePosition = currentPosition;
            }
            currentPosition++;
        }

        return responsePosition;
    }
}
