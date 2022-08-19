package net.eugenpaul.adventofcode.y2021.day10;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day10 extends SolutionTemplate {

    @Getter
    private long score;
    @Getter
    private long middleScore;

    public static void main(String[] args) {
        Day10 puzzle = new Day10();
        puzzle.doPuzzleFromFile("y2021/day10/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        score = eventData.stream().mapToLong(this::getCorruptedScore).sum();

        var incompleteList = eventData.stream().filter(v -> getCorruptedScore(v) == 0).collect(Collectors.toList());

        middleScore = computeMiddleScore(incompleteList);

        logger.log(Level.INFO, () -> "score  : " + getScore());
        logger.log(Level.INFO, () -> "middleScore  : " + getMiddleScore());

        return true;
    }

    private int getCorruptedScore(String data) {
        LinkedList<Character> expects = new LinkedList<>();

        for (var c : data.toCharArray()) {
            if (isOk(expects, c)) {
                if (isOpen(c)) {
                    expects.addLast(c);
                } else {
                    expects.removeLast();
                }
            } else {
                int response;
                switch (c) {
                case ')':
                    response = 3;
                    break;
                case ']':
                    response = 57;
                    break;
                case '}':
                    response = 1197;
                    break;
                case '>':
                    response = 25137;
                    break;
                default:
                    throw new IllegalArgumentException(data + " " + c);
                }
                return response;
            }
        }

        return 0;
    }

    private boolean isOpen(char c) {
        return c == '(' || c == '{' || c == '[' || c == '<';
    }

    private boolean isPair(char a, char b) {
        return (a == '(' && b == ')') //
                || (a == '{' && b == '}') //
                || (a == '[' && b == ']') //
                || (a == '<' && b == '>');
    }

    private boolean isOk(LinkedList<Character> expects, char c) {
        if (isOpen(c)) {
            return true;
        }

        return isPair(expects.getLast(), c);
    }

    private long computeMiddleScore(List<String> incompleteLines) {
        List<Long> scores = new LinkedList<>();

        for (String data : incompleteLines) {
            LinkedList<Character> expects = getExpextedChars(data);

            scores.add(getIncompleteScore(expects));
        }

        scores.sort(Long::compare);

        return scores.get(scores.size() / 2);
    }

    private LinkedList<Character> getExpextedChars(String data) {
        LinkedList<Character> expects = new LinkedList<>();

        for (var c : data.toCharArray()) {
            if (isOpen(c)) {
                expects.addLast(c);
            } else {
                expects.removeLast();
            }
        }
        return expects;
    }

    private long getIncompleteScore(LinkedList<Character> expects) {
        long response = 0L;

        var iterator = expects.descendingIterator();
        while (iterator.hasNext()) {
            var c = iterator.next();
            switch (c) {
            case '(':
                response = response * 5L + 1L;
                break;
            case '[':
                response = response * 5L + 2L;
                break;
            case '{':
                response = response * 5L + 3L;
                break;
            case '<':
                response = response * 5L + 4L;
                break;
            default:
                throw new IllegalArgumentException();
            }
        }

        return response;
    }
}
