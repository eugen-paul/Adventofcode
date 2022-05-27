package net.eugenpaul.adventofcode.y2016.day13;

import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.dijkstra.Dijkstra;

public class Day13 extends SolutionTemplate {

    @Getter
    private int steps;
    @Getter
    private int distinctLocations;

    @Setter
    private int x = 31;
    @Setter
    private int y = 39;
    @Setter
    private int maxSteps = 50;

    public static void main(String[] args) {
        Day13 puzzle = new Day13();
        puzzle.doPuzzleFromFile("y2016/day13/puzzle1.txt");
    }

    @Override
    public boolean doEvent(Integer eventData) {
        BuildingArea area = new BuildingArea(eventData);
        Dijkstra pathFinding = new Dijkstra();

        steps = pathFinding.getSteps(area, 1, 1, x, y);

        pathFinding = new Dijkstra();
        distinctLocations = pathFinding.getReachableFieldsCount(area, 1, 1, maxSteps);

        logger.log(Level.INFO, () -> "steps : " + getSteps());
        logger.log(Level.INFO, () -> "distinctLocations : " + getDistinctLocations());

        return true;
    }

}
