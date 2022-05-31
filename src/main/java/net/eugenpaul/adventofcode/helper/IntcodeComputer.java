package net.eugenpaul.adventofcode.helper;

import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiFunction;

import lombok.Getter;
import lombok.Setter;

public final class IntcodeComputer {

    @Getter
    private Long output = null;

    private long relativOffset = 0;

    private LinkedList<Long> input = null;

    @Setter
    private boolean waitForInput = false;

    private Map<Integer, BiFunction<long[], Integer, Integer>> codes = Map.ofEntries(//
            Map.entry(1, this::doAdd), //
            Map.entry(2, this::doMult), //
            Map.entry(3, this::doInput), //
            Map.entry(4, this::doOutput), //
            Map.entry(5, this::doJumpIfTrue), //
            Map.entry(6, this::doJumpIfFalse), //
            Map.entry(7, this::doLessThen), //
            Map.entry(8, this::doEquals), //
            Map.entry(9, this::doRelOffet), //
            Map.entry(99, this::doEnd)//
    );

    @Setter
    @Getter
    private boolean stopped = false;

    public long runOpcodes(long[] opcodes) {
        int pos = 0;

        while (opcodes[pos] != 99) {
            pos += codes.get((int) (opcodes[pos] % 100L)).apply(opcodes, pos);
        }

        return opcodes[0];
    }

    public int runOpcodes(long[] opcodes, int pos) {
        int currentPos = pos;
        currentPos += codes.get((int) (opcodes[currentPos] % 100L)).apply(opcodes, currentPos);
        return currentPos;
    }

    public boolean isEnd(long[] opcodes, int pos) {
        return opcodes[pos] == 99;
    }

    public void setInput(long... input) {
        this.input = new LinkedList<>();
        for (long i : input) {
            this.input.add(i);
        }
    }

    public void addToInput(long input) {
        this.input.add(input);
    }

    public Long removeOutput() {
        Long response = output;
        output = null;
        return response;
    }

    public boolean isOutput() {
        return output != null;
    }

    private int doEnd(long[] opcodes, Integer pos) {
        stopped = true;
        return 0;
    }

    private Integer doAdd(long[] opcodes, Integer pos) {

        long var1 = getVar1(opcodes, pos);
        long var2 = getVar2(opcodes, pos);

        opcodes[(int) opcodes[pos + 3]] = var1 + var2;
        return 4;
    }

    private Integer doMult(long[] opcodes, Integer pos) {
        long var1 = getVar1(opcodes, pos);
        long var2 = getVar2(opcodes, pos);

        opcodes[(int) opcodes[pos + 3]] = var1 * var2;
        return 4;
    }

    private Integer doInput(long[] opcodes, Integer pos) {
        if (waitForInput && input.isEmpty()) {
            return 0;
        }
        opcodes[(int) opcodes[pos + 1]] = input.pollFirst();
        return 2;
    }

    private Integer doOutput(long[] opcodes, Integer pos) {
        long var1 = getVar1(opcodes, pos);
        output = var1;
        return 2;
    }

    private Integer doJumpIfTrue(long[] opcodes, Integer pos) {
        long var1 = getVar1(opcodes, pos);
        long var2 = getVar2(opcodes, pos);

        if (var1 != 0) {
            return (int) var2 - pos;
        }

        return 3;
    }

    private Integer doJumpIfFalse(long[] opcodes, Integer pos) {
        long var1 = getVar1(opcodes, pos);
        long var2 = getVar2(opcodes, pos);

        if (var1 == 0) {
            return (int) var2 - pos;
        }

        return 3;
    }

    private Integer doLessThen(long[] opcodes, Integer pos) {
        long var1 = getVar1(opcodes, pos);
        long var2 = getVar2(opcodes, pos);

        if (var1 < var2) {
            opcodes[(int) opcodes[pos + 3]] = 1;
        } else {
            opcodes[(int) opcodes[pos + 3]] = 0;
        }

        return 4;
    }

    private Integer doEquals(long[] opcodes, Integer pos) {
        long var1 = getVar1(opcodes, pos);
        long var2 = getVar2(opcodes, pos);

        if (var1 == var2) {
            opcodes[(int) opcodes[pos + 3]] = 1;
        } else {
            opcodes[(int) opcodes[pos + 3]] = 0;
        }

        return 4;
    }

    private Integer doRelOffet(long[] opcodes, Integer pos) {

        long var1 = getVar1(opcodes, pos);

        relativOffset = var1;
        return 2;
    }

    private long getParamMode1(long opcode) {
        return (opcode / 100L) % 10L;
    }

    private long getParamMode2(long opcode) {
        return (opcode / 1000L) % 10L;
    }

    private long getVar2(long[] opcodes, int pos) {
        long var2;
        long mode = getParamMode2(opcodes[pos]);
        if (mode == 0) {
            var2 = opcodes[(int) opcodes[pos + 2]];
        } else if (mode == 1) {
            var2 = opcodes[pos + 2];
        } else if (mode == 2) {
            var2 = opcodes[(int) relativOffset] + (int) opcodes[pos + 2];
        } else {
            throw new IllegalArgumentException("illegal mode. pos: " + pos + ". opcode[pos]: " + opcodes[pos]);
        }
        return var2;
    }

    private long getVar1(long[] opcodes, int pos) {
        long var1;
        long mode = getParamMode1(opcodes[pos]);
        if (mode == 0) {
            var1 = opcodes[(int) opcodes[pos + 1]];
        } else if (mode == 1) {
            var1 = opcodes[pos + 1];
        } else if (mode == 2) {
            var1 = opcodes[(int) relativOffset + (int) opcodes[pos + 1]];
        } else {
            throw new IllegalArgumentException("illegal mode. pos: " + pos + ". opcode[pos]: " + opcodes[pos]);
        }
        return var1;
    }
}
