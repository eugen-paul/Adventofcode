package net.eugenpaul.adventofcode.y2019.day2;

import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.IntcodeComputer;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day2 extends SolutionTemplate {

    @Getter
    private long reg0;
    @Getter
    private int nounVerb;

    @Setter
    private boolean doReplace = true;
    @Setter
    private boolean doPuzzele2 = true;

    public static void main(String[] args) {
        Day2 puzzle = new Day2();
        puzzle.doPuzzleFromFile("y2019/day2/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        long[] opcodes = StringConverter.toLongArray(eventData);

        reg0 = doPuzzle1(opcodes.clone());
        logger.log(Level.INFO, () -> "reg0  : " + getReg0());

        if (doPuzzele2) {
            nounVerb = doPuzzle2(opcodes.clone());
            logger.log(Level.INFO, () -> "nounVerb  : " + getNounVerb());
        }

        return true;
    }

    private long doPuzzle1(long[] opcodes) {
        if (doReplace) {
            opcodes[1] = 12;
            opcodes[2] = 2;
        }

        return runOpcodes(opcodes);
    }

    private int doPuzzle2(long[] opcodes) {
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                try {
                    opcodes[1] = noun;
                    opcodes[2] = verb;
                    if (runOpcodes(opcodes.clone()) == 19690720) {
                        return 100 * noun + verb;
                    }
                } catch (Exception e) {
                    // ignore
                }
            }
        }

        return -1;
    }

    private long runOpcodes(long[] opcodes) {
        IntcodeComputer comp = new IntcodeComputer();

        return comp.runOpcodes(opcodes);
    }

}
