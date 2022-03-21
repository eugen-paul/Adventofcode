package net.eugenpaul.adventofcode.helper;

import java.util.Arrays;
import java.util.List;

public final class NumberArrayHelper {
    private NumberArrayHelper() {

    }

    /**
     * Get the Position of max-element in the List
     * 
     * @param <T>
     * @param data
     * @return response
     */
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

    /**
     * Get the Position of Min-element in the List
     * 
     * @param <T>
     * @param data
     * @return response
     */
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

    /**
     * Convert int-Array to String. Each element is formatted using format.
     * 
     * @param data
     * @param format
     * @return response
     */
    public static String numberArrayToString(int[] data, String format) {
        StringBuilder response = new StringBuilder();
        Arrays.stream(data)//
                .forEach(v -> response.append(String.format(format, v)));
        return response.toString();
    }

    /**
     * Convert Number-List to String. Each element is formatted using format.
     * 
     * @param data
     * @param format
     * @return response
     */
    public static String numberArrayToString(List<Number> data, String format) {
        StringBuilder response = new StringBuilder();
        data.forEach(v -> response.append(String.format(format, v.longValue())));
        return response.toString();
    }

    /**
     * Convert long-Array to String. Each element is formatted using format.
     * 
     * @param data
     * @param format
     * @return response
     */
    public static String numberArrayToString(long[] data, String format) {
        StringBuilder response = new StringBuilder();
        Arrays.stream(data)//
                .forEach(v -> response.append(String.format(format, v)));
        return response.toString();
    }
}
