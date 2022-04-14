package net.eugenpaul.adventofcode.y2015.day8;

import java.util.List;
import java.util.logging.Level;

import org.apache.commons.text.StringEscapeUtils;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day8 extends SolutionTemplate {

    @Getter
    private int charactersOfCode;
    @Getter
    private int charactersOfString;
    @Getter
    private int charactersOfEscapedString;

    public static void main(String[] args) {
        Day8 puzzle = new Day8();
        puzzle.doPuzzleFromFile("y2015/day8/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
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

        logger.log(Level.INFO, () -> "charactersOfCode: " + charactersOfCode);
        logger.log(Level.INFO, () -> "charactersOfString: " + charactersOfString);
        logger.log(Level.INFO, () -> "charactersOfEscapedString: " + charactersOfEscapedString);
        logger.log(Level.INFO, () -> "charactersOfString - charactersOfCode: " + (charactersOfCode - charactersOfString));
        logger.log(Level.INFO, () -> "charactersOfEscapedString - charactersOfString: " + (charactersOfEscapedString - charactersOfCode));

        return true;
    }

}
