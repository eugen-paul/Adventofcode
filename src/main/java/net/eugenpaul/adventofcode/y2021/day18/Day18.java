package net.eugenpaul.adventofcode.y2021.day18;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day18 extends SolutionTemplate {

    @AllArgsConstructor
    private class Pair {
        private int level;
        private int value;
    }

    @Getter
    private long magnitude;
    @Getter
    private long largestMagnitude;

    public static void main(String[] args) {
        Day18 puzzle = new Day18();
        puzzle.doPuzzleFromFile("y2021/day18/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<List<Pair>> numbers = eventData.stream()//
                .map(this::read)//
                .collect(Collectors.toList());

        List<Pair> sum = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            sum = addAndReduce(sum, numbers.get(i));
        }

        magnitude = getMagnitude(sum);

        numbers = eventData.stream()//
                .map(this::read)//
                .collect(Collectors.toList());

        largestMagnitude = Integer.MIN_VALUE;
        for (int e1 = 0; e1 < numbers.size(); e1++) {
            for (int e2 = 0; e2 < numbers.size(); e2++) {
                if (e1 == e2) {
                    continue;
                }
                List<Pair> sumE1E2 = addAndReduce(numbers.get(e1), numbers.get(e2));
                largestMagnitude = Math.max(largestMagnitude, getMagnitude(sumE1E2));
            }
        }

        logger.log(Level.INFO, () -> "magnitude   : " + getMagnitude());
        logger.log(Level.INFO, () -> "largestMagnitude   : " + getLargestMagnitude());

        return true;
    }

    private List<Pair> read(String data) {
        List<Pair> response = new LinkedList<>();
        int currentLevel = -1;
        for (char c : data.toCharArray()) {
            int value = 0;
            switch (c) {
            case '[':
                currentLevel++;
                break;
            case ']':
                currentLevel--;
                break;
            case ',':
                break;
            default:
                value = c - '0';
                response.add(new Pair(currentLevel, value));
                break;
            }
        }
        return response;
    }

    private List<Pair> addAndReduce(List<Pair> a, List<Pair> b) {
        List<Pair> response = add(a, b);
        return reduce(response);
    }

    private List<Pair> add(List<Pair> a, List<Pair> b) {
        List<Pair> sum = new LinkedList<>();

        for (var pair : a) {
            sum.add(new Pair(pair.level + 1, pair.value));
        }
        for (var pair : b) {
            sum.add(new Pair(pair.level + 1, pair.value));
        }

        return sum;
    }

    private List<Pair> reduce(List<Pair> a) {
        boolean isDone = false;
        while (!isDone) {
            isDone = !explode(a) && !split(a);
        }
        return a;
    }

    private boolean explode(List<Pair> a) {

        boolean isLevel4 = a.stream().anyMatch(v -> v.level == 4);

        if (!isLevel4) {
            return false;
        }

        Pair left = null;
        Pair last = null;
        boolean first = true;
        var iterator = a.listIterator();
        while (iterator.hasNext()) {
            var current = iterator.next();
            if (current.level == 4 && last == null) {
                if (first) {
                    first = false;
                    if (left != null) {
                        left.value += current.value;
                    }
                    iterator.remove();
                } else {
                    last = current;
                    iterator.remove();
                    iterator.add(new Pair(3, 0));
                }
            } else if (first) {
                left = current;
            } else if (last != null) {
                current.value += last.value;
                break;
            }
        }

        return true;
    }

    private boolean split(List<Pair> a) {
        var iterator = a.listIterator();
        while (iterator.hasNext()) {
            var current = iterator.next();
            if (current.value >= 10) {
                int left = current.value / 2;
                int right = current.value - left;
                iterator.remove();
                iterator.add(new Pair(current.level + 1, left));
                iterator.add(new Pair(current.level + 1, right));
                return true;
            }
        }

        return false;
    }

    private int getMagnitude(List<Pair> a) {
        List<Pair> copy = new LinkedList<>(a);

        while (copy.size() > 1) {
            var iterator = copy.listIterator();
            int lastLevel = -1;
            int lastValue = -1;
            while (iterator.hasNext()) {
                var current = iterator.next();
                if (current.level != lastLevel) {
                    lastLevel = current.level;
                    lastValue = current.value;
                } else {
                    iterator.remove();
                    iterator.previous();
                    iterator.remove();
                    iterator.add(new Pair(lastLevel - 1, lastValue * 3 + current.value * 2));
                    // The calculation must be stopped after the first find, otherwise wrong pairs will be found.
                    break;
                }
            }
        }

        return copy.get(0).value;
    }
}
