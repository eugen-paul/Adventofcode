package net.eugenpaul.adventofcode.y2020.day13;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MathHelper;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day13 extends SolutionTemplate {

    @Getter
    private int solution1;
    @Getter
    private long solution2;

    public static void main(String[] args) {
        Day13 puzzle = new Day13();
        puzzle.doPuzzleFromFile("y2020/day13/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        solution1 = doPuzzle1(eventData);
        solution2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "solution1   : " + getSolution1());
        logger.log(Level.INFO, () -> "solution2   : " + getSolution2());

        return true;
    }

    private int doPuzzle1(List<String> eventData) {
        var depTime = getDepartTime(eventData);
        var busIdList = getBusIds(eventData);

        int response = -1;

        int minWaitTime = Integer.MAX_VALUE;
        for (var busId : busIdList) {
            int waitTime = busId - depTime % busId;
            if (minWaitTime > waitTime) {
                minWaitTime = waitTime;
                response = minWaitTime * busId;
            }
        }

        return response;
    }

    private long doPuzzle2(List<String> eventData) {
        long response = 0L;

        String[] ids = eventData.get(1).split(",");

        Integer[] busIds = new Integer[ids.length];
        for (int i = 0; i < busIds.length; i++) {
            try {
                busIds[i] = Integer.parseInt(ids[i]);
            } catch (Exception e) {
                busIds[i] = null;
            }
        }

        long a = 0;
        long step = busIds[0];
        for (int i = 1; i < busIds.length; i++) {
            if (busIds[i] == null) {
                continue;
            }
            response = getDelta(a, step, busIds[i], i);
            a = response;
            step = MathHelper.lcm(step, busIds[i]);
        }

        return response;
    }

    private long getDelta(long a, long step, long b, long delta) {
        for (int i = 1; i < b; i++) {
            if (((a + step * i) + delta) % b == 0) {
                return a + step * i;
            }
        }
        throw new IllegalArgumentException();
    }

    private int getDepartTime(List<String> eventData) {
        return Integer.parseInt(eventData.get(0));
    }

    private List<Integer> getBusIds(List<String> eventData) {
        return Stream.of(eventData.get(1).split(","))//
                .filter(v -> !v.equals("x"))//
                .map(Integer::parseInt)//
                .collect(Collectors.toList());
    }

}
