package net.eugenpaul.adventofcode.y2016.day17;

import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day17 extends SolutionTemplate {

    @Getter
    private String shortestPath;

    @Getter
    private int longestPathLength;

    public static void main(String[] args) {
        Day17 puzzle = new Day17();
        puzzle.doPuzzleFromFile("y2016/day17/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        BuildingArea area = new BuildingArea(4, 4);
        Dijkstra pathFinding = new Dijkstra();
        shortestPath = pathFinding.getSteps(area, eventData, 0, 0, 3, 3);

        pathFinding = new Dijkstra();
        longestPathLength = pathFinding.getLongestPathLength(area, eventData, 0, 0, 3, 3);

        logger.log(Level.INFO, () -> "shortestPath : " + getShortestPath());
        logger.log(Level.INFO, () -> "longestPathLength : " + getLongestPathLength());

        return true;
    }

}
