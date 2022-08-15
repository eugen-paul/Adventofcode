package net.eugenpaul.adventofcode.y2021.day6;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day6 extends SolutionTemplate {

    @Getter
    private long fish80;
    @Getter
    private long fish256;

    public static void main(String[] args) {
        Day6 puzzle = new Day6();
        puzzle.doPuzzleFromFile("y2021/day6/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        Map<Integer, Integer> fishsCount = StringConverter.toIntegerLinkedList(eventData)//
                .stream()//
                .collect(Collectors.toMap(v -> v, v -> 1, (v1, v2) -> v1 + v2));

        fish80 = 0;
        for (var entry : fishsCount.entrySet()) {
            fish80 += getFishsAfterDays(80, entry.getKey(), new HashMap<>()) * entry.getValue() + entry.getValue();
        }
        fish256 = 0;
        for (var entry : fishsCount.entrySet()) {
            fish256 += getFishsAfterDays(256, entry.getKey(), new HashMap<>()) * entry.getValue() + entry.getValue();
        }

        logger.log(Level.INFO, () -> "fish80  : " + getFish80());
        logger.log(Level.INFO, () -> "fish256  : " + getFish256());

        return true;
    }

    private long getFishsAfterDays(int days, int delay, Map<Integer, Long> storage) {
        int endDays = days - delay - 1;

        if (storage.containsKey(endDays)) {
            return storage.get(endDays);
        }

        if (endDays < 0) {
            return 0;
        }

        int self = endDays / 7 + 1;

        long childs = 0;

        for (int i = 0; i < self; i++) {
            childs += getFishsAfterDays(endDays - 2 - 7 * i, 6, storage);
        }

        storage.put(endDays, self + childs);

        return self + childs;
    }
}
