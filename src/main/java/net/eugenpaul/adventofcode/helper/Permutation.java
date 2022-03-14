package net.eugenpaul.adventofcode.helper;

import java.util.function.Consumer;

public class Permutation {

    private Permutation() {

    }

    /**
     * Permutation with Heap's algorithm.
     * 
     * @param n        number of elements.
     * @param elements input array (will be modified).
     * @param callback consumer that will be executed by each output
     */
    public <T> void permutationsRecursive(int n, T[] elements, Consumer<T[]> callback) {
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

    private <T> void swap(T[] input, int a, int b) {
        T tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }
}
