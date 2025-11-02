package net.eugenpaul.adventofcode.y2024.day6;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day6 extends SolutionTemplate {

    record PosAndDir(SimplePos pos, Direction dir) {
    }

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day6 puzzle = new Day6();
        puzzle.doPuzzleFromFile("y2024/day6/puzzle1.txt");
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

        SimplePos guardPos = StringConverter.posOfChar(eventData, '^');
        Direction guardDir = Direction.N;
        Map<SimplePos, Boolean> map = StringConverter.toBoolMap(eventData, '#');
        Set<SimplePos> visited = getVisited(guardPos, guardDir, map);

        response = visited.size();

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private Set<SimplePos> getVisited(SimplePos guardPos, Direction guardDir, Map<SimplePos, Boolean> map) {
        Set<SimplePos> visited = new HashSet<>();
        visited.add(guardPos);

        while (true) {
            SimplePos nextPos = guardPos.moveNew(guardDir);
            if (!map.containsKey(nextPos)) {
                break;
            }
            if (Boolean.TRUE.equals(map.get(nextPos))) {
                guardDir = guardDir.turnRight();
                continue;
            }
            guardPos = nextPos;
            visited.add(guardPos);
        }
        return visited;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;
        SimplePos guardPos = StringConverter.posOfChar(eventData, '^');
        Direction guardDir = Direction.N;
        Map<SimplePos, Boolean> map = StringConverter.toBoolMap(eventData, '#');
        Set<SimplePos> visited = getVisited(guardPos.copy(), guardDir, map);

        for (var point : visited) {
            if (point.equals(guardPos)) {
                continue;
            }
            map.put(point, true);
            if (isLoop(guardPos.copy(), guardDir, map)) {
                response++;
            }
            map.put(point, false);
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private boolean isLoop(SimplePos guardPos, Direction guardDir, Map<SimplePos, Boolean> map) {
        Set<PosAndDir> visited = new HashSet<>();
        visited.add(new PosAndDir(guardPos, guardDir));

        while (true) {
            SimplePos nextPos = guardPos.moveNew(guardDir);
            if (!map.containsKey(nextPos)) {
                return false;
            }
            if (Boolean.TRUE.equals(map.get(nextPos))) {
                guardDir = guardDir.turnRight();
                continue;
            }
            guardPos = nextPos;
            var posAndDir = new PosAndDir(guardPos, guardDir);
            if (visited.contains(posAndDir)) {
                return true;
            }
            visited.add(new PosAndDir(guardPos, guardDir));
        }
    }

}
