package net.eugenpaul.adventofcode.y2016.day2;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day2 {

    @AllArgsConstructor
    @Data
    private class Position {
        int x;
        int y;
    }

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day2.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day2.class.getName());

    private static final String[][] keypad = new String[][] { //
            { "1", "2", "3" }, //
            { "4", "5", "6" }, //
            { "7", "8", "9" } //
    };

    private static final String[][] keypadExt = new String[][] { //
            { " ", " ", "1", " ", " " }, //
            { " ", "2", "3", "4", " " }, //
            { "5", "6", "7", "8", "9" }, //
            { " ", "A", "B", "C", " " }, //
            { " ", " ", "D", " ", " " } //
    };

    @Getter
    private String code;

    @Getter
    private String codeExt;

    public static void main(String[] args) {
        Day2 puzzle = new Day2();
        puzzle.doPuzzleFromFile("y2016/day2/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "code: " + getCode());
        logger.log(Level.INFO, () -> "codeExt: " + getCodeExt());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        code = computeCode(new Position(1, 1), eventData, keypad);
        codeExt = computeCode(new Position(0, 2), eventData, keypadExt);

        return true;
    }

    private String computeCode(Position position, List<String> eventData, String[][] kp) {
        StringBuilder response = new StringBuilder();

        for (String instruction : eventData) {
            computeNextPosition(position, kp, instruction);
            response.append(kp[position.getY()][position.getX()]);
        }

        return response.toString();
    }

    private void computeNextPosition(Position position, String[][] kp, String instruction) {
        for (int i = 0; i < instruction.length(); i++) {
            switch (instruction.charAt(i)) {
            case 'U':
                position.setY(isPositionOk(position.getX(), position.getY() - 1, kp) ? position.getY() - 1 : position.getY());
                break;
            case 'D':
                position.setY(isPositionOk(position.getX(), position.getY() + 1, kp) ? position.getY() + 1 : position.getY());
                break;
            case 'R':
                position.setX(isPositionOk(position.getX() + 1, position.getY(), kp) ? position.getX() + 1 : position.getX());
                break;
            case 'L':
                position.setX(isPositionOk(position.getX() - 1, position.getY(), kp) ? position.getX() - 1 : position.getX());
                break;
            default:
                break;
            }
        }
    }

    private boolean isPositionOk(int posx, int posy, String[][] kp) {
        if (posx < 0 || posy < 0 || posy >= kp.length || posx >= kp[0].length) {
            return false;
        }

        return !kp[posy][posx].equals(" ");
    }
}
