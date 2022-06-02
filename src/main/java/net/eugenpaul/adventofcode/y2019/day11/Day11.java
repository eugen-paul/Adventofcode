package net.eugenpaul.adventofcode.y2019.day11;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.IntcodeMapComputer;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day11 extends SolutionTemplate {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private enum Color {
        BLACK(0), WHITE(1);

        private final int value;

        public static Color fromValue(int i) {
            if (i == 0) {
                return BLACK;
            }
            return WHITE;
        }
    }

    @Getter
    private int panels;
    @Getter
    private String text;

    public static void main(String[] args) {
        Day11 puzzle = new Day11();
        puzzle.doPuzzleFromFile("y2019/day11/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        long[] opcodesArray = StringConverter.toLongArray(eventData);

        panels = doPuzzle(opcodesArray, Color.BLACK).size();

        Map<SimplePos, Color> painted = doPuzzle(opcodesArray, Color.WHITE);

        MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(painted, v -> (v == Color.BLACK) ? ' ' : '#'));

        text = MapOfSimplePos.mapToPrintList(painted, v -> (v == Color.BLACK) ? ' ' : '#').stream()//
                .reduce("", (a, b) -> a + b);

        logger.log(Level.INFO, () -> "panels     : " + getPanels());

        return true;
    }

    private Map<SimplePos, Color> doPuzzle(long[] opcodesArray, Color startColor) {
        Map<Long, Long> opcodes = new HashMap<>();
        long counter = 0;
        for (long l : opcodesArray) {
            opcodes.put(counter, l);
            counter++;
        }

        Map<SimplePos, Color> way = new HashMap<>();

        Color currentColor = startColor;

        SimplePos currentPosition = new SimplePos(0, 0);
        way.put(currentPosition, currentColor);

        Direction currentDirection = Direction.N;

        IntcodeMapComputer comp = new IntcodeMapComputer();
        int pos = 0;
        comp.setInput(currentColor.value);

        int outputNumber = 0;

        while (!comp.isEnd(opcodes, pos)) {
            pos = comp.runOpcodes(opcodes, pos);
            if (comp.isOutput()) {
                outputNumber++;
                if (outputNumber % 2 == 1) {
                    // firstOutput => read color
                    Color nextColor = Color.fromValue(comp.removeOutput().intValue());
                    way.put(currentPosition, nextColor);
                } else {
                    if (comp.removeOutput().intValue() == 0) {
                        currentDirection = currentDirection.turnLeft();
                    } else {
                        currentDirection = currentDirection.turnRight();
                    }
                    currentPosition = currentPosition.moveNew(currentDirection);
                    comp.setInput(way.getOrDefault(currentPosition, Color.BLACK).value);
                }
            }
        }

        return way;
    }

}
