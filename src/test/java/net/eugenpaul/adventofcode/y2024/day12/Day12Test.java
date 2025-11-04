package net.eugenpaul.adventofcode.y2024.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day12Test {

    @Test
    void testTest2024Day12_1() {
        Day12 event = new Day12();
        assertEquals(140, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day12/test_puzzle1A.txt")));
        assertEquals(772, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day12/test_puzzle1B.txt")));
        assertEquals(1930, event.doPuzzle1(FileReaderHelper.readListStringFromFile("y2024/day12/test_puzzle1C.txt")));
    }

    @Test
    void testTest2024Day12_2() {
        Day12 event = new Day12();
        assertEquals(80, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day12/test_puzzle2A.txt")));
        assertEquals(436, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day12/test_puzzle2B.txt")));
        assertEquals(236, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day12/test_puzzle2C.txt")));
        assertEquals(368, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day12/test_puzzle2D.txt")));
        assertEquals(1206, event.doPuzzle2(FileReaderHelper.readListStringFromFile("y2024/day12/test_puzzle2E.txt")));
    }

    @Test
    void testSolution2024Day12() {
        Day12 event = new Day12();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2024/Day12/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));

        assertEquals(1375574, event.getTotalScore());
        assertEquals(830566, event.getTotalScore2());
    }
}
