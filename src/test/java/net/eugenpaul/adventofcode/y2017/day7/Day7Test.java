package net.eugenpaul.adventofcode.y2017.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class Day7Test {

    @Test
    void testTest2017Day7_puzzle1() {
        testPuzzle1(List.of(//
                "pbga (66)", //
                "xhth (57)", //
                "ebii (61)", //
                "havc (66)", //
                "ktlj (57)", //
                "fwft (72) -> ktlj, cntj, xhth", //
                "qoyq (66)", //
                "padx (45) -> pbga, havc, qoyq", //
                "tknk (41) -> ugml, padx, fwft", //
                "jptl (61)", //
                "ugml (68) -> gyxo, ebii, jptl", //
                "gyxo (61)", //
                "cntj (57)"), //
                "tknk", //
                60 //
        );
    }

    private void testPuzzle1(List<String> inputData, String steps, int weight) {
        Day7 event = new Day7();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(steps, event.getRoot());
        assertEquals(weight, event.getWeight());
    }

}
