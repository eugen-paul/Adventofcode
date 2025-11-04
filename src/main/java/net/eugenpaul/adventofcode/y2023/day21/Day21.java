package net.eugenpaul.adventofcode.y2023.day21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day21 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    @Setter
    private long steps1 = 64;
    @Setter
    private long steps2 = 26501365;

    @AllArgsConstructor
    private class Step {
        SimplePos pos;
        int cnt;
    }

    public static void main(String[] args) {
        Day21 puzzle = new Day21();
        puzzle.doPuzzleFromFile("y2023/day21/puzzle1.txt");
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

        int maxX = eventData.get(0).length();
        int maxY = eventData.size();

        var m = StringConverter.toMap(eventData);
        SimplePos start = m.entrySet().stream().filter(v -> v.getValue() == 'S').map(Entry::getKey).findFirst().orElseThrow();

        Set<SimplePos> seen = new HashSet<>();
        List<Step> toCheck = new LinkedList<>();
        toCheck.add(new Step(start, 0));
        while (!toCheck.isEmpty()) {
            var cur = toCheck.remove(0);
            if (cur.cnt > steps1) {
                break;
            }
            if (seen.contains(cur.pos)) {
                continue;
            }
            seen.add(cur.pos);
            if (cur.cnt % 2 == 0) {
                response++;
            }
            for (var nb : cur.pos.getNeighbors(false)) {
                if (nb.getX() < 0 || nb.getX() >= maxX || nb.getY() < 0 || nb.getY() >= maxY || m.get(nb) != '.') {
                    continue;
                }
                toCheck.add(new Step(nb, cur.cnt + 1));
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        int maxX = eventData.get(0).length();
        int maxY = eventData.size();

        var m = StringConverter.toMap(eventData);
        SimplePos start = m.entrySet().stream().filter(v -> v.getValue() == 'S').map(Entry::getKey).findFirst().orElseThrow();
        m.put(start, '.');

        // Map<SimplePos,Set<Integer>> TT = new HashMap<>();
        Map<SimplePos, Map<Integer, AtomicInteger>> megaSeen = new HashMap<>();

        Set<SimplePos> seen = new HashSet<>();
        List<Step> toCheck = new LinkedList<>();
        toCheck.add(new Step(start, 0));
        int lastCnt = 0;
        while (!toCheck.isEmpty()) {
            var cur = toCheck.remove(0);
            if (cur.cnt > steps2) {
                break;
            }
            // if (seen.getOrDefault(cur.pos, Collections.emptySet()).contains(cur.cnt)) {
            // continue;
            // }
            // seen.computeIfAbsent(cur.pos, v->new HashSet<>()).add(cur.cnt);
            if (seen.contains(cur.pos)) {
                continue;
            }
            seen.add(cur.pos);

            var test = cur.pos.copy().wrapAround(0, maxX - 1, 0, maxY - 1);
            var visited = megaSeen.computeIfAbsent(test, v -> new HashMap<>());
            if (visited.size() == 8 && !visited.containsKey(cur.cnt)) {
                continue;
            }
            // if (visited.containsKey(cur.cnt - 131) && cur.cnt > 131) {
            //     continue;
            // }
            visited.computeIfAbsent(cur.cnt, v -> new AtomicInteger(0)).incrementAndGet();

            // var test = cur.pos.copy().wrapAround(0, maxX - 1, 0, maxY - 1);
            // // if(test.equals(new SimplePos(65, 65))){
            // // if(test.equals(new SimplePos(0, 0))){
            // // if(test.equals(new SimplePos(103, 107))){
            // // if(test.equals(new SimplePos(56, 22))){
            // // if(test.equals(new SimplePos(2, 4))){
            // if(test.equals(new SimplePos(0, 65))) {
            //     int sqX = cur.pos.getX() / maxX;
            //     if (cur.pos.getX() < 0) {
            //         sqX--;
            //     }

            //     int sqY = cur.pos.getY() / maxY;
            //     if (cur.pos.getY() < 0) {
            //         sqY--;
            //     }

            //     System.out.println("sq: " + sqX + "," + sqY + " pos: " + cur.pos + ". Cnt = " + cur.cnt);
            // }

            // if ((steps2 % 2 == 0 && cur.cnt % 2 == 0) || (steps2 % 2 == 1 && cur.cnt % 2 == 1)) {
            // response++;
            // }
            for (var nb : cur.pos.getNeighbors(false)) {
                if (m.get(nb.copy().wrapAround(0, maxX - 1, 0, maxY - 1)) != '.') {
                    continue;
                }
                toCheck.add(new Step(nb, cur.cnt + 1));
            }
        }

        // for (var entry : megaSeen.entrySet()) {
        //     // if(entry.getKey().equals(new SimplePos(0, 1))){
        //     //     System.out.println(entry.getKey());
        //     // }
        //     if(entry.getValue().values().stream().mapToInt(v->v.get()).sum() != 4){
        //         System.out.println(entry.getKey());
        //     }
        // }
        
        for (var entry : megaSeen.entrySet()) {
            if(entry.getKey().equals(new SimplePos(65, 65))){
                entry.getValue().entrySet().removeIf(v->v.getKey()!=131);
                continue;
            }
            List<Integer> c = entry.getValue().keySet().stream().sorted(Integer::compare).toList();
            for(var z:c){
                entry.getValue().getOrDefault(z+131*1, new AtomicInteger()).addAndGet(-2);
                entry.getValue().getOrDefault(z+131*2, new AtomicInteger()).addAndGet(-3);
                entry.getValue().getOrDefault(z+131*3, new AtomicInteger()).addAndGet(-4);
                entry.getValue().getOrDefault(z+131*4, new AtomicInteger()).addAndGet(-5);
                entry.getValue().getOrDefault(z+131*5, new AtomicInteger()).addAndGet(-6);
            }
            entry.getValue().entrySet().removeIf(v->v.getValue().get() <= 0);
            c = entry.getValue().keySet().stream().sorted(Integer::compare).toList();
            int sum = 0;
            List<Integer> toRemove = new ArrayList<>();
            for(var z:c){
                sum += entry.getValue().get(z).get();
                if(sum > 4){
                    toRemove.add(z);
                }
            }
            entry.getValue().entrySet().removeIf(v->toRemove.contains(v.getKey()));

            if(entry.getValue().values().stream().mapToInt(v->v.get()).sum() != 4){
                System.out.println(entry.getKey());
            }
        }

        // Set<SimplePos> ttss = new HashSet<>(megaSeen.keySet());
        // ttss.addAll(m.entrySet().stream().filter(v->v.getValue() == '#').map(v->v.getKey()).collect(Collectors.toSet()));

        // MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(ttss));

        for (var entry : megaSeen.entrySet()) {
            for (var chechEntry : entry.getValue().entrySet()) {
                // if(entry.getValue().containsKey(chechEntry.getKey() - 131) && chechEntry.getKey() > 131){
                //     continue;
                // }
                int begin;
                int add = 1;
                if (chechEntry.getKey() % 2 == steps2 % 2) {
                    begin = chechEntry.getKey();
                } else {
                    begin = chechEntry.getKey() + 131;
                    add = 2;
                }
                long s = 0;
                for(int i = begin; i <= steps2; i += 131*2){
                    s+=add;
                    add+=2;
                }
                response += s * chechEntry.getValue().get();
            }
        }

        if(steps2 %2 == 0){
            response++;
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    public long doPuzzle2_BF(List<String> eventData) {
        long response = 0;

        int maxX = eventData.get(0).length();
        int maxY = eventData.size();

        var m = StringConverter.toMap(eventData);
        SimplePos start = m.entrySet().stream().filter(v -> v.getValue() == 'S').map(Entry::getKey).findFirst().orElseThrow();
        m.put(start, '.');

        Set<SimplePos> seen = new HashSet<>();
        List<Step> toCheck = new LinkedList<>();
        toCheck.add(new Step(start, 0));
        while (!toCheck.isEmpty()) {
            var cur = toCheck.remove(0);
            if (cur.cnt > steps2) {
                break;
            }
            if (seen.contains(cur.pos)) {
                continue;
            }
            seen.add(cur.pos);

            if ((steps2 % 2 == 0 && cur.cnt % 2 == 0) || (steps2 % 2 == 1 && cur.cnt % 2 == 1)) {
            response++;
            }
            for (var nb : cur.pos.getNeighbors(false)) {
                if (m.get(nb.copy().wrapAround(0, maxX - 1, 0, maxY - 1)) != '.') {
                    continue;
                }
                toCheck.add(new Step(nb, cur.cnt + 1));
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
