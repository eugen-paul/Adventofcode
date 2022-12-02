package net.eugenpaul.adventofcode.y2022.day2;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day2 extends SolutionTemplate {

    @AllArgsConstructor
    @Getter
    private enum RoundResult {
        LOST(0), DRAW(3), WIN(6);

        private int score;

        public static RoundResult getResult(State enemy, State self) {
            if (enemy == self) {
                return DRAW;
            }
            if ((enemy == State.PAPER && self == State.SCISSORS) //
                    || (enemy == State.ROCK && self == State.PAPER) //
                    || (enemy == State.SCISSORS && self == State.ROCK) //
            ) {
                return WIN;
            }
            return LOST;
        }

    }

    @AllArgsConstructor
    @Getter
    private enum State {
        ROCK(1, 'A', 'X'), //
        PAPER(2, 'B', 'Y'), //
        SCISSORS(3, 'C', 'Z'); //

        public static final Map<Character, State> enemyMap = Stream.of(State.values()).collect(Collectors.toMap(State::getEnemy, v -> v));
        public static final Map<Character, State> selfMap = Stream.of(State.values()).collect(Collectors.toMap(State::getSelf, v -> v));

        private int score;
        private char enemy;
        private char self;

        public State getLoseState() {
            switch (this) {
            case ROCK:
                return SCISSORS;
            case PAPER:
                return ROCK;
            case SCISSORS:
                return PAPER;
            default:
                break;
            }
            throw new IllegalArgumentException();
        }

        public State getWinState() {
            switch (this) {
            case ROCK:
                return PAPER;
            case PAPER:
                return SCISSORS;
            case SCISSORS:
                return ROCK;
            default:
                break;
            }
            throw new IllegalArgumentException();
        }
    }

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day2 puzzle = new Day2();
        puzzle.doPuzzleFromFile("y2022/day2/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "totalScore : " + getTotalScore());
        logger.log(Level.INFO, () -> "totalScore2 : " + getTotalScore2());

        return true;
    }

    private int doPuzzle1(List<String> eventData) {
        var response = 0;
        for (String roundData : eventData) {
            var enemy = State.enemyMap.get(roundData.charAt(0));
            var self = State.selfMap.get(roundData.charAt(2));
            response += getRoundScore(enemy, self);
        }
        return response;
    }

    private int doPuzzle2(List<String> eventData) {
        var response = 0;
        for (String roundData : eventData) {
            var enemy = State.enemyMap.get(roundData.charAt(0));
            State self;
            switch (roundData.charAt(2)) {
            case 'X':
                self = enemy.getLoseState();
                break;
            case 'Y':
                self = enemy;
                break;
            case 'Z':
                self = enemy.getWinState();
                break;
            default:
                throw new IllegalArgumentException();
            }
            response += getRoundScore(enemy, self);
        }
        return response;
    }

    private int getRoundScore(State enemy, State self) {
        var result = RoundResult.getResult(enemy, self);
        return self.getScore() + result.getScore();
    }

}
