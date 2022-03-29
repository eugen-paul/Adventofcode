package net.eugenpaul.adventofcode.y2017.day16;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day16 extends SolutionTemplate {

    @Setter
    private String input = "abcdefghijklmnop";

    @Getter
    private String endOrder;
    @Getter
    private String endOrderAfterBillionDances;

    public static void main(String[] args) {
        Day16 puzzle = new Day16();
        puzzle.doPuzzleFromFile("y2017/day16/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        String[] moves = eventData.split(",");
        char[] line = input.toCharArray();

        String begin = new String(line);
        doOneRound(moves, line);
        endOrder = new String(line);

        puzzle2FastSolution(moves, line, begin);
        endOrderAfterBillionDances = new String(line);

        logger.log(Level.INFO, () -> "endOrder : " + getEndOrder());
        logger.log(Level.INFO, () -> "endOrderAfterBillionDances : " + getEndOrderAfterBillionDances());

        return true;
    }

    private void puzzle2FastSolution(String[] moves, char[] line, String begin) {
        Map<String, Integer> cache = new HashMap<>();
        cache.put(begin, 0);

        int restSteps = 0;
        for (int i = 1; i < 1_000_000_000; i++) {
            String beg = new String(line);

            if (cache.containsKey(beg)) {
                int start = cache.get(beg);
                int length = i - start;
                restSteps = (1_000_000_000 - i) % length;
                break;
            } else {
                doOneRound(moves, line);
                cache.put(beg, i);
            }
        }

        for (int i = 0; i < restSteps; i++) {
            doOneRound(moves, line);
        }
    }

    private void doOneRound(String[] moves, char[] line) {
        for (String move : moves) {
            switch (move.charAt(0)) {
            case 's':
                doSpin(line, Integer.parseInt(move.substring(1)));
                break;
            case 'x':
                doExchange(line, //
                        Integer.parseInt(move.substring(1, move.indexOf("/"))), //
                        Integer.parseInt(move.substring(move.indexOf("/") + 1))//
                );
                break;
            case 'p':
                doExchange(line, //
                        move.charAt(1), //
                        move.charAt(3) //
                );
                break;
            default:
                throw new IllegalArgumentException("wrong move: " + move);
            }
        }
    }

    private void doSpin(char[] input, int value) {
        char[] tmp = input.clone();
        System.arraycopy(tmp, input.length - value, input, 0, value);
        System.arraycopy(tmp, 0, input, value, input.length - value);
    }

    private void doExchange(char[] input, int a, int b) {
        char tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    private void doExchange(char[] input, char a, char b) {
        for (int i = 0; i < input.length; i++) {
            if (input[i] == a) {
                input[i] = b;
            } else if (input[i] == b) {
                input[i] = a;
            }
        }
    }

    public void puzzle2SlowSolotion(String begin, char[] line, String[] moves) {
        Map<String, String> cache = new HashMap<>();
        cache.put(begin, endOrder);

        for (int i = 1; i < 1_000_000_000; i++) {
            String beg = new String(line);

            cache.compute(beg, (k, v) -> {
                if (v == null) {
                    doOneRound(moves, line);
                    return new String(line);
                }
                System.arraycopy(v.toCharArray(), 0, line, 0, line.length);
                return v;
            });
        }
        endOrderAfterBillionDances = new String(line);
    }

}
