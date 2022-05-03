package net.eugenpaul.adventofcode.y2018.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day15Test {

    @Test
    void testTest2018Day15_testPuzzle1() {
        testPuzzle1(List.of(//
                "#######", //
                "#.G...#", //
                "#...EG#", //
                "#.#.#G#", //
                "#..G#E#", //
                "#.....#", //
                "#######" //
        ), 27730, 4988);
        testPuzzle1(List.of(//
                "#######", //
                "#E..EG#", //
                "#.#G.E#", //
                "#E.##E#", //
                "#G..#.#", //
                "#..E#.#", //
                "#######" //
        ), 39514, 31284);
        testPuzzle1(List.of(//
                "#######", //
                "#E.G#.#", //
                "#.#G..#", //
                "#G.#.G#", //
                "#G..#.#", //
                "#...E.#", //
                "#######" //
        ), 27755, 3478);
        testPuzzle1(List.of(//
                "#######", //
                "#.E...#", //
                "#.#..G#", //
                "#.###.#", //
                "#E#G#G#", //
                "#...#G#", //
                "#######" //
        ), 28944, 6474);
        testPuzzle1(List.of(//
                "#########", //
                "#G......#", //
                "#.E.#...#", //
                "#..##..G#", //
                "#...##..#", //
                "#...#...#", //
                "#.G...G.#", //
                "#.....G.#", //
                "#########" //
        ), 18740, 1140);

    }

    private void testPuzzle1(List<String> inputData, int outcome, int outcome2) {
        Day15 event = new Day15();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(outcome, event.getOutcome());
        assertEquals(outcome2, event.getOutcome2());
    }

    @Test
    void testSolution2018Day15() {
        Day15 event = new Day15();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day15/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(182376, event.getOutcome());
        assertEquals(57540, event.getOutcome2());
    }

}
