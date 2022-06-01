package net.eugenpaul.adventofcode.y2019.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day10Test {

    @Test
    void testTest2019Day10_testPuzzle1() {
        testPuzzle1(List.of(//
                ".#..#", //
                ".....", //
                "#####", //
                "....#", //
                "...##" //
        ), 8);

        testPuzzle1(List.of(//
                "......#.#.", //
                "#..#.#....", //
                "..#######.", //
                ".#.#.###..", //
                ".#..#.....", //
                "..#....#.#", //
                "#..#....#.", //
                ".##.#..###", //
                "##...#..#.", //
                ".#....####" //
        ), 33);

        testPuzzle1(List.of(//
                "#.#...#.#.", //
                ".###....#.", //
                ".#....#...", //
                "##.#.#.#.#", //
                "....#.#.#.", //
                ".##..###.#", //
                "..#...##..", //
                "..##....##", //
                "......#...", //
                ".####.###." //
        ), 35);

        testPuzzle1(List.of(//
                ".#..#..###", //
                "####.###.#", //
                "....###.#.", //
                "..###.##.#", //
                "##.##.#.#.", //
                "....###..#", //
                "..#.#..#.#", //
                "#..#.#.###", //
                ".##...##.#", //
                ".....#.#.." //
        ), 41);

        testPuzzle1(List.of(//
                ".#..##.###...#######", //
                "##.############..##.", //
                ".#.######.########.#", //
                ".###.#######.####.#.", //
                "#####.##.#.##.###.##", //
                "..#####..#.#########", //
                "####################", //
                "#.####....###.#.#.##", //
                "##.#################", //
                "#####.##.###..####..", //
                "..######..##.#######", //
                "####.##.####...##..#", //
                ".#####..#.######.###", //
                "##...#.##########...", //
                "#.##########.#######", //
                ".####.#.###.###.#.##", //
                "....##.##.###..#####", //
                ".#.#.###########.###", //
                "#.#.#.#####.####.###", //
                "###.##.####.##.#..##" //
        ), 210);
    }

    private void testPuzzle1(List<String> inputData, int detected) {
        Day10 event = new Day10();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(detected, event.getDetected());
    }

    @Test
    void testSolution2019Day10() {
        Day10 event = new Day10();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day10/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(267, event.getDetected());
        assertEquals("1309", event.getA200());
    }

}
