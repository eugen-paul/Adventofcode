package net.eugenpaul.adventofcode.y2025.day6;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static net.eugenpaul.adventofcode.helper.StringConverter.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MatrixHelper;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day6 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day6 puzzle = new Day6();
        puzzle.doPuzzleFromFile("y2025/day6/puzzle1.txt");
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

        String ll = eventData.get(eventData.size() - 1);
        for (int i = 0; i < 20; i++) {
            ll = ll.replace("  ", " ");
        }
        String[] m = ll.split(" ");

        List<List<Long>> z = new ArrayList<>();
        for (int i = 0; i < eventData.size() - 1; i++) {
            z.add(StringConverter.toLongArrayList(eventData.get(i)));
        }

        for (int i = 0; i < m.length; i++) {
            var c = m[i];
            if (c.charAt(0) == '+') {
                var res = 0L;
                for (var zz : z) {
                    res += zz.get(i);
                }
                response += res;
            } else {
                var res = 1L;
                for (var zz : z) {
                    res *= zz.get(i);
                }
                response += res;
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        String ll = eventData.get(eventData.size() - 1);
        for (int i = 0; i < 20; i++) {
            ll = ll.replace("  ", " ");
        }
        String[] m = ll.split(" ");

        List<String> dd = new ArrayList<>(eventData);
        dd.removeLast();

        dd = rotateRStrings(dd, 3);

        var ddd = asListList(dd);

        for (int i = 0; i < m.length; i++) {
            var c = m[i];
            if (c.charAt(0) == '+') {
                var res = 0L;
                for (var zz : ddd.get(ddd.size() - i - 1)) {
                    res += Long.valueOf(zz.trim());
                }
                response += res;
            } else {
                var res = 1L;
                for (var zz : ddd.get(ddd.size() - i - 1)) {
                    res *= Long.valueOf(zz.trim());
                }
                response += res;
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    public long doPuzzle2_a(List<String> eventData) {
        long response = 0;

        String ll = eventData.get(eventData.size() - 1);
        for (int i = 0; i < 20; i++) {
            ll = ll.replace("  ", " ");
        }
        String[] m = ll.split(" ");

        List<List<Long>> z = new ArrayList<>();
        for (int i = 0; i < eventData.size() - 1; i++) {
            z.add(StringConverter.toLongArrayList(eventData.get(i)));
        }

        start = 0;

        for (int i = 0; i < m.length; i++) {
            var c = m[i];
            if (c.charAt(0) == '+') {
                var res = 0L;
                List<Long> data = new ArrayList<>();
                for (var zz : z) {
                    data.add(zz.get(i));
                }
                data = read2(eventData);
                for (var zz : data) {
                    res += zz;
                }
                response += res;
            } else {
                var res = 1L;
                List<Long> data = new ArrayList<>();
                for (var zz : z) {
                    data.add(zz.get(i));
                }
                data = read2(eventData);
                for (var zz : data) {
                    res *= zz;
                }
                response += res;
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private int start;

    private List<Long> read2(List<String> eventData) {
        int end = start;
        while (eventData.get(0).length() > end + 1) {
            boolean ok = true;
            end++;
            for (String d : eventData) {
                if (d.charAt(end) != ' ') {
                    ok = false;
                }
            }
            if (ok) {
                break;
            }
        }

        List<String> data = new ArrayList<>();
        for (int i = 0; i < eventData.size() - 1; i++) {
            data.add(eventData.get(i).substring(start, end + 1));
        }

        List<Long> dd = read(data);
        start = end;
        return dd;

    }

    private List<Long> read(List<String> data) {
        List<String> d;
        d = MatrixHelper.rotateRStrings(data, 3);
        return d.stream().map(String::trim).filter(v -> !v.isEmpty()).map(Long::valueOf).toList();
    }

}
