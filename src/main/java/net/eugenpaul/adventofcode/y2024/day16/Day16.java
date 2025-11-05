package net.eugenpaul.adventofcode.y2024.day16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day16 extends SolutionTemplate {

    private record Step(SimplePos pos, Direction d) {

    }

    private record StepCost(SimplePos pos, Direction d, long cost) {

    }

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day16 puzzle = new Day16();
        puzzle.doPuzzleFromFile("y2024/day16/puzzle1.txt");
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

        Map<SimplePos, Boolean> m = StringConverter.toBoolMap(eventData, '.', 'E', 'S');
        SimplePos start = StringConverter.posOfChar(eventData, 'S');
        SimplePos end = StringConverter.posOfChar(eventData, 'E');

        Map<Step, Long> bestCost = new HashMap<>();
        PriorityQueue<StepCost> toCheck = new PriorityQueue<>((a, b) -> Long.compare(a.cost, b.cost));
        toCheck.add(new StepCost(start, Direction.E, 0L));

        while (!toCheck.isEmpty()) {
            StepCost cur = toCheck.poll();

            if (cur.pos.equals(end)) {
                response = cur.cost;
                break;
            }

            if (bestCost.getOrDefault(new Step(cur.pos, cur.d), Long.MAX_VALUE) <= cur.cost) {
                continue;
            }

            bestCost.put(new Step(cur.pos, cur.d), cur.cost);

            Direction dir = cur.d;
            SimplePos nxt = cur.pos.moveNew(dir);
            checkAndDoStep(m, bestCost, toCheck, cur, dir, nxt, 1);

            Direction r = cur.d.turnRight();
            SimplePos rNxt = cur.pos;
            checkAndDoStep(m, bestCost, toCheck, cur, r, rNxt, 1000);

            Direction l = cur.d.turnLeft();
            SimplePos lNxt = cur.pos;
            checkAndDoStep(m, bestCost, toCheck, cur, l, lNxt, 1000);
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private void checkAndDoStep(Map<SimplePos, Boolean> m, Map<Step, Long> bestCost, PriorityQueue<StepCost> toCheck, StepCost cur, Direction dir,
            SimplePos nxt, long stepCost) {
        if (m.get(nxt).booleanValue()) {
            if (bestCost.getOrDefault(new Step(nxt, dir), Long.MAX_VALUE) > cur.cost + stepCost) {
                toCheck.add(new StepCost(nxt, dir, cur.cost + stepCost));
            } else {
                //
            }
        }
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        Map<SimplePos, Boolean> m = StringConverter.toBoolMap(eventData, '.', 'E', 'S');
        SimplePos start = StringConverter.posOfChar(eventData, 'S');
        SimplePos end = StringConverter.posOfChar(eventData, 'E');

        Map<Step, Long> bestCost = new HashMap<>();
        PriorityQueue<StepCost> toCheck = new PriorityQueue<>((a, b) -> Long.compare(a.cost, b.cost));
        toCheck.add(new StepCost(start, Direction.E, 0L));

        Map<StepCost, Set<SimplePos>> ways = new HashMap<>();
        ways.put(new StepCost(start, Direction.E, 0L), new HashSet<>());

        while (!toCheck.isEmpty()) {
            StepCost cur = toCheck.poll();

            if (cur.pos.equals(end)) {
                response = ways.get(cur).size();
                break;
            }

            if (bestCost.getOrDefault(new Step(cur.pos, cur.d), Long.MAX_VALUE) < cur.cost) {
                continue;
            }

            Set<SimplePos> myWay = ways.get(cur);

            Direction dir = cur.d;
            SimplePos nxt = cur.pos.moveNew(cur.d);
            SimplePos nxtCheck = cur.pos.moveNew(cur.d);
            checkAndDoStep2(m, bestCost, toCheck, ways, cur, myWay, dir, nxt, nxtCheck, 1);

            Direction r = cur.d.turnRight();
            SimplePos rNxt = cur.pos;
            SimplePos rNxtCheck = cur.pos.moveNew(r);
            checkAndDoStep2(m, bestCost, toCheck, ways, cur, myWay, r, rNxt, rNxtCheck, 1000);

            Direction l = cur.d.turnLeft();
            SimplePos lNxt = cur.pos;
            SimplePos lNxtCheck = cur.pos.moveNew(l);
            checkAndDoStep2(m, bestCost, toCheck, ways, cur, myWay, l, lNxt, lNxtCheck, 1000);
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private void checkAndDoStep2(Map<SimplePos, Boolean> m, Map<Step, Long> bestCost, PriorityQueue<StepCost> toCheck, Map<StepCost, Set<SimplePos>> ways,
            StepCost cur, Set<SimplePos> myWay, Direction dir, SimplePos nxt, SimplePos nxtCheck, long stepCost) {
        if (m.get(nxtCheck).booleanValue()) {
            var nxtStep = new StepCost(nxt, dir, cur.cost + stepCost);
            if (bestCost.getOrDefault(new Step(nxt, dir), Long.MAX_VALUE) > cur.cost + stepCost) {
                Set<SimplePos> newWay = new HashSet<>(myWay);
                newWay.add(nxt);
                ways.put(nxtStep, newWay);
                toCheck.add(nxtStep);
                bestCost.put(new Step(cur.pos, dir), cur.cost + stepCost);
            } else if (bestCost.getOrDefault(new Step(nxt, dir), Long.MAX_VALUE) == cur.cost + stepCost) {
                Set<SimplePos> otherWay = ways.get(nxtStep);
                otherWay.addAll(myWay);
            }
        }
    }

}
