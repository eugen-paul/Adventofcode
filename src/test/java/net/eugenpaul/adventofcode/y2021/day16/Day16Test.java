package net.eugenpaul.adventofcode.y2021.day16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

class Day16Test {

    @Test
    void testTest2021Day16_1a() {
        testPuzzle1(List.of(//
                "8A004A801A8002F478" //
        ), 16, 2_188_189_693_529L);
    }

    @Test
    void testTest2021Day16_1b() {
        testPuzzle1(List.of(//
                "620080001611562C8802118E34" //
        ), 12, 2_188_189_693_529L);
    }

    @Test
    void testTest2021Day16_1c() {
        testPuzzle1(List.of(//
                "C0015000016115A2E0802F182340" //
        ), 23, 2_188_189_693_529L);
    }

    @Test
    void testTest2021Day16_1d() {
        testPuzzle1(List.of(//
                "A0016C880162017C3686B18A3D4780" //
        ), 31, 2_188_189_693_529L);
    }

    private void testPuzzle1(List<String> inputData, long versionssum, long sub2) {
        Day16 event = new Day16();

        assertTrue(event.doPuzzleFromData(inputData.get(0)));
        assertEquals(versionssum, event.getVersionssum());
    }

    @Test
    void testTest2021Day16_2a() {
        testPuzzle2(List.of(//
                "C200B40A82" //
        ), 3);
    }

    @Test
    void testTest2021Day16_2b() {
        testPuzzle2(List.of(//
                "04005AC33890" //
        ), 54);
    }

    @Test
    void testTest2021Day16_2c() {
        testPuzzle2(List.of(//
                "880086C3E88112" //
        ), 7);
    }

    @Test
    void testTest2021Day16_2d() {
        testPuzzle2(List.of(//
                "CE00C43D881120" //
        ), 9);
    }

    @Test
    void testTest2021Day16_2e() {
        testPuzzle2(List.of(//
                "D8005AC2A8F0" //
        ), 1);
    }

    @Test
    void testTest2021Day16_2f() {
        testPuzzle2(List.of(//
                "F600BC2D8F" //
        ), 0);
    }

    @Test
    void testTest2021Day16_2g() {
        testPuzzle2(List.of(//
                "9C005AC2F8F0" //
        ), 0);
    }

    @Test
    void testTest2021Day16_2h() {
        testPuzzle2(List.of(//
                "9C0141080250320F1802104A08" //
        ), 1);
    }

    private void testPuzzle2(List<String> inputData, long solution2) {
        Day16 event = new Day16();

        assertTrue(event.doPuzzleFromData(inputData.get(0)));
        assertEquals(solution2, event.getSolution2());
    }

    @Test
    void testSolution2021Day16() {
        Day16 event = new Day16();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2021/day16/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(854, event.getVersionssum());
        assertEquals(186189840660L, event.getSolution2());
    }

}
