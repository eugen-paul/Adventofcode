package net.eugenpaul.adventofcode.y2019.day21;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.IntcodeMapComputer;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day21 extends SolutionTemplate {

    @Getter
    private long puzzle1;
    @Getter
    private long puzzle2;

    public static void main(String[] args) {
        Day21 puzzle = new Day21();
        puzzle.doPuzzleFromFile("y2019/day21/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        Map<Long, Long> opcodesOrigin = initOpcodes(eventData);

        List<Integer> c1 = toAsciiList(//
                "" //
                        + "NOT A T\n" //
                        + "NOT B J\n" //
                        + "OR  T J\n" //
                        + "NOT C T\n" //
                        + "OR  T J\n" //
                        + "AND D J\n" //
        );
        List<Integer> w = toAsciiList("WALK\n");

        List<Integer> input = new LinkedList<>();
        input.addAll(c1);
        input.addAll(w);

        puzzle1 = doPuzzle(opcodesOrigin, input);

        List<Integer> c2 = toAsciiList(//
                "" //
                   // rule1: double jump
                        + "OR  H T\n" // eight is a hull

                        // rule2: jump + walk + jump
                        + "OR  E J\n" // six is a hull
                        + "AND I J\n" // nine is a hull

                        // rule1 or rule2 is true
                        + "OR  J T\n" //

                        // rule3: jump + walk + walk
                        + "NOT E J\n" // six is a hull
                        + "NOT J J\n" //
                        + "AND F J\n" // seven is a hull

                        // rule1 or rule2 or rule3 is true
                        + "OR  J T\n" //

                        // rule4: Rules 1-3 may be executed only when there is a hole in front of us.
                        + "NOT A J\n" // one is a hull
                        + "NOT J J\n" //
                        + "AND B J\n" // two is a hull
                        + "AND C J\n" // three is a hull
                        + "NOT J J\n" // make a hole from a hull

                        // (rule1 or rule2 or rule3) and rule4 is true
                        + "AND T J\n" //

                        // rule5: You can jump only if there is a hull in the 4 tiles away.
                        + "AND D J\n" //
        );
        List<Integer> r = toAsciiList("RUN\n");

        input = new LinkedList<>();
        input.addAll(c2);
        input.addAll(r);

        puzzle2 = doPuzzle(opcodesOrigin, input);

        logger.log(Level.INFO, () -> "puzzle1  : " + getPuzzle1());
        logger.log(Level.INFO, () -> "puzzle2  : " + getPuzzle2());

        return true;
    }

    private long doPuzzle(Map<Long, Long> opcodesOrigin, List<Integer> input) {
        int pos = 0;
        IntcodeMapComputer comp;
        comp = new IntcodeMapComputer();

        Map<Long, Long> opcodes = new HashMap<>(opcodesOrigin);

        comp.setInput(input.stream().mapToLong(i -> i).toArray());

        long response = 0;

        while (!comp.isEnd(opcodes, pos)) {
            pos = comp.runOpcodes(opcodes, pos);
            if (comp.isOutput()) {
                response = comp.removeOutput();
            }
        }
        return response;
    }

    private List<Integer> toAsciiList(String in) {
        return in.chars().boxed().collect(Collectors.toList());
    }

    private Map<Long, Long> initOpcodes(String eventData) {
        Map<Long, Long> opcodesOrigin;

        // init Intcode
        long[] opcodesArray = StringConverter.toLongArray(eventData);
        opcodesOrigin = new HashMap<>();

        long counter = 0;
        for (long l : opcodesArray) {
            opcodesOrigin.put(counter, l);
            counter++;
        }
        return opcodesOrigin;
    }

}