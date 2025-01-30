package net.eugenpaul.adventofcode.y2023.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day14 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    private int maxX;
    private int maxY;

    public static void main(String[] args) {
        Day14 puzzle = new Day14();
        puzzle.doPuzzleFromFile("y2023/day14/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        maxX = eventData.get(0).length();
        maxY = eventData.size();

        Map<SimplePos, Character> m = StringConverter.toMap(eventData);

        for (int y = 1; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                SimplePos pos = new SimplePos(x, y);
                if (m.get(pos) == 'O') {
                    rollN(m, pos);
                }
            }
        }

        response = m.entrySet().stream()//
                .filter(v -> v.getValue() == 'O')//
                .mapToLong(v -> maxY - v.getKey().getY()) //
                .sum();

        logger.log(Level.INFO, "Solution 1 {0}", response);
        return response;
    }

    private void rollN(Map<SimplePos, Character> m, SimplePos pos) {
        var p = pos.copy();
        boolean wall = false;
        for (int y = pos.getY() - 1; y >= 0; y--) {
            p.move(Direction.N);
            if (m.get(p) != '.') {
                wall = true;
                break;
            }
        }
        m.put(pos, '.');

        if (wall) {
            m.put(p.move(Direction.S), 'O');
        } else {
            m.put(p, 'O');
        }
    }

    private void rollS(Map<SimplePos, Character> m, SimplePos pos) {
        var p = pos.copy();
        boolean wall = false;
        for (int y = pos.getY() + 1; y < maxY; y++) {
            p.move(Direction.S);
            if (m.get(p) != '.') {
                wall = true;
                break;
            }
        }
        m.put(pos, '.');

        if (wall) {
            m.put(p.move(Direction.N), 'O');
        } else {
            m.put(p, 'O');
        }
    }

    private void rollW(Map<SimplePos, Character> m, SimplePos pos) {
        var p = pos.copy();
        boolean wall = false;
        for (int x = pos.getX() - 1; x >= 0; x--) {
            p.move(Direction.W);
            if (m.get(p) != '.') {
                wall = true;
                break;
            }
        }
        m.put(pos, '.');

        if (wall) {
            m.put(p.move(Direction.E), 'O');
        } else {
            m.put(p, 'O');
        }
    }

    private void rollE(Map<SimplePos, Character> m, SimplePos pos) {
        var p = pos.copy();
        boolean wall = false;
        for (int x = pos.getX() + 1; x < maxX; x++) {
            p.move(Direction.E);
            if (m.get(p) != '.') {
                wall = true;
                break;
            }
        }
        m.put(pos, '.');

        if (wall) {
            m.put(p.move(Direction.W), 'O');
        } else {
            m.put(p, 'O');
        }
    }

    private void cycle(Map<SimplePos, Character> m) {
        for (int y = 1; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                SimplePos pos = new SimplePos(x, y);
                if (m.get(pos) == 'O') {
                    rollN(m, pos);
                }
            }
        }

        for (int x = 1; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                SimplePos pos = new SimplePos(x, y);
                if (m.get(pos) == 'O') {
                    rollW(m, pos);
                }
            }
        }

        for (int y = maxY - 2; y >= 0; y--) {
            for (int x = 0; x < maxX; x++) {
                SimplePos pos = new SimplePos(x, y);
                if (m.get(pos) == 'O') {
                    rollS(m, pos);
                }
            }
        }

        for (int x = maxX - 2; x >= 0; x--) {
            for (int y = 0; y < maxY; y++) {
                SimplePos pos = new SimplePos(x, y);
                if (m.get(pos) == 'O') {
                    rollE(m, pos);
                }
            }
        }
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        maxX = eventData.get(0).length();
        maxY = eventData.size();

        Map<SimplePos, Character> m = StringConverter.toMap(eventData);
        Map<String, Integer> hashs = new HashMap<>();

        boolean skip = false;
        for (int i = 0; i < 1_000_000_000; i++) {
            cycle(m);
            String h = hash(m);
            if (!skip && hashs.containsKey(h)) {
                skip = true;
                int per = i - hashs.get(h);
                int rest = 1_000_000_000 - i;
                i += (rest / per) * per;
            }
            hashs.put(hash(m), i);
        }

        response = m.entrySet().stream()//
                .filter(v -> v.getValue() == 'O')//
                .mapToLong(v -> maxY - v.getKey().getY()) //
                .sum();

        logger.log(Level.INFO, "Solution 2 {0}", response);
        return response;
    }

    private String hash(Map<SimplePos, Character> m) {
        return m.entrySet().stream()//
                .filter(v -> v.getValue() == 'O')//
                .map(Entry::getKey)//
                .sorted((a, b) -> {
                    if (a.getX() != b.getX()) {
                        return Integer.compare(a.getX(), b.getX());
                    }
                    return Integer.compare(a.getY(), b.getY());
                })//
                .map(v -> (v.getX() * 1000 + v.getY()) + "")//
                .reduce((a, b) -> a + ":" + b).orElseThrow();
    }
}
