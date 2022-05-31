package net.eugenpaul.adventofcode.y2019.day9;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.IntcodeMapComputer;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day9 extends SolutionTemplate {

    @Getter
    private long response1;
    @Getter
    private long response2;
    @Getter
    private String message;

    public static void main(String[] args) {
        Day9 puzzle = new Day9();
        puzzle.doPuzzleFromFile("y2019/day9/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        long[] opcodesArray = StringConverter.toLongArray(eventData);

        response1 = doPuzzle(opcodesArray, 1L);
        response2 = doPuzzle(opcodesArray, 2L);

        logger.log(Level.INFO, () -> "response1   : " + getResponse1());
        logger.log(Level.INFO, () -> "response2   : " + getResponse2());

        return true;
    }

    private long doPuzzle(long[] opcodesArray, long input) {
        Map<Long, Long> opcodes = new HashMap<>();

        long counter = 0;
        for (long l : opcodesArray) {
            opcodes.put(counter, l);
            counter++;
        }

        IntcodeMapComputer comp = new IntcodeMapComputer();
        comp.setInput(input);
        int pos = 0;
        while (!comp.isEnd(opcodes, pos)) {
            pos = comp.runOpcodes(opcodes, pos);
        }
        return comp.getOutput();
    }

}
