package net.eugenpaul.adventofcode.y2024.day7;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day7 extends SolutionTemplate {

    record Data(Long result, List<Long> numbers) {

    }

    private enum Op {
        ADD, MULT, CON
    }

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day7 puzzle = new Day7();
        puzzle.doPuzzleFromFile("y2024/day7/puzzle1.txt");
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

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        List<Data> d = eventData.stream().map(this::comp).toList();

        response = d.stream().mapToLong(this::s1).sum();

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private Data comp(String in) {
        String[] splits = in.split(":");
        Long r = Long.parseLong(splits[0].trim());
        List<Long> n = StringConverter.toLongArrayList(splits[1].trim());
        return new Data(r, n);
    }

    private long s1(Data in) {
        if (rec1(in.numbers.get(0), in.result, 1, in.numbers, true) || rec1(in.numbers.get(0), in.result, 1, in.numbers, false)) {
            return in.result;
        }
        return 0;
    }

    private boolean rec1(long curr, long target, int pos, List<Long> numbers, boolean add) {
        if (pos == numbers.size()) {
            return curr == target;
        }
        if (curr > target) {
            return false;
        }
        long l = add ? curr + numbers.get(pos) : curr * numbers.get(pos);
        return rec1(l, target, pos + 1, numbers, true) || rec1(l, target, pos + 1, numbers, false);
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        List<Data> d = eventData.stream().map(this::comp).toList();

        response = d.stream().mapToLong(this::s2).sum();

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private long s2(Data in) {
        if (//
        rec2(in.numbers.get(0), in.result, 1, in.numbers, Op.ADD) //
                || rec2(in.numbers.get(0), in.result, 1, in.numbers, Op.MULT) //
                || rec2(in.numbers.get(0), in.result, 1, in.numbers, Op.CON) //
        ) {
            return in.result;
        }
        return 0;
    }

    private boolean rec2(long curr, long target, int pos, List<Long> numbers, Op op) {
        if (pos == numbers.size()) {
            return curr == target;
        }
        if (curr > target) {
            return false;
        }

        long l = switch (op) {
        case ADD -> curr + numbers.get(pos);
        case MULT -> curr * numbers.get(pos);
        case CON -> Long.parseLong(curr + "" + numbers.get(pos));
        };

        return rec2(l, target, pos + 1, numbers, Op.ADD) || rec2(l, target, pos + 1, numbers, Op.MULT)  || rec2(l, target, pos + 1, numbers, Op.CON);
    }

}
