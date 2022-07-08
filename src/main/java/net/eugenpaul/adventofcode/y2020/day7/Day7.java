package net.eugenpaul.adventofcode.y2020.day7;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day7 extends SolutionTemplate {

    @Getter
    private long bagsWithShinyGold;
    @Getter
    private long bagsInShinyGold;

    public static void main(String[] args) {
        Day7 puzzle = new Day7();
        puzzle.doPuzzleFromFile("y2020/day7/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        var bagsData = initBags(eventData);

        String myBagColor = "shiny gold";

        bagsWithShinyGold = doPuzzle1(bagsData, myBagColor);
        bagsInShinyGold = doPuzzle2(bagsData, myBagColor, new HashMap<>());

        logger.log(Level.INFO, () -> "bagsWithShinyGold  : " + getBagsWithShinyGold());
        logger.log(Level.INFO, () -> "bagsInShinyGold  : " + getBagsInShinyGold());

        return true;
    }

    private long doPuzzle1(Map<String, Map<String, Integer>> bagsData, String myBagColor) {
        LinkedList<String> bagsToCheck = new LinkedList<>();

        bagsToCheck.addAll(bagsWithColor(bagsData, myBagColor));

        Set<String> bagsWithMyBagColor = new HashSet<>();

        while (!bagsToCheck.isEmpty()) {
            String firstToCheck = bagsToCheck.removeFirst();
            if (bagsWithMyBagColor.contains(firstToCheck)) {
                continue;
            }
            bagsWithMyBagColor.add(firstToCheck);
            bagsToCheck.addAll(bagsWithColor(bagsData, firstToCheck));
        }
        return bagsWithMyBagColor.size();
    }

    private long doPuzzle2(Map<String, Map<String, Integer>> bagsData, String myBagColor, Map<String, Long> cap) {

        long response = 0;

        Map<String, Integer> contentOfBag = bagsData.get(myBagColor);
        for (var entry : contentOfBag.entrySet()) {
            if (cap.containsKey(entry.getKey())) {
                response += entry.getValue() + entry.getValue() * cap.get(entry.getKey());
            } else {
                response += entry.getValue() + entry.getValue() * doPuzzle2(bagsData, entry.getKey(), cap);
            }
        }

        cap.put(myBagColor, response);

        return response;
    }

    private Map<String, Map<String, Integer>> initBags(List<String> eventData) {
        Map<String, Map<String, Integer>> response = new HashMap<>();

        for (String data : eventData) {
            int delimer1 = data.indexOf(" bags contain ");
            String key = data.substring(0, delimer1);
            String[] values = data.substring(delimer1 + " bags contain ".length()).split(",");

            Map<String, Integer> bagData = new HashMap<>();
            response.put(key, bagData);

            for (String b : values) {
                if (b.startsWith("no other bags")) {
                    continue;
                }
                String trimB = b.trim();
                int numberPos = trimB.indexOf(" ");
                int endPos = trimB.indexOf(" bag");

                Integer count = Integer.parseInt(trimB.substring(0, numberPos));
                String color = trimB.substring(numberPos + 1, endPos);

                bagData.put(color, count);
            }
        }

        return response;
    }

    private List<String> bagsWithColor(Map<String, Map<String, Integer>> bags, String color) {
        return bags.entrySet().stream()//
                .filter(v -> v.getValue().get(color) != null)//
                .map(Entry::getKey)//
                .collect(Collectors.toList());
    }

}
