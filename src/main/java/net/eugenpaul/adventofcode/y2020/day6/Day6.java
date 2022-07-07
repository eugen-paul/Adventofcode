package net.eugenpaul.adventofcode.y2020.day6;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day6 extends SolutionTemplate {

    @Getter
    private long sum;
    @Getter
    private long sum2;

    public static void main(String[] args) {
        Day6 puzzle = new Day6();
        puzzle.doPuzzleFromFile("y2020/day6/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<List<Set<Character>>> groups = initGroups(eventData);

        sum = groups.stream()//
                .map(this::getAnyoneAnswers) //
                .mapToLong(Set::size)//
                .sum();

        sum2 = groups.stream()//
                .map(this::getEveryoneAnswers) //
                .mapToLong(Set::size)//
                .sum();

        logger.log(Level.INFO, () -> "sum  : " + getSum());
        logger.log(Level.INFO, () -> "sum2 : " + getSum2());

        return true;
    }

    private List<List<Set<Character>>> initGroups(List<String> eventData) {
        List<List<Set<Character>>> response = new LinkedList<>();

        List<Set<Character>> group = new LinkedList<>();
        response.add(group);

        for (String data : eventData) {
            if (data.isBlank()) {
                group = new LinkedList<>();
                response.add(group);
                continue;
            }

            Set<Character> person = StringConverter.toCharStream(data).collect(Collectors.toCollection(HashSet::new));
            group.add(person);
        }

        return response;
    }

    private Set<Character> getAnyoneAnswers(List<Set<Character>> group) {
        Set<Character> response = new HashSet<>();
        for (var person : group) {
            response.addAll(person);
        }
        return response;
    }

    private Set<Character> getEveryoneAnswers(List<Set<Character>> group) {
        Set<Character> response = new HashSet<>();

        for (char c = 'a'; c <= 'z'; c++) {
            Character cIntern = Character.valueOf(c);
            if (group.stream().allMatch(v -> v.contains(cIntern))) {
                response.add(c);
            }
        }
        return response;
    }

}
