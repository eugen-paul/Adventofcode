package net.eugenpaul.adventofcode.y2016.day21;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.y2016.day21.operation.OperationFactory;
import net.eugenpaul.adventofcode.y2016.day21.operation.ScrambleOperation;

public class Day21 extends SolutionTemplate {

    @Getter
    private String password;

    @Getter
    private String unScrambled;

    @Setter
    private String text = "abcdefgh";
    @Setter
    private String pwd = "fbgdceah";

    public static void main(String[] args) {
        Day21 puzzle = new Day21();
        puzzle.doPuzzleFromFile("y2016/day21/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
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

        logger.log(Level.INFO, () -> "password " + getPassword());
        logger.log(Level.INFO, () -> "unScrambled " + getUnScrambled());

        return true;
    }

}
