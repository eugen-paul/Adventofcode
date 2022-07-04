package net.eugenpaul.adventofcode.y2020.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day3Test {

    @Test
    void testTest2020Day3_testPuzzle() {
        testPuzzle(List.of(//
                "..##.......", //
                "#...#...#..", //
                ".#....#..#.", //
                "..#.#...#.#", //
                ".#...##..#.", //
                "..#.##.....", //
                ".#.#.#....#", //
                ".#........#", //
                "#.##...#...", //
                "#...##....#", //
                ".#..#...#.#" //
        ), 7, 336);
    }

    private void testPuzzle(List<String> inputData, int trees, int treesMult) {
        Day3 event = new Day3();

        assertTrue(event.doEvent(inputData));
        assertEquals(trees, event.getTrees());
        assertEquals(treesMult, event.getTreesMult());
    }

    @Test
    void testSolution2020Day3() {
        Day3 event = new Day3();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2020/day3/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(171, event.getTrees());
        assertEquals(1206576000, event.getTreesMult());
    }

}
