package net.eugenpaul.adventofcode.y2016.day21;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;
import net.eugenpaul.adventofcode.y2016.day21.operation.OperationFactory;
import net.eugenpaul.adventofcode.y2016.day21.operation.ScrambleOperation;

public class Day21 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day21.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day21.class.getName());

    @Getter
    private String password;

    @Getter
    private String unScrambled;

    public static void main(String[] args) {
        Day21 puzzle = new Day21();
        puzzle.doPuzzleFromFile("y2016/day21/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData, "abcdefgh", "fbgdceah");
    }

    public boolean doPuzzleFromData(List<String> eventData, String text, String pwd) {
        if (!doEvent(eventData, text, pwd)) {
            return false;
        }

        logger.log(Level.INFO, () -> "password " + getPassword());
        logger.log(Level.INFO, () -> "unScrambled " + getUnScrambled());

        return true;
    }

    private boolean doEvent(List<String> eventData, String text, String pwd) {
        if (null == eventData) {
            return false;
        }

        List<ScrambleOperation> op = eventData.stream()//
                .map(OperationFactory::fromString)//
                .collect(Collectors.toList());

        // Puzzle 1
        StringBuilder builder = new StringBuilder(text);
        for (ScrambleOperation scrambleOperation : op) {
            builder = scrambleOperation.execute(builder);
        }

        password = builder.toString();

        // Puzzle 2
        builder = new StringBuilder(pwd);
        for (int i = op.size() - 1; i >= 0; i--) {
            builder = op.get(i).reverse(builder);
        }

        unScrambled = builder.toString();

        return true;
    }

}
