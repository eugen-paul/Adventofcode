package net.eugenpaul.adventofcode.y2021.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day2Test {

    @Test
    void testTest2021Day2() {
        testPuzzle(List.of(//
                "forward 5", //
                "down 5", //
                "forward 8", //
                "up 3", //
                "down 8", //
                "forward 2" //
        ), 150, 900);
    }

    private void testPuzzle(List<String> inputData, int multiply, int multiply2) {
        Day2 event = new Day2();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(multiply, event.getMultiply());
        assertEquals(multiply2, event.getMultiply2());
    }

    @Test
    void testSolution2021Day2() {
        Day2 event = new Day2();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day2/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1524750, event.getMultiply());
        assertEquals(1592426537, event.getMultiply2());
    }

}
