package net.eugenpaul.adventofcode.y2019.day23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.IntcodeMapComputer;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day23 extends SolutionTemplate {

    @Getter
    private Long puzzle1;
    @Getter
    private Long puzzle2;

    public static void main(String[] args) {
        Day23 puzzle = new Day23();
        puzzle.doPuzzleFromFile("y2019/day23/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        Map<Long, Long> opcodesOrigin = initOpcodes(eventData);

        doPuzzle(opcodesOrigin);

        logger.log(Level.INFO, () -> "puzzle1  : " + getPuzzle1());
        logger.log(Level.INFO, () -> "puzzle2  : " + getPuzzle2());

        return true;
    }

    private void doPuzzle(Map<Long, Long> opcodesOrigin) {
        // init all data
        int[] pos = new int[50];
        Arrays.fill(pos, 0);

        IntcodeMapComputer[] comp = new IntcodeMapComputer[50];
        List<Map<Long, Long>> opcodes = new ArrayList<>();
        Map<Integer, LinkedList<Long>> packets = new HashMap<>();
        for (int i = 0; i < comp.length; i++) {
            comp[i] = new IntcodeMapComputer();
            comp[i].setInput((long) i);
            comp[i].setEmptyInput(true);

            opcodes.add(new HashMap<>(opcodesOrigin));

            packets.put(i, new LinkedList<>());
        }

        LinkedList<Long> natPacket = new LinkedList<>();
        Long lastNatYTo0 = null;

        boolean idle = false;

        // run all computers "parallel"
        while (puzzle1 == null || puzzle2 == null) {

            if (!natPacket.isEmpty()) {
                idle = true;
            }

            idle = doStep(pos, comp, opcodes, packets, natPacket, idle);

            if (idle) {
                if (lastNatYTo0 != null && lastNatYTo0.equals(natPacket.get(1))) {
                    puzzle2 = lastNatYTo0;
                }
                comp[0].addToInput(natPacket.removeFirst());
                comp[0].addToInput(natPacket.getFirst());
                lastNatYTo0 = natPacket.removeFirst();
                idle = false;
            }
        }
    }

    private boolean doStep(int[] pos, IntcodeMapComputer[] comp, List<Map<Long, Long>> opcodes, Map<Integer, LinkedList<Long>> packets,
            LinkedList<Long> natPacket, boolean idle) {
        for (int i = 0; i < comp.length; i++) {
            if (!comp[i].isStopped()) {
                pos[i] = comp[i].runOpcodes(opcodes.get(i), pos[i]);
                checkOutput(comp, packets, i, natPacket);
            }

            if (!comp[i].isInputEmpty()) {
                idle = false;
            }
        }
        return idle;
    }

    private void checkOutput(IntcodeMapComputer[] comp, Map<Integer, LinkedList<Long>> packets, int i, List<Long> natPacket) {
        if (comp[i].isOutput()) {
            var toSend = packets.get(i);
            toSend.add(comp[i].removeOutput());
            while (toSend.size() >= 3) {
                int to = toSend.removeFirst().intValue();
                Long x = toSend.removeFirst();
                Long y = toSend.removeFirst();
                if (to == 255) {
                    if (puzzle1 == null) {
                        puzzle1 = y;
                    }
                    natPacket.clear();
                    natPacket.add(x);
                    natPacket.add(y);
                } else {
                    comp[to].addToInput(x);
                    comp[to].addToInput(y);
                }
            }
        }
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