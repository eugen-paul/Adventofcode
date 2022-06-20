package net.eugenpaul.adventofcode.y2019.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day18Test {

    @Test
    void testTest2019Day18_testPuzzle_1() {
        testPuzzle1(List.of(//
                "#########", //
                "#b.A.@.a#", //
                "#########" //
        ), 8);
    }

    @Test
    void testTest2019Day18_testPuzzle_2() {
        testPuzzle1(List.of(//
                "########################", //
                "#f.D.E.e.C.b.A.@.a.B.c.#", //
                "######################.#", //
                "#d.....................#", //
                "########################" //
        ), 86);
    }

    @Test
    void testTest2019Day18_testPuzzle_3() {
        testPuzzle1(List.of(//
                "########################", //
                "#...............b.C.D.f#", //
                "#.######################", //
                "#.....@.a.B.c.d.A.e.F.g#", //
                "########################" //
        ), 132);
    }

    @Test
    void testTest2019Day18_testPuzzle_4() {
        testPuzzle1(List.of(//
                "#################", //
                "#i.G..c...e..H.p#", //
                "########.########", //
                "#j.A..b...f..D.o#", //
                "########@########", //
                "#k.E..a...g..B.n#", //
                "########.########", //
                "#l.F..d...h..C.m#", //
                "#################" //
        ), 136);
    }

    @Test
    void testTest2019Day18_testPuzzle_5() {
        testPuzzle1(List.of(//
                "########################", //
                "#@..............ac.GI.b#", //
                "###d#e#f################", //
                "###A#B#C################", //
                "###g#h#i################", //
                "########################" //
        ), 81);
    }

    private void testPuzzle1(List<String> inputData, int minSteps) {
        Day18 event = new Day18();
        event.setDoStep2(false);
        assertTrue(event.doEvent(inputData));
        assertEquals(minSteps, event.getMinSteps());
    }

    @Test
    void testTest2019Day18_testPuzzle2_1() {
        testPuzzle2(List.of(//
                "#######", //
                "#a.#Cd#", //
                "##...##", //
                "##.@.##", //
                "##...##", //
                "#cB#Ab#", //
                "#######" //
        ), 8);
    }

    @Test
    void testTest2019Day18_testPuzzle2_2() {
        testPuzzle2(List.of(//
                "###############", //
                "#d.ABC.#.....a#", //
                "######...######", //
                "######.@.######", //
                "######...######", //
                "#b.....#.....c#", //
                "###############" //
        ), 24);
    }

    @Test
    void testTest2019Day18_testPuzzle2_3() {
        testPuzzle2(List.of(//
                "#############", //
                "#DcBa.#.GhKl#", //
                "#.###...#I###", //
                "#e#d#.@.#j#k#", //
                "###C#...###J#", //
                "#fEbA.#.FgHi#", //
                "#############" //
        ), 32);
    }

    @Test
    void testTest2019Day18_testPuzzle2_4() {
        testPuzzle2(List.of(//
                "#############", //
                "#g#f.D#..h#l#", //
                "#F###e#E###.#", //
                "#dCba...BcIJ#", //
                "#####.@.#####", //
                "#nK.L...G...#", //
                "#M###N#H###.#", //
                "#o#m..#i#jk.#", //
                "#############" //
        ), 72);
    }

    private void testPuzzle2(List<String> inputData, int minSteps) {
        Day18 event = new Day18();
        event.setDoStep1(false);
        assertTrue(event.doEvent(inputData));
        assertEquals(minSteps, event.getMinSteps2());
    }

    @Test
    void testSolution2019Day18() {
        Day18 event = new Day18();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day18/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(6098, event.getMinSteps());
        assertEquals(1698, event.getMinSteps2());
    }

}
