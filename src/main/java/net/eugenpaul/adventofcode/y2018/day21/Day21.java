package net.eugenpaul.adventofcode.y2018.day21;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.computer.DevicesOpcodes;

public class Day21 extends SolutionTemplate {

    @Getter
    private long lowestRegister0;
    @Getter
    private long lowestRegister0ForMostInstructions;

    public static void main(String[] args) {
        Day21 puzzle = new Day21();
        puzzle.doPuzzleFromFile("y2018/day21/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        lowestRegister0 = doPuzzle1(eventData);
        lowestRegister0ForMostInstructions = doPuzzle2Fastest();

        logger.log(Level.INFO, () -> "lowestRegister0 : " + getLowestRegister0());
        logger.log(Level.INFO, () -> "lowestRegister0ForMostInstructions : " + getLowestRegister0ForMostInstructions());

        return true;
    }

    /**
     * There is only one access to register 0 at the end of the program:
     * 
     * <code>
     * 27 seti 7 9 2           R2 = 7                  JUMP D: R2 = 7
     * 28 eqrr 3 0 4           R4 = (R3 == R0)?1:0          B: R4 = (R3 == R0)?1:0
     * 29 addr 4 2 2           R2 = R4 + R2                    R2 = R4 + R2    EXIT if(R3 == R0)
     * 30 seti 5 4 2           R2 = 5                          R2 = 5
     * </code>
     * 
     * Solution let the program run until Instruction 28 is executed.
     * 
     * The content of register 0 is compared with the content of register 3. If the registers are the same, the program is stopped.
     */
    private long doPuzzle1(List<String> eventData) {
        long[] register = { 0, 0, 0, 0, 0, 0 };
        int ipReg = Integer.parseInt(eventData.get(0).split(" ")[1]);
        int ip = (int) (register[ipReg] + 1);
        while (ip < eventData.size()) {
            if (register[ipReg] == 28) {
                return register[3];
            }
            register = DevicesOpcodes.execute(eventData.get(ip), register);
            register[ipReg]++;
            ip = (int) (register[ipReg] + 1);
        }
        return 0;
    }

    /**
     * The program is a cycle, we wait until register 3 at instruction 28 gets the same value as before.
     */
    @SuppressWarnings("unused")
    private long doPuzzle2(List<String> eventData) {
        Set<Long> set = new HashSet<>();
        long lastRegister3 = 0;

        long[] register = { 0, 0, 0, 0, 0, 0 };
        int ipReg = Integer.parseInt(eventData.get(0).split(" ")[1]);
        int ip = (int) (register[ipReg] + 1);
        while (ip < eventData.size()) {
            if (register[ipReg] == 28) {
                if (set.contains(register[3])) {
                    return lastRegister3;
                }
                set.add(register[3]);
                lastRegister3 = register[3];
            }
            register = DevicesOpcodes.execute(eventData.get(ip), register);
            register[ipReg]++;
            ip = (int) (register[ipReg] + 1);
        }
        return 0;
    }

    @SuppressWarnings("unused")
    private long doPuzzle2Fast() {
        long r0 = 0L;
        long r1;
        long r3 = 0L;
        long r4;
        long r5;

        Set<Long> set = new HashSet<>();
        long lastRegister3 = 0;

        do {
            r1 = r3 | 65536;
            r3 = 4921097;

            do {
                r4 = r1 & 255;
                r3 = r3 + r4;
                r3 = r3 & 16777215;
                r3 = r3 * 65899;
                r3 = r3 & 16777215;

                if (256 > r1) {
                    break;
                }

                r4 = 0;

                do {
                    r5 = r4 + 1;
                    r5 = r5 * 256;
                    if (r5 > r1) {
                        break;
                    }
                    r4 = r4 + 1;
                } while (true);

                r1 = r4;

            } while (true);

            if (set.contains(r3)) {
                return lastRegister3;
            }
            set.add(r3);
            lastRegister3 = r3;
        } while (r3 != r0);

        return 0;
    }

    private long doPuzzle2Fastest() {
        long r0 = 0L;
        long r3 = 0L;

        Set<Long> set = new HashSet<>();
        long lastRegister3 = 0;

        do {
            long r1;
            r1 = r3 | 65536;
            r3 = 4921097;

            do {
                long r4;
                r4 = r1 & 255;
                r3 = r3 + r4;
                r3 = r3 & 16777215;
                r3 = r3 * 65899;
                r3 = r3 & 16777215;

                if (256 > r1) {
                    break;
                }

                r1 = r1 / 256;
            } while (true);

            if (set.contains(r3)) {
                return lastRegister3;
            }
            set.add(r3);
            lastRegister3 = r3;
        } while (r3 != r0);

        return 0;
    }

}
