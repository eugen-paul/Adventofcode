package net.eugenpaul.adventofcode.y2022.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day9Test {

    @Test
    void testTest2022Day10_part1() {
        testPuzzle_1(List.of(//
                "addx 15", //
                "addx -11", //
                "addx 6", //
                "addx -3", //
                "addx 5", //
                "addx -1", //
                "addx -8", //
                "addx 13", //
                "addx 4", //
                "noop", //
                "addx -1", //
                "addx 5", //
                "addx -1", //
                "addx 5", //
                "addx -1", //
                "addx 5", //
                "addx -1", //
                "addx 5", //
                "addx -1", //
                "addx -35", //
                "addx 1", //
                "addx 24", //
                "addx -19", //
                "addx 1", //
                "addx 16", //
                "addx -11", //
                "noop", //
                "noop", //
                "addx 21", //
                "addx -15", //
                "noop", //
                "noop", //
                "addx -3", //
                "addx 9", //
                "addx 1", //
                "addx -3", //
                "addx 8", //
                "addx 1", //
                "addx 5", //
                "noop", //
                "noop", //
                "noop", //
                "noop", //
                "noop", //
                "addx -36", //
                "noop", //
                "addx 1", //
                "addx 7", //
                "noop", //
                "noop", //
                "noop", //
                "addx 2", //
                "addx 6", //
                "noop", //
                "noop", //
                "noop", //
                "noop", //
                "noop", //
                "addx 1", //
                "noop", //
                "noop", //
                "addx 7", //
                "addx 1", //
                "noop", //
                "addx -13", //
                "addx 13", //
                "addx 7", //
                "noop", //
                "addx 1", //
                "addx -33", //
                "noop", //
                "noop", //
                "noop", //
                "addx 2", //
                "noop", //
                "noop", //
                "noop", //
                "addx 8", //
                "noop", //
                "addx -1", //
                "addx 2", //
                "addx 1", //
                "noop", //
                "addx 17", //
                "addx -9", //
                "addx 1", //
                "addx 1", //
                "addx -3", //
                "addx 11", //
                "noop", //
                "noop", //
                "addx 1", //
                "noop", //
                "addx 1", //
                "noop", //
                "noop", //
                "addx -13", //
                "addx -19", //
                "addx 1", //
                "addx 3", //
                "addx 26", //
                "addx -30", //
                "addx 12", //
                "addx -1", //
                "addx 3", //
                "addx 1", //
                "noop", //
                "noop", //
                "noop", //
                "addx -9", //
                "addx 18", //
                "addx 1", //
                "addx 2", //
                "noop", //
                "noop", //
                "addx 9", //
                "noop", //
                "noop", //
                "noop", //
                "addx -1", //
                "addx 2", //
                "addx -37", //
                "addx 1", //
                "addx 3", //
                "noop", //
                "addx 15", //
                "addx -21", //
                "addx 22", //
                "addx -6", //
                "addx 1", //
                "noop", //
                "addx 2", //
                "addx 1", //
                "noop", //
                "addx -10", //
                "noop", //
                "noop", //
                "addx 20", //
                "addx 1", //
                "addx 2", //
                "addx 2", //
                "addx -6", //
                "addx -11", //
                "noop", //
                "noop", //
                "noop" //
        ), 13140);
    }

    private void testPuzzle_1(List<String> inputData, int tailPositions) {
        Day10 event = new Day10();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(tailPositions, event.getSumSixSiglanStrengths());
    }

    @Test
    void testSolution2022Day10() {
        Day10 event = new Day10();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day10/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(13920, event.getSumSixSiglanStrengths());
        //Part 2: EGLHBLFJ
    }

}
