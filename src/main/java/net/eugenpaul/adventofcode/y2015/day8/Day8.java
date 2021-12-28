package net.eugenpaul.adventofcode.y2015.day8;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.apache.commons.text.StringEscapeUtils;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

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

    private int charactersOfCode;
    private int charactersOfString;
    private int charactersOfEscapedString;

    public int getCharactersOfCode() {
        return charactersOfCode;
    }

    public int getCharactersOfString() {
        return charactersOfString;
    }

    public int getCharactersOfEscapedString() {
        return charactersOfEscapedString;
    }

    public static void main(String[] args) {
        Day8 puzzle = new Day8();
        puzzle.doPuzzleFromFile("y2015/day8/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "charactersOfCode: " + charactersOfCode);
        logger.log(Level.INFO, () -> "charactersOfString: " + charactersOfString);
        logger.log(Level.INFO, () -> "charactersOfEscapedString: " + charactersOfEscapedString);
        logger.log(Level.INFO, () -> "charactersOfString - charactersOfCode: " + (charactersOfCode - charactersOfString));
        logger.log(Level.INFO, () -> "charactersOfEscapedString - charactersOfString: " + (charactersOfEscapedString - charactersOfCode));

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        charactersOfCode = 0;
        charactersOfString = 0;
        charactersOfEscapedString = 0;

        for (String data : eventData) {
            String unescapeString = ConverterHelper.UNESCAPE_JAVA.translate(data);
            charactersOfCode += data.length();
            charactersOfString += unescapeString.length() - 2;

            String escapeString = StringEscapeUtils.escapeJava(data);
            charactersOfEscapedString += (escapeString.length() + 2); // "+2" for the two missing double quotes
        }

        return true;
    }

}
