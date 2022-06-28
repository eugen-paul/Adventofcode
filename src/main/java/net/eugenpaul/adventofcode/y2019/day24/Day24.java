package net.eugenpaul.adventofcode.y2019.day24;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day24 extends SolutionTemplate {

    @Getter
    private Long rating;
    @Getter
    private Long bugsCount;

    @Setter
    private int minStep2 = 200;

    public static void main(String[] args) {
        Day24 puzzle = new Day24();
        puzzle.doPuzzleFromFile("y2019/day24/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        int width = eventData.get(0).length();
        int height = eventData.size();

        Set<SimplePos> bugs = StringConverter.toSet(eventData, '#');

        rating = doPuzzle1(bugs, width, height);

        bugsCount = doPuzzle2(bugs, width, height);

        logger.log(Level.INFO, () -> "rating    : " + getRating());
        logger.log(Level.INFO, () -> "bugsCount : " + getBugsCount());

        return true;
    }

    private long doPuzzle1(Set<SimplePos> bugs, int width, int height) {
        Set<String> hashData = new HashSet<>();

        Set<SimplePos> currentStep = bugs;
        String lastHash = getHashOfBugs(bugs);

        while (!hashData.contains(lastHash)) {
            hashData.add(lastHash);
            Set<SimplePos> nextStep = new HashSet<>();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    SimplePos pos = new SimplePos(x, y);
                    if (nextState(currentStep, pos)) {
                        nextStep.add(pos);
                    }
                }
            }
            currentStep = nextStep;
            lastHash = getHashOfBugs(currentStep);
        }

        long response = 0;
        response = computeRating(width, height, currentStep, response);

        return response;
    }

    private long doPuzzle2(Set<SimplePos> bugs, int width, int height) {
        Map<Integer, Set<SimplePos>> currentStep = new HashMap<>();
        currentStep.put(0, new HashSet<>(bugs));
        int minLevel = 0;
        int maxLevel = 0;

        for (int i = 0; i < minStep2; i++) {
            Map<Integer, Set<SimplePos>> nextStep = new HashMap<>();

            minLevel--;
            maxLevel++;

            computeNextLevelStep(width, height, currentStep, minLevel, nextStep);
            computeNextLevelStep(width, height, currentStep, maxLevel, nextStep);

            for (var stepEntry : currentStep.entrySet()) {
                computeNextLevelStep(width, height, currentStep, stepEntry.getKey(), nextStep);
            }

            currentStep = nextStep;
        }

        return currentStep.entrySet().stream()//
                .mapToLong(v -> v.getValue().size())//
                .sum();
    }

    private void computeNextLevelStep(int width, int height, Map<Integer, Set<SimplePos>> currentStep, int minLevel, Map<Integer, Set<SimplePos>> nextStep) {
        Set<SimplePos> nextOfLevel = new HashSet<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 2 && y == 2) {
                    continue;
                }
                SimplePos pos = new SimplePos(x, y);
                if (nextInfiniteState(currentStep, minLevel, pos)) {
                    nextOfLevel.add(pos);
                }
            }
        }

        nextStep.put(minLevel, nextOfLevel);
    }

    private boolean nextInfiniteState(Map<Integer, Set<SimplePos>> fullMap, int level, SimplePos pos) {
        Set<SimplePos> currentLevel = fullMap.getOrDefault(level, new HashSet<>());
        if (currentLevel.contains(pos)) {
            int count = 0;
            count = getNeighbors(fullMap, level, pos);
            return count == 1;
        }

        int count = 0;
        count = getNeighbors(fullMap, level, pos);
        return count == 1 || count == 2;
    }

    private int getNeighbors(Map<Integer, Set<SimplePos>> fullMap, int level, SimplePos from) {

        int count = 0;
        count += getLevelNeighbors(fullMap, level, from.moveNew(Direction.N), Direction.N);
        count += getLevelNeighbors(fullMap, level, from.moveNew(Direction.S), Direction.S);
        count += getLevelNeighbors(fullMap, level, from.moveNew(Direction.E), Direction.E);
        count += getLevelNeighbors(fullMap, level, from.moveNew(Direction.W), Direction.W);

        return count;
    }

    private int getLevelNeighbors(Map<Integer, Set<SimplePos>> fullMap, int level, SimplePos toPos, Direction moveDirection) {
        Set<SimplePos> levelToCheck;

        if (toPos.getX() == 2 && toPos.getY() == 2) {
            levelToCheck = fullMap.getOrDefault(level + 1, new HashSet<>());
            switch (moveDirection) {
            case N:
                return countLowerRow(levelToCheck);
            case S:
                return countUpperRow(levelToCheck);
            case E:
                return countLeftRow(levelToCheck);
            case W:
                return countRightRow(levelToCheck);
            default:
                throw new IllegalArgumentException();
            }
        }

        if (toPos.getX() < 0 || 4 < toPos.getX()) {
            levelToCheck = fullMap.getOrDefault(level - 1, new HashSet<>());
            switch (moveDirection) {
            case E:
                return coutInnerRight(levelToCheck);
            case W:
                return countInnerLeft(levelToCheck);
            default:
                throw new IllegalArgumentException();
            }
        }

        if (toPos.getY() < 0 || 4 < toPos.getY()) {
            levelToCheck = fullMap.getOrDefault(level - 1, new HashSet<>());
            switch (moveDirection) {
            case N:
                return countInnerUp(levelToCheck);
            case S:
                return countInnerBottom(levelToCheck);
            default:
                throw new IllegalArgumentException("Wronng data: pos = " + toPos + ", d = " + moveDirection);
            }
        }

        levelToCheck = fullMap.getOrDefault(level, new HashSet<>());
        if (levelToCheck.contains(toPos)) {
            return 1;
        }

        return 0;
    }

    private int countInnerBottom(Set<SimplePos> levelToCheck) {
        int count = 0;
        if (levelToCheck.contains(new SimplePos(2, 3))) {
            count++;
        }
        return count;
    }

    private int countInnerUp(Set<SimplePos> levelToCheck) {
        int count = 0;
        if (levelToCheck.contains(new SimplePos(2, 1))) {
            count++;
        }
        return count;
    }

    private int countInnerLeft(Set<SimplePos> levelToCheck) {
        int count = 0;
        if (levelToCheck.contains(new SimplePos(1, 2))) {
            count++;
        }
        return count;
    }

    private int coutInnerRight(Set<SimplePos> levelToCheck) {
        int count = 0;
        if (levelToCheck.contains(new SimplePos(3, 2))) {
            count++;
        }
        return count;
    }

    private int countRightRow(Set<SimplePos> levelToCheck) {
        int count = 0;
        if (levelToCheck.contains(new SimplePos(4, 0))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(4, 1))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(4, 2))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(4, 3))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(4, 4))) {
            count++;
        }
        return count;
    }

    private int countLeftRow(Set<SimplePos> levelToCheck) {
        int count = 0;
        if (levelToCheck.contains(new SimplePos(0, 0))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(0, 1))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(0, 2))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(0, 3))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(0, 4))) {
            count++;
        }
        return count;
    }

    private int countUpperRow(Set<SimplePos> levelToCheck) {
        int count = 0;
        if (levelToCheck.contains(new SimplePos(0, 0))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(1, 0))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(2, 0))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(3, 0))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(4, 0))) {
            count++;
        }
        return count;
    }

    private int countLowerRow(Set<SimplePos> levelToCheck) {
        int count = 0;
        if (levelToCheck.contains(new SimplePos(0, 4))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(1, 4))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(2, 4))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(3, 4))) {
            count++;
        }
        if (levelToCheck.contains(new SimplePos(4, 4))) {
            count++;
        }
        return count;
    }

    private long computeRating(int width, int height, Set<SimplePos> currentStep, long response) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                SimplePos pos = new SimplePos(x, y);
                if (currentStep.contains(pos)) {
                    int power = x + width * y;
                    response += 1 << power;
                }
            }
        }
        return response;
    }

    private String getHashOfBugs(Set<SimplePos> bugs) {
        StringBuilder response = new StringBuilder(bugs.size() * 4);
        bugs.stream()//
                .sorted((a, b) -> {
                    if (a.getX() != b.getX()) {
                        return a.getX() - b.getX();
                    }
                    return a.getY() - b.getY();
                })//
                .forEach(v -> {
                    response.append(v.getX());
                    response.append(".");
                    response.append(v.getY());
                    response.append(":");
                });
        return response.toString();
    }

    private boolean nextState(Set<SimplePos> bugs, SimplePos pos) {
        if (bugs.contains(pos)) {
            int count = getNeighbors(bugs, pos);
            return count == 1;
        }

        int count = getNeighbors(bugs, pos);
        return count == 1 || count == 2;
    }

    private int getNeighbors(Set<SimplePos> bugs, SimplePos pos) {
        int count = 0;
        if (bugs.contains(pos.moveNew(Direction.N))) {
            count++;
        }
        if (bugs.contains(pos.moveNew(Direction.S))) {
            count++;
        }
        if (bugs.contains(pos.moveNew(Direction.E))) {
            count++;
        }
        if (bugs.contains(pos.moveNew(Direction.W))) {
            count++;
        }
        return count;
    }

}