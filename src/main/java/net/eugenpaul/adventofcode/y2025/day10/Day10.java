package net.eugenpaul.adventofcode.y2025.day10;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static net.eugenpaul.adventofcode.helper.StringConverter.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        // buttons.sort((a, b) -> b.size() - a.size());

        int start = (int) max(lights);
        int end = (int) sum(lights);
        // for(int i = start; i <= end; i++){
        //     // if(i == 245){
        //     //     System.out.println();
        //     // }
        //     if(isOk(buttons, lights, i)){
        //         // System.out.println("res = " + i);
        //         return (long)i;
        //     }
        // }

        //generiere Code fuer Python , weil MatheclipseHelper kann nciht alle gelichungen loesen.
        generator(buttons, lights, start, end, glNr);
        //Starte danach das Code mit Python :D
        return 0L;

        // int m;

        // Set<String> cache = new HashSet<>();
        // while (start <= end) {
        //     m = (start + end) / 2;
        //     if (isOk(buttons, lights, m)) {
        //         end = m - 1;
        //     } else {
        //         start = m + 1;
        //     }
        // }
        // return (long) start;

        // throw new IllegalArgumentException();

        // return (long) calc(buttons, 0, lights, cur);
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

    private boolean isOk123(List<List<Integer>> buttons, List<Integer> target, int cnt) {
        MatheclipseHelper helper = new MatheclipseHelper();
        
        String ges = "";
        for (int i = 0; i < buttons.size(); i++) {
            helper.addUnknown("x" + i);
            if(!ges.isEmpty()){
                ges += "+";
            }
            ges += "x" + i;
        }

        ges += " == " + cnt;
        
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
            helper.addEquation(eq);
            // System.out.println(eq);
        }
        helper.addEquation(ges);
        
        helper.solve();
        System.out.println(cnt + ": " + helper.getRawResult());

        boolean ok = true;
        boolean allLeer = true;
        for (int i = 0; i < buttons.size(); i++) {
            List<String> res = helper.getResults("x"+i);
            if(!res.isEmpty()){
                allLeer = false;
            }
            try{
                Long rr = Long.valueOf(res.get(0));
                if(rr < 0){
                    ok = false;
                }
            } catch (Exception e){
                //ignore
            }
        }

        return ok && !allLeer;
    }

    // private boolean calc(List<List<Integer>> buttons, int valNr, List<Integer> current) {
    //     // String key = butNr + ":" + String.join(",", current.stream().map(v -> v + "").toList());

    //     // if (cache.containsKey(key)) {
    //     // return cache.get(key);
    //     // }

    //     if (current.stream().allMatch(v -> v == 0)) {
    //         return true;
    //     }

    //     if (current.stream().anyMatch(v -> v < 0)) {
    //         return false;
    //     }

    //     Integer resp = Integer.MAX_VALUE;
    //     int toPress = target.get(valNr);
    //     List<List<Integer>> buttonsWithVal = buttons.stream().filter(v -> v.contains(valNr)).toList();

    //     // cache.put(key, resp == Long.MAX_VALUE ? null : resp);

    //     return resp == Integer.MAX_VALUE ? null : resp;
    // }

    private boolean isOk(List<List<Integer>> buttons, int butNr, List<Integer> target, List<Integer> current, int rest, Set<String> cache) {
        // String key = butNr + ":" + String.join(",", current.stream().map(v -> v + "").toList()) + ":" + rest;

        // if (cache.contains(key)) {
        // return false;
        // }

        if (target.equals(current)) {
            return true;
        }

        if (butNr == buttons.size()) {
            return false;
        }

        if (!isPossible(buttons, butNr, target, current, rest)) {
            return false;
        }

        boolean resp = false;

        int firstBn = buttons.get(butNr).get(0);
        int curValueBn = current.get(firstBn);
        int tarValueBn = target.get(firstBn);
        // for (int i = 0; i <= min(tarValueBn - curValueBn, rest); i++) {
        for (int i = min(tarValueBn - curValueBn, rest); i >= 0; i--) {
            List<Integer> cur = new ArrayList<>(current);
            for (var but : buttons.get(butNr)) {
                cur.set(but, cur.get(but) + i);
            }
            if (!isOk(target, cur)) {
                continue;
            }
            boolean ok = isOk(buttons, butNr + 1, target, cur, rest - i, cache);
            if (ok) {
                resp = ok;
                break;
            }
        }

        // if (!resp) {
        // cache.add(key);
        // }

        return resp;
    }

    private boolean isPossible(List<List<Integer>> buttons, int butNr, List<Integer> target, List<Integer> current, int rest) {
        Set<Integer> restBut = new HashSet<>();
        for (int i = butNr; i < buttons.size(); i++) {
            restBut.addAll(buttons.get(i));
        }
        for (int i = 0; i < target.size(); i++) {
            if (!target.get(i).equals(current.get(i)) && !restBut.contains(i)) {
                return false;
            }
            if (current.get(i) + rest < target.get(i)) {
                return false;
            }
        }
        return true;
    }

    private Integer calc3(List<List<Integer>> buttons, int butNr, List<Integer> target, List<Integer> current, int curCnt, int curCntBest) {
        if (target.equals(current)) {
            return 0;
        }

        if (butNr == buttons.size()) {
            return null;
        }

        if (!isPossible(buttons, butNr, target, current)) {
            return null;
        }

        Integer resp = Integer.MAX_VALUE;

        int firstBn = buttons.get(butNr).get(0);
        int curValueBn = current.get(firstBn);
        int tarValueBn = target.get(firstBn);
        for (int i = 0; i <= tarValueBn - curValueBn; i++) {
            List<Integer> cur = new ArrayList<>(current);
            for (var but : buttons.get(butNr)) {
                cur.set(but, cur.get(but) + i);
            }
            if (!isOk(target, cur)) {
                break;
            }
            if (curCnt + i >= curCntBest) {
                break;
            }
            Integer tmpRes = calc3(buttons, butNr + 1, target, cur, curCnt + i, min(curCntBest, resp));
            if (tmpRes != null) {
                resp = min(tmpRes + i, resp);
            }
        }

        return resp == Integer.MAX_VALUE ? null : resp;
    }

    // private Integer calc(List<List<Integer>> buttons, List<Integer> current, HashMap<String, Integer> cache) {
    // // String key = String.join(",", current.stream().map(v -> v + "").toList());
    // // if (cache.containsKey(key)) {
    // // return cache.get(key);
    // // }

    // if (current.stream().allMatch(v -> v == 0)) {
    // return 0;
    // }

    // if (current.stream().anyMatch(v -> v < 0)) {
    // return Integer.MAX_VALUE;
    // }

    // Integer resp = Integer.MAX_VALUE;

    // for (var b : buttons) {
    // minus(current, b);
    // if(current.stream().allMatch(v -> v >= 0)){
    // Integer tmpRes = calc(buttons, current, cache);
    // if (tmpRes != Integer.MAX_VALUE) {
    // resp = min(tmpRes + 1, resp);
    // }
    // add(current, b);
    // } else {
    // add(current, b);
    // break;
    // }
    // }

    // // cache.put(key, resp);

    // return resp;
    // }

    private void minus(List<Integer> current, List<Integer> buttons) {
        for (var b : buttons) {
            current.set(b, current.get(b) - 1);
        }
    }

    private void add(List<Integer> current, List<Integer> buttons) {
        for (var b : buttons) {
            current.set(b, current.get(b) + 1);
        }
    }

    private Integer calc2(List<List<Integer>> buttons, int butNr, List<Integer> target, List<Integer> current, HashMap<String, Long> cache) {
        // String key = butNr + ":" + String.join(",", current.stream().map(v -> v + "").toList());

        // if (cache.containsKey(key)) {
        // return cache.get(key);
        // }

        if (target.equals(current)) {
            return 0;
        }

        if (butNr == buttons.size()) {
            return null;
        }

        if (!isPossible(buttons, butNr, target, current)) {
            return null;
        }

        Integer resp = Integer.MAX_VALUE;

        int firstBn = buttons.get(butNr).get(0);
        int curValueBn = current.get(firstBn);
        int tarValueBn = target.get(firstBn);
        for (int i = 0; i <= tarValueBn - curValueBn; i++) {
            List<Integer> cur = new ArrayList<>(current);
            for (var but : buttons.get(butNr)) {
                cur.set(but, cur.get(but) + i);
            }
            if (!isOk(target, cur)) {
                break;
            }
            Integer tmpRes = calc2(buttons, butNr + 1, target, cur, cache);
            if (tmpRes != null) {
                resp = min(tmpRes + i, resp);
            }
        }

        // cache.put(key, resp == Long.MAX_VALUE ? null : resp);

        return resp == Integer.MAX_VALUE ? null : resp;
    }

    private boolean isOk(List<Integer> target, List<Integer> current) {
        for (int i = 0; i < target.size(); i++) {
            if (target.get(i) < current.get(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPossible(List<List<Integer>> buttons, int butNr, List<Integer> target, List<Integer> current) {
        Set<Integer> restBut = new HashSet<>();
        for (int i = butNr; i < buttons.size(); i++) {
            restBut.addAll(buttons.get(i));
        }
        for (int i = 0; i < target.size(); i++) {
            if (!target.get(i).equals(current.get(i)) && !restBut.contains(i)) {
                return false;
            }
        }
        return true;
    }

}
