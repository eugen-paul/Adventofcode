package net.eugenpaul.adventofcode.y2022.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day6Test {

    @Test
    void testTest2022Day6() {
        testPuzzle("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 7, 19);
        testPuzzle("bvwbjplbgvbhsrlpgdmjqwftvncz", 5, 23);
        testPuzzle("nppdvjthqldpwncqszvftbrmjlhg", 6, 23);
        testPuzzle("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10, 29);
        testPuzzle("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11, 26);
    }

    private void testPuzzle(String inputData, int firstMarker, int firstMessage) {
        Day6 event = new Day6();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(firstMarker, event.getFirstMarker());
        assertEquals(firstMessage, event.getFirstMessage());
    }

    @Test
    void testSolution2022Day6() {
        Day6 event = new Day6();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day6/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1544, event.getFirstMarker());
        assertEquals(2145, event.getFirstMessage());
    }

}
