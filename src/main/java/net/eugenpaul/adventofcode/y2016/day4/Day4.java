package net.eugenpaul.adventofcode.y2016.day4;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day4 extends SolutionTemplate {

    @Getter
    private int sumOfTheSectorIds;

    @Getter
    private int idOfNorthPole;

    public static void main(String[] args) {
        Day4 puzzle = new Day4();
        puzzle.doPuzzleFromFile("y2016/day4/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        sumOfTheSectorIds = 0;
        idOfNorthPole = 0;

        List<Room> rooms = eventData.stream()//
                .map(Room::fromString)//
                .collect(Collectors.toList());

        for (Room room : rooms) {
            if (room.isReal()) {
                sumOfTheSectorIds += room.getId();
            }
            if (room.getDecryptedName().equalsIgnoreCase("northpole object storage")) {
                idOfNorthPole = room.getId();
            }
        }

        logger.log(Level.INFO, () -> "sumOfTheSectorIds: " + getSumOfTheSectorIds());
        logger.log(Level.INFO, () -> "idOfNorthPole: " + getIdOfNorthPole());

        return true;
    }

}
