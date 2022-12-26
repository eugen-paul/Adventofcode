package net.eugenpaul.adventofcode.y2019.day13;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;
import net.eugenpaul.adventofcode.helper.computer.IntcodeMapComputer;

public class Day13 extends SolutionTemplate {

    @AllArgsConstructor
    private enum Tile {
        EMPTY(0L, ' '), WALL(1L, '#'), BLOCK(2L, '*'), PADDLE(3L, '-'), BALL(4L, 'o');

        @Getter
        private final long value;
        @Getter
        private final char drawChar;

        private static final Map<Long, Tile> LONG_TO_TILE = Stream.of(Tile.values()).collect(Collectors.toMap(Tile::getValue, v -> v));

        public static Tile fromLong(long value) {
            return LONG_TO_TILE.get(value);
        }
    }

    @Getter
    private int blockCount;
    @Getter
    private int score;

    @Setter
    private boolean printDisplay = false;

    public static void main(String[] args) {
        Day13 puzzle = new Day13();
        puzzle.doPuzzleFromFile("y2019/day13/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        long[] opcodesArray = StringConverter.toLongArray(eventData);

        blockCount = doPuzzle1(opcodesArray);
        score = doPuzzle2(opcodesArray);

        logger.log(Level.INFO, () -> "blockCount : " + getBlockCount());
        logger.log(Level.INFO, () -> "score      : " + getScore());

        return true;
    }

    private int doPuzzle1(long[] opcodesArray) {
        Map<Long, Long> opcodes = initOpcodes(opcodesArray);

        IntcodeMapComputer comp = new IntcodeMapComputer();
        int pos = 0;

        int outputCounter = 0;
        int x = 0;
        int y = 0;
        Tile value = null;

        Map<SimplePos, Tile> display = new HashMap<>();

        while (!comp.isEnd(opcodes, pos)) {
            pos = comp.runOpcodes(opcodes, pos);
            if (comp.isOutput()) {
                switch (outputCounter) {
                case 0:
                    x = comp.removeOutput().intValue();
                    outputCounter++;
                    break;
                case 1:
                    y = comp.removeOutput().intValue();
                    outputCounter++;
                    break;
                case 2:
                    value = Tile.fromLong(comp.removeOutput());
                    display.put(//
                            new SimplePos(x, y), //
                            value //
                    );
                    outputCounter = 0;
                    break;
                default:
                    break;
                }
            }
        }

        return (int) display.values().stream()//
                .filter(v -> v == Tile.BLOCK)//
                .count();
    }

    private int doPuzzle2(long[] opcodesArray) {
        Map<Long, Long> opcodes = initOpcodes(opcodesArray);
        opcodes.put(0L, 2L);

        IntcodeMapComputer comp = new IntcodeMapComputer();
        int pos = 0;

        int outputCounter = 0;
        int x = 0;
        int y = 0;
        Tile value = null;

        Integer parrlePosX = null;
        Integer ballPosX = null;

        Map<SimplePos, Tile> display = new HashMap<>();

        int currentScore = 0;

        comp.setInput(0);
        while (!comp.isEnd(opcodes, pos)) {
            pos = comp.runOpcodes(opcodes, pos);
            if (!comp.isOutput()) {
                continue;
            }

            switch (outputCounter) {
            case 0:
                x = comp.removeOutput().intValue();
                outputCounter++;
                break;
            case 1:
                y = comp.removeOutput().intValue();
                outputCounter++;
                break;
            case 2:
                if (x == -1) {
                    currentScore = getScore(comp, display);
                } else {
                    value = Tile.fromLong(comp.removeOutput());
                    display.put(//
                            new SimplePos(x, y), //
                            value //
                    );
                    if (value == Tile.BALL) {
                        ballPosX = x;
                    } else if (value == Tile.PADDLE) {
                        parrlePosX = x;
                    }
                    setInput(comp, parrlePosX, ballPosX);
                }
                outputCounter = 0;
                break;
            default:
                break;
            }
        }

        return currentScore;
    }

    private void setInput(IntcodeMapComputer comp, Integer parrlePosX, Integer ballPosX) {
        if (ballPosX != null && parrlePosX != null) {
            if (ballPosX > parrlePosX) {
                comp.setInput(1L);
            } else if (ballPosX < parrlePosX) {
                comp.setInput(-1L);
            } else {
                comp.setInput(0L);
            }
        }
    }

    private int getScore(IntcodeMapComputer comp, Map<SimplePos, Tile> display) {
        int currentScore;
        currentScore = comp.removeOutput().intValue();
        if (printDisplay) {
            MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(display, v -> (v == null) ? ' ' : v.getDrawChar()));
        }
        return currentScore;
    }

    private Map<Long, Long> initOpcodes(long[] opcodesArray) {
        Map<Long, Long> opcodes = new HashMap<>();
        long counter = 0;
        for (long l : opcodesArray) {
            opcodes.put(counter, l);
            counter++;
        }
        return opcodes;
    }

}
