package net.eugenpaul.adventofcode.y2025.day7;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static net.eugenpaul.adventofcode.helper.StringConverter.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day7 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day7 puzzle = new Day7();
        puzzle.doPuzzleFromFile("y2025/day7/puzzle1.txt");
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

        Map<SimplePos, Character> m = StringConverter.toMap(eventData);
        var start = StringConverter.posOfChar(eventData, 'S');

        HashSet<SimplePos> seen = new HashSet<>();
        LinkedList<SimplePos> nxt = new LinkedList<>();
        var d = Direction.S;
        nxt.add(start.moveNew(Direction.S));

        while (!nxt.isEmpty()) {
            var cur = nxt.pollFirst();
            if (seen.contains(cur) || !m.containsKey(cur)) {
                continue;
            }
            seen.add(cur);
            if (m.get(cur) == '^') {
                response++;
                nxt.add(cur.moveNew(d).moveNew(Direction.E));
                nxt.add(cur.moveNew(d).moveNew(Direction.W));
            } else {
                nxt.add(cur.moveNew(d));
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        Map<SimplePos, Character> m = StringConverter.toMap(eventData);
        var start = StringConverter.posOfChar(eventData, 'S');

        response = 1 + step(m, start.moveNew(Direction.S), new HashMap<>());

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private long step(Map<SimplePos, Character> m, SimplePos pos, Map<SimplePos, Long> cache) {
        if (cache.containsKey(pos)) {
            return cache.get(pos);
        }

        if (!m.containsKey(pos)) {
            return 0L;
        }

        long response = 0L;
        if (m.get(pos) == '^') {
            response += step(m, pos.moveNew(Direction.S).moveNew(Direction.E), cache);
            response += step(m, pos.moveNew(Direction.S).moveNew(Direction.W), cache);
            response++;
        } else {
            response += step(m, pos.moveNew(Direction.S), cache);
        }

        cache.put(pos, response);

        return response;
    }

}
