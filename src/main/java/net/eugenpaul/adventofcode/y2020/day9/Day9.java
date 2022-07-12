package net.eugenpaul.adventofcode.y2020.day9;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day9 extends SolutionTemplate {

    @Getter
    private long number;
    @Getter
    private long number2;

    @Setter
    private int preamble = 25;

    public static void main(String[] args) {
        Day9 puzzle = new Day9();
        puzzle.doPuzzleFromFile("y2020/day9/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        LinkedList<Long> numbers = eventData.stream()//
                .mapToLong(Long::parseLong).boxed()//
                .collect(Collectors.toCollection(LinkedList::new));

        number = doPuzzle1(new LinkedList<>(numbers));
        number2 = doPuzzle2(new ArrayList<>(numbers), number);

        logger.log(Level.INFO, () -> "number  : " + getNumber());
        logger.log(Level.INFO, () -> "number2 : " + getNumber2());

        return true;
    }

    private long doPuzzle1(LinkedList<Long> numbers) {
        LinkedList<Long> preambleList = new LinkedList<>();
        for (int i = 0; i < preamble; i++) {
            preambleList.add(numbers.removeFirst());
        }

        long sum = numbers.removeFirst();
        Long firstNumber = getFirstNumberOfSum(preambleList, sum);

        while (firstNumber != null) {
            preambleList.add(sum);
            preambleList.removeFirst();

            sum = numbers.removeFirst();
            firstNumber = getFirstNumberOfSum(preambleList, sum);
        }

        return sum;
    }

    private Long getFirstNumberOfSum(List<Long> preambleList, long sum) {
        for (int first = 0; first < preambleList.size() - 1; first++) {
            for (int second = first + 1; second < preambleList.size(); second++) {
                Long firstNumber = preambleList.get(first);
                Long secondNumber = preambleList.get(second);
                if (sum == firstNumber + secondNumber) {
                    return firstNumber;
                }
            }
        }
        return null;
    }

    private long doPuzzle2(List<Long> numbers, long sum) {

        int from = 0;
        int to = 1;
        long sumOfSet = getSum(from, to, numbers);
        while (sum != sumOfSet) {
            if (sumOfSet < sum) {
                to++;
            } else {
                from++;
            }
            sumOfSet = getSum(from, to, numbers);
        }

        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;

        for (int i = from; i <= to; i++) {
            min = Math.min(min, numbers.get(i));
            max = Math.max(max, numbers.get(i));
        }

        return min + max;
    }

    private long getSum(int from, int to, List<Long> numbers) {
        long response = 0;
        for (int i = from; i <= to; i++) {
            response += numbers.get(i);
        }
        return response;
    }

}
