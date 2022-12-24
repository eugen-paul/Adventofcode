package net.eugenpaul.adventofcode.y2022.day24;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.MathHelper;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.dijkstra.DijkstraGen;
import net.eugenpaul.adventofcode.helper.dijkstra.DijkstraStepGen;
import net.eugenpaul.adventofcode.helper.dijkstra.MazeGen;

public class Day24 extends SolutionTemplate {

    @AllArgsConstructor
    private class DStep implements DijkstraStepGen {
        private int roundNumber = 0;
        private SimplePos pos;
        private SimplePos endPos;

        @Override
        public String compHash() {
            if (pos.equals(endPos)) {
                return endPos.toString();
            }
            return roundNumber + pos.toString();
        }

        @Override
        public int getCost() {
            return 1;
        }
    }

    @AllArgsConstructor
    private class Maze implements MazeGen<DStep> {

        private Map<Integer, Set<SimplePos>> minuteToArea;
        private List<Bl> bl;
        private int maxX;
        private int maxY;

        @Override
        public List<DStep> getNextSteps(DStep from) {
            var round = (from.roundNumber + 1) % MathHelper.lcm(maxY, maxX);
            Set<SimplePos> currentArea = areaAtMinute(round);

            List<DStep> nextPos = new LinkedList<>();

            if (!currentArea.contains(from.pos)) {
                // can wait => add it to response
                nextPos.add(new DStep(round, from.pos.copy(), from.endPos));
            }

            var n = from.pos.getNeighbors(false);
            for (var nPos : n) {
                // next point is end point => add it to response
                if (nPos.equals(from.endPos)) {
                    nextPos.add(new DStep(round, nPos.copy(), from.endPos));
                }
                // next point is a wall => ignore it
                if (nPos.getX() < 1 || nPos.getY() < 1 || nPos.getX() > maxX || nPos.getY() > maxY) {
                    continue;
                }
                if (!currentArea.contains(nPos)) {
                    // next point is blizzard free point => add it to response
                    nextPos.add(new DStep(round, nPos.copy(), from.endPos));
                }
            }

            return nextPos;
        }

        private Set<SimplePos> areaAtMinute(int minute) {
            Set<SimplePos> response = minuteToArea.get(minute);
            if (response != null) {
                return response;
            }

            // move all blizzards and create new area from it
            response = bl.stream()//
                    .map(v -> v.pos.copy()//
                            .move(v.d, minute)//
                            .wrapAround(1, maxX, 1, maxY)//
                    )//
                    .collect(Collectors.toSet());

            minuteToArea.put(minute, response);
            return response;
        }

    }

    @AllArgsConstructor
    private class Bl {
        SimplePos pos;
        Direction d;
    }

    @Getter
    private long part1;
    @Getter
    private long part2;

    public static void main(String[] args) {
        Day24 puzzle = new Day24();
        puzzle.doPuzzleFromFile("y2022/day24/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Bl> bl = readBl(eventData);
        SimplePos start = new SimplePos(1, 0);
        SimplePos end = new SimplePos(eventData.get(0).length() - 2, eventData.size() - 1);
        Map<Integer, Set<SimplePos>> minuteToArea = new HashMap<>();

        DStep starStep = new DStep(0, start, end);
        DStep endStep = new DStep(0, end, end);

        Maze maze = new Maze(new HashMap<>(), bl, eventData.get(0).length() - 2, eventData.size() - 2);

        var steps = DijkstraGen.getSteps(maze, starStep, endStep);
        part1 = steps;

        starStep = new DStep(steps, end, start);
        endStep = new DStep(steps, start, start);
        maze = new Maze(minuteToArea, bl, eventData.get(0).length() - 2, eventData.size() - 2);
        steps += DijkstraGen.getSteps(maze, starStep, endStep);

        starStep = new DStep(steps, start, end);
        endStep = new DStep(steps, end, end);
        maze = new Maze(minuteToArea, bl, eventData.get(0).length() - 2, eventData.size() - 2);
        steps += DijkstraGen.getSteps(maze, starStep, endStep);

        part2 = steps;

        logger.log(Level.INFO, () -> "part1 : " + getPart1());
        logger.log(Level.INFO, () -> "part2 : " + getPart2());

        return true;
    }

    private List<Bl> readBl(List<String> eventData) {
        List<Bl> response = new LinkedList<>();
        int y = 0;
        for (String string : eventData) {
            int x = 0;
            for (char c : string.toCharArray()) {
                switch (c) {
                case '<', '>', '^', 'v':
                    response.add(//
                            new Bl(//
                                    new SimplePos(x, y), //
                                    Direction.fromArrow(c) //
                            )//
                    );
                    break;
                default:
                    break;
                }
                x++;
            }
            y++;
        }
        return response;
    }

}
