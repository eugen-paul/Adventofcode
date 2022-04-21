package net.eugenpaul.adventofcode.y2015.day13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Permutation;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day13 extends SolutionTemplate {

    @Getter
    private int maxHappiness;
    @Getter
    private int maxHappinessWithMe;

    public static void main(String[] args) {
        Day13 puzzle = new Day13();
        puzzle.doPuzzleFromFile("y2015/day13/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        FullPlan personList = new FullPlan();

        for (String data : eventData) {
            String[] parts = data.split(" ");
            String name = parts[0];
            String neighbor = parts[10].substring(0, parts[10].length() - 1);
            int relation = parts[2].equals("gain") ? Integer.parseInt(parts[3]) : Integer.parseInt(parts[3]) * (-1);
            personList.addRelation(name, neighbor, relation);
        }

        List<String> names = personList.getPersonList();
        maxHappiness = computeMaxHappiness(personList, names);

        // add me ("I") to list and compute the maxHappinessWithMe
        for (String name : names) {
            personList.addRelation(name, "I", 0);
            personList.addRelation("I", name, 0);
        }
        names = personList.getPersonList();
        maxHappinessWithMe = computeMaxHappiness(personList, names);

        logger.log(Level.INFO, () -> "MaxHappiness:         " + getMaxHappiness());
        logger.log(Level.INFO, () -> "MaxHappiness with me: " + getMaxHappinessWithMe());

        return true;
    }

    private int computeMaxHappiness(FullPlan personList, List<String> names) {
        List<Integer> happinessList = new ArrayList<>();
        Permutation.permutationsRecursive(//
                names.size(), //
                names.toArray(new String[0]), //
                v -> happinessList.add(personList.getTotalHappiness(v) //
                ));

        Collections.sort(happinessList);
        return happinessList.get(happinessList.size() - 1);
    }

}
