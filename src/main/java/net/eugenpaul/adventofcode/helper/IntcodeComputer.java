package net.eugenpaul.adventofcode.helper;

import java.util.Map;
import java.util.function.BiFunction;

import lombok.Getter;
import lombok.Setter;

public final class IntcodeComputer {

    @Getter
    private Integer output = null;
    @Setter
    private Integer input = null;

    private Map<Integer, BiFunction<int[], Integer, Integer>> codes = Map.ofEntries(//
            Map.entry(1, this::doAdd), //
            Map.entry(2, this::doMult), //
            Map.entry(3, this::doInput), //
            Map.entry(4, this::doOutput), //
            Map.entry(5, this::doJumpIfTrue), //
            Map.entry(6, this::doJumpIfFalse), //
            Map.entry(7, this::doLessThen), //
            Map.entry(8, this::doEquals), //
            Map.entry(99, this::doEnd)//
    );

    @Setter
    @Getter
    private boolean stopped = false;

    public int runOpcodes(int[] opcodes) {
        int pos = 0;

        while (opcodes[pos] != 99) {
            pos += codes.get(opcodes[pos] % 100).apply(opcodes, pos);
        }

        return opcodes[0];
    }

    private int doEnd(int[] opcodes, int pos) {
        stopped = true;
        return 0;
    }

    private int doAdd(int[] opcodes, int pos) {

        int var1 = getVar1(opcodes, pos);
        int var2 = getVar2(opcodes, pos);

        opcodes[opcodes[pos + 3]] = var1 + var2;
        return 4;
    }

    private Integer doMult(int[] opcodes, Integer pos) {
        int var1 = getVar1(opcodes, pos);
        int var2 = getVar2(opcodes, pos);

        opcodes[opcodes[pos + 3]] = var1 * var2;
        return 4;
    }

    private int doInput(int[] opcodes, int pos) {
        opcodes[opcodes[pos + 1]] = input;
        input = null;
        return 2;
    }

    private int doOutput(int[] opcodes, int pos) {
        int var1 = getVar1(opcodes, pos);
        output = var1;
        return 2;
    }

    private Integer doJumpIfTrue(int[] opcodes, Integer pos) {
        int var1 = getVar1(opcodes, pos);
        int var2 = getVar2(opcodes, pos);

        if (var1 != 0) {
            return var2 - pos;
        }

        return 3;
    }

    private Integer doJumpIfFalse(int[] opcodes, Integer pos) {
        int var1 = getVar1(opcodes, pos);
        int var2 = getVar2(opcodes, pos);

        if (var1 == 0) {
            return var2 - pos;
        }

        return 3;
    }

    private Integer doLessThen(int[] opcodes, Integer pos) {
        int var1 = getVar1(opcodes, pos);
        int var2 = getVar2(opcodes, pos);

        if (var1 < var2) {
            opcodes[opcodes[pos + 3]] = 1;
        } else {
            opcodes[opcodes[pos + 3]] = 0;
        }

        return 4;
    }

    private Integer doEquals(int[] opcodes, Integer pos) {
        int var1 = getVar1(opcodes, pos);
        int var2 = getVar2(opcodes, pos);

        if (var1 == var2) {
            opcodes[opcodes[pos + 3]] = 1;
        } else {
            opcodes[opcodes[pos + 3]] = 0;
        }

        return 4;
    }

    private int getParamMode1(int opcode) {
        return (opcode / 100) % 10;
    }

    private int getParamMode2(int opcode) {
        return (opcode / 1000) % 10;
    }

    private int getVar2(int[] opcodes, int pos) {
        int var2;
        if (getParamMode2(opcodes[pos]) == 0) {
            var2 = opcodes[opcodes[pos + 2]];
        } else {
            var2 = opcodes[pos + 2];
        }
        return var2;
    }

    private int getVar1(int[] opcodes, int pos) {
        int var1;
        if (getParamMode1(opcodes[pos]) == 0) {
            var1 = opcodes[opcodes[pos + 1]];
        } else {
            var1 = opcodes[pos + 1];
        }
        return var1;
    }
}
