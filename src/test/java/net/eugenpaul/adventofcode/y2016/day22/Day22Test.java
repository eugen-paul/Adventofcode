package net.eugenpaul.adventofcode.y2016.day22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day22Test {

    @Test
    void testTest2016Day22_puzzle1() {
        testPuzzle1(//
                List.of(//
                        "root@ebhq-gridcenter# df -h", //
                        "Filesystem              Size  Used  Avail  Use%", //
                        "/dev/grid/node-x0-y0     85T    1T    85T    0%", //
                        "/dev/grid/node-x0-y1     89T   10T    79T   xx%", //
                        "/dev/grid/node-x0-y2    200T  150T    50T   xx%" //
                ), //
                4 //
        );

        testPuzzle1(//
                List.of(//
                        "Filesystem            Size  Used  Avail  Use%", //
                        "/dev/grid/node-x0-y0   10T    8T     2T   80%", //
                        "/dev/grid/node-x0-y1   11T    6T     5T   54%", //
                        "/dev/grid/node-x0-y2   32T   28T     4T   87%", //
                        "/dev/grid/node-x1-y0    9T    7T     2T   77%", //
                        "/dev/grid/node-x1-y1    8T    0T     8T    0%", //
                        "/dev/grid/node-x1-y2   11T    7T     4T   63%", //
                        "/dev/grid/node-x2-y0   10T    6T     4T   60%", //
                        "/dev/grid/node-x2-y1    9T    8T     1T   88%", //
                        "/dev/grid/node-x2-y2    9T    6T     3T   66%" //
                ), //
                7 //
        );
    }

    private void testPuzzle1(List<String> inputData, int viablePairs) {
        Day22 event = new Day22();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(viablePairs, event.getViablePairs());
    }

    @Test
    void testSolution2016Day22() {
        Day22 event = new Day22();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day22/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(967, event.getViablePairs());
    }

}
