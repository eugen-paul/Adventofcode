package net.eugenpaul.adventofcode.y2019.day5;

import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;
import net.eugenpaul.adventofcode.helper.computer.IntcodeComputer;

public class Day5 extends SolutionTemplate {

    @Getter
    private long diagnosticCode;
    @Getter
    private long diagnosticCode2;

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        puzzle.doPuzzleFromFile("y2019/day5/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        long[] opcodes = StringConverter.toLongArray(eventData);

        diagnosticCode = doPuzzle(opcodes, 1L);

        opcodes = StringConverter.toLongArray(eventData);
        diagnosticCode2 = doPuzzle(opcodes, 5L);

        logger.log(Level.INFO, () -> "diagnosticCode   : " + getDiagnosticCode());
        logger.log(Level.INFO, () -> "diagnosticCode2  : " + getDiagnosticCode2());

        return true;
    }

    private long doPuzzle(long[] opcodes, long input) {
        IntcodeComputer comp = new IntcodeComputer();
        comp.setInput(input);
        comp.runOpcodes(opcodes);
        return comp.getOutput();
    }

}
