package net.eugenpaul.adventofcode.y2018.day16;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.computer.DevicesOpcodes;

public class Day16 extends SolutionTemplate {
    private static final String BEFOR_FORMAT = "^Before: \\[([0-9]*), ([0-9]*), ([0-9]*), ([0-9]*)\\]$";
    private static final String REGISTER_FORMAT = "^([0-9]*) ([0-9]*) ([0-9]*) ([0-9]*)$";
    private static final String AFTER_FORMAT = "^After:  \\[([0-9]*), ([0-9]*), ([0-9]*), ([0-9]*)\\]$";
    private static final Pattern BEFOR_PATTERN = Pattern.compile(BEFOR_FORMAT);
    private static final Pattern REGISTER_PATTERN = Pattern.compile(REGISTER_FORMAT);
    private static final Pattern AFTER_PATTERN = Pattern.compile(AFTER_FORMAT);

    @Getter
    private Integer threeOrMoreOpcodes;

    @Getter
    private int register0;

    public static void main(String[] args) {
        Day16 puzzle = new Day16();
        puzzle.doPuzzleFromFile("y2018/day16/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        long[] registerBefor = new long[4];
        long[] inst = new long[4];
        long[] registerAfter = new long[4];

        int pos = 0;
        threeOrMoreOpcodes = 0;

        Map<Long, Set<DevicesOpcodes>> opcodes = new HashMap<>();

        while (readTest(eventData, pos, registerBefor, inst, registerAfter)) {
            int count = 0;
            for (DevicesOpcodes i : DevicesOpcodes.values()) {
                if (isInstruction(i, registerBefor, inst, registerAfter)) {
                    count++;

                    opcodes.computeIfAbsent(inst[0], v -> new HashSet<>())//
                            .add(i);
                }
            }

            if (count >= 3) {
                threeOrMoreOpcodes++;
            }

            pos += 4;
        }

        Map<Long, DevicesOpcodes> op = getOpcodes(opcodes);

        register0 = 0;
        if (op.size() == 16) {
            register0 = (int) doPuzzle2(op, eventData, pos);
        }

        logger.log(Level.INFO, () -> "threeOrMoreOpcodes : " + getThreeOrMoreOpcodes());
        logger.log(Level.INFO, () -> "register0 : " + getRegister0());

        return true;
    }

    private long doPuzzle2(Map<Long, DevicesOpcodes> op, List<String> eventData, int pos) {
        long[] register = { 0, 0, 0, 0 };
        long[] inst = { 0, 0, 0, 0 };

        int currentPos = pos + 2;

        while (readInstruction(eventData, currentPos, inst)) {
            register = op.get(inst[0]).execute(register, inst);
            currentPos++;
        }

        return register[0];
    }

    private Map<Long, DevicesOpcodes> getOpcodes(Map<Long, Set<DevicesOpcodes>> opcodes) {
        Map<Long, DevicesOpcodes> response = new HashMap<>();

        boolean done = false;
        while (!done) {

            DevicesOpcodes unique = null;
            for (var opcodesEntry : opcodes.entrySet()) {
                if (opcodesEntry.getValue().size() == 1) {
                    unique = opcodesEntry.getValue().stream().findFirst().orElseThrow();
                    response.put(opcodesEntry.getKey(), unique);
                    break;
                }
            }

            if (unique != null) {
                for (var opcodesEntry : opcodes.entrySet()) {
                    opcodesEntry.getValue().remove(unique);
                }
            } else {
                done = true;
            }
        }

        return response;
    }

    private boolean readInstruction(List<String> eventData, int pos, long[] inst) {
        if (eventData.size() <= pos) {
            return false;
        }
        Matcher m = REGISTER_PATTERN.matcher(eventData.get(pos));
        if (m.find()) {
            inst[0] = Integer.parseInt(m.group(1));
            inst[1] = Integer.parseInt(m.group(2));
            inst[2] = Integer.parseInt(m.group(3));
            inst[3] = Integer.parseInt(m.group(4));
        } else {
            return false;
        }

        return true;
    }

    private boolean readTest(List<String> eventData, int pos, long[] registerBefor, long[] inst, long[] registerAfter) {
        if (eventData.size() <= pos) {
            return false;
        }
        Matcher m = BEFOR_PATTERN.matcher(eventData.get(pos));
        if (m.find()) {
            registerBefor[0] = Integer.parseInt(m.group(1));
            registerBefor[1] = Integer.parseInt(m.group(2));
            registerBefor[2] = Integer.parseInt(m.group(3));
            registerBefor[3] = Integer.parseInt(m.group(4));
        } else {
            return false;
        }

        if (!readInstruction(eventData, pos + 1, inst)) {
            return false;
        }

        m = AFTER_PATTERN.matcher(eventData.get(pos + 2));
        if (m.find()) {
            registerAfter[0] = Integer.parseInt(m.group(1));
            registerAfter[1] = Integer.parseInt(m.group(2));
            registerAfter[2] = Integer.parseInt(m.group(3));
            registerAfter[3] = Integer.parseInt(m.group(4));
        } else {
            return false;
        }

        return true;
    }

    private boolean isInstruction(DevicesOpcodes testInst, long[] registerBefor, long[] inst, long[] registerAfter) {
        long[] testOut = testInst.execute(registerBefor, inst);

        return Arrays.compare(testOut, registerAfter) == 0;
    }

}
