package net.eugenpaul.adventofcode.y2021.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day12Test {

    @Test
    void testTest2021Day12_a() {
        testPuzzle(List.of(//
                "start-A", //
                "start-b", //
                "A-c", //
                "A-b", //
                "b-d", //
                "A-end", //
                "b-end" //
        ), 10, 36);
    }

    @Test
    void testTest2021Day12_b() {
        testPuzzle(List.of(//
                "dc-end", //
                "HN-start", //
                "start-kj", //
                "dc-start", //
                "dc-HN", //
                "LN-dc", //
                "HN-end", //
                "kj-sa", //
                "kj-HN", //
                "kj-dc" //
        ), 19, 103);
    }

    @Test
    void testTest2021Day12_c() {
        testPuzzle(List.of(//
                "fs-end", //
                "he-DX", //
                "fs-he", //
                "start-DX", //
                "pj-DX", //
                "end-zg", //
                "zg-sl", //
                "zg-pj", //
                "pj-he", //
                "RW-he", //
                "fs-DX", //
                "pj-RW", //
                "zg-RW", //
                "start-pj", //
                "he-WI", //
                "zg-he", //
                "pj-fs", //
                "start-RW" //
        ), 226, 3509);
    }

    private void testPuzzle(List<String> inputData, long paths1, long paths2) {
        Day12 event = new Day12();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(paths1, event.getPaths1());
        assertEquals(paths2, event.getPaths2());
    }

    @Test
    void testSolution2021Day12() {
        Day12 event = new Day12();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day12/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(3856, event.getPaths1());
        assertEquals(116692, event.getPaths2());
    }

}
