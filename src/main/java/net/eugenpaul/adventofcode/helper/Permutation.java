package net.eugenpaul.adventofcode.helper;

import java.util.function.Consumer;

public final class Permutation {

    private Permutation() {

    }

    /**
     * Permutation with Heap's algorithm.
     * 
     * @param n        number of elements.
     * @param elements input array (will be modified).
     * @param callback consumer that will be executed by each output
     */
    public static <T> void permutationsRecursive(int n, T[] elements, Consumer<T[]> callback) {
        if (n == 1) {
            callback.accept(elements);
        } else {
            for (int i = 0; i < n - 1; i++) {
                permutationsRecursive(n - 1, elements, callback);
                if (n % 2 == 0) {
                    swap(elements, i, n - 1);
                } else {
                    swap(elements, 0, n - 1);
                }
            }
            permutationsRecursive(n - 1, elements, callback);
        }
    }

    public static void permutationsRecursive(int n, char[] elements, Consumer<char[]> callback) {
        if (n == 1) {
            callback.accept(elements);
        } else {
            for (int i = 0; i < n - 1; i++) {
                permutationsRecursive(n - 1, elements, callback);
                if (n % 2 == 0) {
                    swap(elements, i, n - 1);
                } else {
                    swap(elements, 0, n - 1);
                }
            }
            permutationsRecursive(n - 1, elements, callback);
        }
    }

    /**
     * Swap two element in array
     * 
     * @param <T>
     * @param input
     * @param a
     * @param b
     */
    public static <T> void swap(T[] input, int a, int b) {
        T tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    /**
     * Swap two element in array
     * 
     * @param input
     * @param a
     * @param b
     */
    public static void swap(int[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    /**
     * Swap two element in array
     * 
     * @param input
     * @param a
     * @param b
     */
    public static void swap(long[] input, int a, int b) {
        long tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    /**
     * Swap two element in array
     * 
     * @param input
     * @param a
     * @param b
     */
    public static void swap(char[] input, int a, int b) {
        char tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }
}
