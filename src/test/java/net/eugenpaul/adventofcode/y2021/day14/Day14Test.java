package net.eugenpaul.adventofcode.y2021.day14;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day14Test {

    @Test
    void testTest2021Day14() {
        testPuzzle(List.of(//
                "NNCB", //
                "", //
                "CH -> B", //
                "HH -> N", //
                "CB -> H", //
                "NH -> C", //
                "HB -> C", //
                "HC -> B", //
                "HN -> C", //
                "NN -> C", //
                "BH -> H", //
                "NC -> B", //
                "NB -> B", //
                "BN -> B", //
                "BB -> N", //
                "BC -> B", //
                "CC -> N", //
                "CN -> C" //
        ), 1588, 2_188_189_693_529L);
    }

    private void testPuzzle(List<String> inputData, long sub, long sub2) {
        Day14 event = new Day14();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(sub, event.getSub());
        assertEquals(sub2, event.getSub2());
    }

    @Test
    void testSolution2021Day14() {
        Day14 event = new Day14();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day14/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(2797, event.getSub());
        assertEquals(2926813379532L, event.getSub2());
    }

}
