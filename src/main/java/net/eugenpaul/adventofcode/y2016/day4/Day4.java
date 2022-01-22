package net.eugenpaul.adventofcode.y2016.day4;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day4 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day4.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day4.class.getName());

    @Getter
    private int sumOfTheSectorIds;

    @Getter
    private int idOfNorthPole;

    public static void main(String[] args) {
        Day4 puzzle = new Day4();
        puzzle.doPuzzleFromFile("y2016/day4/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "sumOfTheSectorIds: " + getSumOfTheSectorIds());
        logger.log(Level.INFO, () -> "idOfNorthPole: " + getIdOfNorthPole());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

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

        return true;
    }

}
