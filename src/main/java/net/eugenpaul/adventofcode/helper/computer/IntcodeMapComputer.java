package net.eugenpaul.adventofcode.helper.computer;

import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiFunction;

import lombok.Getter;
import lombok.Setter;

public final class IntcodeMapComputer {

    @Getter
    private Long output = null;

    private long relativOffset = 0;

    private LinkedList<Long> input = null;

    @Setter
    private boolean waitForInput = false;

    @Setter
    private boolean emptyInput = false;

    @Setter
    private Long emptyInputValue = -1L;

    private Map<Integer, BiFunction<Map<Long, Long>, Integer, Integer>> codes = Map.ofEntries(//
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

    public long runOpcodes(Map<Long, Long> opcodes) {
        int pos = 0;

        while (opcodes.get((long) pos) != 99) {
            pos += codes.get((int) (opcodes.get((long) pos) % 100L)).apply(opcodes, pos);
        }

        return opcodes.get(0L);
    }

    public int runOpcodes(Map<Long, Long> opcodes, int pos) {
        int currentPos = pos;
        currentPos += codes.get((int) (opcodes.get((long) currentPos) % 100L)).apply(opcodes, currentPos);
        return currentPos;
    }

    public boolean isEnd(Map<Long, Long> opcodes, int pos) {
        return opcodes.get((long) pos) == 99;
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

    public boolean isInputEmpty() {
        return input.isEmpty();
    }

    private int doEnd(Map<Long, Long> opcodes, Integer pos) {
        stopped = true;
        return 0;
    }

    private void setMemory(Map<Long, Long> opcodes, long offset, long mode, long value) {
        if (mode == 0L) {
            opcodes.put(opcodes.getOrDefault(offset, 0L), value);
        } else if (mode == 1L) {
            opcodes.put(offset, value);
        } else if (mode == 2L) {
            opcodes.put(opcodes.getOrDefault(offset, 0L) + relativOffset, value);
        } else {
            throw new IllegalArgumentException("illegal mode");
        }
    }

    private Integer doAdd(Map<Long, Long> opcodes, Integer pos) {
        long var1 = getVar1(opcodes, pos);
        long var2 = getVar2(opcodes, pos);

        long mode = getParamMode3(opcodes.getOrDefault((long) pos, 0L));
        setMemory(opcodes, (long) pos + 3L, mode, var1 + var2);

        return 4;
    }

    private Integer doMult(Map<Long, Long> opcodes, Integer pos) {
        long var1 = getVar1(opcodes, pos);
        long var2 = getVar2(opcodes, pos);

        long mode = getParamMode3(opcodes.getOrDefault((long) pos, 0L));
        setMemory(opcodes, (long) pos + 3L, mode, var1 * var2);

        return 4;
    }

    private Integer doInput(Map<Long, Long> opcodes, Integer pos) {
        if (waitForInput && input.isEmpty()) {
            return 0;
        }

        if (emptyInput && input.isEmpty()) {
            input.add(emptyInputValue);
        }

        long mode = getParamMode1(opcodes.getOrDefault((long) pos, 0L));
        setMemory(opcodes, (long) pos + 1L, mode, input.pollFirst());

        return 2;
    }

    private Integer doOutput(Map<Long, Long> opcodes, Integer pos) {
        long var1 = getVar1(opcodes, pos);
        output = var1;
        return 2;
    }

    private Integer doJumpIfTrue(Map<Long, Long> opcodes, Integer pos) {
        long var1 = getVar1(opcodes, pos);
        long var2 = getVar2(opcodes, pos);

        if (var1 != 0) {
            return (int) var2 - pos;
        }

        return 3;
    }

    private Integer doJumpIfFalse(Map<Long, Long> opcodes, Integer pos) {
        long var1 = getVar1(opcodes, pos);
        long var2 = getVar2(opcodes, pos);

        if (var1 == 0) {
            return (int) var2 - pos;
        }

        return 3;
    }

    private Integer doLessThen(Map<Long, Long> opcodes, Integer pos) {
        long var1 = getVar1(opcodes, pos);
        long var2 = getVar2(opcodes, pos);

        long result;
        if (var1 < var2) {
            result = 1L;
        } else {
            result = 0L;
        }

        long mode = getParamMode3(opcodes.getOrDefault((long) pos, 0L));
        setMemory(opcodes, (long) pos + 3L, mode, result);

        return 4;
    }

    private Integer doEquals(Map<Long, Long> opcodes, Integer pos) {
        long var1 = getVar1(opcodes, pos);
        long var2 = getVar2(opcodes, pos);

        long result;
        if (var1 == var2) {
            result = 1L;
        } else {
            result = 0L;
        }

        long mode = getParamMode3(opcodes.getOrDefault((long) pos, 0L));
        setMemory(opcodes, (long) pos + 3L, mode, result);

        return 4;
    }

    private Integer doRelOffet(Map<Long, Long> opcodes, Integer pos) {
        long var1 = getVar1(opcodes, pos);

        relativOffset += var1;
        return 2;
    }

    private long getParamMode1(long opcode) {
        return (opcode / 100L) % 10L;
    }

    private long getParamMode2(long opcode) {
        return (opcode / 1000L) % 10L;
    }

    private long getParamMode3(long opcode) {
        return (opcode / 10000L) % 10L;
    }

    private long getVar2(Map<Long, Long> opcodes, Integer pos) {
        long var2;
        long mode = getParamMode2(opcodes.getOrDefault((long) pos, 0L));
        if (mode == 0) {
            var2 = opcodes.getOrDefault(opcodes.getOrDefault(pos + 2L, 0L), 0L);
        } else if (mode == 1) {
            var2 = opcodes.getOrDefault(pos + 2L, 0L);
        } else if (mode == 2) {
            var2 = opcodes.getOrDefault(opcodes.getOrDefault(pos + 2L, 0L) + relativOffset, 0L);
        } else {
            throw new IllegalArgumentException("illegal mode. pos: " + pos + ". opcode[pos]: " + mode);
        }
        return var2;
    }

    private long getVar1(Map<Long, Long> opcodes, Integer pos) {
        long var1;
        long mode = getParamMode1(opcodes.getOrDefault((long) pos, 0L));
        if (mode == 0) {
            var1 = opcodes.getOrDefault(opcodes.getOrDefault(pos + 1L, 0L), 0L);
        } else if (mode == 1) {
            var1 = opcodes.getOrDefault(pos + 1L, 0L);
        } else if (mode == 2) {
            var1 = opcodes.getOrDefault(opcodes.getOrDefault(pos + 1L, 0L) + relativOffset, 0L);
        } else {
            throw new IllegalArgumentException("illegal mode. pos: " + pos + ". opcode[pos]: " + mode);
        }
        return var1;
    }
}
