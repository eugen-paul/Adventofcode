package net.eugenpaul.adventofcode.y2015.day13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullPlan {

    private class Relations {
        private Map<String, Integer> happiness = new HashMap<>();

        public void setRelation(String to, int relation) {
            happiness.put(to, relation);
        }

        public int getHappines(String neighbor1, String neighbor2) {
            return happiness.get(neighbor1) + happiness.get(neighbor2);
        }
    }

    private Map<String, Relations> personRelations;

    public FullPlan() {
        personRelations = new HashMap<>();
    }

    public void addRelation(String person, String neighbor, int relation) {
        personRelations.computeIfAbsent(person, k -> new Relations())//
                .setRelation(neighbor, relation);
    }

    public List<String> getPersonList() {
        return new ArrayList<>(personRelations.keySet());
    }

    public int getTotalHappiness(String[] arrangement) {
        int responseHappines = 0;
        for (int i = 0; i < arrangement.length; i++) {
            String currentPerson = arrangement[i];
            String neighborNext;
            String neighborPrevious;

            if (i == 0) {
                neighborPrevious = arrangement[arrangement.length - 1];
                neighborNext = arrangement[i + 1];
            } else if (i == arrangement.length - 1) {
                neighborPrevious = arrangement[arrangement.length - 2];
                neighborNext = arrangement[0];
            } else {
                neighborPrevious = arrangement[i - 1];
                neighborNext = arrangement[i + 1];
            }

            responseHappines += personRelations.get(currentPerson).getHappines(neighborPrevious, neighborNext);
        }
        return responseHappines;
    }
}
