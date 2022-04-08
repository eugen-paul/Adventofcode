package net.eugenpaul.adventofcode.y2017.day24;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day24 extends SolutionTemplate {

    @AllArgsConstructor
    @Data
    private class Bridge {
        private int in;
        private int out;

        public Bridge(String data) {
            String[] elements = data.split("/");
            in = Integer.parseInt(elements[0]); //
            out = Integer.parseInt(elements[1]);//
        }

        public int getStrength() {
            return in + out;
        }

        public boolean hasPost(int port) {
            return in == port || out == port;
        }

        public int getOther(int port) {
            if (in == port) {
                return out;
            }
            return in;
        }
    }

    @AllArgsConstructor
    @Data
    private class Pair {
        private int length;
        private int strength;
    }

    @Getter
    private long strongestBridge;
    @Getter
    private long longestStrongestBridge;

    public static void main(String[] args) {
        Day24 puzzle = new Day24();
        puzzle.doPuzzleFromFile("y2017/day24/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        List<Bridge> bridges = eventData.stream()//
                .map(Bridge::new)//
                .collect(Collectors.toCollection(ArrayList::new));

        strongestBridge = getMaxStrength(bridges, 0, 0);
        longestStrongestBridge = getMaxLongestStrength(bridges, 0, new Pair(0, 0)).getStrength();

        logger.log(Level.INFO, () -> "strongestBridge: " + getStrongestBridge());
        logger.log(Level.INFO, () -> "longestStrongestBridge: " + getLongestStrongestBridge());

        return true;
    }

    private long getMaxStrength(List<Bridge> bridges, int input, long currentStrengt) {
        var possibleBridges = bridges.stream().filter(v -> v.hasPost(input)).collect(Collectors.toCollection(ArrayList::new));
        long bestStrength = currentStrengt;
        for (Bridge bridge : possibleBridges) {
            List<Bridge> subBridges = bridges.stream().filter(v -> v != bridge).collect(Collectors.toCollection(ArrayList::new));
            long subStrength = currentStrengt + bridge.getStrength();
            bestStrength = Math.max(bestStrength, getMaxStrength(subBridges, bridge.getOther(input), subStrength));
        }

        return bestStrength;
    }

    private Pair getMaxLongestStrength(List<Bridge> bridges, int input, Pair currentStrengt) {
        var possibleBridges = bridges.stream().filter(v -> v.hasPost(input)).collect(Collectors.toCollection(ArrayList::new));
        Pair bestStrength = currentStrengt;
        for (Bridge bridge : possibleBridges) {
            List<Bridge> subBridges = bridges.stream().filter(v -> v != bridge).collect(Collectors.toCollection(ArrayList::new));
            Pair subStrength = new Pair(currentStrengt.getLength() + 1, currentStrengt.getStrength() + bridge.getStrength());
            Pair subPair = getMaxLongestStrength(subBridges, bridge.getOther(input), subStrength);
            if (subPair.length > bestStrength.length || (subPair.length == bestStrength.length && subPair.strength > bestStrength.strength)) {
                bestStrength = subPair;
            }
        }

        return bestStrength;
    }

}
