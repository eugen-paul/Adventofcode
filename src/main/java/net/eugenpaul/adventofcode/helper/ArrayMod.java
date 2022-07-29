package net.eugenpaul.adventofcode.helper;

import java.lang.reflect.Array;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ArrayMod {

    public static <T> T[][] flipH(Class<T> clazz, T[][] data) {

        @SuppressWarnings("unchecked")
        T[][] response = (T[][]) Array.newInstance(clazz, data.length, data[0].length);

        for (int x = 0; x < data.length; x++) {
            for (int y = 0; y < data[0].length; y++) {
                response[x][y] = data[data.length - x - 1][y];
            }
        }

        return response;
    }

    public static <T> T[][] flipV(Class<T> clazz, T[][] data) {

        @SuppressWarnings("unchecked")
        T[][] response = (T[][]) Array.newInstance(clazz, data.length, data[0].length);

        for (int x = 0; x < data.length; x++) {
            for (int y = 0; y < data[0].length; y++) {
                response[x][y] = data[x][data[0].length - y - 1];
            }
        }

        return response;
    }

    public static <T> T[][] rotateR(Class<T> clazz, T[][] data) {

        @SuppressWarnings("unchecked")
        T[][] response = (T[][]) Array.newInstance(clazz, data[0].length, data.length);

        for (int x = 0; x < data[0].length; x++) {
            for (int y = 0; y < data.length; y++) {
                response[x][y] = data[y][data[0].length - x - 1];
            }
        }

        return response;
    }

    public static <T> T[][] rotateR(Class<T> clazz, T[][] data, int count) {
        T[][] response = data;

        for (int i = 0; i < count; i++) {
            response = rotateR(clazz, response);
        }

        return response;
    }
}
