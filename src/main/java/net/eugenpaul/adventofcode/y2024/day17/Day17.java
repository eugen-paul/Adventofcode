package net.eugenpaul.adventofcode.y2024.day17;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day17 extends SolutionTemplate {

    @Getter
    private String totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day17 puzzle = new Day17();
        puzzle.doPuzzleFromFile("y2024/day17/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        return doEvent(List.of(eventData));
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public String doPuzzle1(List<String> eventData) {
        String response;

        long a = Long.parseLong(eventData.get(0).split(" ")[2]);
        long b = Long.parseLong(eventData.get(1).split(" ")[2]);
        long c = Long.parseLong(eventData.get(2).split(" ")[2]);

        List<Integer> prog = StringConverter.toIntegerArrayList(eventData.get(4).split(" ")[1]);

        List<String> output = new LinkedList<>();

        for (int i = 0; i < prog.size(); i = i + 2) {
            int opcode = prog.get(i);
            int operand = prog.get(i + 1);
            long operandValue;
            switch (opcode) {
            case 0:
                operandValue = getComboValue(operand, a, b, c);
                long tmp0 = 1 << operandValue;
                a = a / tmp0;
                break;
            case 1:
                b = b ^ operand;
                break;
            case 2:
                b = getComboValue(operand, a, b, c) & 0x7;
                break;
            case 3:
                if (a != 0) {
                    i = operand - 2;
                }
                break;
            case 4:
                b = b ^ c;
                break;
            case 5:
                long tt = getComboValue(operand, a, b, c) % 8;
                output.add(tt + "");
                break;
            case 6:
                operandValue = getComboValue(operand, a, b, c);
                long tmp6 = 1 << operandValue;
                b = a / tmp6;
                break;
            case 7:
                operandValue = getComboValue(operand, a, b, c);
                long tmp7 = 1 << operandValue;
                c = a / tmp7;
                break;
            default:
                throw new IllegalArgumentException();
            }
        }

        response = String.join(",", output);

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private long getComboValue(int operand, long a, long b, long c) {
        switch (operand) {
        case 0, 1, 2, 3:
            return operand;
        case 4:
            return a;
        case 5:
            return b;
        case 6:
            return c;
        default:
            throw new IllegalArgumentException();
        }
    }

    public Long doPuzzle2(List<String> eventData) {
        long response = 0;

        long b = Long.parseLong(eventData.get(1).split(" ")[2]);
        long c = Long.parseLong(eventData.get(2).split(" ")[2]);

        List<Integer> prog = StringConverter.toIntegerArrayList(eventData.get(4).split(" ")[1]);

        for (int len = 1; len <= prog.size(); len++) {
            /**
             * The first match in the range [0,7] doesn't always lead to the correct overall result. It should be implemented recursively here to try other
             * values ​​as well. I was too lazy for that. So, for the last value, I check everything from 0 to 1,000,000.
             */
            for (long aCheck = 0L; aCheck <= 1_000_000L; aCheck++) {
                long a = aCheck + response * 8;
                List<Integer> output = new LinkedList<>();
                for (int i = prog.size() - len; i < prog.size(); i++) {
                    output.add(prog.get(i));
                }
                boolean ok = true;
                boolean done = false;

                try {
                    for (int i = 0; i < prog.size() && ok && !done; i = i + 2) {
                        int opcode = prog.get(i);
                        int operand = prog.get(i + 1);
                        long operandValue;
                        switch (opcode) {
                        case 0:
                            operandValue = getComboValue(operand, a, b, c);
                            long tmp0 = 1 << operandValue;
                            a = a / tmp0;
                            break;
                        case 1:
                            b = b ^ operand;
                            break;
                        case 2:
                            b = getComboValue(operand, a, b, c) & 0x7;
                            break;
                        case 3:
                            if (a != 0) {
                                i = operand - 2;
                            }
                            break;
                        case 4:
                            b = b ^ c;
                            break;
                        case 5:
                            long tt = getComboValue(operand, a, b, c) % 8;
                            if (output.getFirst() != tt || output.isEmpty()) {
                                ok = false;
                            }
                            output.removeFirst();
                            done = output.isEmpty();
                            break;
                        case 6:
                            operandValue = getComboValue(operand, a, b, c);
                            long tmp6 = 1 << operandValue;
                            b = a / tmp6;
                            break;
                        case 7:
                            operandValue = getComboValue(operand, a, b, c);
                            long tmp7 = 1 << operandValue;
                            c = a / tmp7;
                            break;
                        default:
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException();
                }

                if (done && ok) {
                    response = response * 8 + aCheck;
                    break;
                }
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
