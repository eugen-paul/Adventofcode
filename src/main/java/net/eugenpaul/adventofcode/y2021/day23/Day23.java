package net.eugenpaul.adventofcode.y2021.day23;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;
import net.eugenpaul.adventofcode.helper.dijkstra.Dijkstra;
import net.eugenpaul.adventofcode.helper.dijkstra.DijkstraGen;
import net.eugenpaul.adventofcode.helper.dijkstra.DijkstraStepGen;
import net.eugenpaul.adventofcode.helper.dijkstra.Maze;
import net.eugenpaul.adventofcode.helper.dijkstra.MazeGen;

public class Day23 extends SolutionTemplate {

    private class Step implements DijkstraStepGen {

        private int cost = 0;
        private final int depth;

        private Character[][] stepArea;

        public Step(int depth) {
            this.depth = depth;
            stepArea = new Character[13][this.depth];
            for (int x = 0; x < 13; x++) {
                Arrays.fill(stepArea[x], ' ');
            }
        }

        public Step(Step b) {
            this.depth = b.depth;
            stepArea = new Character[13][this.depth];
            for (int x = 0; x < 13; x++) {
                System.arraycopy(b.stepArea[x], 0, stepArea[x], 0, this.depth);
            }
        }

        @Override
        public String compHash() {

            StringBuilder response = new StringBuilder(70);

            for (int x = 0; x < 13; x++) {
                for (int y = 0; y < this.depth; y++) {
                    response.append(stepArea[x][y]);
                }
            }

            return response.toString();
        }

        @Override
        public int getCost() {
            return cost;
        }

        public void print() {
            for (int y = 0; y < this.depth; y++) {
                for (int x = 0; x < 13; x++) {
                    System.out.print(stepArea[x][y]);
                }
                System.out.println("");
            }
        }

    }

    @AllArgsConstructor
    private class SimpleMaze implements Maze {
        private Set<SimplePos> area;

        @Override
        public List<SimplePos> getNextSteps(SimplePos from) {

            List<SimplePos> response = new LinkedList<>();

            for (Direction direction : Direction.values()) {
                if (area.contains(from.moveNew(direction))) {
                    response.add(from.moveNew(direction));
                }
            }

            return response;
        }

    }

    private class BurrowMaze implements MazeGen<Step> {

        private Set<SimplePos> area;

        private List<SimplePos> hallwayStops = List.of(//
                new SimplePos(1, 1), //
                new SimplePos(2, 1), //
                new SimplePos(4, 1), //
                new SimplePos(6, 1), //
                new SimplePos(8, 1), //
                new SimplePos(10, 1), //
                new SimplePos(11, 1) //
        );

        @Override
        public List<Step> getNextSteps(Step from) {
            Set<SimplePos> stepArea = new HashSet<>(area);
            SimpleMaze simple = new SimpleMaze(stepArea);

            Map<SimplePos, Character> fromRoomMap = new HashMap<>();
            Map<SimplePos, Character> fromHallwayMap = new HashMap<>();
            initStepData(from, stepArea, fromRoomMap, fromHallwayMap);

            List<Step> response = new LinkedList<>();

            for (var entry : fromRoomMap.entrySet()) {
                for (var pos : hallwayStops) {
                    Dijkstra d = new Dijkstra();
                    Integer steps = d.getSteps(simple, entry.getKey(), pos);
                    if (steps != null) {
                        Step step = new Step(from);
                        setCost(step, entry, steps);
                        step.stepArea[entry.getKey().getX()][entry.getKey().getY()] = ' ';
                        step.stepArea[pos.getX()][pos.getY()] = entry.getValue();
                        response.add(step);
                    }
                }

            }

            for (var entry : fromHallwayMap.entrySet()) {
                List<SimplePos> roomStops = getToRoomStops(from, entry.getKey());
                for (var pos : roomStops) {
                    Dijkstra d = new Dijkstra();
                    Integer steps = d.getSteps(simple, entry.getKey(), pos);
                    if (steps != null) {
                        Step step = new Step(from);
                        setCost(step, entry, steps);
                        step.stepArea[entry.getKey().getX()][entry.getKey().getY()] = ' ';
                        step.stepArea[pos.getX()][pos.getY()] = entry.getValue();
                        response.add(step);
                    }
                }
            }

            return response;
        }

        private List<SimplePos> getToRoomStops(Step currentStep, SimplePos fromPos) {
            Character c = currentStep.stepArea[fromPos.getX()][fromPos.getY()];
            if (c == 'c') {
                return Collections.emptyList();
            }

            int targetX;

            switch (c) {
            case 'A':
                targetX = 3;
                break;
            case 'B':
                targetX = 5;
                break;
            case 'C':
                targetX = 7;
                break;
            case 'D':
                targetX = 9;
                break;
            default:
                throw new IllegalArgumentException();
            }

            if (fromPos.getX() == targetX) {
                return Collections.emptyList();
            }

            List<SimplePos> response = new LinkedList<>();

            if (currentStep.depth == 5) {
                if (currentStep.stepArea[targetX][2] == ' ' && currentStep.stepArea[targetX][3].charValue() == c.charValue()) {
                    response.add(new SimplePos(targetX, 2));
                } else if (currentStep.stepArea[targetX][2] == ' ' && currentStep.stepArea[targetX][3] == ' ') {
                    response.add(new SimplePos(targetX, 3));
                }
            } else {
                if (currentStep.stepArea[targetX][2] == ' ' //
                        && currentStep.stepArea[targetX][3].charValue() == c.charValue()//
                        && currentStep.stepArea[targetX][4].charValue() == c.charValue()//
                        && currentStep.stepArea[targetX][5].charValue() == c.charValue()//
                ) {
                    response.add(new SimplePos(targetX, 2));
                } else if (currentStep.stepArea[targetX][2] == ' ' //
                        && currentStep.stepArea[targetX][3] == ' ' //
                        && currentStep.stepArea[targetX][4].charValue() == c.charValue()//
                        && currentStep.stepArea[targetX][5].charValue() == c.charValue()//
                ) {
                    response.add(new SimplePos(targetX, 3));
                } else if (currentStep.stepArea[targetX][2] == ' ' //
                        && currentStep.stepArea[targetX][3] == ' ' //
                        && currentStep.stepArea[targetX][4] == ' ' //
                        && currentStep.stepArea[targetX][5].charValue() == c.charValue()//
                ) {
                    response.add(new SimplePos(targetX, 4));
                } else if (currentStep.stepArea[targetX][2] == ' ' //
                        && currentStep.stepArea[targetX][3] == ' ' //
                        && currentStep.stepArea[targetX][4] == ' ' //
                        && currentStep.stepArea[targetX][5] == ' ' //
                ) {
                    response.add(new SimplePos(targetX, 5));
                }
            }

            return response;
        }

        private void setCost(Step step, Entry<SimplePos, Character> entry, Integer steps) {
            switch (entry.getValue()) {
            case 'A':
                step.cost = steps;
                break;
            case 'B':
                step.cost = steps * 10;
                break;
            case 'C':
                step.cost = steps * 100;
                break;
            case 'D':
                step.cost = steps * 1000;
                break;
            default:
                throw new IllegalArgumentException();
            }
        }

        private void initStepData(Step from, Set<SimplePos> stepArea, Map<SimplePos, Character> fromRoomMap, Map<SimplePos, Character> fromHallwayMap) {
            // hallway
            if (from.stepArea[1][1] != ' ') {
                fromHallwayMap.put(new SimplePos(1, 1), from.stepArea[1][1]);
                stepArea.remove(new SimplePos(1, 1));
            }
            if (from.stepArea[2][1] != ' ') {
                fromHallwayMap.put(new SimplePos(2, 1), from.stepArea[2][1]);
                stepArea.remove(new SimplePos(2, 1));
            }
            if (from.stepArea[4][1] != ' ') {
                fromHallwayMap.put(new SimplePos(4, 1), from.stepArea[4][1]);
                stepArea.remove(new SimplePos(4, 1));
            }
            if (from.stepArea[6][1] != ' ') {
                fromHallwayMap.put(new SimplePos(6, 1), from.stepArea[6][1]);
                stepArea.remove(new SimplePos(6, 1));
            }
            if (from.stepArea[8][1] != ' ') {
                fromHallwayMap.put(new SimplePos(8, 1), from.stepArea[8][1]);
                stepArea.remove(new SimplePos(8, 1));
            }
            if (from.stepArea[10][1] != ' ') {
                fromHallwayMap.put(new SimplePos(10, 1), from.stepArea[10][1]);
                stepArea.remove(new SimplePos(10, 1));
            }
            if (from.stepArea[11][1] != ' ') {
                fromHallwayMap.put(new SimplePos(11, 1), from.stepArea[11][1]);
                stepArea.remove(new SimplePos(11, 1));
            }

            // rooms
            setFromRoomSteps(from, stepArea, fromRoomMap, 3, 'A');
            setFromRoomSteps(from, stepArea, fromRoomMap, 5, 'B');
            setFromRoomSteps(from, stepArea, fromRoomMap, 7, 'C');
            setFromRoomSteps(from, stepArea, fromRoomMap, 9, 'D');
        }

        private void setFromRoomSteps(Step from, Set<SimplePos> stepArea, Map<SimplePos, Character> fromRoomMap, int x, char amphipod) {
            if (from.depth == 5) {
                if (from.stepArea[x][2] != ' ' && (from.stepArea[x][2] != amphipod || from.stepArea[x][3] != amphipod)) {
                    fromRoomMap.put(new SimplePos(x, 2), from.stepArea[x][2]);
                    stepArea.remove(new SimplePos(x, 2));
                }
                if (from.stepArea[x][3] != ' ' && (from.stepArea[x][3] != amphipod && from.stepArea[x][2] == ' ')) {
                    fromRoomMap.put(new SimplePos(x, 3), from.stepArea[x][3]);
                    stepArea.remove(new SimplePos(x, 3));
                }
            } else {
                if (from.stepArea[x][2] != ' ' //
                        && (from.stepArea[x][2] != amphipod || from.stepArea[x][3] != amphipod || from.stepArea[x][4] != amphipod
                                || from.stepArea[x][5] != amphipod)) {
                    fromRoomMap.put(new SimplePos(x, 2), from.stepArea[x][2]);
                    stepArea.remove(new SimplePos(x, 2));
                }
                if (from.stepArea[x][3] != ' ' && from.stepArea[x][2] == ' '
                        && (from.stepArea[x][3] != amphipod || from.stepArea[x][4] != amphipod || from.stepArea[x][5] != amphipod)) {
                    fromRoomMap.put(new SimplePos(x, 3), from.stepArea[x][3]);
                    stepArea.remove(new SimplePos(x, 3));
                }
                if (from.stepArea[x][4] != ' ' && from.stepArea[x][3] == ' ' && from.stepArea[x][2] == ' '
                        && (from.stepArea[x][4] != amphipod || from.stepArea[x][5] != amphipod)) {
                    fromRoomMap.put(new SimplePos(x, 4), from.stepArea[x][4]);
                    stepArea.remove(new SimplePos(x, 4));
                }
                if (from.stepArea[x][5] != ' ' && from.stepArea[x][4] == ' ' && from.stepArea[x][3] == ' ' && from.stepArea[x][2] == ' '
                        && from.stepArea[x][5] != amphipod) {
                    fromRoomMap.put(new SimplePos(x, 5), from.stepArea[x][5]);
                    stepArea.remove(new SimplePos(x, 5));
                }
            }
        }

    }

    @Getter
    private long totalEnergy;
    @Getter
    private long totalEnergy2;

    public static void main(String[] args) {
        Day23 puzzle = new Day23();
        puzzle.doPuzzleFromFile("y2021/day23/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        totalEnergy = doPuzzle1(eventData);
        totalEnergy2 = doPuzzle2(eventData);
        logger.log(Level.INFO, () -> "totalEnergy  : " + getTotalEnergy());
        logger.log(Level.INFO, () -> "totalEnergy2 : " + getTotalEnergy2());

        return true;
    }

    private int doPuzzle1(List<String> eventData) {
        Set<SimplePos> area = StringConverter.toSet(eventData, 'A', 'B', 'C', 'D', '.');

        BurrowMaze maze = new BurrowMaze();
        maze.area = area;

        Step from = new Step(5);
        from.stepArea[3][2] = eventData.get(2).charAt(3);
        from.stepArea[3][3] = eventData.get(3).charAt(3);
        from.stepArea[5][2] = eventData.get(2).charAt(5);
        from.stepArea[5][3] = eventData.get(3).charAt(5);
        from.stepArea[7][2] = eventData.get(2).charAt(7);
        from.stepArea[7][3] = eventData.get(3).charAt(7);
        from.stepArea[9][2] = eventData.get(2).charAt(9);
        from.stepArea[9][3] = eventData.get(3).charAt(9);

        Step to = new Step(5);
        to.stepArea[3][2] = 'A';
        to.stepArea[3][3] = 'A';
        to.stepArea[5][2] = 'B';
        to.stepArea[5][3] = 'B';
        to.stepArea[7][2] = 'C';
        to.stepArea[7][3] = 'C';
        to.stepArea[9][2] = 'D';
        to.stepArea[9][3] = 'D';

        return DijkstraGen.getSteps(maze, from, to);
    }

    private Integer doPuzzle2(List<String> eventData) {
        List<String> newEventData = new LinkedList<>();
        newEventData.add(eventData.get(0));
        newEventData.add(eventData.get(1));
        newEventData.add(eventData.get(2));

        newEventData.add("  #D#C#B#A#  ");
        newEventData.add("  #D#B#A#C#  ");

        newEventData.add(eventData.get(3));
        newEventData.add(eventData.get(4));

        Set<SimplePos> area = StringConverter.toSet(newEventData, 'A', 'B', 'C', 'D', '.');

        BurrowMaze maze = new BurrowMaze();
        maze.area = area;

        Step from = new Step(7);
        for (int x = 3; x <= 9; x = x + 2) {
            for (int y = 2; y <= 5; y++) {
                from.stepArea[x][y] = newEventData.get(y).charAt(x);
            }
        }

        Step to = new Step(7);
        for (int y = 2; y <= 5; y++) {
            to.stepArea[3][y] = 'A';
        }
        for (int y = 2; y <= 5; y++) {
            to.stepArea[5][y] = 'B';
        }
        for (int y = 2; y <= 5; y++) {
            to.stepArea[7][y] = 'C';
        }
        for (int y = 2; y <= 5; y++) {
            to.stepArea[9][y] = 'D';
        }

        return DijkstraGen.getSteps(maze, from, to);
    }

}
