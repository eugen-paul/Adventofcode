package net.eugenpaul.adventofcode.y2015.day13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullPlan {
    private Map<String, Person> plan;

    public FullPlan() {
        plan = new HashMap<>();
    }

    public void addRelation(String person, String neighbor, int relation) {
        plan.compute(person, (k, v) -> {
            if (null == v) {
                Person data = new Person(person);
                data.setRelation(neighbor, relation);
                return data;
            }
            v.setRelation(neighbor, relation);
            return v;
        });
    }

    public List<String> getPersonList() {
        return new ArrayList<>(plan.keySet());
    }

    public int getTotalHappiness(String[] arrangement) {
        int responseHappines = 0;
        for (int i = 0; i < arrangement.length; i++) {
            if (i == 0) {
                responseHappines += plan.get(arrangement[i]).getHappines(arrangement[i + 1], arrangement[arrangement.length - 1]);
            } else if (i == arrangement.length - 1) {
                responseHappines += plan.get(arrangement[i]).getHappines(arrangement[0], arrangement[i - 1]);
            } else {
                responseHappines += plan.get(arrangement[i]).getHappines(arrangement[i + 1], arrangement[i - 1]);
            }
        }
        return responseHappines;
    }
}
