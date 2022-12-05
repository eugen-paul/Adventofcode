package net.eugenpaul.adventofcode.y2022.day5;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day5 extends SolutionTemplate {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    private static class Move {
        private static final String FORMAT = "^move (\\d*) from (\\d*) to (\\d*)$";
        private static final Pattern PATTERN = Pattern.compile(FORMAT);

        private int size;
        private int from;
        private int to;

        public static Move fromString(String group) {
            Matcher m = PATTERN.matcher(group);
            if (!m.find()) {
                throw new IllegalArgumentException();
            }
            return new Move(//
                    Integer.parseInt(m.group(1)), //
                    Integer.parseInt(m.group(2)), //
                    Integer.parseInt(m.group(3))//
            );
        }
    }

    @Getter
    private String crateEnds;
    @Getter
    private String crateEnds2;

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        puzzle.doPuzzleFromFile("y2022/day5/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        Map<Integer, LinkedList<Character>> stacks = new HashMap<>();
        List<Move> moves = new LinkedList<>();

        var iterator = eventData.iterator();
        while (iterator.hasNext()) {
            var current = iterator.next();
            if (!current.isEmpty()) {
                addToStack(current, stacks);
            } else {
                iterator.forEachRemaining(v -> moves.add(Move.fromString(v)));
            }
        }

        crateEnds = doPuzzle1(copyStack(stacks), moves);
        crateEnds2 = doPuzzle2(copyStack(stacks), moves);

        logger.log(Level.INFO, () -> "crateEnds : " + getCrateEnds());
        logger.log(Level.INFO, () -> "crateEnds2 : " + getCrateEnds2());

        return true;
    }

    private Map<Integer, LinkedList<Character>> copyStack(Map<Integer, LinkedList<Character>> stacks){
        Map<Integer, LinkedList<Character>> response = new HashMap<>();

        for (var entry : stacks.entrySet()) {
            response.put(entry.getKey(), new LinkedList<>(entry.getValue()));
        }

        return response;
    }

    private void addToStack(String data, Map<Integer, LinkedList<Character>> stacks) {
        for (int i = 1; i <= data.length(); i = i + 4) {
            if (data.charAt(i) != ' ' && data.charAt(i) >= 'A') {
                stacks.computeIfAbsent(i / 4 + 1, k -> new LinkedList<>())//
                        .addLast(data.charAt(i));
            }
        }
    }

    private String doPuzzle1(Map<Integer, LinkedList<Character>> stacks, List<Move> moves) {
        for (var move : moves) {
            for (int i = 0; i < move.size; i++) {
                var c = stacks.get(move.from).removeFirst();
                stacks.get(move.to).addFirst(c);
            }
        }

        var response = new StringBuilder();
        for (int i = 1; i <= stacks.size(); i++) {
            response.append(stacks.get(i).getFirst());
        }
        return response.toString();
    }

    private String doPuzzle2(Map<Integer, LinkedList<Character>> stacks, List<Move> moves) {
        for (var move : moves) {
            for (int i = move.size - 1; i >= 0; i--) {
                var c = stacks.get(move.from).remove(i);
                stacks.get(move.to).addFirst(c);
            }
        }

        var response = new StringBuilder();
        for (int i = 1; i <= stacks.size(); i++) {
            response.append(stacks.get(i).getFirst());
        }
        return response.toString();
    }

}
