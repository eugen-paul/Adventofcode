package net.eugenpaul.adventofcode.y2019.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day24Test {

    @Test
    void testTest2019Day22_testPuzzle() {
        testShuffle(List.of(//
                "....#", //
                "#..#.", //
                "#..##", //
                "..#..", //
                "#...." //
        ), 2129920L, 99);
    }

    private void testShuffle(List<String> inputData, long rating, long bugsCount) {
        Day24 event = new Day24();

        event.setMinStep2(10);
        assertTrue(event.doEvent(inputData));
        assertEquals(rating, event.getRating());
        assertEquals(bugsCount, event.getBugsCount());
    }

    @Test
    void testSolution2019Day24() {
        Day24 event = new Day24();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day24/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(32523825, event.getRating());
        assertEquals(2052, event.getBugsCount());
    }

}
