package net.eugenpaul.adventofcode.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public final class SubSet {

    /**
     * berechnet alles SubSets fuer list. Fuer jeden SubSet wird callback aufgerufen.
     * <p>
     * Beispiel:
     * <p>
     * list = Arrays.asList("1", "2", "3") callback wird aufgerufen mit:
     * <ul>
     * <li>[1]</li>
     * <li>[2]</li>
     * <li>[1, 2]</li>
     * <li>[3]</li>
     * <li>[1, 3]</li>
     * <li>[2, 3]</li>
     * <li>[1, 2, 3]</li>
     * </ul>
     * 
     * @param <T>
     * @param list
     * @param callback
     */
    public static <T> void subset(List<T> list, Consumer<List<T>> callback) {
        final int n = list.size();
        // 1 << n = 2^n mögliche Kombinationen
        for (int mask = 1; mask < (1 << n); mask++) {
            List<T> subset = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(list.get(i));
                }
            }
            callback.accept(subset);
        }
    }

    public static void main(String[] args) {
        subset(List.of(1, 2, 3), System.out::println);
    }
}
