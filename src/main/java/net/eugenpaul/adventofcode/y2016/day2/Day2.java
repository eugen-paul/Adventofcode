package net.eugenpaul.adventofcode.y2016.day2;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day2 extends SolutionTemplate {

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

    @Override
    public boolean doEvent(List<String> eventData) {

        code = computeCode(new SimplePos(1, 1), eventData, keypad);
        codeExt = computeCode(new SimplePos(0, 2), eventData, keypadExt);

        logger.log(Level.INFO, () -> "code: " + getCode());
        logger.log(Level.INFO, () -> "codeExt: " + getCodeExt());

        return true;
    }

    private String computeCode(SimplePos position, List<String> eventData, String[][] kp) {
        StringBuilder response = new StringBuilder();

        for (String instruction : eventData) {
            computeNextPosition(position, kp, instruction);
            response.append(kp[position.getY()][position.getX()]);
        }

        return response.toString();
    }

    private void computeNextPosition(SimplePos position, String[][] kp, String instruction) {
        for (char c : instruction.toCharArray()) {
            Direction direction = Direction.fromUdrl(c);

            if (isPositionOk(position.moveNew(direction), kp)) {
                position.move(direction);
            }
        }
    }

    private boolean isPositionOk(SimplePos pos, String[][] kp) {
        if (pos.getX() < 0 || pos.getY() < 0 || pos.getY() >= kp.length || pos.getX() >= kp[0].length) {
            return false;
        }

        return !kp[pos.getY()][pos.getX()].equals(" ");
    }
}
