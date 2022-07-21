package net.eugenpaul.adventofcode.y2020.day16;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day16 extends SolutionTemplate {
    private static final String FORMAT = "^([a-zA-Z ]*): ([\\d]*)-([\\d]*) or ([\\d]*)-([\\d]*)$";
    private static final Pattern FORMAT_PATTREN = Pattern.compile(FORMAT);

    @AllArgsConstructor
    @Data
    private class Range {
        private int from;
        private int to;

        public boolean inRange(int value) {
            return from <= value && value <= to;
        }
    }

    @AllArgsConstructor
    private class Rule {

        @Getter
        private String name;
        private List<Range> ranges;

        private boolean inRange(int i) {
            return ranges.stream().anyMatch(v -> v.inRange(i));
        }
    }

    @AllArgsConstructor
    private class Ticket {
        @Getter
        private List<Integer> numbers;
    }

    @Getter
    private long errorRate;
    @Getter
    private long departures;

    @Setter
    private boolean doPuzzle2 = true;

    public static void main(String[] args) {
        Day16 puzzle = new Day16();
        puzzle.doPuzzleFromFile("y2020/day16/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Rule> rules = new LinkedList<>();

        int line = 0;
        while (!eventData.get(line).isBlank()) {
            rules.add(fromString(eventData.get(line)));
            line++;
        }

        line++;
        line++;
        Ticket myTicket = ticketFromString(eventData.get(line));

        line++;
        line++;
        line++;

        List<Ticket> nearbyTickets = new LinkedList<>();
        while (line < eventData.size()) {
            nearbyTickets.add(ticketFromString(eventData.get(line)));
            line++;
        }

        errorRate = doPuzzle1(rules, nearbyTickets);
        departures = doPuzzle2(rules, nearbyTickets, myTicket);

        logger.log(Level.INFO, () -> "errorRate   : " + getErrorRate());
        logger.log(Level.INFO, () -> "departures  : " + getDepartures());

        return true;
    }

    private long doPuzzle1(List<Rule> rules, List<Ticket> nearbyTickets) {
        long response = 0;

        for (Ticket ticket : nearbyTickets) {
            for (var number : ticket.numbers) {
                if (rules.stream().noneMatch(v -> v.inRange(number))) {
                    response += number;
                }
            }
        }

        return response;
    }

    private long doPuzzle2(List<Rule> rules, List<Ticket> nearbyTickets, Ticket myTicket) {

        List<Ticket> computeNewOkList = getOkTickets(rules, nearbyTickets);

        int numbers = nearbyTickets.get(0).numbers.size();

        Map<String, Integer> nameToNumber = new HashMap<>();
        Map<String, List<Integer>> nameToOkList = new HashMap<>();

        List<Rule> restRules = new LinkedList<>(rules);

        while (!restRules.isEmpty()) {
            Rule ruleToCheck = restRules.remove(0);

            List<Integer> okList = nameToOkList.get(ruleToCheck.name);
            if (okList == null) {
                okList = extracted(computeNewOkList, numbers, nameToNumber, ruleToCheck);
                nameToOkList.put(ruleToCheck.name, okList);
            }

            if (okList.size() == 1) {
                Integer ruleNumber = okList.get(0);
                nameToNumber.put(ruleToCheck.getName(), ruleNumber);
                nameToOkList.remove(ruleToCheck.name);
                nameToOkList.values().stream()//
                        .forEach(v -> v.remove(ruleNumber));
            } else {
                restRules.add(ruleToCheck);
            }
        }

        return nameToNumber.entrySet().stream()//
                .filter(v -> v.getKey().startsWith("departure"))//
                .mapToLong(v -> myTicket.getNumbers().get(v.getValue()))//
                .reduce(1L, (a, b) -> a * b);
    }

    private List<Integer> extracted(List<Ticket> okTickets, int numbers, Map<String, Integer> nameToNumber, Rule ruleToCheck) {
        List<Integer> okList = new LinkedList<>();
        for (int i = 0; i < numbers; i++) {
            if (nameToNumber.values().contains(i)) {
                continue;
            }

            int numberToCheck = i;
            if (okTickets.stream().allMatch(v -> ruleToCheck.inRange(v.getNumbers().get(numberToCheck)))) {
                okList.add(numberToCheck);
            }
        }
        return okList;
    }

    private List<Ticket> getOkTickets(List<Rule> rules, List<Ticket> nearbyTickets) {
        List<Ticket> okTickets = new LinkedList<>();
        for (Ticket ticket : nearbyTickets) {
            boolean ok = true;
            for (var number : ticket.numbers) {
                if (rules.stream().noneMatch(v -> v.inRange(number))) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                okTickets.add(ticket);
            }
        }
        return okTickets;
    }

    public Rule fromString(String data) {
        Matcher m = FORMAT_PATTREN.matcher(data);
        if (!m.find()) {
            throw new IllegalArgumentException(data);
        }

        return new Rule(m.group(1), //
                List.of(//
                        new Range(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3))), //
                        new Range(Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5))) //
                ));
    }

    public Ticket ticketFromString(String data) {
        return new Ticket(StringConverter.toIntegerArrayList(data));
    }

}
