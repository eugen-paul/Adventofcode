package net.eugenpaul.adventofcode.y2022.day11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day11 extends SolutionTemplate {

    private class Operation {
        private char operator;
        private boolean valueIsOld;
        private int value;

        public Operation(char operator) {
            this.operator = operator;
            this.valueIsOld = true;
            this.value = 0;
        }

        public Operation(char operator, int value) {
            this.operator = operator;
            this.valueIsOld = false;
            this.value = value;
        }

        public long comp(long old) {
            long secondValue = (valueIsOld) ? old : value;
            switch (operator) {
            case '+':
                return old + secondValue;
            case '*':
                return old * secondValue;
            default:
                throw new IllegalArgumentException("Illegal operator " + operator);
            }
        }
    }

    @NoArgsConstructor
    private class Monkey {
        private LinkedList<Long> items;
        private long counter;
        private Operation op;
        private int divisibleTest;
        private int targetByTrue;
        private int targetByFalse;
    }

    @Getter
    private long monkeyBusiness;
    @Getter
    private long monkeyBusiness10000;

    public static void main(String[] args) {
        Day11 puzzle = new Day11();
        puzzle.doPuzzleFromFile("y2022/day11/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        monkeyBusiness = doPuzzle1(eventData);
        monkeyBusiness10000 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "monkeyBusiness : " + getMonkeyBusiness());
        logger.log(Level.INFO, () -> "monkeyBusiness10000 : " + getMonkeyBusiness10000());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {
        return doGame(eventData, 20, true);
    }

    private long doPuzzle2(List<String> eventData) {
        return doGame(eventData, 10_000, false);
    }

    private long doGame(List<String> eventData, int rounds, boolean doDivide) {
        var monkeys = getMonkeys(eventData);

        var mod = monkeys.stream()//
                .mapToInt(v -> v.divisibleTest)//
                .reduce(1, (a, b) -> a * b);

        for (int i = 0; i < rounds; i++) {
            for (var monkey : monkeys) {
                for (var items : monkey.items) {
                    monkey.counter++;

                    long worryLevel = monkey.op.comp(items);
                    if (doDivide) {
                        worryLevel = worryLevel / 3L;
                    }

                    worryLevel = worryLevel % mod;
                    if (worryLevel % monkey.divisibleTest == 0) {
                        monkeys.get(monkey.targetByTrue).items.add(worryLevel);
                    } else {
                        monkeys.get(monkey.targetByFalse).items.add(worryLevel);
                    }
                }
                monkey.items.clear();
            }
        }

        return monkeys.stream().mapToLong(v -> v.counter).boxed()//
                .sorted(Collections.reverseOrder())//
                .limit(2)//
                .reduce(1L, (a, b) -> a * b);
    }

    private List<Monkey> getMonkeys(List<String> eventData) {
        var response = new ArrayList<Monkey>();

        var iterator = eventData.iterator();
        while (iterator.hasNext()) {
            var line = iterator.next();

            if (line.isBlank()) {
                //no need to read monkey number
                iterator.next();
            }

            //create new monkey and set counter to 0
            var monkey = new Monkey();
            monkey.counter = 0L;
            
            //read items: "  Starting items: 79, 98"
            line = iterator.next();
            var startingItems = line.replace(",", "").split(" ");

            LinkedList<Long> items = new LinkedList<>();
            for (int i = 4; i < startingItems.length; i++) {
                items.add(Long.parseLong(startingItems[i]));
            }
            monkey.items = items;

            //read operation: "  Operation: new = old * 19"
            line = iterator.next();
            var opData = line.split(" ");
            Operation op;
            if (opData[7].equals("old")) {
                op = new Operation(//
                        opData[6].charAt(0) //
                );
            } else {
                op = new Operation(//
                        opData[6].charAt(0), //
                        Integer.parseInt(opData[7]) //
                );
            }
            monkey.op = op;

            //read test: "  Test: divisible by 23"
            line = iterator.next();
            int divisible = Integer.parseInt(line.split(" ")[5]);
            monkey.divisibleTest = divisible;
            
            //Read true decision: "    If true: throw to monkey 2"
            line = iterator.next();
            int targetByTrue = Integer.parseInt(line.split(" ")[9]);
            monkey.targetByTrue = targetByTrue;
            
            //Read false decision: "    If false: throw to monkey 3"
            line = iterator.next();
            int targetByFalse = Integer.parseInt(line.split(" ")[9]);
            monkey.targetByFalse = targetByFalse;

            response.add(monkey);
        }

        return response;
    }

}
