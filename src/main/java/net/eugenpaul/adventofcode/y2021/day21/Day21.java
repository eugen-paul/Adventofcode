package net.eugenpaul.adventofcode.y2021.day21;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import lombok.Getter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day21 extends SolutionTemplate {

    @Getter
    private long score;
    @Getter
    private long wins;

    public static void main(String[] args) {
        Day21 puzzle = new Day21();
        puzzle.doPuzzleFromFile("y2021/day21/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        score = doPuzzle1(eventData);
        wins = doPuzzle2(eventData);
        logger.log(Level.INFO, () -> "score  : " + getScore());
        logger.log(Level.INFO, () -> "wins : " + getWins());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {
        int pos1 = Integer.parseInt(eventData.get(0).split(" ")[4]) - 1;
        int pos2 = Integer.parseInt(eventData.get(1).split(" ")[4]) - 1;

        long score1 = 0;
        long score2 = 0;

        int roll = 0;

        while (true) {
            pos1 = (pos1 + roll * 3 + 1 + 2 + 3) % 10;
            roll += 3;
            score1 += pos1 + 1;
            if (score1 >= 1000) {
                return roll * score2;
            }

            pos2 = (pos2 + roll * 3 + 1 + 2 + 3) % 10;
            roll += 3;
            score2 += pos2 + 1;
            if (score2 >= 1000) {
                return roll * score1;
            }
        }
    }

    private long doPuzzle2(List<String> eventData) {
        int pos1 = Integer.parseInt(eventData.get(0).split(" ")[4]) - 1;
        int pos2 = Integer.parseInt(eventData.get(1).split(" ")[4]) - 1;

        // After rolling the dice three times, you can only get 7 different total results.
        Map<Integer, Integer> scoreCount = Map.ofEntries(//
                Map.entry(3, 1), //
                Map.entry(4, 3), //
                Map.entry(5, 6), //
                Map.entry(6, 7), //
                Map.entry(7, 6), //
                Map.entry(8, 3), //
                Map.entry(9, 1)//
        );

        var result = doStepPlayer1(scoreCount, pos1, pos2, 0, 0);

        return Math.max(result.getKey(), result.getValue());
    }

    private Entry<Long, Long> doStepPlayer1(Map<Integer, Integer> scoreCount, int pos1, int pos2, int score1, int score2) {
        while (true) {
            long wins1 = 0;
            long wins2 = 0;

            for (var entry : scoreCount.entrySet()) {
                int pos1Intern = (pos1 + entry.getKey()) % 10;
                int score1Intern = score1 + pos1Intern + 1;
                if (score1Intern >= 21) {
                    wins1 += entry.getValue();
                } else {
                    var result = doStepPlayer2(scoreCount, pos1Intern, pos2, score1Intern, score2);
                    wins1 += result.getKey() * entry.getValue();
                    wins2 += result.getValue() * entry.getValue();
                }
            }
            return Map.entry(wins1, wins2);
        }
    }

    private Entry<Long, Long> doStepPlayer2(Map<Integer, Integer> scoreCount, int pos1, int pos2, int score1, int score2) {
        while (true) {
            long wins1 = 0;
            long wins2 = 0;

            for (var entry : scoreCount.entrySet()) {
                int pos2Intern = (pos2 + entry.getKey()) % 10;
                int score2Intern = score2 + pos2Intern + 1;
                if (score2Intern >= 21) {
                    wins2 += entry.getValue();
                } else {
                    var result = doStepPlayer1(scoreCount, pos1, pos2Intern, score1, score2Intern);
                    wins1 += result.getKey() * entry.getValue();
                    wins2 += result.getValue() * entry.getValue();
                }
            }
            return Map.entry(wins1, wins2);
        }
    }

}
