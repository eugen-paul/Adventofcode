package net.eugenpaul.adventofcode.y2019.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day14 extends SolutionTemplate {

    @AllArgsConstructor
    @Getter
    private static class Reaction {
        private String output;
        private Long units;
        private Map<String, Long> input;

        private static Reaction fromString(String data) {
            String[] io = data.split("=");
            String[] i = io[0].split(",");

            Map<String, Long> inputMap = new HashMap<>();
            for (String inputData : i) {
                String[] id = inputData.trim().split(" ");
                inputMap.put(//
                        id[1], //
                        Long.parseLong(id[0]) //
                );
            }

            String[] o = io[1].split(" ");
            return new Reaction(//
                    o[2], //
                    Long.parseLong(o[1]), //
                    inputMap //
            );
        }
    }

    @Getter
    private long ore4Fuel;
    @Getter
    private long fuel41kkk;

    @Setter
    private boolean doStep2 = true;

    @Setter
    private boolean printDisplay = false;

    public static void main(String[] args) {
        Day14 puzzle = new Day14();
        puzzle.doPuzzleFromFile("y2019/day14/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        Map<String, Reaction> reactions = eventData.stream()//
                .map(Reaction::fromString)//
                .collect(Collectors.toMap(v -> v.getOutput(), v -> v));

        ore4Fuel = compOre4Fuel(reactions, 1L);

        if (doStep2) {
            fuel41kkk = doPuzzle2(reactions);
        } else {
            fuel41kkk = -1;
        }

        logger.log(Level.INFO, () -> "ore4Fuel   : " + getOre4Fuel());
        logger.log(Level.INFO, () -> "fuel41kkk  : " + getFuel41kkk());

        return true;
    }

    private long doPuzzle2(Map<String, Reaction> reactions) {
        long fuelMin = 1_000_000_000_000L / ore4Fuel;
        long fuelMax = fuelMin * 10;
        long currentFuel = fuelMin + (fuelMax - fuelMin) / 2;
        while (fuelMin < fuelMax) {
            long oreNeeded = compOre4Fuel(reactions, currentFuel);
            if (oreNeeded == 1_000_000_000_000L) {
                fuelMin = currentFuel;
                fuelMax = currentFuel;
            } else if (oreNeeded > 1_000_000_000_000L) {
                fuelMax = currentFuel - 1;
                currentFuel = fuelMin + (fuelMax - fuelMin) / 2;
            } else {
                fuelMin = currentFuel + 1;
                currentFuel = fuelMin + (fuelMax - fuelMin) / 2;
            }
        }
        return currentFuel;
    }

    private long compOre4Fuel(Map<String, Reaction> reactions, long count) {
        Reaction fuel = reactions.get("FUEL");
        Map<String, Long> need = new HashMap<>();
        need.put(fuel.getOutput(), count);

        return compOreNeeded(reactions, need);
    }

    private long compOreNeeded(Map<String, Reaction> reactions, Map<String, Long> need) {
        Map<String, Reaction> restReactions = new HashMap<>(reactions);

        while (!restReactions.isEmpty()) {
            String next = getNext(restReactions);
            var needEntry = need.remove(next);
            var reaction = restReactions.remove(next);
            if (needEntry == null) {
                continue;
            }

            Map<String, Long> inputNeeded = reaction.getInput();
            for (var entryNeeded : inputNeeded.entrySet()) {
                long reactCounter = (long) Math.ceil((double) needEntry / reaction.getUnits());
                need.compute(entryNeeded.getKey(), (k, v) -> {
                    if (v == null) {
                        return entryNeeded.getValue() * reactCounter;
                    }
                    return v + entryNeeded.getValue() * reactCounter;
                });
            }
        }
        return need.get("ORE");
    }

    private String getNext(Map<String, Reaction> restReactions) {
        for (var entry : restReactions.entrySet()) {
            boolean isOk = true;
            for (var entryInner : restReactions.entrySet()) {
                if (entryInner.getValue().getInput().containsKey(entry.getKey())) {
                    isOk = false;
                    break;
                }
            }
            if (isOk) {
                return entry.getKey();
            }
        }
        return null;
    }
}
