package net.eugenpaul.adventofcode.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MatrixHelper {

    /**
     * Flipt eine Matrix Horisontal:<br>
     * {{1,2,3}<br>
     * {4,5,6}}<br>
     * nach:<br>
     * {{4,5,6}}<br>
     * {1,2,3}}<br>
     * 
     * @param <T>
     * @param clazz
     * @param data
     * @return
     */
    public static <T> T[][] flipH(T[][] data) {

        @SuppressWarnings("unchecked")
        T[][] response = (T[][]) java.lang.reflect.Array.newInstance(data.getClass().getComponentType().getComponentType(), data.length, data[0].length);

        for (int x = 0; x < data.length; x++) {
            for (int y = 0; y < data[0].length; y++) {
                response[x][y] = data[data.length - x - 1][y];
            }
        }

        return response;
    }

    /**
     * Flipt eine Matrix Vertikal:<br>
     * {{1,2,3}<br>
     * {4,5,6}}<br>
     * nach:<br>
     * {{3,2,1}<br>
     * {6,5,4}}<br>
     * 
     * @param <T>
     * @param clazz
     * @param data
     * @return
     */
    public static <T> T[][] flipV(T[][] data) {

        @SuppressWarnings("unchecked")
        T[][] response = (T[][]) java.lang.reflect.Array.newInstance(data.getClass().getComponentType().getComponentType(), data.length, data[0].length);

        for (int x = 0; x < data.length; x++) {
            for (int y = 0; y < data[0].length; y++) {
                response[x][y] = data[x][data[0].length - y - 1];
            }
        }

        return response;
    }

    /**
     * Rotiert die Matrix in Uhrzeigerrichtung:<br>
     * { <br>
     * {1,2,3}, <br>
     * {4,5,6} <br>
     * } <br>
     * zu:<br>
     * { <br>
     * {4,1}, <br>
     * {5,2}, <br>
     * {6,3} <br>
     * } <br>
     * 
     * @param <T>
     * @param in
     * @return
     */
    public static <T> T[][] rotateR(T[][] in) {
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

    /**
     * Rotiert die Matrix in Uhrzeigerrichtung count-Mal:<br>
     * { <br>
     * {1,2,3}, <br>
     * {4,5,6} <br>
     * } <br>
     * zu:<br>
     * { <br>
     * {4,1}, <br>
     * {5,2}, <br>
     * {6,3} <br>
     * } <br>
     * 
     * @param <T>
     * @param in
     * @return
     */
    public static <T> T[][] rotateR(T[][] data, int count) {
        T[][] response = data;

        for (int i = 0; i < count; i++) {
            response = rotateR(response);
        }

        return response;
    }

    /**
     * Rotiert die Charakter in den Strings in Uhrzeigerrichtung:<br>
     * List.of( <br>
     * "123", <br>
     * "456" <br>
     * ); <br>
     * zu:<br>
     * List.of( <br>
     * "41", <br>
     * "52" <br>
     * "63" <br>
     * ); <br>
     * 
     * @param <T>
     * @param in
     * @return
     */
    public static List<String> rotateRStrings(List<String> in) {
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

    /**
     * Rotiert die Matrix (List of Lists) in Uhrzeigerrichtung:<br>
     * List<List<String>> in = List.of( <br>
     *     List.of("a", "b", "c"), <br>
     *     List.of("d", "e", "f") <br>
     * ); <br>
     * zu:<br>
     * List<List<String>> expected = List.of( <br>
     *     List.of("d", "a"), <br>
     *     List.of("e", "b"), <br>
     *     List.of("f", "c") <br>
     * ); <br>
     * 
     * @param <T>
     * @param in
     * @return
     */
    public static <T> List<List<T>> rotateR(List<List<T>> in) {
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
