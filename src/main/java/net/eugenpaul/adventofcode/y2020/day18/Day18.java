package net.eugenpaul.adventofcode.y2020.day18;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day18 extends SolutionTemplate {

    private enum Operator {
        MULT, SUM, NONE;
    }

    @Getter
    private long sum;
    @Getter
    private long sum2;

    public static void main(String[] args) {
        Day18 puzzle = new Day18();
        puzzle.doPuzzleFromFile("y2020/day18/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        sum = eventData.stream().mapToLong(v -> getResult(v, 0, v.length())).sum();
        sum2 = eventData.stream().mapToLong(v -> getResult2(v, 0, v.length())).sum();

        logger.log(Level.INFO, () -> "sum   : " + getSum());
        logger.log(Level.INFO, () -> "sum2  : " + getSum2());

        return true;
    }

    private long getResult(String data, int from, int to) {

        long response = 0;

        StringBuilder input = new StringBuilder();
        Operator operator = Operator.NONE;
        int pos = from;

        while (pos < to) {
            switch (data.charAt(pos)) {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
                input.append(data.charAt(pos));
                break;
            case '+':
                operator = Operator.SUM;
                break;
            case '*':
                operator = Operator.MULT;
                break;
            case '(':
                int end = getClosing(data, pos);
                input.append(getResult(data, pos + 1, end));
                pos = end;
                break;
            case ' ':
                if (input.length() > 0) {
                    switch (operator) {
                    case NONE:
                        response = Integer.parseInt(input.toString());
                        break;
                    case MULT:
                        response *= Integer.parseInt(input.toString());
                        break;
                    case SUM:
                        response += Integer.parseInt(input.toString());
                        break;
                    default:
                        break;
                    }
                    input = new StringBuilder();
                }
                break;
            default:
                throw new IllegalArgumentException("pos: " + pos);
            }
            pos++;
        }

        if (input.length() > 0) {
            switch (operator) {
            case NONE:
                response = Integer.parseInt(input.toString());
                break;
            case MULT:
                response *= Integer.parseInt(input.toString());
                break;
            case SUM:
                response += Integer.parseInt(input.toString());
                break;
            default:
                break;
            }
        }

        return response;
    }

    private long getResult2(String data, int from, int to) {

        long response = 0;

        List<Long> multOperators = new LinkedList<>();

        StringBuilder input = new StringBuilder();
        Operator operator = Operator.NONE;

        int pos = from;
        while (pos < to) {
            switch (data.charAt(pos)) {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
                input.append(data.charAt(pos));
                break;
            case '+':
                operator = Operator.SUM;
                break;
            case '*':
                operator = Operator.MULT;
                break;
            case '(':
                int end = getClosing(data, pos);
                input.append(getResult2(data, pos + 1, end));
                pos = end;
                break;
            case ' ':
                if (input.length() > 0) {
                    switch (operator) {
                    case NONE:
                        response = Long.parseLong(input.toString());
                        break;
                    case MULT:
                        multOperators.add(response);
                        response = Long.parseLong(input.toString());
                        break;
                    case SUM:
                        response += Long.parseLong(input.toString());
                        break;
                    default:
                        break;
                    }
                    input = new StringBuilder();
                }
                break;
            default:
                throw new IllegalArgumentException("pos: " + pos);
            }
            pos++;
        }

        if (input.length() > 0) {
            switch (operator) {
            case MULT:
                multOperators.add(response);
                multOperators.add(Long.parseLong(input.toString()));
                break;
            case SUM:
                response += Long.parseLong(input.toString());
                multOperators.add(response);
                break;
            default:
                break;
            }
        }

        return multOperators.stream().mapToLong(v -> v).reduce(1, (a, b) -> a * b);
    }

    private int getClosing(String data, int from) {
        int counter = 0;
        for (int i = from; i < data.length(); i++) {
            switch (data.charAt(i)) {
            case '(':
                counter++;
                break;
            case ')':
                counter--;
                if (counter == 0) {
                    return i;
                }
                break;
            default:
                break;
            }
        }
        throw new IllegalArgumentException("data: from " + from);
    }

}
