package net.eugenpaul.adventofcode.y2018.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day12Test {

    @Test
    void testTest2018Day12_testPuzzle1() {
        testPuzzle1(List.of(//
                "initial state: #..#.#..##......###...###", //
                "", //
                "...## => #", //
                "..#.. => #", //
                ".#... => #", //
                ".#.#. => #", //
                ".#.## => #", //
                ".##.. => #", //
                ".#### => #", //
                "#.#.# => #", //
                "#.### => #", //
                "##.#. => #", //
                "##.## => #", //
                "###.. => #", //
                "###.# => #", //
                "####. => #" //
        ), 325);
    }

    private void testPuzzle1(List<String> inputData, int sumOfAllPots) {
        Day12 event = new Day12();
        event.setDoPuzzle2(false);

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(sumOfAllPots, event.getSumOfAllPots());
    }

    @Test
    void testSolution2018Day12() {
        Day12 event = new Day12();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day12/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(4200L, event.getSumOfAllPots());
        assertEquals(9699999999321L, event.getSumOfAllPots2());
    }

}
