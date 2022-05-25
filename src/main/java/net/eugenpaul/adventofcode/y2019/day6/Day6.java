package net.eugenpaul.adventofcode.y2019.day6;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day6 extends SolutionTemplate {

    @Getter
    private int numberOfOrbits;
    @Getter
    private int youToSan;

    public static void main(String[] args) {
        Day6 puzzle = new Day6();
        puzzle.doPuzzleFromFile("y2019/day6/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        var orbits = initOrbits(eventData);

        numberOfOrbits = orbits.keySet().stream()//
                .mapToInt(v -> lenToCom(v, orbits))//
                .sum();

        LinkedList<String> youToCom = pathToCom("YOU", orbits);
        LinkedList<String> sanToCom = pathToCom("SAN", orbits);

        if (!youToCom.isEmpty() && !sanToCom.isEmpty()) {
            while (youToCom.getLast().equals(sanToCom.getLast())) {
                youToCom.removeLast();
                sanToCom.removeLast();
            }
        }

        youToSan = youToCom.size() + sanToCom.size();

        logger.log(Level.INFO, () -> "numberOfOrbits  : " + getNumberOfOrbits());
        logger.log(Level.INFO, () -> "youToSan  : " + getYouToSan());

        return true;
    }

    private Map<String, String> initOrbits(List<String> eventData) {
        Map<String, String> response = new HashMap<>();

        for (String orbit : eventData) {
            String[] data = orbit.split("\\)");
            response.put(data[1], data[0]);
        }

        return response;
    }

    private int lenToCom(String obj, Map<String, String> orbits) {
        int response = 0;
        String testObj = orbits.get(obj);
        while (testObj != null) {
            testObj = orbits.get(testObj);
            response++;
        }
        return response;
    }

    private LinkedList<String> pathToCom(String obj, Map<String, String> orbits) {
        LinkedList<String> response = new LinkedList<>();
        String testObj = orbits.get(obj);
        while (testObj != null) {
            response.add(testObj);
            testObj = orbits.get(testObj);
        }
        return response;
    }

}
