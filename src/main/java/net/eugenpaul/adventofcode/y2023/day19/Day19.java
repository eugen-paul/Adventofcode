package net.eugenpaul.adventofcode.y2023.day19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Stream;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day19 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    private static class Part {
        int x;
        int m;
        int a;
        int s;

        public static Part fromString(String data) {
            var d = data.substring(1, data.length() - 1);
            var splits = d.split(",");

            var resp = new Part();
            resp.x = Integer.parseInt(splits[0].substring(2));
            resp.m = Integer.parseInt(splits[1].substring(2));
            resp.a = Integer.parseInt(splits[2].substring(2));
            resp.s = Integer.parseInt(splits[3].substring(2));

            return resp;
        }

        public long sum() {
            return (long) x + m + a + s;
        }
    }

    private static class Rule {
        char c;
        boolean gt;
        boolean alwaysOk = false;
        int value;
        String target;

        static Rule fromString(String data) {
            var splits = data.split(":");

            if (splits.length == 1) {
                var response = new Rule();
                response.alwaysOk = true;
                response.target = data;
                return response;
            }

            var response = new Rule();

            response.c = splits[0].charAt(0);
            response.gt = splits[0].charAt(1) == '>';
            response.value = Integer.parseInt(splits[0].substring(2));
            response.target = splits[1];

            return response;
        }

        public boolean isMatch(Part p) {
            if (alwaysOk) {
                return true;
            }

            return switch (c) {
            case 'x' -> gt ? value < p.x : value > p.x;
            case 'm' -> gt ? value < p.m : value > p.m;
            case 'a' -> gt ? value < p.a : value > p.a;
            case 's' -> gt ? value < p.s : value > p.s;
            default -> throw new IllegalArgumentException("" + c);
            };
        }
    }

    private static class Workflow {

        String name;
        List<Rule> rules;

        public static Workflow fromString(String data) {
            var ind = data.indexOf('{');
            var resp = new Workflow();
            resp.name = data.substring(0, ind);
            var r = data.substring(ind + 1, data.length() - 1).split(",");
            resp.rules = Stream.of(r).map(Rule::fromString).toList();
            return resp;
        }

        public String getTarget(Part p) {
            for (var r : rules) {
                if (r.isMatch(p)) {
                    return r.target;
                }
            }
            throw new IllegalArgumentException(p.toString());
        }
    }

    public static void main(String[] args) {
        Day19 puzzle = new Day19();
        puzzle.doPuzzleFromFile("y2023/day19/puzzle1.txt");
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

        Map<String, Workflow> wf = new HashMap<>();
        List<Part> parts = new LinkedList<>();

        boolean blank = false;
        for (String data : eventData) {
            if (data.isBlank()) {
                blank = true;
                continue;
            }

            if (!blank) {
                var w = Workflow.fromString(data);
                wf.put(w.name, w);
            } else {
                parts.add(Part.fromString(data));
            }
        }

        List<Part> ok = new ArrayList<>();

        for (var part : parts) {
            String target = "in";
            while (!target.equals("A") && !target.equals("R")) {
                target = wf.get(target).getTarget(part);
            }

            if (target.equals("A")) {
                ok.add(part);
            }
        }

        response = ok.stream().mapToLong(Part::sum).sum();

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        Map<String, Workflow> wf = new HashMap<>();

        for (String data : eventData) {
            if (data.isBlank()) {
                break;
            }

            var w = Workflow.fromString(data);
            wf.put(w.name, w);
        }

        response = p2(1, 4000, 1, 4000, 1, 4000, 1, 4000, wf, "in");

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    public long p2(int minX, int maxX, int minM, int maxM, int minA, int maxA, int minS, int maxS, Map<String, Workflow> all, String w) {
        if (minX > maxX || minM > maxM || minA > maxA || minS > maxS) {
            return 0;
        }
        if (w.equals("A")) {
            return ((long) maxX - minX + 1) * (maxM - minM + 1) * (maxA - minA + 1) * (maxS - minS + 1);
        }
        if (w.equals("R")) {
            return 0;
        }

        long resp = 0;

        Workflow wf = all.get(w);
        for (var r : wf.rules) {
            if (minX > maxX || minM > maxM || minA > maxA || minS > maxS) {
                break;
            }
            if (r.alwaysOk && r.target.equals("R")) {
                break;
            }
            if (r.alwaysOk && r.target.equals("A")) {
                resp += ((long) maxX - minX + 1) * (maxM - minM + 1) * (maxA - minA + 1) * (maxS - minS + 1);
                break;
            }
            if(r.alwaysOk){
                resp += p2(minX, maxX, minM, maxM, minA, maxA, minS, maxS, all, r.target);
            }
            if (r.c == 'x') {
                if (r.gt) {
                    if (r.value < maxX) {
                        int tmp = minX;
                        minX = Math.max(minX, r.value + 1);
                        resp += p2(minX, maxX, minM, maxM, minA, maxA, minS, maxS, all, r.target);
                        minX = tmp;
                        maxX = r.value;
                    }
                } else {
                    if (minX < r.value) {
                        int tmp = maxX;
                        maxX = Math.min(maxX, r.value - 1);
                        resp += p2(minX, maxX, minM, maxM, minA, maxA, minS, maxS, all, r.target);
                        maxX = tmp;
                        minX = r.value;
                    }
                }
            }
            if (r.c == 'm') {
                if (r.gt) {
                    if (r.value < maxM) {
                        int tmp = minM;
                        minM = Math.max(minM, r.value + 1);
                        resp += p2(minX, maxX, minM, maxM, minA, maxA, minS, maxS, all, r.target);
                        minM = tmp;
                        maxM = r.value;
                    }
                } else {
                    if (minM < r.value) {
                        int tmp = maxM;
                        maxM = Math.min(maxM, r.value - 1);
                        resp += p2(minX, maxX, minM, maxM, minA, maxA, minS, maxS, all, r.target);
                        maxM = tmp;
                        minM = r.value;
                    }
                }
            }
            if (r.c == 'a') {
                if (r.gt) {
                    if (r.value < maxA) {
                        int tmp = minA;
                        minA = Math.max(minA, r.value + 1);
                        resp += p2(minX, maxX, minM, maxM, minA, maxA, minS, maxS, all, r.target);
                        minA = tmp;
                        maxA = r.value;
                    }
                } else {
                    if (minA < r.value) {
                        int tmp = maxA;
                        maxA = Math.min(maxA, r.value - 1);
                        resp += p2(minX, maxX, minM, maxM, minA, maxA, minS, maxS, all, r.target);
                        maxA = tmp;
                        minA = r.value;
                    }
                }
            }
            if (r.c == 's') {
                if (r.gt) {
                    if (r.value < maxS) {
                        int tmp = minS;
                        minS = Math.max(minS, r.value + 1);
                        resp += p2(minX, maxX, minM, maxM, minA, maxA, minS, maxS, all, r.target);
                        minS = tmp;
                        maxS = r.value;
                    }
                } else {
                    if (minS < r.value) {
                        int tmp = maxS;
                        maxS = Math.min(maxS, r.value - 1);
                        resp += p2(minX, maxX, minM, maxM, minA, maxA, minS, maxS, all, r.target);
                        maxS = tmp;
                        minS = r.value;
                    }
                }
            }
        }

        return resp;
    }

}
