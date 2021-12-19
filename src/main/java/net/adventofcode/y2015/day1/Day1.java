package net.adventofcode.y2015.day1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day1 {

    private static Logger logger = null;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n");
        logger = Logger.getLogger(Day1.class.getName());
    }

    private Integer firstPositionOfbasement = null;
    private Integer onFlor = null;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzle("y2015/day1/puzzle1.txt");
    }

    public boolean doPuzzle(String filename) {
        String eventData = readDataFromFile(filename);
        if (null == eventData) {
            return false;
        }

        if (!getFloor(0, eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "Santa on flor " + onFlor);
        logger.log(Level.INFO, () -> "Enter the basement on step " + firstPositionOfbasement);

        return true;
    }

    private String readDataFromFile(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());

        if (!file.canRead() || !file.isFile()) {
            return null;
        }

        StringBuilder responseData = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
                responseData.append(zeile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseData.toString();
    }

    private boolean getFloor(int startFloor, String data) {
        onFlor = startFloor;
        for (int i = 0; i < data.length(); i++) {
            char step = data.charAt(i);
            switch (step) {
                case '(':
                    onFlor++;
                    break;
                case ')':
                    onFlor--;
                    break;
                default:
                    return false;
            }

            if (null == firstPositionOfbasement && -1 == onFlor) {
                firstPositionOfbasement = i + 1;
            }
        }
        return true;
    }
}
