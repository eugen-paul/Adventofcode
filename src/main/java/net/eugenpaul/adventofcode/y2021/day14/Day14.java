package net.eugenpaul.adventofcode.y2021.day14;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day14 extends SolutionTemplate {

    @AllArgsConstructor
    private class StepData {
        private int steps;
        private Map<Character, Long> count;
        private Character lastCharacter;
    }

    @Getter
    private long sub;
    @Getter
    private long sub2;

    public static void main(String[] args) {
        Day14 puzzle = new Day14();
        puzzle.doPuzzleFromFile("y2021/day14/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        var template = eventData.get(0).chars()//
                .mapToObj(c -> (char) c)//
                .collect(Collectors.toCollection(LinkedList::new));

        Map<String, Character> rules = eventData.stream()//
                .filter(v -> v.contains("->"))//
                .collect(Collectors.toMap(v -> v.substring(0, 2), v -> v.charAt(6)));

        sub = doPuzzle1(template, rules);
        sub2 = doPuzzle2(template, rules);

        logger.log(Level.INFO, () -> "sub   : " + getSub());
        logger.log(Level.INFO, () -> "sub2  : " + getSub2());

        return true;
    }

    /** Slow */
    private long doPuzzle1(LinkedList<Character> inputTemplate, Map<String, Character> rules) {
        LinkedList<Character> currentTemplate = new LinkedList<>(inputTemplate);

        for (int i = 0; i < 10; i++) {
            currentTemplate = doStep(currentTemplate, rules);
        }

        Map<Character, Integer> count = currentTemplate.stream()//
                .collect(Collectors.toMap(v -> v, v -> 1, (v1, v2) -> v1 + v2));

        long max = Long.MIN_VALUE;
        long min = Long.MAX_VALUE;

        for (var value : count.values()) {
            max = Math.max(max, value);
            min = Math.min(min, value);
        }

        return max - min;
    }

    /** Fast */
    private long doPuzzle2(LinkedList<Character> inputTemplate, Map<String, Character> rules) {
        var pairs = getPairs(inputTemplate);
        StepData stepData = new StepData(40, new HashMap<>(), null);
        Map<String, StepData> storage = new HashMap<>();

        for (var pair : pairs) {
            StepData pairStepData = stepRecursiv(pair, 40, storage, rules);
            stepData = merge(stepData, pairStepData);
        }

        Map<Character, Long> count = stepData.count;

        long max = Long.MIN_VALUE;
        long min = Long.MAX_VALUE;

        for (var value : count.values()) {
            max = Math.max(max, value);
            min = Math.min(min, value);
        }

        return max - min;
    }

    private LinkedList<Character> doStep(LinkedList<Character> template, Map<String, Character> rules) {

        LinkedList<Character> response = new LinkedList<>();

        LinkedList<String> pairs = getPairs(template);
        for (var pair : pairs) {
            response.add(pair.charAt(0));
            Character element = rules.get(pair);
            if (element != null) {
                response.add(rules.get(pair));
            }
        }
        response.add(pairs.getLast().charAt(1));

        return response;
    }

    private StepData stepRecursiv(String pair, int stepsLeft, Map<String, StepData> storage, Map<String, Character> rules) {

        if (stepsLeft == 0) {
            Map<Character, Long> count = new HashMap<>();
            if (pair.charAt(0) == pair.charAt(1)) {
                count.put(pair.charAt(0), 2L);
            } else {
                count.put(pair.charAt(0), 1L);
                count.put(pair.charAt(1), 1L);
            }
            return new StepData(//
                    0, //
                    count, //
                    pair.charAt(1) //
            );
        }

        String hash = compHash(pair, stepsLeft);
        var hashValue = storage.get(hash);

        if (hashValue != null) {
            return hashValue;
        }

        List<String> newPairs = doRule(pair, rules);
        StepData e1 = stepRecursiv(newPairs.get(0), stepsLeft - 1, storage, rules);
        StepData e2 = stepRecursiv(newPairs.get(1), stepsLeft - 1, storage, rules);
        StepData response = merge(e1, e2);
        response.steps = stepsLeft;
        storage.put(hash, response);
        return response;
    }

    private StepData merge(StepData e1, StepData e2) {
        Map<Character, Long> count = new HashMap<>(e1.count);

        for (var entry : e2.count.entrySet()) {
            count.merge(entry.getKey(), entry.getValue(), (v1, v2) -> v1 + v2);
        }

        if (e1.lastCharacter != null) {
            // dont count last character of e1 twice
            count.merge(e1.lastCharacter, -1L, (v1, v2) -> v1 + v2);
        }

        return new StepData(//
                e1.steps, //
                count, //
                e2.lastCharacter //
        );
    }

    private List<String> doRule(String pair, Map<String, Character> rules) {
        Character c = rules.get(pair);
        String e1 = "" + pair.charAt(0) + c;
        String e2 = "" + c + pair.charAt(1);
        return List.of(e1, e2);
    }

    private String compHash(String pair, int stepsLeft) {
        return pair + stepsLeft;
    }

    private LinkedList<String> getPairs(LinkedList<Character> template) {
        LinkedList<String> response = new LinkedList<>();
        Character last = null;
        var iterator = template.listIterator();

        while (iterator.hasNext()) {
            var c = iterator.next();
            if (last == null) {
                last = c;
                continue;
            }
            String in = "" + last + c;
            response.add(in);
            last = c;
        }

        return response;
    }

}
