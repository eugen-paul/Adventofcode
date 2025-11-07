package net.eugenpaul.adventofcode.y2024.day24;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day24 extends SolutionTemplate {

    private enum OP {
        AND, OR, XOR;

        public char comp(char a, char b) {
            return switch (this) {
            case AND -> (a == b && a == '1') ? '1' : '0';
            case OR -> (a == '1' || b == '1') ? '1' : '0';
            case XOR -> (a != b) ? '1' : '0';
            };
        }
    }

    private record Gate(String a, OP op, String b) {
        public Gate(String a, OP op, String b) {
            if (a.compareTo(b) > 0) {
                this.a = b;
                this.b = a;
            } else {
                this.a = a;
                this.b = b;
            }
            this.op = op;
        }

    }

    @Getter
    private long totalScore;
    @Getter
    private String totalScore2;

    public static void main(String[] args) {
        Day24 puzzle = new Day24();
        puzzle.doPuzzleFromFile("y2024/day24/puzzle1.txt");
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

        Map<String, Gate> outToIn = new HashMap<>();
        Set<String> zs = new HashSet<>();
        Map<String, Character> allValues = new HashMap<>();
        for (String line : eventData) {
            if (line.isBlank()) {
                continue;
            }
            if (line.contains(":")) {
                var splits = line.split(":");
                var name = splits[0];
                var value = splits[1].trim().charAt(0);
                allValues.put(name, value);
                if (name.startsWith("z")) {
                    zs.add(name);
                }
            } else {
                var splits = line.split(" ");
                var name = splits[4];
                var gate = new Gate(splits[0], OP.valueOf(splits[1]), splits[2]);
                outToIn.put(name, gate);
                if (name.startsWith("z")) {
                    zs.add(name);
                }
            }
        }

        Map<String, Character> zValues = new HashMap<>();
        for (String z : zs) {
            zValues.put(z, getValue(z, outToIn, allValues));
        }

        List<String> r = zValues.entrySet().stream().sorted((a, b) -> b.getKey().compareTo(a.getKey())).map(e -> e.getValue() + "").toList();

        String responseS = String.join("", r);
        response = Long.parseLong(responseS, 2);

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public Character getValue(String name, Map<String, Gate> outToIn, Map<String, Character> allValues) {
        if (allValues.containsKey(name)) {
            return allValues.get(name);
        }

        Gate g = outToIn.get(name);
        Character r = g.op.comp( //
                getValue(g.a, outToIn, allValues), //
                getValue(g.b, outToIn, allValues) //
        );

        allValues.put(name, r);

        return r;
    }

    public String doPuzzle2(List<String> eventData) {
        String response = "";

        Map<String, Gate> outToIn = new HashMap<>();
        Set<String> zs = new HashSet<>();
        Map<String, Character> allValues = new HashMap<>();
        for (String line : eventData) {
            if (line.isBlank()) {
                continue;
            }
            if (line.contains(":")) {
                var splits = line.split(":");
                var name = splits[0];
                var value = splits[1].trim().charAt(0);
                allValues.put(name, value);
                if (name.startsWith("z")) {
                    zs.add(name);
                }
            } else {
                var splits = line.split(" ");
                var name = splits[4];
                var gate = new Gate(splits[0], OP.valueOf(splits[1]), splits[2]);
                outToIn.put(name, gate);
                if (name.startsWith("z")) {
                    zs.add(name);
                }
            }
        }

        Map<String, Character> allZeroValues = new HashMap<>();
        for (int i = 0; i <= 44; i++) {
            String suffix = String.format("%02d", i);
            allZeroValues.put("x" + suffix, '0');
            allZeroValues.put("y" + suffix, '0');
        }

        for (int i = 0; i <= 44; i++) {
            String suffix = String.format("%02d", i);
            Map<String, Character> allZeroValuesCopy = new HashMap<>(allZeroValues);
            allZeroValuesCopy.put("x" + suffix, '1');
            allZeroValuesCopy.put("y" + suffix, '0');

            Map<String, Character> zValues = new HashMap<>();
            for (String z : zs) {
                zValues.put(z, getValue(z, outToIn, allZeroValuesCopy));
            }
            List<String> r = zValues.entrySet().stream().sorted((a, b) -> b.getKey().compareTo(a.getKey())).map(e -> e.getValue() + "").toList();
            String responseS = String.join("", r);
            System.out.println(responseS);
            for (int k = 0; k <= 44; k++) {
                String zSuffix = String.format("%02d", k);
                String z = "z" + zSuffix;
                if (k != i && zValues.get(z) != '0') {
                    System.out.println("error in " + zSuffix);
                }
                if (k == i && zValues.get(z) != '1') {
                    System.out.println("error in " + zSuffix);
                }
            }
        }
        // Error in z08, z09, z16, z17, z28, z29, z39, z40
        // kwv OR ctv -> z08 <- error, must be XOR
        // y08 XOR x08 -> kgn
        // gvw AND kgn -> kwv
        // y08 AND x08 -> ctv
        // hjm OR tss -> gvw
        // gvw XOR kgn -> vvr <- this mus be z08

        // pvv XOR rnq -> z16 <- ok
        // y16 AND x16 -> rnq <- error, must be XOR
        // gms OR ngf -> pvv
        // y16 XOR x16 -> bkr <- error
        // mcc OR bkr -> kbg
        // y15 AND x15 -> gms

        // y28 AND x28 -> z28 <- error, must be XOR
        // y28 XOR x28 -> ptk
        // djn XOR ptk -> tfb <- error, must be z28
        // qpg OR csr -> djn
        // x27 AND y27 -> csr

        // thk AND wnk -> z39 <- error, must be XOR
        // wnk XOR thk -> mqh <- error
        // y39 XOR x39 -> wnk

        // ------- Errors ----------
        // kwv OR ctv -> z08
        // gvw XOR kgn -> vvr

        // y16 AND x16 -> rnq
        // y16 XOR x16 -> bkr

        // y28 AND x28 -> z28
        // djn XOR ptk -> tfb

        // thk AND wnk -> z39
        // wnk XOR thk -> mqh

        response = "bkr,mqh,rnq,tfb,vvr,z08,z28,z39";

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
