package net.eugenpaul.adventofcode.y2023.day4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MathHelper;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day4 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day4 puzzle = new Day4();
        puzzle.doPuzzleFromFile("y2023/day4/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;
        response = eventData.stream()
            .mapToLong(this::countWins)
            .filter(n->n>0)
            .map(n->MathHelper.pow(2, n-1))
            .sum();
        return response;
    }

    private long countWins(String data) {
        String cards = data.split(":")[1];
        Set<Integer> win = Stream.of(cards.split("\\|")[0].trim().split(" "))
                                 .filter(n -> !n.isEmpty())
                                 .map(n -> Integer.parseInt(n.trim()))
                                 .collect(Collectors.toSet());
        Set<Integer> num = Stream.of(cards.split("\\|")[1].trim().split(" "))
                                 .filter(n -> !n.isEmpty())
                                 .map(n -> Integer.parseInt(n.trim()))
                                 .collect(Collectors.toSet());
        long exp = 0;
        for (int n : num) {
            if (win.contains(n)) {
                exp++;
            }
        }
        return exp;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;
        Map<Integer, Long> copies = new HashMap<>();
        for (int i = 1; i <= eventData.size(); i++) {
            copies.put(i, 1L);
        }

        int id = 1;
        for (String card : eventData) {
            long exp = countWins(card);
            long c = copies.get(id);
            for (int i = 0; i < exp; i++) {
                copies.computeIfPresent(id + i + 1, (k, v) -> v + c);
            }
            id++;
        }

        response = copies.values().stream().mapToLong(Long::longValue).sum();

        return response;
    }

}
