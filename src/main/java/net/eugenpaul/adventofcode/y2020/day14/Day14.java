package net.eugenpaul.adventofcode.y2020.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day14 extends SolutionTemplate {

    private static final String MASK = "mask = ";
    private static final String MEM_PATTERN_STRING = "^mem\\[([\\d]*)\\] = ([\\d]*)$";
    private static final Pattern MEM_PATTERN = Pattern.compile(MEM_PATTERN_STRING);

    @Getter
    private long sum;
    @Getter
    private long sum2;

    @Setter
    private boolean doStep2 = true;

    public static void main(String[] args) {
        Day14 puzzle = new Day14();
        puzzle.doPuzzleFromFile("y2020/day14/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        sum = doPuzzle1(eventData);

        if (doStep2) {
            sum2 = doPuzzle2(eventData);
        }

        logger.log(Level.INFO, () -> "sum   : " + getSum());
        logger.log(Level.INFO, () -> "sum2  : " + getSum2());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {
        Map<Integer, Long> mem = new HashMap<>();

        String mask = "";

        for (String data : eventData) {
            if (data.startsWith(MASK)) {
                mask = data.substring(MASK.length());
            } else {
                Matcher m = MEM_PATTERN.matcher(data);

                if (!m.find()) {
                    throw new IllegalArgumentException();
                }

                setMemory(//
                        mem, //
                        Integer.parseInt(m.group(1)), //
                        Long.parseLong(m.group(2)), //
                        mask //
                );
            }
        }

        return mem.values().stream().reduce(0L, (a, b) -> a + b);
    }

    private long doPuzzle2(List<String> eventData) {
        Map<String, Long> mem = new HashMap<>();

        String mask = "";

        for (String data : eventData) {
            if (data.startsWith(MASK)) {
                mask = data.substring(MASK.length());
            } else {
                Matcher m = MEM_PATTERN.matcher(data);

                if (!m.find()) {
                    throw new IllegalArgumentException();
                }

                setMemory2(//
                        mem, //
                        Integer.parseInt(m.group(1)), //
                        Long.parseLong(m.group(2)), //
                        mask //
                );
            }
        }

        return mem.values().stream().reduce(0L, (a, b) -> a + b);
    }

    private void setMemory(Map<Integer, Long> mem, int index, long value, String mask) {
        long endValue = value;
        for (int i = 0; i < 36; i++) {
            switch (mask.charAt(mask.length() - 1 - i)) {
            case '0':
                long a = (1L << i);
                long b = ~a;
                long c = b & 0x1FFFFFFFFFFL;
                endValue = c & endValue;
                break;
            case '1':
                endValue = (1L << i) | endValue;
                break;
            default:
                break;
            }
        }
        mem.put(index, endValue);
    }

    private void setMemory2(Map<String, Long> mem, int index, long value, String mask) {
        String indexBinary = String.format("%36s", Long.toBinaryString(index)).replace(' ', '0');
        StringBuilder endInxed = new StringBuilder();
        int xCount = 0;
        for (int i = 0; i < 36; i++) {
            switch (mask.charAt(i)) {
            case '0':
                endInxed.append(indexBinary.charAt(i));
                break;
            case '1':
                endInxed.append('1');
                break;
            default:
                endInxed.append('X');
                xCount++;
                break;
            }
        }

        if (xCount == 0) {
            mem.put(endInxed.toString(), value);
        } else {
            int number = 1 << xCount;
            for (int i = 0; i < number; i++) {
                String adress = endInxed.toString();

                for (int j = xCount - 1; j >= 0; j--) {
                    boolean isBit = ((1 << j) & i) != 0;
                    adress = adress.replaceFirst("X", isBit ? "1" : "0");
                }
                mem.put(adress, value);
            }
        }
    }

}
