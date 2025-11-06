package net.eugenpaul.adventofcode.y2024.day21;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Permutation;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day21 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    private static Map<String, List<String>> kp;
    private static Map<String, List<String>> dp;

    static {
        kp = new HashMap();
        kp.put("77", List.of(""));
        kp.put("78", List.of(">"));
        kp.put("79", List.of(">>"));
        kp.put("74", List.of("v"));
        kp.put("75", List.of("v>", ">v"));
        kp.put("76", List.of("v>>", ">v>", ">>v"));
        kp.put("71", List.of("vv"));
        kp.put("72", List.of("vv>", "v>v", ">vv"));
        kp.put("73", List.of("vv>>", "v>v>", "v>>v", ">vv>", ">v>v", ">>vv"));
        kp.put("70", List.of(">vvv", "v>vv", "vv>v"));
        kp.put("7A", List.of(">vvv>", ">vv>v", ">v>vv", ">>vvv", "v>vv>", "v>v>v", "v>>vv", "vv>v>", "vv>>v"));
        kp.put("87", List.of("<"));
        kp.put("88", List.of(""));
        kp.put("89", List.of(">"));
        kp.put("84", List.of("<v", "v<"));
        kp.put("85", List.of("v"));
        kp.put("86", List.of("v>", ">v"));
        kp.put("81", List.of("vv<", "v<v", "<vv"));
        kp.put("82", List.of("vv"));
        kp.put("83", List.of("vv>", "v>v", ">vv"));
        kp.put("80", List.of("vvv"));
        kp.put("8A", List.of("vvv>", "vv>v", "v>vv", ">vvv"));
        kp.put("97", List.of("<<"));
        kp.put("98", List.of("<"));
        kp.put("99", List.of("<"));
        kp.put("94", List.of("v<<", "<v<", "<<v"));
        kp.put("95", List.of("v<", "<v"));
        kp.put("96", List.of("v"));
        kp.put("91", List.of("vv<<", "v<v<", "v<<v", "<v<v", "<<vv", "<vv<"));
        kp.put("92", List.of("vv<", "v<v", "<vv"));
        kp.put("93", List.of("vv"));
        kp.put("90", List.of("vvv<", "vv<v", "v<vv", "<vvv"));
        kp.put("9A", List.of("vvv"));
        kp.put("47", List.of("^"));
        kp.put("48", List.of("^>", ">^"));
        kp.put("49", List.of("^>>", ">^>", ">>^"));
        kp.put("44", List.of(""));
        kp.put("45", List.of(">"));
        kp.put("46", List.of(">>"));
        kp.put("41", List.of("v"));
        kp.put("42", List.of("v>", ">v"));
        kp.put("43", List.of("v>>", ">v>", ">>v"));
        kp.put("40", List.of(">vv", "v>v"));
        kp.put("4A", List.of("v>v>", "v>>v", ">vv>", ">v>v", ">>vv"));
        kp.put("57", List.of("^<", "<^"));
        kp.put("58", List.of("^"));
        kp.put("59", List.of("^>", ">^"));
        kp.put("54", List.of("<"));
        kp.put("55", List.of(""));
        kp.put("56", List.of(">"));
        kp.put("51", List.of("v<", "<v"));
        kp.put("52", List.of("v"));
        kp.put("53", List.of("v>", ">v"));
        kp.put("50", List.of("vv"));
        kp.put("5A", List.of("vv>", "v>v", ">vv"));
        kp.put("67", List.of("^<<", "<^<", "<<^"));
        kp.put("68", List.of("^<", "<^"));
        kp.put("69", List.of("^"));
        kp.put("64", List.of("<<"));
        kp.put("65", List.of("<"));
        kp.put("66", List.of(""));
        kp.put("61", List.of("v<<", "<v<", "<<v"));
        kp.put("62", List.of("v<", "<v"));
        kp.put("63", List.of("v"));
        kp.put("60", List.of("vv<", "v<v", "<vv"));
        kp.put("6A", List.of("vv"));
        kp.put("17", List.of("^^"));
        kp.put("18", List.of("^^>", "^>^", ">^^"));
        kp.put("19", List.of("^^>>", "^>^>", ">^^>", ">^>^", ">>^^", "^>>^"));
        kp.put("14", List.of("^"));
        kp.put("15", List.of("^>", ">^"));
        kp.put("16", List.of("^>>", ">^>", ">>^"));
        kp.put("11", List.of(""));
        kp.put("12", List.of(">"));
        kp.put("13", List.of(">>"));
        kp.put("10", List.of(">v", "v>"));
        kp.put("1A", List.of(">>v", ">v>"));
        kp.put("27", List.of("^^<", "^<^", "<^^"));
        kp.put("28", List.of("^^"));
        kp.put("29", List.of("^^>", "^>^", ">^^"));
        kp.put("24", List.of("^<", "<^"));
        kp.put("25", List.of("^"));
        kp.put("26", List.of("^>", ">^"));
        kp.put("21", List.of("<"));
        kp.put("22", List.of(""));
        kp.put("23", List.of(">"));
        kp.put("20", List.of("v"));
        kp.put("2A", List.of("v>", ">v"));
        kp.put("37", List.of("^^<<", "^<^<", "^<<^", "<^^<", "<<^^", "<^<^"));
        kp.put("38", List.of("^^<", "^<^", "<^^"));
        kp.put("39", List.of("^^"));
        kp.put("34", List.of("^<<", "<^<", "<<^"));
        kp.put("35", List.of("^<", "<^"));
        kp.put("36", List.of("^"));
        kp.put("31", List.of("<<"));
        kp.put("32", List.of("<"));
        kp.put("33", List.of(""));
        kp.put("30", List.of("v<", "<v"));
        kp.put("3A", List.of("v"));
        kp.put("07", List.of("^^^<", "^^<^", "^<^^"));
        kp.put("08", List.of("^^^"));
        kp.put("09", List.of("^^^>", "^^>^", "^>^^", ">^^^"));
        kp.put("04", List.of("^^<", "^<^"));
        kp.put("05", List.of("^^"));
        kp.put("06", List.of("^^>", "^>^", ">^^"));
        kp.put("01", List.of("^<"));
        kp.put("02", List.of("^"));
        kp.put("03", List.of("^>", ">^"));
        kp.put("00", List.of(""));
        kp.put("0A", List.of(">"));
        kp.put("A7", List.of("<^<^^", "<^^<^", "<^^^<", "^<<^^", "^<^<^", "^<^^<", "^^<<^", "^^<^<", "^^^<<"));
        kp.put("A8", List.of("^^^<", "^^<^", "^<^^", "<^^^"));
        kp.put("A9", List.of("^^^"));
        kp.put("A4", List.of("^^<<", "^<^<", "<^^<", "^<<^", "<^<^"));
        kp.put("A5", List.of("^^<", "^<^", "<^^"));
        kp.put("A6", List.of("^^"));
        kp.put("A1", List.of("^<<", "<^<"));
        kp.put("A2", List.of("^<", "<^"));
        kp.put("A3", List.of("^"));
        kp.put("A0", List.of("<"));
        kp.put("AA", List.of(""));

        dp = new HashMap<>();
        dp.put("^^", List.of(""));
        dp.put("^A", List.of(">"));
        dp.put("^<", List.of("v<"));
        dp.put("^v", List.of("v"));
        dp.put("^>", p("v>", null));

        dp.put("<^", List.of(">^"));
        dp.put("<A", p(">>^", "^>>"));
        dp.put("<<", List.of(""));
        dp.put("<v", List.of(">"));
        dp.put("<>", List.of(">>"));

        dp.put("v^", List.of("^"));
        dp.put("vA", p("^>", null));
        dp.put("v<", List.of("<"));
        dp.put("vv", List.of(""));
        dp.put("v>", List.of(">"));

        dp.put(">^", p("<^", null));
        dp.put(">A", List.of("^"));
        dp.put("><", List.of("<<"));
        dp.put(">v", List.of("<"));
        dp.put(">>", List.of(""));

        dp.put("A^", List.of("<"));
        dp.put("AA", List.of(""));
        dp.put("A<", p("v<<", "<<v"));
        dp.put("Av", p("v<", null));
        dp.put("A>", List.of("v"));
    }

    public static void main(String[] args) {
        Day21 puzzle = new Day21();
        puzzle.doPuzzleFromFile("y2024/day21/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        return doEvent(List.of(eventData));
    }

    private static List<String> p(String in, String exception) {
        char[] c = in.toCharArray();
        List<String> r = new LinkedList<>();
        Permutation.permutationsRecursive(in.length(), c, a -> {
            String d = new String(a);
            if (exception != null && d.equals(exception)) {
                return;
            }
            r.add(d);
        });
        return r;
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        response = solution(eventData, response, 2);

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private long cnt(String cmd, int nb, Map<String, Map<Integer, Long>> cndToNbToBestlen) {
        Long resp = cndToNbToBestlen.getOrDefault(cmd, Collections.emptyMap()).get(nb);
        if (resp != null) {
            return resp;
        }

        if (nb == 0) {
            return cmd.length();
        }

        String pos = "A";
        resp = 0L;

        for (char c : cmd.toCharArray()) {
            String mv = pos + c;
            List<String> values = dp.get(mv);
            long bestV = Long.MAX_VALUE;
            for (var v : values) {
                bestV = Math.min(bestV, cnt(v + "A", nb - 1, cndToNbToBestlen));
            }
            resp += bestV;
            pos = c + "";
        }

        cndToNbToBestlen//
                .computeIfAbsent(cmd, k -> new HashMap<>())//
                .put(nb, resp);

        return resp;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        response = solution(eventData, response, 25);

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private long solution(List<String> eventData, long response, int steps) {
        for (String code : eventData) {
            if (code.isBlank()) {
                continue;
            }

            // Calculate all possible variations for the input direct on numeric keypad
            List<List<String>> r1 = new ArrayList<>();
            r1.add(Collections.emptyList());

            String pos = "A";
            for (char c : code.toCharArray()) {
                String mv = pos + c;
                List<String> values = kp.get(mv);
                List<List<String>> r1Copy = new ArrayList<>();
                for (var v : values) {
                    for (var r1v : r1) {
                        List<String> tt = new LinkedList<>(r1v);
                        tt.add(v + "A");
                        r1Copy.add(tt);
                    }
                }
                r1 = r1Copy;
                pos = c + "";
            }
            Map<String, Map<Integer, Long>> cndToNbToBestlen = new HashMap<>();

            // Calculate all possible variations for the input on directional keypad.
            // Since you always have to press A when entering text on the directional keypad, it's like a reset => determine the best input method for each part
            // separately.
            long shortest = Long.MAX_VALUE;
            for (var codeBlock : r1) {
                long bestLen = 0;
                for (var word : codeBlock) {
                    bestLen += cnt(word, steps, cndToNbToBestlen);
                }
                shortest = Math.min(shortest, bestLen);
            }

            response += shortest * Integer.parseInt(code.substring(0, 3));
        }
        return response;
    }
}
