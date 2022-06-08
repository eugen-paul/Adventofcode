package net.eugenpaul.adventofcode.y2017.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day1Test {

    @Test
    void testTest2017Day1_puzzle1() {
        testPuzzle1("1122", 3);
        testPuzzle1("1111", 4);
        testPuzzle1("1234", 0);
        testPuzzle1("91212129", 9);
    }

    private void testPuzzle1(String inputData, int sum) {
        Day1 event = new Day1();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(sum, event.getSum());
    }

    @Test
    void testTest2016Day1_puzzle2() {
        testPuzzle2("1212", 6);
        testPuzzle2("1221", 0);
        testPuzzle2("123425", 4);
        testPuzzle2("123123", 12);
        testPuzzle2("12131415", 4);
    }

    private void testPuzzle2(String inputData, int sum2) {
        Day1 event = new Day1();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(sum2, event.getSum2());
    }

    @Test
    void testSolution2017Day1() {
        Day1 event = new Day1();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2017/day1/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1343, event.getSum());
        assertEquals(1274, event.getSum2());
    }

}
