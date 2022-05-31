package net.eugenpaul.adventofcode.y2019.day7;

import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.IntcodeComputer;
import net.eugenpaul.adventofcode.helper.Permutation;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day7 extends SolutionTemplate {

    @Getter
    private long highestSignal;
    @Getter
    private long highestSignal2;

    private long[] opcodes;

    @Setter
    private boolean doFirst = true;
    @Setter
    private boolean doSecond = true;

    public static void main(String[] args) {
        Day7 puzzle = new Day7();
        puzzle.doPuzzleFromFile("y2019/day7/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        opcodes = StringConverter.toLongArray(eventData);

        highestSignal = 0;
        highestSignal2 = 0;

        if (doFirst) {
            Long[] sequence1 = { 0L, 1L, 2L, 3L, 4L };
            Permutation.permutationsRecursive(sequence1.length, sequence1, this::doPuzzle1);
        }

        if (doSecond) {
            Long[] sequence2 = { 5L, 6L, 7L, 8L, 9L };
            Permutation.permutationsRecursive(sequence2.length, sequence2, this::doPuzzle2);
        }

        logger.log(Level.INFO, () -> "highestSignal   : " + getHighestSignal());
        logger.log(Level.INFO, () -> "highestSignal2  : " + getHighestSignal2());

        return true;
    }

    private void doPuzzle1(Long[] sequence) {
        long input = 0L;

        for (Long s : sequence) {
            long[] stepOpcodes = opcodes.clone();

            IntcodeComputer comp = new IntcodeComputer();
            comp.setInput(s, input);
            comp.runOpcodes(stepOpcodes);

            input = comp.getOutput();
        }

        highestSignal = Math.max(highestSignal, input);
    }

    private void doPuzzle2(Long[] sequence) {
        long lastOutput = 0L;

        long[] stepOpcodes1 = opcodes.clone();
        IntcodeComputer comp1 = new IntcodeComputer();
        comp1.setWaitForInput(true);
        comp1.setInput(sequence[0], 0);
        int pos1 = 0;

        long[] stepOpcodes2 = opcodes.clone();
        IntcodeComputer comp2 = new IntcodeComputer();
        comp2.setWaitForInput(true);
        comp2.setInput(sequence[1]);
        int pos2 = 0;

        long[] stepOpcodes3 = opcodes.clone();
        IntcodeComputer comp3 = new IntcodeComputer();
        comp3.setWaitForInput(true);
        comp3.setInput(sequence[2]);
        int pos3 = 0;

        long[] stepOpcodes4 = opcodes.clone();
        IntcodeComputer comp4 = new IntcodeComputer();
        comp4.setWaitForInput(true);
        comp4.setInput(sequence[3]);
        int pos4 = 0;

        long[] stepOpcodes5 = opcodes.clone();
        IntcodeComputer comp5 = new IntcodeComputer();
        comp5.setWaitForInput(true);
        comp5.setInput(sequence[4]);
        int pos5 = 0;

        while (!comp5.isEnd(stepOpcodes5, pos5)) {

            pos1 = comp1.runOpcodes(stepOpcodes1, pos1);
            if (comp1.isOutput()) {
                comp2.addToInput(comp1.removeOutput());
            }

            pos2 = comp2.runOpcodes(stepOpcodes2, pos2);
            if (comp2.isOutput()) {
                comp3.addToInput(comp2.removeOutput());
            }

            pos3 = comp3.runOpcodes(stepOpcodes3, pos3);
            if (comp3.isOutput()) {
                comp4.addToInput(comp3.removeOutput());
            }

            pos4 = comp4.runOpcodes(stepOpcodes4, pos4);
            if (comp4.isOutput()) {
                comp5.addToInput(comp4.removeOutput());
            }

            pos5 = comp5.runOpcodes(stepOpcodes5, pos5);
            if (comp5.isOutput()) {
                lastOutput = comp5.removeOutput();
                comp1.addToInput(lastOutput);
            }
        }

        highestSignal2 = Math.max(highestSignal2, lastOutput);
    }

}
