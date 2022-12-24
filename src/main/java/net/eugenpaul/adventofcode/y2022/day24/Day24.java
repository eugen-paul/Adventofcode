package net.eugenpaul.adventofcode.y2022.day24;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;
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

        private List<Bl> bl;
        private int maxX;
        private int maxY;

        @Override
        public List<DStep> getNextSteps(DStep from) {
            Set<SimplePos> currentArea = areaAtMinute(from.roundNumber + 1);

            List<DStep> nextPos = new LinkedList<>();

            if (!currentArea.contains(from.pos)) {
                nextPos.add(new DStep(from.roundNumber + 1, from.pos.copy(), from.endPos));
            }

            var n = from.pos.getNeighbors(false);
            for (var nPos : n) {
                if (nPos.equals(from.endPos)) {
                    nextPos.add(new DStep(from.roundNumber + 1, nPos.copy(), from.endPos));
                }
                if (nPos.getX() <= 0 || nPos.getY() <= 0 || nPos.getX() >= maxX || nPos.getY() >= maxY) {
                    continue;
                }
                if (!currentArea.contains(nPos)) {
                    nextPos.add(new DStep(from.roundNumber + 1, nPos.copy(), from.endPos));
                }
            }

            return nextPos;
        }

        private Set<SimplePos> areaAtMinute(int minute) {
            Set<SimplePos> response = new HashSet<>();
            int moveH = minute % (maxX - 1);
            int moveV = minute % (maxY - 1);
            for (var pos : bl) {
                SimplePos newPos = pos.pos.copy();
                switch (pos.d) {
                case N, S:
                    for (int i = 0; i < moveV; i++) {
                        newPos.move(pos.d);
                        if (newPos.getY() <= 0) {
                            newPos.setY(newPos.getY() + maxY - 1);
                        }
                        if (newPos.getY() >= maxY) {
                            newPos.setY(newPos.getY() - (maxY - 1));
                        }
                    }
                    response.add(newPos);
                    break;
                case W, E:
                    for (int i = 0; i < moveH; i++) {
                        newPos.move(pos.d);
                        if (newPos.getX() <= 0) {
                            newPos.setX(newPos.getX() + maxX - 1);
                        }
                        if (newPos.getX() >= maxX) {
                            newPos.setX(newPos.getX() - (maxX - 1));
                        }
                    }
                    response.add(newPos);
                    break;
                default:
                    break;
                }
            }
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

        part1 = doPuzzle1(eventData);
        part2 = doPuzzle2(eventData, (int) part1);

        logger.log(Level.INFO, () -> "part1 : " + getPart1());
        logger.log(Level.INFO, () -> "part2 : " + getPart2());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {

        List<Bl> bl = readBl(eventData);
        SimplePos start = new SimplePos(1, 0);
        SimplePos end = new SimplePos(eventData.get(0).length() - 2, eventData.size() - 1);

        DStep starStep = new DStep(0, start, end);
        DStep endStep = new DStep(0, end, end);

        Maze maze = new Maze(bl, eventData.get(0).length() - 1, eventData.size() - 1);

        return DijkstraGen.getSteps(maze, starStep, endStep);
    }

    private long doPuzzle2(List<String> eventData, int steps1) {
        List<Bl> bl = readBl(eventData);
        SimplePos start = new SimplePos(1, 0);
        SimplePos end = new SimplePos(eventData.get(0).length() - 2, eventData.size() - 1);

        int steps = steps1;

        DStep starStep = new DStep(steps, end, start);
        DStep endStep = new DStep(steps, start, start);
        Maze maze = new Maze(bl, eventData.get(0).length() - 1, eventData.size() - 1);
        steps += DijkstraGen.getSteps(maze, starStep, endStep);

        starStep = new DStep(steps, start, end);
        endStep = new DStep(steps, end, end);
        maze = new Maze(bl, eventData.get(0).length() - 1, eventData.size() - 1);
        steps += DijkstraGen.getSteps(maze, starStep, endStep);

        return steps;
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
