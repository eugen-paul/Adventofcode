package net.eugenpaul.adventofcode.y2025.day10;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static net.eugenpaul.adventofcode.helper.StringConverter.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MatheclipseHelper;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.SubSet;

public class Day10 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day10 puzzle = new Day10();
        puzzle.doPuzzleFromFile("y2025/day10/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        return doEvent(List.of(eventData));
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        for (var data : eventData) {
            response += getMinPressed(data);
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private long getMinPressed(String data) {
        String[] splits = data.split(" ");
        String li = splits[0].substring(1, splits[0].length() - 1);
        boolean[] lights = new boolean[li.length()];
        for (int i = 0; i < li.length(); i++) {
            lights[i] = li.charAt(i) == '#';
        }

        List<List<Integer>> buttons = new ArrayList<>();
        for (int i = 1; i < splits.length - 1; i++) {
            var d = splits[i].substring(1, splits[i].length() - 1).split(",");
            List<Integer> bt = new ArrayList<>();
            for (var dd : d) {
                bt.add(toInt(dd));
            }
            buttons.add(bt);
        }

        AtomicInteger resp = new AtomicInteger(Integer.MAX_VALUE);

        SubSet.subset(buttons, but -> {
            boolean[] l = new boolean[li.length()];
            Arrays.fill(l, false);

            for (var b : but) {
                for (var bb : b) {
                    l[bb] = !l[bb];
                }
            }

            if (Arrays.compare(lights, l) == 0) {
                resp.set(min(resp.get(), but.size()));
            }
        });

        return resp.get();
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        System.out.println("from typing import List\r\n" + //
        "from z3 import *");
        
        System.out.println();
        
        int i = 0;
        for (var data : eventData) {
            response += getMinPressed2(data, i++);
        }

        System.out.print("ges:int = gleichung0()");
        for(int k = 1; k < i; k++){
            System.out.print(" + gleichung"+k+"()");
        }
        System.out.println();
        System.out.println("print(\"Endloesung: \" + str(ges)) ");
        

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private Long getMinPressed2(String data, int glNr) {
        String[] splits = data.split(" ");
        String li = splits[splits.length - 1].substring(1, splits[splits.length - 1].length() - 1);
        List<Integer> lights = new ArrayList<>();
        for (var dd : li.split(",")) {
            lights.add(toInt(dd));
        }

        List<List<Integer>> buttons = new ArrayList<>();
        for (int i = 1; i < splits.length - 1; i++) {
            var d = splits[i].substring(1, splits[i].length() - 1).split(",");
            List<Integer> bt = new ArrayList<>();
            for (var dd : d) {
                bt.add(toInt(dd));
            }
            buttons.add(bt);
        }

        List<Integer> cur = new ArrayList<>();
        for (var l : lights) {
            cur.add(0);
        }

        int start = (int) max(lights);
        int end = (int) sum(lights);

        //generiere Code fuer Python , weil MatheclipseHelper kann nciht alle gelichungen loesen.
        generator(buttons, lights, start, end, glNr);
        //Starte danach das Code mit Python :D
        return 0L;
    }

    private void generator(List<List<Integer>> buttons, List<Integer> target, int cntMin, int cntmax, int gl){
        System.out.println("def gleichung"+gl+"() -> int:\r\n" + //
                           "    for CNT in range("+cntMin+","+(cntmax+1)+"):");
        System.out.println("        s = Solver()");
        for (int i = 0; i < buttons.size(); i++) {
            System.out.println("        x" + i + " = Int('x"+i+"')");
        }

        String ges = "";
        for (int i = 0; i < buttons.size(); i++) {
            if(!ges.isEmpty()){
                ges += "+";
            }
            ges += "x" + i;
            System.out.println("        s.add(" + "x" + i + " >= 0)");
        }
        System.out.println("        s.add(" + ges + " == CNT)");
        
        for (int i = 0; i < target.size(); i++) {
            List<String> but = new ArrayList<>();
            for (int b = 0; b < buttons.size(); b++) {
                if (buttons.get(b).contains(i)) {
                    but.add("x" + b);
                }
            }
            if (but.isEmpty()) {
                throw new IllegalArgumentException();
            }
            String eq = String.join("+", but) + "==" + target.get(i);
            System.out.println("        s.add(" + eq + ")");
        }
        System.out.println("        if s.check().r == 1:\r\n" + //
                            "            s.model()\r\n" + //
                            "            print(\"Loesung \" + str(CNT))\r\n" + //
                            "            return CNT\r\n" + //
                            // "        else:\r\n" + //
                            // "            print(\"Keine Loesung (UNSAT) oder Unbestimmt (UNKNOWN)\")\r\n" + //
                            "    return -1");
    }
}
