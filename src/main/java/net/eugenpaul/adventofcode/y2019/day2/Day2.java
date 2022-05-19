package net.eugenpaul.adventofcode.y2019.day2;

import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day2 extends SolutionTemplate {

    @Getter
    private int reg0;
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

        int[] opcodes = StringConverter.toIntArray(eventData);

        reg0 = doPuzzle1(opcodes.clone());
        logger.log(Level.INFO, () -> "reg0  : " + getReg0());

        if (doPuzzele2) {
            nounVerb = doPuzzle2(opcodes.clone());
            logger.log(Level.INFO, () -> "nounVerb  : " + getNounVerb());
        }

        return true;
    }

    private int doPuzzle1(int[] opcodes) {
        if (doReplace) {
            opcodes[1] = 12;
            opcodes[2] = 2;
        }

        return runOpcodes(opcodes);
    }

    private int doPuzzle2(int[] opcodes) {
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

    private int runOpcodes(int[] opcodes) {
        int pos = 0;

        while (opcodes[pos] != 99) {
            switch (opcodes[pos]) {
            case 1:
                opcodes[opcodes[pos + 3]] = opcodes[opcodes[pos + 1]] + opcodes[opcodes[pos + 2]];
                break;
            case 2:
                opcodes[opcodes[pos + 3]] = opcodes[opcodes[pos + 1]] * opcodes[opcodes[pos + 2]];
                break;
            default:
                throw new IllegalArgumentException("Wrong opcode: " + opcodes[pos] + ". pos " + pos);
            }
            pos += 4;
        }

        return opcodes[0];
    }

}
