package net.eugenpaul.adventofcode.y2016.day8;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;
import net.eugenpaul.adventofcode.y2016.day8.instruction.InstructionFactory;

public class Day8 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day8.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day8.class.getName());

    @Getter
    private long pixelsLit;

    public static void main(String[] args) {
        Day8 puzzle = new Day8();
        puzzle.doPuzzleFromFile("y2016/day8/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData, 50, 6);
    }

    public boolean doPuzzleFromData(List<String> eventData, int sizeX, int sizeY) {
        if (!doEvent(eventData, sizeX, sizeY)) {
            return false;
        }

        logger.log(Level.INFO, () -> "pixelsLit: " + getPixelsLit());

        return true;
    }

    private boolean doEvent(List<String> eventData, int sizeX, int sizeY) {
        if (null == eventData) {
            return false;
        }

        boolean[][] display = new boolean[sizeX][sizeY];
        for (int i = 0; i < display.length; i++) {
            Arrays.fill(display[i], false);
        }

        eventData.stream().forEach(v -> {
            InstructionFactory.fromString(v).doInstruction(display);
        });

        pixelsLit = 0;
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (display[i][j]) {
                    pixelsLit++;
                }
            }
        }

        prindDisplay(display);

        return true;
    }

    private void prindDisplay(boolean[][] display) {
        for (int i = 0; i < display[0].length; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < display.length; j++) {
                line.append(display[j][i] ? "#" : " ");
            }
            logger.log(Level.INFO, line::toString);
        }
    }
}
