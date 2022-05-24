package net.eugenpaul.adventofcode.y2016.day10;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day10 extends SolutionTemplate {

    @Getter
    private int bot;
    @Getter
    private int multiplyValues;

    @Setter
    private int lowValue = 17;
    @Setter
    private int highValue = 61;

    public static void main(String[] args) {
        Day10 puzzle = new Day10();
        puzzle.doPuzzleFromFile("y2016/day10/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        InstructionFactory factory = new InstructionFactory(lowValue, highValue);

        eventData.stream().forEach(factory::readInstruction);
        factory.doInit();
        factory.doInstructions();

        bot = factory.getBotnumber();
        multiplyValues = factory.getMultiplyValues();

        logger.log(Level.INFO, () -> "bot: " + getBot());
        logger.log(Level.INFO, () -> "multiplyValues: " + getMultiplyValues());

        return true;
    }
}
