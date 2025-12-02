package net.eugenpaul.adventofcode.y2025.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day2Test {

    @Test
    void testTest2025Day2_1() {
        Day2 event = new Day2();
        assertEquals(1227775554, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2025/day2/test_puzzle.txt")));
    }

    @Test
    void testTest2025Day2_2() {
        Day2 event = new Day2();
        assertEquals(4174379265L, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2025/day2/test_puzzle.txt")));
    }

    @Test
    void testSolution2025Day2() {
        Day2 event = new Day2();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2025/Day2/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(44487518055L, event.getTotalScore());
        assertEquals(53481866137L, event.getTotalScore2());
    }
}
