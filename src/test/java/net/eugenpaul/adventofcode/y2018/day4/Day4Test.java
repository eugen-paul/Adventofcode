package net.eugenpaul.adventofcode.y2018.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day4Test {

    @Test
    void testTest2018Day4_puzzle1() {
        testPuzzle1(List.of(//
                "[1518-11-01 00:00] Guard #10 begins shift", //
                "[1518-11-01 00:05] falls asleep", //
                "[1518-11-01 00:25] wakes up", //
                "[1518-11-01 00:30] falls asleep", //
                "[1518-11-01 00:55] wakes up", //
                "[1518-11-01 23:58] Guard #99 begins shift", //
                "[1518-11-02 00:40] falls asleep", //
                "[1518-11-02 00:50] wakes up", //
                "[1518-11-03 00:05] Guard #10 begins shift", //
                "[1518-11-03 00:24] falls asleep", //
                "[1518-11-03 00:29] wakes up", //
                "[1518-11-04 00:02] Guard #99 begins shift", //
                "[1518-11-04 00:36] falls asleep", //
                "[1518-11-04 00:46] wakes up", //
                "[1518-11-05 00:03] Guard #99 begins shift", //
                "[1518-11-05 00:45] falls asleep", //
                "[1518-11-05 00:55] wakes up" //
        ), 240, 4455);
    }

    private void testPuzzle1(List<String> inputData, long idMultipliedMinute, long idMultipliedMinute2) {
        Day4 event = new Day4();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(idMultipliedMinute, event.getIdMultipliedMinute());
        assertEquals(idMultipliedMinute2, event.getIdMultipliedMinute2());
    }

    @Test
    void testSolution2018Day4() {
        Day4 event = new Day4();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day4/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(85296, event.getIdMultipliedMinute());
        assertEquals(58559, event.getIdMultipliedMinute2());
    }

}
