package net.eugenpaul.adventofcode.y2017.day23;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.PrimeNumberGen;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;
import net.eugenpaul.adventofcode.helper.computer.InstructionFactory;
import net.eugenpaul.adventofcode.helper.computer.instruction.InstructionMul;

public class Day23 extends SolutionTemplate {

    @Getter
    private long mulInvoked;
    @Getter
    private long regHValue;

    @Setter
    private boolean doSlow = false;
    @Setter
    private boolean doSlowEdit = false;

    public static void main(String[] args) {
        Day23 puzzle = new Day23();
        puzzle.doPuzzleFromFile("y2017/day23/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        mulInvoked = doPuzzle1(eventData);
        regHValue = doPuzzle2Fast();

        if (doSlow) {
            doPuzzle2Slow();
        }
        if (doSlowEdit) {
            doPuzzle2Refactored();
        }

        logger.log(Level.INFO, () -> "mulInvoked: " + getMulInvoked());
        logger.log(Level.INFO, () -> "regHValue: " + getRegHValue());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {
        List<Instruction> instructions = eventData.stream()//
                .map(InstructionFactory::fromString)//
                .collect(Collectors.toList());

        LinkedList<Long> queue = new LinkedList<>();
        Computer computer = new Computer(0, 0, queue, queue);

        int mulCounter = 0;

        while (computer.getPosition() < instructions.size()) {
            Instruction ins = instructions.get(computer.getPosition());
            if (ins instanceof InstructionMul) {
                mulCounter++;
            }
            ins.doInstruction(computer);
        }

        return mulCounter;
    }

    private long doPuzzle2Fast() {
        long b = 109900L;
        long c = 126900L;
        long h = 0L;
        for (long i = b; i <= c; i += 17) {
            if (!PrimeNumberGen.checkPrim(i)) {
                h++;
            }
        }
        return h;
    }

    private long doPuzzle2Refactored() {
        long b = 109900L;
        long c = 126900L;
        long d;
        long e;
        long f;
        long h = 0L;

        while (true) {
            f = 1;
            d = 2;
            do {
                e = 2;
                do {
                    if (d * e == b) {
                        f = 0; // only if d * e equal b is f will be set zero => we can break both while-loops there
                    }
                    e++; // e is a counter from 2 to b
                } while (e != b);
                d++; // d is a counter from 2 to b
            } while (d != b);
            if (f == 0) {
                h++; // only if f is equal to zero is h incremented
            }
            if (b == c) {
                return h;
            }
            b = b + 17;
        }

        // e and d are counters from 2 to b
        // only if e*d equal to b is h incremented
        // => h incremented if b is not prim
        // b is a counter from b to c
        // solution count non prime numbers from b to c
    }

    private long doPuzzle2Slow() {
        long b = 109900L;
        long c = 126900L;
        long d;
        long e;
        long f;
        long g;
        long h = 0L;
        while (true) {
            f = 1;
            d = 2;
            do {
                e = 2;
                do {
                    g = d;
                    g = g * e;
                    g = g - b;
                    if (g == 0) {
                        f = 0;
                    }
                    e = e + 1;
                    g = e;
                    g = g - b;
                } while (g != 0);
                d = d + 1;
                g = d;
                g = g - b;
            } while (g != 0);
            if (f == 0) {
                h = h + 1;
            }
            g = b;
            g = g - c;
            if (g == 0) {
                return h;
            }
            b = b + 17;
        }

    }

}
