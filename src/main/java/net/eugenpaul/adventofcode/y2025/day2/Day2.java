package net.eugenpaul.adventofcode.y2025.day2;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static net.eugenpaul.adventofcode.helper.StringConverter.*;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.LongStream;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Range;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day2 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day2 puzzle = new Day2();
        puzzle.doPuzzleFromFile("y2025/day2/puzzle1.txt");
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
        for (var d : eventData.get(0).split(",")) {
            response += LongStream.rangeClosed(toLong(d.split("-")[0]), toLong(d.split("-")[1]))//
                    .filter(v -> {
                        return (v + "").matches("^(.+)\\1$"); // reddit tipp
                    }).sum();
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle1_c(List<String> eventData) {
        long response = 0;
        for (var d : eventData.get(0).split(",")) {
            response += LongStream.rangeClosed(toLong(d.split("-")[0]), toLong(d.split("-")[1]))//
                    .filter(v -> {
                        var s = v + "";
                        return s.substring(0, s.length() / 2).equals(s.substring(s.length() / 2));
                    }).sum();
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle1_b(List<String> eventData) {
        long response = 0;
        for (var d : eventData.get(0).split(",")) {
            var r = Range.fromString(d, "-");
            response += r.stream().filter(v -> {
                var s = v + "";
                return s.substring(0, s.length() / 2).equals(s.substring(s.length() / 2));
            }).sum();
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle1_a(List<String> eventData) {
        long response = 0;

        for (var d : eventData.get(0).split(",")) {
            var a = toLong(d.split("-")[0]);
            var b = toLong(d.split("-")[1]);
            for (long i = a; i <= b; i++) {
                var s = i + "";
                var h1 = s.substring(0, s.length() / 2);
                var h2 = s.substring(s.length() / 2);
                if (h1.equals(h2)) {
                    response += i;
                }
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;
        for (var d : eventData.get(0).split(",")) {
            response += LongStream.rangeClosed(toLong(d.split("-")[0]), toLong(d.split("-")[1]))//
                    .filter(v -> {
                        return (v + "").matches("^(.+)\\1+$"); // reddit tipp
                    }).sum();
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    public long doPuzzle2_d(List<String> eventData) {
        long response = 0;
        for (var d : eventData.get(0).split(",")) {
            var r = Range.fromString(d, "-");
            response += r.stream().filter(v -> {
                var s = v + "";
                return (s + s).indexOf(s, 1) != s.length(); // solution megathread tipp
            }).sum();
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    public long doPuzzle2_c(List<String> eventData) {
        long response = 0;
        for (var d : eventData.get(0).split(",")) {
            var r = Range.fromString(d, "-");
            response += r.stream().filter(v -> {
                var s = v + "";
                for (int i = 1; i <= s.length() / 2; i++) {
                    if (/* s.length() % i == 0 && */ s.substring(0, i).repeat(s.length() / i).equals(s)) {
                        return true;
                    }
                }
                return false;
            }).sum();
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    public long doPuzzle2_b(List<String> eventData) {
        long response = 0;

        for (var d : eventData.get(0).split(",")) {
            var a = toLong(d.split("-")[0]);
            var b = toLong(d.split("-")[1]);
            for (long i = a; i <= b; i++) {
                var s = i + "";
                var ok = true;
                for (var k = 1; k <= s.length() / 2 && ok; k++) {
                    var dig = s.substring(0, k);
                    if (dig.repeat(s.length() / dig.length()).equals(s)) {
                        response += i;
                        break;
                    }
                }
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    public long doPuzzle2_a(List<String> eventData) {
        long response = 0;

        for (var d : eventData.get(0).split(",")) {
            var a = toLong(d.split("-")[0]);
            var b = toLong(d.split("-")[1]);
            for (long i = a; i <= b; i++) {
                var s = i + "";
                var ok = true;
                for (var k = 1; k <= s.length() / 2 && ok; k++) {
                    var dig = s.substring(0, k);
                    while (dig.length() <= s.length()) {
                        if (dig.equals(s)) {
                            ok = false;
                            response += i;
                            break;
                        }
                        dig = dig + s.substring(0, k);
                    }
                }
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
