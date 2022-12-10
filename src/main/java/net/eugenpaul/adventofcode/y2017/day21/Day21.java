package net.eugenpaul.adventofcode.y2017.day21;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day21 extends SolutionTemplate {

    @Getter
    private int pixelsOn;

    @Setter
    private int iterations = 5;

    @Setter
    private boolean doTest2 = true;

    public static void main(String[] args) {
        Day21 puzzle = new Day21();
        puzzle.doPuzzleFromFile("y2017/day21/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        pixelsOn = doPuzzle(eventData);
        logger.log(Level.INFO, () -> "pixelsOn after 5: " + getPixelsOn());

        if (!doTest2) {
            return true;
        }

        iterations = 18;
        pixelsOn = doPuzzle(eventData);
        logger.log(Level.INFO, () -> "pixelsOn after 18: " + getPixelsOn());

        return true;
    }

    private int doPuzzle(List<String> eventData) {
        List<Rule> rules = eventData.stream()//
                .map(Rule::fromString)//
                .collect(Collectors.toList());

        Rule startRule = Rule.initRule();

        for (int i = 0; i < iterations; i++) {
            List<Rule> roundList = startRule.divide();

            List<Rule> outList = new ArrayList<>();

            for (Rule rule : roundList) {
                Rule matcher = rules.stream().filter(v -> v.isRule(rule)).findFirst().orElseThrow();
                outList.add(matcher.getOutputRule());
            }

            startRule = Rule.concat(outList);
        }
        return startRule.getOn();
    }

}
