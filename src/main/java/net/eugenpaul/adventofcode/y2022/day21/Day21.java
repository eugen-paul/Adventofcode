package net.eugenpaul.adventofcode.y2022.day21;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day21 extends SolutionTemplate {

    @AllArgsConstructor
    private class Monkey {
        private String name;
        private String op1;
        private String op2;
        private Long value;
        private Character op;
    }

    @Getter
    private long part1;
    @Getter
    private long part2;

    public static void main(String[] args) {
        Day21 puzzle = new Day21();
        puzzle.doPuzzleFromFile("y2022/day21/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        part1 = doPuzzle1(eventData);
        part2 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "part1 : " + getPart1());
        logger.log(Level.INFO, () -> "part2 : " + getPart2());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {

        Map<String, Monkey> mm = new HashMap<>();
        for (String data : eventData) {
            var m = fromString(data);
            mm.put(m.name, m);
        }

        return getValue(mm.get("root"), mm);
    }

    private long getValue(Monkey m, Map<String, Monkey> mm) {
        if (m.value != null) {
            return m.value;
        }

        switch (m.op) {
        case '+':
            return getValue(mm.get(m.op1), mm) + getValue(mm.get(m.op2), mm);
        case '-':
            return getValue(mm.get(m.op1), mm) - getValue(mm.get(m.op2), mm);
        case '*':
            return getValue(mm.get(m.op1), mm) * getValue(mm.get(m.op2), mm);
        case '/':
            return getValue(mm.get(m.op1), mm) / getValue(mm.get(m.op2), mm);
        default:
            break;
        }
        throw new IllegalArgumentException();
    }

    private long doPuzzle2(List<String> eventData) {
        Map<String, Monkey> mm = new HashMap<>();
        for (String data : eventData) {
            var m = fromString(data);
            mm.put(m.name, m);
        }

        mm.get("root").op = '=';
        long lastMathResult = 0;
        String nameMonkey = "root";

        while (!nameMonkey.equals("humn")) {
            var monkey = mm.get(nameMonkey);

            long currentMathResult;
            boolean isHumnLeft = hasHumn(mm.get(monkey.op1), mm);

            if (isHumnLeft) {
                currentMathResult = getValue(mm.get(monkey.op2), mm);
                nameMonkey = monkey.op1;
            } else {
                currentMathResult = getValue(mm.get(monkey.op1), mm);
                nameMonkey = monkey.op2;
            }

            switch (monkey.op) {
            case '+':
                lastMathResult = lastMathResult - currentMathResult;
                break;
            case '-':
                if (isHumnLeft) {
                    lastMathResult = lastMathResult + currentMathResult;
                } else {
                    lastMathResult = currentMathResult - lastMathResult;
                }
                break;
            case '*':
                lastMathResult = lastMathResult / currentMathResult;
                break;
            case '/':
                if (isHumnLeft) {
                    lastMathResult = lastMathResult * currentMathResult;
                } else {
                    lastMathResult = lastMathResult / currentMathResult;
                }
                break;
            case '=':
                lastMathResult = currentMathResult;
                break;
            default:
                break;
            }
        }

        return lastMathResult;
    }

    private boolean hasHumn(Monkey m, Map<String, Monkey> mm) {
        if (m.name.equals("humn")) {
            return true;
        }

        if (m.value != null) {
            return false;
        }

        return hasHumn(mm.get(m.op1), mm) || hasHumn(mm.get(m.op2), mm);
    }

    private Monkey fromString(String data) {
        var sp = data.split(" ");
        if (sp.length == 2) {
            return new Monkey(//
                    sp[0].replace(":", ""), //
                    null, //
                    null, //
                    Long.parseLong(sp[1]), //
                    null //
            );
        }
        return new Monkey(//
                sp[0].replace(":", ""), //
                sp[1], //
                sp[3], //
                null, //
                sp[2].charAt(0) //
        );
    }
}
