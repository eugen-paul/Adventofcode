package net.eugenpaul.adventofcode.y2021.day8;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day8 extends SolutionTemplate {

    @AllArgsConstructor
    private enum Digit {
        ZERO(List.of('a', 'b', 'c', 'e', 'f', 'g'), 0), //
        ONE(List.of('c', 'f'), 1), //
        TWO(List.of('a', 'c', 'd', 'e', 'g'), 2), //
        THREE(List.of('a', 'c', 'd', 'f', 'g'), 3), //
        FOUR(List.of('b', 'c', 'd', 'f'), 4), //
        FIVE(List.of('a', 'b', 'd', 'f', 'g'), 5), //
        SIX(List.of('a', 'b', 'd', 'e', 'f', 'g'), 6), //
        SEVEN(List.of('a', 'c', 'f'), 7), //
        EIGHT(List.of('a', 'b', 'c', 'd', 'e', 'f', 'g'), 8), //
        NINE(List.of('a', 'b', 'c', 'd', 'f', 'g'), 9), //
        ;

        @Getter
        private final List<Character> segments;
        private final int value;

        public int getSize() {
            return segments.size();
        }
    }

    @Getter
    private long count;
    @Getter
    private long sum;

    public static void main(String[] args) {
        Day8 puzzle = new Day8();
        puzzle.doPuzzleFromFile("y2021/day8/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        count = doPuzzle1(eventData);
        sum = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "count  : " + getCount());
        logger.log(Level.INFO, () -> "sum  : " + getSum());

        return true;
    }

    private int doPuzzle1(List<String> eventData) {
        List<Digit> digits = List.of(Digit.ONE, Digit.FOUR, Digit.SEVEN, Digit.EIGHT);
        int response = 0;

        for (var data : eventData) {
            for (var code : data.split("\\|")[1].split(" ")) {
                if (digits.stream().anyMatch(v -> v.getSize() == code.length())) {
                    response++;
                }
            }
        }
        return response;
    }

    private int doPuzzle2(List<String> eventData) {
        return eventData.stream().mapToInt(this::getCode).sum();
    }

    private int getCode(String data) {
        Map<Character, Character> dec = analyse(data);
        return digitsToOutput(data.split("\\|")[1], dec);
    }

    private int digitsToOutput(String outputData, Map<Character, Character> dec) {
        int mult = 1000;
        int response = 0;
        for (var digits : outputData.trim().split(" ")) {
            List<Character> sequence = digits.chars()//
                    .mapToObj(c -> (char) c)//
                    .map(dec::get)//
                    .sorted().collect(Collectors.toList());

            for (var dCode : Digit.values()) {
                if (dCode.segments.equals(sequence)) {
                    response += dCode.value * mult;
                    mult /= 10;
                    break;
                }
            }
        }
        return response;
    }

    private Map<Character, Character> analyse(String data) {
        List<Digit> digits = List.of(Digit.ONE, Digit.FOUR, Digit.SEVEN, Digit.EIGHT);

        Map<Digit, List<Character>> digitToData = new HashMap<>();

        Map<Integer, List<List<Character>>> countToData = new HashMap<>();

        for (var code : data.split(" ")) {
            if (code.length() > 1) {
                List<Character> sequence = code.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
                countToData.computeIfAbsent(sequence.size(), v -> new LinkedList<>())//
                        .add(sequence);

                for (Digit d : digits) {
                    if (d.getSize() == code.length()) {
                        digitToData.put(//
                                d, //
                                sequence //
                        );
                    }
                }
            }
        }

        Map<Character, Character> response = new HashMap<>();

        Character a = computeA(digitToData.get(Digit.ONE), digitToData.get(Digit.SEVEN));
        response.put(a, 'a');

        Character c = computeC(digitToData.get(Digit.SEVEN), countToData.get(6));
        response.put(c, 'c');

        Character f = computeF(digitToData.get(Digit.ONE), c);
        response.put(f, 'f');

        Character e = computeE(digitToData.get(Digit.FOUR), countToData.get(5));
        response.put(e, 'e');

        Character d = computeD(response, countToData.get(6));
        response.put(d, 'd');

        Character b = computeB(digitToData.get(Digit.FOUR), response);
        response.put(b, 'b');

        Character g = computeG(digitToData.get(Digit.EIGHT), response);
        response.put(g, 'g');

        return response;
    }

    private Character computeA(List<Character> one, List<Character> seven) {
        return getNewChar(one, seven);
    }

    private Character computeC(List<Character> seven, List<List<Character>> sixDigits) {
        for (var d : seven) {
            if (!sixDigits.stream().allMatch(v -> v.contains(d))) {
                return d;
            }
        }
        throw new IllegalArgumentException();
    }

    private Character computeF(List<Character> one, Character c) {
        if (!one.get(0).equals(c)) {
            return one.get(0);
        }
        return one.get(1);
    }

    private Character computeE(List<Character> four, List<List<Character>> fiveDigits) {
        List<List<Character>> fiveDigitsMinusFour = new LinkedList<>();

        for (var fiveSeq : fiveDigits) {
            List<Character> data = new LinkedList<>();
            for (var d : fiveSeq) {
                if (!four.contains(d)) {
                    data.add(d);
                }
            }
            fiveDigitsMinusFour.add(data);
        }

        List<Character> five = fiveDigitsMinusFour.stream().filter(v -> v.size() == 3).findFirst().orElseThrow();
        List<Character> twoOrThree = fiveDigitsMinusFour.stream().filter(v -> v.size() == 2).findFirst().orElseThrow();

        return getNewChar(twoOrThree, five);
    }

    private Character computeD(Map<Character, Character> decACFE, List<List<Character>> sixDigits) {
        List<List<Character>> sixDigitsMinusACEF = removeOldChars(decACFE, sixDigits);

        List<Character> zero = sixDigitsMinusACEF.stream().filter(v -> v.size() == 2).findFirst().orElseThrow();
        List<Character> sixOrNine = sixDigitsMinusACEF.stream().filter(v -> v.size() == 3).findFirst().orElseThrow();

        return getNewChar(zero, sixOrNine);
    }

    private List<List<Character>> removeOldChars(Map<Character, Character> dec, List<List<Character>> digits) {
        List<List<Character>> response = new LinkedList<>();

        for (var sequence : digits) {
            List<Character> data = new LinkedList<>();
            for (var d : sequence) {
                if (!dec.containsKey(d)) {
                    data.add(d);
                }
            }
            response.add(data);
        }
        return response;
    }

    private Character computeB(List<Character> four, Map<Character, Character> decACDEF) {
        return getNewChar(four, decACDEF);
    }

    private Character computeG(List<Character> eight, Map<Character, Character> decABCDEF) {
        return getNewChar(eight, decABCDEF);
    }

    private Character getNewChar(List<Character> digit, Map<Character, Character> dec) {
        for (var d : digit) {
            if (!dec.containsKey(d)) {
                return d;
            }
        }

        throw new IllegalArgumentException();
    }

    private Character getNewChar(List<Character> shortList, List<Character> longList) {
        for (var d : longList) {
            if (!shortList.contains(d)) {
                return d;
            }
        }

        throw new IllegalArgumentException();
    }
}
