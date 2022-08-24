package net.eugenpaul.adventofcode.y2021.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day15Test {

    @Test
    void testTest2021Day15() {
        testPuzzle(List.of(//
                "1163751742", //
                "1381373672", //
                "2136511328", //
                "3694931569", //
                "7463417111", //
                "1319128137", //
                "1359912421", //
                "3125421639", //
                "1293138521", //
                "2311944581" //
        ), 40, 2_188_189_693_529L);
    }

    private void testPuzzle(List<String> inputData, long lowestRisk, long sub2) {
        Day15 event = new Day15();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(lowestRisk, event.getLowestRisk());
    }

    @Test
    void testSolution2021Day15() {
        Day15 event = new Day15();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day15/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(687, event.getLowestRisk());
        assertEquals(2957, event.getLowestRisk2());
    }

}
