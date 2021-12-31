package net.eugenpaul.adventofcode.y2015.day13;

import java.util.HashMap;
import java.util.Map;

public class Person {
    private String name;
    private Map<String, Integer> happiness;

    public Person(String name) {
        this.name = name;
        happiness = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setRelation(String to, int relation) {
        happiness.put(to, relation);
    }

    public int getHappines(String neighbor1, String neighbor2) {
        return happiness.get(neighbor1) + happiness.get(neighbor2);
    }
}
