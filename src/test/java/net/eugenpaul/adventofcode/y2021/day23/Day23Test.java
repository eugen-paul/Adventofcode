package net.eugenpaul.adventofcode.y2021.day23;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day23Test {

    @Test
    void testTest2021Day23() {
        testPuzzle(List.of(//
                "#############", //
                "#...........#", //
                "###B#C#B#D###", //
                "  #A#D#C#A#  ", //
                "  #########  " //
        ), 12521L, 44169L);
    }

    private void testPuzzle(List<String> inputData, long totalEnergy, long totalEnergy2) {
        Day23 event = new Day23();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(totalEnergy, event.getTotalEnergy());
        assertEquals(totalEnergy2, event.getTotalEnergy2());
    }

    @Test
    void testSolution2021Day23() {
        Day23 event = new Day23();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day23/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(14627L, event.getTotalEnergy());
        assertEquals(41591L, event.getTotalEnergy2());
    }

}
