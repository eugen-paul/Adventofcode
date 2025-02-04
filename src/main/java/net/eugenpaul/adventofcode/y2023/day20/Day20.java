package net.eugenpaul.adventofcode.y2023.day20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day20 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    private enum Signal {
        LOW, HIGH, NONE;

        @Override
        public String toString() {
            return switch (this) {
            case HIGH -> "H";
            case LOW -> "L";
            case NONE -> "-";
            };
        }
    }

    private record Step(String from, String to, Signal sig) {
    }

    private abstract static class Module {
        String name;
        List<String> outputs = new ArrayList<>();

        abstract void addInput(String in);

        abstract List<Step> doInput(String from, Signal sig);
    }

    private static class ModuleFactory {
        static Module crate(String data) {
            return switch (data.charAt(0)) {
            case '%' -> FF.create(data);
            case '&' -> Con.create(data);
            case 'b' -> Broadcast.create(data);
            default -> throw new IllegalArgumentException(data);
            };
        }
    }

    private static class Empty extends Module {

        boolean end = false;

        Empty(String name) {
            this.name = name;
        }

        @Override
        void addInput(String in) {
            //
        }

        @Override
        List<Step> doInput(String from, Signal sig) {
            if (sig == Signal.LOW) {
                end = true;
            }
            return Collections.emptyList();
        }

        @Override
        public String toString() {
            return "E";
        }
    }

    private static class FF extends Module {
        boolean on = false;

        static Module create(String data) {
            var splits = data.replace(",", "").split(" ");
            var resp = new FF();
            resp.name = splits[0].substring(1);
            for (int i = 2; i < splits.length; i++) {
                resp.outputs.add(splits[i]);
            }
            return resp;
        }

        @Override
        public void addInput(String in) {
            // nothig
        }

        @Override
        public List<Step> doInput(String from, Signal sig) {
            if (sig == Signal.LOW) {
                var r = on ? Signal.LOW : Signal.HIGH;
                on = !on;
                return outputs.stream().map(v -> new Step(name, v, r)).toList();
            }
            return Collections.emptyList();
        }

        @Override
        public String toString() {
            return "FF " + name + " " + on;
        }
    }

    private static class Con extends Module {
        Map<String, Signal> inputs = new HashMap<>();
        List<String> ins = new ArrayList<>();
        
        static Module create(String data) {
            var splits = data.replace(",", "").split(" ");
            var resp = new Con();
            resp.name = splits[0].substring(1);
            for (int i = 2; i < splits.length; i++) {
                resp.outputs.add(splits[i]);
            }
            return resp;
        }

        @Override
        void addInput(String in) {
            inputs.computeIfAbsent(in, v -> Signal.LOW);
            ins.add(in);
        }

        @Override
        List<Step> doInput(String from, Signal sig) {
            inputs.put(from, sig);
            if (inputs.values().stream().allMatch(v -> v == Signal.HIGH)) {
                return outputs.stream().map(v -> new Step(name, v, Signal.LOW)).toList();
            }
            return outputs.stream().map(v -> new Step(name, v, Signal.HIGH)).toList();
        }

        @Override
        public String toString() {
            return "CO " + name + " " + ins.stream().map(v -> v + ": " + inputs.get(v)).reduce("", (a, b) -> a + ", " + b);
        }

        public boolean isZero(){
            return inputs.values().stream().allMatch(v->v == Signal.LOW);
        }

        public boolean isOne(){
            return inputs.values().stream().allMatch(v->v == Signal.HIGH);
        }
    }

    private static class Broadcast extends Module {
        static Module create(String data) {
            var splits = data.replace(",", "").split(" ");
            var resp = new Broadcast();
            resp.name = splits[0];
            for (int i = 2; i < splits.length; i++) {
                resp.outputs.add(splits[i]);
            }
            return resp;
        }

        @Override
        void addInput(String in) {
            throw new UnsupportedOperationException("Unimplemented method 'addInput'");
        }

        @Override
        List<Step> doInput(String from, Signal sig) {
            return outputs.stream().map(v -> new Step(name, v, Signal.LOW)).toList();
        }

        @Override
        public String toString() {
            return "BB";
        }
    }

    public static void main(String[] args) {
        Day20 puzzle = new Day20();
        puzzle.doPuzzleFromFile("y2023/day20/puzzle1.txt");
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

        Map<String, Module> modules = new HashMap<>();
        for (String data : eventData) {
            Module m = ModuleFactory.crate(data);
            modules.put(m.name, m);
        }

        List<Module> e = new ArrayList<>();
        for (var mEntry : modules.entrySet()) {
            for (var out : mEntry.getValue().outputs) {
                if (modules.containsKey(out)) {
                    modules.get(out).addInput(mEntry.getKey());
                } else {
                    e.add(new Empty(out));
                }
            }
        }

        for (var m : e) {
            modules.put(m.name, m);
        }

        List<Step> steps = new LinkedList<>();

        long lows = 0;
        long highs = 0;

        for (int i = 0; i < 1000; i++) {
            steps.add(new Step("button", "broadcaster", Signal.LOW));
            lows++;
            while (!steps.isEmpty()) {
                var step = steps.removeFirst();
                if (step.sig == Signal.NONE) {
                    continue;
                }
                var m = modules.get(step.to);
                var next = m.doInput(step.from, step.sig);
                lows += next.stream().filter(v -> v.sig == Signal.LOW).count();
                highs += next.stream().filter(v -> v.sig == Signal.HIGH).count();
                steps.addAll(next);
            }
        }

        response = lows * highs;

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        Map<String, Module> modules = new HashMap<>();
        for (String data : eventData) {
            Module m = ModuleFactory.crate(data);
            modules.put(m.name, m);
        }

        List<Module> e = new ArrayList<>();
        for (var mEntry : modules.entrySet()) {
            for (var out : mEntry.getValue().outputs) {
                if (modules.containsKey(out)) {
                    modules.get(out).addInput(mEntry.getKey());
                } else {
                    e.add(new Empty(out));
                }
            }
        }

        for (var m : e) {
            modules.put(m.name, m);
        }

        List<Step> steps = new LinkedList<>();

        long i = 0;
        long delta_jv = 0;
        long delta_qs = 0;
        long delta_jm = 0;
        long delta_pr = 0;
        while (!((Empty) modules.get("rx")).end && i < 10_000) {
            if(((Con)modules.get("jv")).isZero()){
                if(i > 1000 && delta_jv == 0){
                    delta_jv = i;
                }
            }
            if(((Con)modules.get("qs")).isZero()){
                if(i > 1000 && delta_qs == 0){
                    delta_qs = i;
                }
            }
            if(((Con)modules.get("jm")).isZero()){
                if(i > 1000 && delta_jm == 0){
                    delta_jm = i;
                }
            }
            if(((Con)modules.get("pr")).isZero()){
                if(i > 1000 && delta_pr == 0){
                    delta_pr = i;
                }
            }
            i++;
            
            steps.add(new Step("button", "broadcaster", Signal.LOW));
            while (!steps.isEmpty()) {
                var step = steps.removeFirst();
                if (step.sig == Signal.NONE) {
                    continue;
                }
                var m = modules.get(step.to);
                var next = m.doInput(step.from, step.sig);
                steps.addAll(next);
            }
        }

        response = delta_jv * delta_qs * delta_jm * delta_pr;

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
