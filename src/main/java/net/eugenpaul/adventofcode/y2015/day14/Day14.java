package net.eugenpaul.adventofcode.y2015.day14;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day14 extends SolutionTemplate {

    @Getter
    private int maxDistance;
    @Getter
    private int winnerPoints;

    @Setter
    private int puzzleTime = 2503;

    public static void main(String[] args) {
        Day14 puzzle = new Day14();
        puzzle.doPuzzleFromFile("y2015/day14/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        List<Reindeer> reindeers = eventData.stream()//
                .map(Reindeer::fromString)//
                .collect(Collectors.toCollection(ArrayList::new));

        // Part One Solution
        maxDistance = -1;
        for (Reindeer reindeer : reindeers) {
            maxDistance = Math.max(maxDistance, reindeer.computeDistance(puzzleTime));
        }

        // Part Two Solution
        RaceData race = new RaceData(reindeers);
        race.doRace(puzzleTime);

        winnerPoints = race.getWinnerPoints();

        logger.log(Level.INFO, () -> "maxDistance: " + getMaxDistance());
        logger.log(Level.INFO, () -> "winnerPoints: " + getWinnerPoints());
        return true;
    }

}
