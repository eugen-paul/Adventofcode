package net.eugenpaul.adventofcode.y2024.day25;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import com.google.common.collect.Lists;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day25 extends SolutionTemplate {

    private record Comb(int a, int b, int c, int d, int e) {
    }

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day25 puzzle = new Day25();
        puzzle.doPuzzleFromFile("y2024/day25/puzzle1.txt");
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
        var ll = asListList(eventData);
        List<List<String>> keys = new LinkedList<>();
        List<List<String>> locks = new LinkedList<>();

        for (var d : ll) {
            if (d.get(0).equals("#####"))
                locks.add(d);
            else
                keys.add(d);
        }

        var r = Lists.cartesianProduct(keys, locks).stream()
        .map(v->v.stream().flatMap(Collection::stream).toList())
        .map(v->turnRightStrings(v))
        .map(v->v.stream().mapToInt(z->z.replace(".", "").length()).max().orElseGet(null))
        .filter(v->v <= 7)
        .count()
        ;

        logger.log(Level.INFO, "Solution 1 " + r);
        return r;
    }

    public long doPuzzle1_b(List<String> eventData) {
        var ll = asListList(eventData);
        List<List<String>> keys = new LinkedList<>();
        List<List<String>> locks = new LinkedList<>();

        for (var d : ll) {
            if (d.get(0).equals("#####"))
                locks.add(d);
            else
                keys.add(d);
        }

        long r = 0;
        for (var k : keys) {
            for (var l : locks) {
                boolean ok = true;
                for (int col = 0; col < k.get(0).length(); col++) {
                    int sum = 0;
                    for (String row : k)
                        sum += row.charAt(col) == '#' ? 1 : 0;

                    for (String row : l)
                        sum += row.charAt(col) == '#' ? 1 : 0;

                    ok &= (sum <= 7);
                }
                if (ok)
                    r++;

            }
        }

        logger.log(Level.INFO, "Solution 1 " + r);
        return r;
    }

    public long doPuzzle1_a(List<String> eventData) {
        long response = 0;

        List<Comb> keys = new ArrayList<>();
        List<Comb> locks = new ArrayList<>();

        boolean first = true;
        boolean key = false;
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;

        for (String data : eventData) {
            if (data.isEmpty()) {
                first = true;
                if (key) {
                    keys.add(new Comb(a - 1, b - 1, c - 1, d - 1, e - 1));
                } else {
                    locks.add(new Comb(a - 1, b - 1, c - 1, d - 1, e - 1));
                }
                continue;
            }
            if (first) {
                key = data.equals(".....");
                a = 0;
                b = 0;
                c = 0;
                d = 0;
                e = 0;
                first = false;
            }

            if (data.charAt(0) == '#') {
                a++;
            }
            if (data.charAt(1) == '#') {
                b++;
            }
            if (data.charAt(2) == '#') {
                c++;
            }
            if (data.charAt(3) == '#') {
                d++;
            }
            if (data.charAt(4) == '#') {
                e++;
            }
        }
        if (key) {
            keys.add(new Comb(a - 1, b - 1, c - 1, d - 1, e - 1));
        } else {
            locks.add(new Comb(a - 1, b - 1, c - 1, d - 1, e - 1));
        }

        for (var k : keys) {
            for (var lock : locks) {
                if (//
                k.a + lock.a <= 5//
                        && k.b + lock.b <= 5//
                        && k.c + lock.c <= 5//
                        && k.d + lock.d <= 5//
                        && k.e + lock.e <= 5//
                ) {
                    response++;
                }
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
