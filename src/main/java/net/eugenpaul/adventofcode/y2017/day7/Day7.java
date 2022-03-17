package net.eugenpaul.adventofcode.y2017.day7;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day7 extends SolutionTemplate {

    @Getter
    private String root;
    @Getter
    private Integer weight;

    public static void main(String[] args) {
        Day7 puzzle = new Day7();
        puzzle.doPuzzleFromFile("y2017/day7/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        Map<String, DiscData> discs = eventData.stream()//
                .map(DiscData::fromString)//
                .collect(Collectors.toMap(DiscData::getName, v -> v));

        List<String> allProgrammNames = discs.keySet().stream().collect(Collectors.toList());

        for (var entry : discs.entrySet()) {
            entry.getValue().getPrograms().stream().forEach(allProgrammNames::remove);
        }

        root = allProgrammNames.get(0);
        logger.log(Level.INFO, () -> "root : " + getRoot());

        checkWeight(discs, root);
        logger.log(Level.INFO, () -> "weight : " + getWeight());

        return true;
    }

    private int checkWeight(Map<String, DiscData> discs, String currentRoot) {
        DiscData currentDisc = discs.get(currentRoot);
        if (currentDisc.getPrograms().isEmpty()) {
            return currentDisc.getWeight();
        }

        //compute recursive the weight of own children ans store it to map <childName, childWeight>
        Map<String, Integer> weights = currentDisc.getPrograms().stream()//
                .collect(Collectors.toMap(name -> name, k -> checkWeight(discs, k)));

        int fullDiscWeight = currentDisc.getWeight();

        Map<Integer, List<String>> childWeights = new HashMap<>();

        for (var entry : weights.entrySet()) {
            fullDiscWeight += entry.getValue();

            childWeights.compute(entry.getValue(), (k, v) -> {
                if (v == null) {
                    v = new LinkedList<>();
                }
                v.add(entry.getKey());
                return v;
            });
        }

        // if one of the childs has a wrong weight then the map childWeights will have more then one extry
        if (childWeights.size() != 1) {
            findWrongDisc(discs, childWeights);
        }

        return fullDiscWeight;
    }

    private void findWrongDisc(Map<String, DiscData> discs, Map<Integer, List<String>> childWeights) {
        int wrongTotalWeight = 0;
        String wrongDiscName = "";
        int rightTotalWeight = 0;

        for (var elememt : childWeights.entrySet()) {
            if (elememt.getValue().size() != 1) {
                rightTotalWeight = elememt.getKey();
            } else {
                wrongTotalWeight = elememt.getKey();
                wrongDiscName = elememt.getValue().get(0);
            }
        }

        if (!wrongDiscName.isBlank()) {
            DiscData wrongDisc = discs.get(wrongDiscName);
            if (weight == null) {
                weight = wrongDisc.getWeight() - (wrongTotalWeight - rightTotalWeight);
            }
        }
    }

}
