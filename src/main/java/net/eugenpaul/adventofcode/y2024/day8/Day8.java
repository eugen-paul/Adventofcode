package net.eugenpaul.adventofcode.y2024.day8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day8 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day8 puzzle = new Day8();
        puzzle.doPuzzleFromFile("y2024/day8/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        return doEvent(List.of(eventData));
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        Map<SimplePos, Character> d = StringConverter.toMap(eventData);
        Map<Character, List<SimplePos>> d2 = new HashMap<>();
        for (var entry : d.entrySet()) {
            if (entry.getValue() == '.') {
                continue;
            }
            d2.compute(entry.getValue(), (k, v) -> {
                if (v == null) {
                    v = new ArrayList<SimplePos>();
                }
                v.add(entry.getKey());
                return v;
            });
        }

        Set<SimplePos> antennas = new HashSet<>();

        for (var pos : d2.values()) {
            for (int a = 0; a < pos.size(); a++) {
                for (int b = a + 1; b < pos.size(); b++) {
                    SimplePos aPos = pos.get(a);
                    SimplePos bPos = pos.get(b);
                    antennas.add(aPos.subNew(bPos.subNew(aPos)));
                    antennas.add(bPos.addNew(bPos.subNew(aPos)));
                }
            }
        }

        response = antennas.stream()//
                .filter(v -> v.getX() >= 0 && v.getX() < eventData.get(0).length())//
                .filter(v -> v.getY() >= 0 && v.getY() < eventData.size())//
                .count();

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        Map<SimplePos, Character> d = StringConverter.toMap(eventData);
        Map<Character, List<SimplePos>> d2 = new HashMap<>();
        for (var entry : d.entrySet()) {
            if (entry.getValue() == '.') {
                continue;
            }
            d2.compute(entry.getValue(), (k, v) -> {
                if (v == null) {
                    v = new ArrayList<SimplePos>();
                }
                v.add(entry.getKey());
                return v;
            });
        }

        Set<SimplePos> antennas = new HashSet<>();

        for (var pos : d2.values()) {
            for (int a = 0; a < pos.size(); a++) {
                for (int b = a + 1; b < pos.size(); b++) {
                    SimplePos aPos = pos.get(a);
                    SimplePos bPos = pos.get(b);
                    SimplePos delta = bPos.subNew(aPos);
                    SimplePos start = aPos;
                    while (start.inRange(0, eventData.get(0).length() - 1L, 0, eventData.size() - 1L)) {
                        antennas.add(start);
                        start = start.addNew(delta);
                    }
                    start = aPos;
                    while (start.inRange(0, eventData.get(0).length() - 1L, 0, eventData.size() - 1L)) {
                        antennas.add(start);
                        start = start.subNew(delta);
                    }
                }
            }
        }

        response = antennas.size();

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
