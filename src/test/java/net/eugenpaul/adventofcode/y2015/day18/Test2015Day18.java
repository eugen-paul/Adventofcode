package net.eugenpaul.adventofcode.y2015.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

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
                "4", //
                4,//
                14//
        );

        System.out.println("All tests OK.");
    }

    private void testPuzzle(List<String> inputData, String steps, int lightsOn, int brockenLightsOn) {
        Day18 event = new Day18();

        assertTrue(event.doPuzzleFromData(inputData, steps));
        assertEquals(lightsOn, event.getLightsOn());
        assertEquals(brockenLightsOn, event.getLightsBrokenOn());
    }

}