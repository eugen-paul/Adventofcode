package net.eugenpaul.adventofcode.y2019.day5;

import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.IntcodeComputer;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day5 extends SolutionTemplate {

    @Getter
    private int diagnosticCode;
    @Getter
    private int diagnosticCode2;

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        puzzle.doPuzzleFromFile("y2019/day5/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        int[] opcodes = StringConverter.toIntArray(eventData);

        diagnosticCode = doPuzzle(opcodes, 1);

        opcodes = StringConverter.toIntArray(eventData);
        diagnosticCode2 = doPuzzle(opcodes, 5);

        logger.log(Level.INFO, () -> "diagnosticCode   : " + getDiagnosticCode());
        logger.log(Level.INFO, () -> "diagnosticCode2  : " + getDiagnosticCode2());

        return true;
    }

    private int doPuzzle(int[] opcodes, int input) {
        IntcodeComputer comp = new IntcodeComputer();
        comp.setInput(input);
        comp.runOpcodes(opcodes);
        return comp.getOutput();
    }

}
