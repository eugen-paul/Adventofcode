package net.eugenpaul.adventofcode.y2019.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day3Test {

    @Test
    void testTest2019Day3_testPuzzle() {
        testPuzzle(List.of("R8,U5,L5,D3", "U7,R6,D4,L4"), 6, 30);
        testPuzzle(List.of("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83"), 159, 610);
        testPuzzle(List.of("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"), 135, 410);
    }

    private void testPuzzle(List<String> inputData, int distance, int mitStepsToIntersection) {
        Day3 event = new Day3();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(distance, event.getDistance());
        assertEquals(mitStepsToIntersection, event.getMitStepsToIntersection());
    }

    @Test
    void testSolution2019Day3() {
        Day3 event = new Day3();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day3/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(4981, event.getDistance());
        assertEquals(164012, event.getMitStepsToIntersection());
    }

}
