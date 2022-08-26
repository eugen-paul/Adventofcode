package net.eugenpaul.adventofcode.y2021.day20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day20Test {

    @Test
    void testTest2021Day20() {
        testPuzzle(List.of(//
                "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##", //
                "#..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###", //
                ".######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#.", //
                ".#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#.....", //
                ".#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#..", //
                "...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.....", //
                "..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#", //
                "", //
                "#..#.", //
                "#....", //
                "##..#", //
                "..#..", //
                "..###" //
        ), 35, 3351);
    }

    private void testPuzzle(List<String> inputData, long lightPixels, long lightPixels2) {
        Day20 event = new Day20();

        assertTrue(event.doPuzzleFromData(inputData));
        assertEquals(lightPixels, event.getLightPixels());
        assertEquals(lightPixels2, event.getLightPixels2());
    }

    @Test
    void testSolution2021Day20() {
        Day20 event = new Day20();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day20/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(5419, event.getLightPixels());
        assertEquals(17325, event.getLightPixels2());
    }

}
