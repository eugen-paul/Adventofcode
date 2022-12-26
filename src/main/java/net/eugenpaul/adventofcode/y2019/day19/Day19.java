package net.eugenpaul.adventofcode.y2019.day19;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;
import net.eugenpaul.adventofcode.helper.computer.IntcodeMapComputer;

public class Day19 extends SolutionTemplate {

    @Getter
    private int pulled;
    @Getter
    private int coordinate2;

    public static void main(String[] args) {
        Day19 puzzle = new Day19();
        puzzle.doPuzzleFromFile("y2019/day19/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        pulled = getPulled(eventData);

        coordinate2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "pulled  : " + getPulled());
        logger.log(Level.INFO, () -> "coordinate2  : " + getCoordinate2());

        return true;
    }

    private int doPuzzle2(String eventData) {
        int min = 1;
        int max = 10_000;

        SimplePos point = getMiddle(eventData, 100);

        while (min < max) {
            int current = (max - min) / 2 + min;
            if (isSquare(eventData, new SimplePos(point.getX() * current, point.getY() * current))) {
                max = current;
            } else {
                min = current + 1;
            }
        }

        SimplePos currentPos = new SimplePos(point.getX() * min, point.getY() * min);

        boolean moved = true;
        while (moved) {
            SimplePos startPos = currentPos;
            currentPos = moveUp(eventData, currentPos);
            currentPos = moveLeft(eventData, currentPos);
            currentPos = moveUpLeft(eventData, currentPos);
            currentPos = moveUpUpLeft(eventData, currentPos);
            currentPos = moveUpLeftLeft(eventData, currentPos);

            moved = !startPos.equals(currentPos);
        }

        return currentPos.getX() * 10000 + currentPos.getY();
    }

    private SimplePos getMiddle(String eventData, int y) {
        Map<Long, Long> opcodesOrigin = initOpcodes(eventData);

        int start = 0;

        while (!isPulled(opcodesOrigin, start, y)) {
            start++;
        }

        int end = start + 1;
        while (isPulled(opcodesOrigin, end, y)) {
            end++;
        }

        return new SimplePos((end - start) / 2 + start, y);
    }

    private SimplePos moveUp(String eventData, SimplePos point) {
        if (isSquare(eventData, point.moveNew(Direction.N))) {
            return point.moveNew(Direction.N);
        }
        return point;
    }

    private SimplePos moveLeft(String eventData, SimplePos point) {
        if (isSquare(eventData, point.moveNew(Direction.W))) {
            return point.moveNew(Direction.W);
        }
        return point;
    }

    private SimplePos moveUpLeft(String eventData, SimplePos point) {
        if (isSquare(eventData, point.moveNew(Direction.N).moveNew(Direction.W))) {
            return point.moveNew(Direction.N).moveNew(Direction.W);
        }
        return point;
    }

    private SimplePos moveUpLeftLeft(String eventData, SimplePos point) {
        if (isSquare(eventData, point.moveNew(Direction.N).moveNew(Direction.W).moveNew(Direction.W))) {
            return point.moveNew(Direction.N).moveNew(Direction.W).moveNew(Direction.W);
        }
        return point;
    }

    private SimplePos moveUpUpLeft(String eventData, SimplePos point) {
        if (isSquare(eventData, point.moveNew(Direction.N).moveNew(Direction.N).moveNew(Direction.W))) {
            return point.moveNew(Direction.N).moveNew(Direction.N).moveNew(Direction.W);
        }
        return point;
    }

    private int getPulled(String eventData) {
        Map<Long, Long> opcodesOrigin = initOpcodes(eventData);

        int response = 0;

        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 50; y++) {
                if (isPulled(opcodesOrigin, x, y)) {
                    response++;
                }
            }
        }

        return response;
    }

    private boolean isPulled(Map<Long, Long> opcodesOrigin, int x, int y) {
        int pos = 0;
        IntcodeMapComputer comp;
        comp = new IntcodeMapComputer();

        Map<Long, Long> opcodes = new HashMap<>(opcodesOrigin);

        comp.setInput(x, y);

        while (!comp.isEnd(opcodes, pos)) {
            pos = comp.runOpcodes(opcodes, pos);
            if (comp.isOutput()) {
                long output = comp.removeOutput();
                return output == 1;
            }
        }
        return false;
    }

    private boolean isSquare(String eventData, SimplePos point) {
        Map<Long, Long> opcodesOrigin = initOpcodes(eventData);

        return isPulled(opcodesOrigin, point.getX(), point.getY()) //
                && isPulled(opcodesOrigin, point.getX() + 99, point.getY()) //
                && isPulled(opcodesOrigin, point.getX(), point.getY() + 99) //
        ;
    }

    private Map<Long, Long> initOpcodes(String eventData) {
        Map<Long, Long> opcodesOrigin;

        // init Intcode
        long[] opcodesArray = StringConverter.toLongArray(eventData);
        opcodesOrigin = new HashMap<>();

        long counter = 0;
        for (long l : opcodesArray) {
            opcodesOrigin.put(counter, l);
            counter++;
        }
        return opcodesOrigin;
    }

}