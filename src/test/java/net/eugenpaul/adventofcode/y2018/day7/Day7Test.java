package net.eugenpaul.adventofcode.y2018.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day7Test {

    @Test
    void testTest2018Day7_puzzle1() {
        testPuzzle1(List.of(//
                "Step C must be finished before step A can begin.", //
                "Step C must be finished before step F can begin.", //
                "Step A must be finished before step B can begin.", //
                "Step A must be finished before step D can begin.", //
                "Step B must be finished before step E can begin.", //
                "Step D must be finished before step E can begin.", //
                "Step F must be finished before step E can begin." //
        ), "CABDFE", 15);

    }

    private void testPuzzle1(List<String> inputData, String correctOrder, int timeToComplete) {
        Day7 event = new Day7();

        event.setWorkerCount(2);
        event.setAddTimeProStep(0);

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(correctOrder, event.getCorrectOrder());
        assertEquals(timeToComplete, event.getTimeToComplete());
    }

    @Test
    void testSolution2018Day7() {
        Day7 event = new Day7();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2018/day7/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals("GJFMDHNBCIVTUWEQYALSPXZORK", event.getCorrectOrder());
        assertEquals(1050, event.getTimeToComplete());
    }

}
