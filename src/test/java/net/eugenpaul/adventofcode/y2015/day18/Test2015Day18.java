package net.eugenpaul.adventofcode.y2015.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

/**
 * Test2015Day18
 */
class Test2015Day18 {

    @Test
    void testTest2015Day18() {
        testPuzzle(//
                List.of(".#.#.#", //
                        "...##.", //
                        "#....#", //
                        "..#...", //
                        "#.#..#", //
                        "####.."//
                ), //
                4, //
                4, //
                14//
        );

        System.out.println("All tests OK.");
    }

    private void testPuzzle(List<String> inputData, int steps, int lightsOn, int brockenLightsOn) {
        Day18 event = new Day18();
        event.setSteps(steps);

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(lightsOn, event.getLightsOn());
        assertEquals(brockenLightsOn, event.getLightsBrokenOn());
    }

    @Test
    void testSolution2015Day18() {
        Day18 event = new Day18();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2015/day18/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(768, event.getLightsOn());
        assertEquals(781, event.getLightsBrokenOn());
    }

}