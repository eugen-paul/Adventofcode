package net.eugenpaul.adventofcode.y2021.day4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day4 extends SolutionTemplate {

    private class BingoField {
        private static final int GRID = 5;
        private Map<SimplePos, Integer> field;

        public BingoField() {
            field = new HashMap<>();
        }

        public void addRow(List<Integer> row) {
            int x = field.size() / GRID;
            int y = 0;
            for (Integer number : row) {
                field.put(new SimplePos(x, y), number);
                y++;
            }
        }

        public int score() {
            return field.values().stream().reduce(0, (a, b) -> a + b);
        }

        /**
         * 
         * @param number - number are drawn
         * @return true if all numbers in any row or any column of a board are marked
         */
        public boolean draw(int number) {
            SimplePos pos = null;
            for (var entry : field.entrySet()) {
                if (entry.getValue().intValue() == number) {
                    pos = entry.getKey();
                    field.remove(entry.getKey());
                    break;
                }
            }
            if (pos != null) {
                return isOver(pos);
            }
            return false;
        }

        private boolean isOver(SimplePos removedPos) {
            boolean end = true;
            for (int x = 0; x < GRID; x++) {
                if (field.containsKey(new SimplePos(x, removedPos.getY()))) {
                    end = false;
                    break;
                }
            }

            if (end) {
                return true;
            }

            end = true;

            for (int y = 0; y < GRID; y++) {
                if (field.containsKey(new SimplePos(removedPos.getX(), y))) {
                    end = false;
                    break;
                }
            }
            return end;
        }
    }

    @Getter
    private long winScore;
    @Getter
    private long lastWinScore;

    public static void main(String[] args) {
        Day4 puzzle = new Day4();
        puzzle.doPuzzleFromFile("y2021/day4/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        winScore = getFirstWinScore(eventData);
        lastWinScore = getLastWinScore(eventData);

        logger.log(Level.INFO, () -> "winScore  : " + getWinScore());
        logger.log(Level.INFO, () -> "lastWinScore  : " + getLastWinScore());

        return true;
    }

    private long getFirstWinScore(List<String> eventData) {
        var drawnNumbers = StringConverter.toIntegerArrayList(eventData.get(0));
        var fields = readFields(eventData);
        for (var currentNumber : drawnNumbers) {
            for (var field : fields) {
                if (field.draw(currentNumber)) {
                    return (long) currentNumber * field.score();
                }
            }
        }
        return -1;
    }

    private long getLastWinScore(List<String> eventData) {
        var drawnNumbers = StringConverter.toIntegerArrayList(eventData.get(0));
        var fields = readFields(eventData);

        for (var currentNumber : drawnNumbers) {
            var fieldIterator = fields.iterator();
            while (fieldIterator.hasNext()) {
                var field = fieldIterator.next();
                if (field.draw(currentNumber)) {
                    if (fields.size() == 1) {
                        return (long) currentNumber * field.score();
                    }
                    fieldIterator.remove();
                }
            }
        }
        return -1;
    }

    private List<BingoField> readFields(List<String> eventData) {
        var iterator = eventData.listIterator(1);
        List<BingoField> fields = new LinkedList<>();
        BingoField field = new BingoField();

        while (iterator.hasNext()) {
            var data = iterator.next();
            if (data.isEmpty()) {
                field = new BingoField();
                fields.add(field);
                continue;
            }
            field.addRow(StringConverter.toIntegerArrayList(data.trim().replace("  ", " ")));
        }
        return fields;
    }
}
