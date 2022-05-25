package net.eugenpaul.adventofcode.y2016.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Test2016Day12 {

    @Test
    void test2016Day12() {
        Day12 puzzle = new Day12();

        List.of(//
                puzzle.doPuzzleFromData(//
                        List.of(//
                                "cpy 41 a", //
                                "inc a", //
                                "inc a", //
                                "dec a", //
                                "jnz a 2", //
                                "dec a"//
                        )//
                )//
        );

        assertEquals(42, puzzle.getA());
    }

    @Test
    void testSolution2016Day12() {
        Day12 event = new Day12();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2016/day12/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(318007, event.getA());
        assertEquals(9227661, event.getA2());
    }

}