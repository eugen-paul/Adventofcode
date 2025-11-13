package net.eugenpaul.adventofcode.y2023.day3;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day3 extends SolutionTemplate {

    private record N(int n, Set<SimplePos> poss) {

    }

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    private long id = 0;

    private Map<Long, Long> idToNumber = new HashMap<>();
    private Map<String, Long> numbersId = new HashMap<>();

    public static void main(String[] args) {
        Day3 puzzle = new Day3();
        puzzle.doPuzzleFromFile("y2023/day3/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        int response = 0;

        List<N> numbers = new LinkedList<>();
        Map<SimplePos, Character> others = new HashMap<>();

        int y = 0;
        for (var line : eventData) {
            int x = 0;
            Integer cur = null;
            Set<SimplePos> p = new HashSet<>();
            for (var c : line.toCharArray()) {
                if (Character.isDigit(c)) {
                    if (cur == null) {
                        cur = c - '0';
                    } else {
                        cur = cur * 10 + c - '0';
                    }
                    p.add(new SimplePos(x, y));
                } else {
                    if (cur != null) {
                        numbers.add(new N(cur, p));
                        cur = null;
                        p = new HashSet<>();
                    }
                    if (c != '.') {
                        others.put(new SimplePos(x, y), c);
                    }
                }
                x++;
            }
            if (cur != null) {
                numbers.add(new N(cur, p));
            }
            y++;
        }

        for (var e : numbers) {
            if (e.poss.stream().flatMap(v->v.getNeighbors(true).stream()).anyMatch(others::containsKey)) {
                response += e.n;
            }
        }

        return response;
    }

    public long doPuzzle1_old(List<String> eventData) {
        int response = 0;
        int start = 999_999;
        for (int y = 0; y < eventData.size(); y++) {
            for (int x = 0; x < eventData.get(0).length(); x++) {
                if (isCharAt(x, y, eventData)) {
                    start = Math.min(start, x);
                } else {
                    response += number(start, x - 1, y, eventData);
                    start = 999_999;
                }
            }
            response += number(start, eventData.get(0).length() - 1, y, eventData);
            start = 999_999;
        }

        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        int response = 0;

        List<N> numbers = new LinkedList<>();
        Map<SimplePos, Character> others = new HashMap<>();

        int y = 0;
        for (var line : eventData) {
            int x = 0;
            Integer cur = null;
            Set<SimplePos> p = new HashSet<>();
            for (var c : line.toCharArray()) {
                if (Character.isDigit(c)) {
                    if (cur == null) {
                        cur = toInt(c);
                    } else {
                        cur = cur * 10 + toInt(c);
                    }
                    p.add(new SimplePos(x, y));
                } else {
                    if (cur != null) {
                        numbers.add(new N(cur, p));
                        cur = null;
                        p = new HashSet<>();
                    }
                    if (c != '.') {
                        others.put(new SimplePos(x, y), c);
                    }
                }
                x++;
            }
            if (cur != null) {
                numbers.add(new N(cur, p));
            }
            y++;
        }

        for (var e : others.entrySet()) {
            if (e.getValue() != '*') {
                continue;
            }
            Set<N> n = new HashSet<>();
            for (var pos : e.getKey().getNeighbors(true)) {
                N nn = numbers.stream().filter(v -> v.poss.contains(pos)).findFirst().orElse(null);
                if (nn != null) {
                    n.add(nn);
                }
            }
            var nList = new ArrayList<>(n);
            if (nList.size() == 2) {
                response += nList.get(0).n * nList.get(1).n;
            }
        }

        return response;
    }

    public long doPuzzle2_old(List<String> eventData) {
        int response = 0;
        for (int y = 0; y < eventData.size(); y++) {
            int gear = -1;
            while (true) {
                gear = eventData.get(y).indexOf("*", gear + 1);
                if (gear == -1) {
                    break;
                }

                response += getRatio(gear, y);
            }
        }
        return response;
    }

    private long getRatio(int gear, int y) {
        long a = -1L;
        long b = -1L;

        int[][] offsets = { { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 } };

        for (int[] offset : offsets) {
            int offsetX = offset[0];
            int offsetY = offset[1];
            String key = (gear + offsetX) + ":" + (y + offsetY);
            if (numbersId.containsKey(key)) {
                Long c = numbersId.get(key);
                if (a == -1 || a == c) {
                    a = c;
                } else {
                    b = c;
                }
            }
        }
        if (b != -1) {
            return idToNumber.get(a) * idToNumber.get(b);
        }
        return 0;
    }

    private boolean isOk(int startX, int endX, int y, List<String> eventData) {
        boolean response = false;
        for (int x = startX - 1; x <= endX + 1; x++) {
            if (isSymbol(x, y - 1, eventData)) {
                response = true;
                break;
            }
        }
        for (int x = startX - 1; x <= endX + 1; x++) {
            if (isSymbol(x, y + 1, eventData)) {
                response = true;
                break;
            }
        }
        response = response || isSymbol(startX - 1, y, eventData) || isSymbol(endX + 1, y, eventData);
        return response;
    }

    private long number(int start, int end, int lineNr, List<String> eventData) {
        if (start == 999_999) {
            return 0;
        }
        if (!isOk(start, end, lineNr, eventData)) {
            return 0;
        }

        Long r = Long.valueOf(eventData.get(lineNr).substring(start, end + 1));
        for (int x = start; x <= end; x++) {
            numbersId.put(x + ":" + lineNr, id);
        }
        idToNumber.put(id, r);
        id++;

        return r;
    }

    private boolean isCharAt(int x, int y, List<String> eventData) {
        if (y < 0 || y >= eventData.size()) {
            return false;
        }
        if (x < 0 || x >= eventData.get(y).length()) {
            return false;
        }
        return Character.isDigit(eventData.get(y).charAt(x));
    }

    private boolean isSymbol(int x, int y, List<String> eventData) {
        if (y < 0 || y >= eventData.size()) {
            return false;
        }
        if (x < 0 || x >= eventData.get(y).length()) {
            return false;
        }
        return eventData.get(y).charAt(x) != '.' && !Character.isDigit(eventData.get(y).charAt(x));
    }

}
