package net.eugenpaul.adventofcode.y2015.day2;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day2 extends SolutionTemplate {

    @Getter
    private Long totalSquare = null;
    @Getter
    private Long totalRibbon = null;

    public static void main(String[] args) {
        Day2 puzzle = new Day2();
        puzzle.doPuzzleFromFile("y2015/day2/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Square> squareData = eventData.stream().map(Square::fromString).collect(Collectors.toList());

        long responseTotalSquare = 0;
        long responseTotalRibbon = 0;
        for (Square box : squareData) {
            responseTotalSquare += ElvesHelper.computeSurfaceArea(box);
            responseTotalRibbon += ElvesHelper.computeRibbon(box);
        }

        totalSquare = responseTotalSquare;
        totalRibbon = responseTotalRibbon;

        logger.log(Level.INFO, () -> "Total square feet of wrapping paper " + totalSquare);
        logger.log(Level.INFO, () -> "Total feet of ribbon  " + totalRibbon);

        return true;
    }

}
