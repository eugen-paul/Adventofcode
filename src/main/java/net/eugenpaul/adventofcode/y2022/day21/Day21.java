package net.eugenpaul.adventofcode.y2022.day21;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.CircleMemory;
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
        long valueSoll = 0;
        String nameNext = "";

        String rootL = getFormel(mm.get(mm.get("root").op1), mm);
        System.out.println("left: " + rootL);

        if (rootL.contains("x")) {
            System.out.println("left has humn");
            nameNext = mm.get("root").op1;
        } else {
            valueSoll = getValue(mm.get(mm.get("root").op1), mm);
            System.out.println("right value = " + valueSoll);
        }

        String rootR = getFormel(mm.get(mm.get("root").op2), mm);
        System.out.println("right: " + rootR);
        if (rootR.contains("x")) {
            System.out.println("right has humn");
            nameNext = mm.get("root").op2;
        } else {
            valueSoll = getValue(mm.get(mm.get("root").op2), mm);
            System.out.println("right value = " + valueSoll);
        }

        while (true) {
            var currentM = mm.get(nameNext);
            var sollOld = valueSoll;
            boolean leftHasX = true;
            if (currentM.name.equals("humn")) {
                return valueSoll;
            }
            String l = getFormel(mm.get(currentM.op1), mm);
            System.out.println("left: " + l);

            if (l.contains("x")) {
                System.out.println("left has humn");
                nameNext = currentM.op1;
                leftHasX = true;
            } else {
                valueSoll = getValue(mm.get(currentM.op1), mm);
                System.out.println("right value = " + valueSoll);

            }

            String r = getFormel(mm.get(currentM.op2), mm);
            System.out.println("right: " + r);
            if (r.contains("x")) {
                System.out.println("right has humn");
                nameNext = currentM.op2;
                leftHasX = false;
            } else {
                valueSoll = getValue(mm.get(currentM.op2), mm);
                System.out.println("right value = " + valueSoll);
            }
            switch (currentM.op) {
            case '+':
                valueSoll = sollOld - valueSoll;
                break;
            case '-':
                if (leftHasX) {
                    valueSoll = sollOld + valueSoll;
                } else {
                    valueSoll = valueSoll - sollOld;
                }
                break;
            case '*':
                valueSoll = sollOld / valueSoll;
                break;
            case '/':
                if (leftHasX) {
                    valueSoll = sollOld * valueSoll;
                } else {
                    valueSoll = sollOld / valueSoll;
                }
                break;
            default:
                break;
            }
        }
    }

    private String getFormel(Monkey m, Map<String, Monkey> mm) {
        if (m.name.equals("humn")) {
            return " x ";
        }

        if (m.value != null) {
            return m.value + "";
        }

        switch (m.op) {
        case '+':
            return "(" + getFormel(mm.get(m.op1), mm) + " + " + getFormel(mm.get(m.op2), mm) + ")";
        case '-':
            return "(" + getFormel(mm.get(m.op1), mm) + " + " + getFormel(mm.get(m.op2), mm) + ")";
        case '*':
            return "(" + getFormel(mm.get(m.op1), mm) + " * " + getFormel(mm.get(m.op2), mm) + ")";
        case '/':
            return "(" + getFormel(mm.get(m.op1), mm) + "/" + getFormel(mm.get(m.op2), mm) + ")";
        default:
            break;
        }
        throw new IllegalArgumentException();
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
