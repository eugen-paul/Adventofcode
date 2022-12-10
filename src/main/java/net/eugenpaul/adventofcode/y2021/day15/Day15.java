package net.eugenpaul.adventofcode.y2021.day15;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.dijkstra.DijkstraStepGen;
import net.eugenpaul.adventofcode.helper.dijkstra.DijkstraGen;
import net.eugenpaul.adventofcode.helper.dijkstra.MazeGen;

public class Day15 extends SolutionTemplate {

    @AllArgsConstructor
    private class MyStep implements DijkstraStepGen {
        @Getter
        private int cost;
        private SimplePos pos;

        @Override
        public String compHash() {
            return pos.toString();
        }
    }

    @AllArgsConstructor
    private class MyMaze implements MazeGen<MyStep> {

        private Map<SimplePos, Integer> area;

        @Override
        public List<MyStep> getNextSteps(MyStep from) {
            List<MyStep> response = new LinkedList<>();

            List<SimplePos> neighbors = from.pos.getNeighbors(false);

            for (var neighbor : neighbors) {
                if (area.containsKey(neighbor)) {
                    response.add(new MyStep(area.get(neighbor), neighbor));
                }
            }

            return response;
        }
    }

    @Getter
    private long lowestRisk;
    @Getter
    private long lowestRisk2;

    public static void main(String[] args) {
        Day15 puzzle = new Day15();
        puzzle.doPuzzleFromFile("y2021/day15/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        Map<SimplePos, Integer> area = MapOfSimplePos.initMapOfDigits(eventData);

        MyMaze maze = new MyMaze(area);

        lowestRisk = DijkstraGen.getSteps(//
                maze, //
                new MyStep(0, new SimplePos(0, 0)), //
                new MyStep(0, new SimplePos(eventData.get(0).length() - 1, eventData.size() - 1)) //
        );

        Map<SimplePos, Integer> fullArea = getFullArea(area, eventData.get(0).length(), eventData.size());

        MyMaze fullMaze = new MyMaze(fullArea);
        lowestRisk2 = DijkstraGen.getSteps(//
                fullMaze, //
                new MyStep(0, new SimplePos(0, 0)), //
                new MyStep(0, new SimplePos(//
                        eventData.get(0).length() * 5 - 1, //
                        eventData.size() * 5 - 1) //
                ) //
        );

        logger.log(Level.INFO, () -> "lowestRisk   : " + getLowestRisk());
        logger.log(Level.INFO, () -> "lowestRisk2  : " + getLowestRisk2());

        return true;
    }

    private Map<SimplePos, Integer> getFullArea(Map<SimplePos, Integer> area, int w, int h) {
        Map<SimplePos, Integer> response = new HashMap<>();
        for (var entry : area.entrySet()) {
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    int delta = x + y;
                    response.put(//
                            new SimplePos(//
                                    entry.getKey().getX() + x * w, //
                                    entry.getKey().getY() + y * h //
                            ), //
                            (entry.getValue() + delta > 9) ? (entry.getValue() + delta + 1) % 10 : entry.getValue() + delta //
                    );
                }
            }
        }
        return response;
    }
}
