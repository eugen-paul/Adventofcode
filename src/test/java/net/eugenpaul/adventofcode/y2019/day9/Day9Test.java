package net.eugenpaul.adventofcode.y2019.day9;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;
import net.eugenpaul.adventofcode.helper.StringConverter;
import net.eugenpaul.adventofcode.helper.computer.IntcodeMapComputer;

class Day9Test {

    @Test
    void testTest2019Day9_testComp_1() {
        String input = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99";
        IntcodeMapComputer comp = new IntcodeMapComputer();
        Map<Long, Long> opcodes = new HashMap<>();

        long[] opcodesArray = StringConverter.toLongArray(input);
        long counter = 0;
        for (long l : opcodesArray) {
            opcodes.put(counter, l);
            counter++;
        }

        int pos = 0;
        List<Long> outputs = new LinkedList<>();
        while (!comp.isEnd(opcodes, pos)) {
            pos = comp.runOpcodes(opcodes, pos);
            if (comp.isOutput()) {
                outputs.add(comp.removeOutput());
            }
        }
        assertArrayEquals(opcodesArray, outputs.stream().mapToLong(Long::intValue).toArray());
    }

    @Test
    void testTest2019Day9_testComp_2() {
        String input = "1102,34915192,34915192,7,4,7,99";
        IntcodeMapComputer comp = new IntcodeMapComputer();
        Map<Long, Long> opcodes = new HashMap<>();

        long[] opcodesArray = StringConverter.toLongArray(input);
        long counter = 0;
        for (long l : opcodesArray) {
            opcodes.put(counter, l);
            counter++;
        }

        int pos = 0;
        while (!comp.isEnd(opcodes, pos)) {
            pos = comp.runOpcodes(opcodes, pos);
        }
        assertEquals(1219070632396864L, comp.getOutput());
    }

    @Test
    void testTest2019Day9_testComp_3() {
        String input = "104,1125899906842624,99";
        IntcodeMapComputer comp = new IntcodeMapComputer();
        Map<Long, Long> opcodes = new HashMap<>();

        long[] opcodesArray = StringConverter.toLongArray(input);
        long counter = 0;
        for (long l : opcodesArray) {
            opcodes.put(counter, l);
            counter++;
        }

        int pos = 0;
        while (!comp.isEnd(opcodes, pos)) {
            pos = comp.runOpcodes(opcodes, pos);
        }
        assertEquals(1125899906842624L, comp.getOutput());
    }

    @Test
    void testSolution2019Day9() {
        Day9 event = new Day9();

        List<String> eventData = FileReaderHelper.readListStringFromFile("y2019/day9/puzzle1.txt");
        assertTrue(event.doPuzzleFromData(eventData));
        assertEquals(3063082071L, event.getResponse1());
        assertEquals(81348L, event.getResponse2());
    }

}
