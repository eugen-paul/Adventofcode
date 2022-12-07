package net.eugenpaul.adventofcode.y2022.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day7Test {

    @Test
    void testTest2022Day7() {
        testPuzzle(List.of(//
                "$ cd /", //
                "$ ls", //
                "dir a", //
                "14848514 b.txt", //
                "8504156 c.dat", //
                "dir d", //
                "$ cd a", //
                "$ ls", //
                "dir e", //
                "29116 f", //
                "2557 g", //
                "62596 h.lst", //
                "$ cd e", //
                "$ ls", //
                "584 i", //
                "$ cd ..", //
                "$ cd ..", //
                "$ cd d", //
                "$ ls", //
                "4060174 j", //
                "8033020 d.log", //
                "5626152 d.ext", //
                "7214296 k" //
        ), 95437, 24933642);
    }

    private void testPuzzle(List<String> inputData, int firstMarker, int firstMessage) {
        Day7 event = new Day7();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(firstMarker, event.getTotalSizesOfMost100k());
        assertEquals(firstMessage, event.getSizeOfDeleteDir());
    }

    @Test
    void testSolution2022Day7() {
        Day7 event = new Day7();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2022/day7/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(1555642, event.getTotalSizesOfMost100k());
        assertEquals(5974547, event.getSizeOfDeleteDir());
    }

}
