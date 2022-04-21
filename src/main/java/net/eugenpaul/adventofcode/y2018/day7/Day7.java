package net.eugenpaul.adventofcode.y2018.day7;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day7 extends SolutionTemplate {

    @Getter
    private String correctOrder;
    @Getter
    private int timeToComplete;

    @Setter
    private int workerCount = 5;

    @Setter
    private int addTimeProStep = 60;

    public static void main(String[] args) {
        Day7 puzzle = new Day7();
        puzzle.doPuzzleFromFile("y2018/day7/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        correctOrder = doPuzzle1(eventData);
        timeToComplete = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "correctOrder  : " + getCorrectOrder());
        logger.log(Level.INFO, () -> "timeToComplete  : " + getTimeToComplete());

        return true;
    }

    private String doPuzzle1(List<String> eventData) {
        /** key: step, value: List of open Requirements */
        Map<Character, List<Character>> restSteps = new HashMap<>();
        TreeMap<Character, Boolean> openInstructions = new TreeMap<>();

        StringBuilder orderBuilder = new StringBuilder();

        convertEventData(eventData, restSteps, openInstructions);

        while (!restSteps.isEmpty()) {
            var completeList = getCompleteList(restSteps, openInstructions);
            orderBuilder.append(completeList.get(0));
            removeCompletedSteps(restSteps, completeList.get(0));
            openInstructions.remove(completeList.get(0));
        }

        return orderBuilder.toString() + openInstructions.keySet().stream().map(v -> v + "").reduce("", (a, b) -> a + b);
    }

    private void removeCompletedSteps(Map<Character, List<Character>> restSteps, Character completeStep) {
        var iterator = restSteps.entrySet().iterator();
        while (iterator.hasNext()) {
            var stepsEntry = iterator.next();
            stepsEntry.getValue().remove(completeStep);
            if (stepsEntry.getValue().isEmpty()) {
                iterator.remove();
            }
        }

    }

    private int doPuzzle2(List<String> eventData) {
        /** key: step, value: List of open Requirements */
        Map<Character, List<Character>> restSteps = new HashMap<>();
        TreeMap<Character, Boolean> openInstructions = new TreeMap<>();

        /** key: work until (time), value: steps in work */
        Map<Integer, List<Character>> inWork = new HashMap<>();

        convertEventData(eventData, restSteps, openInstructions);

        int currentTime = 0;
        while (true) {
            removeCompletedSteps(inWork, currentTime, restSteps);
            int freeWorkerCount = workerCount - inWork.size();
            if (freeWorkerCount > 0) {
                var completeList = getCompleteList(restSteps, openInstructions);
                for (int i = 0; i < freeWorkerCount && i < completeList.size(); i++) {
                    Character nextStep = completeList.get(i);
                    Integer workUntil = completeList.get(i) - 'A' + addTimeProStep + 1 + currentTime;

                    inWork.computeIfAbsent(workUntil, k -> new LinkedList<>())//
                            .add(nextStep);

                    openInstructions.remove(completeList.get(i));
                }
            }

            currentTime++;

            if (restSteps.isEmpty()) {
                break;
            }
        }

        return inWork.keySet().stream().sorted((a, b) -> b - a).findFirst().orElseThrow();
    }

    private void removeCompletedSteps(Map<Integer, List<Character>> inWork, int currentTime, Map<Character, List<Character>> restSteps) {
        var completeList = inWork.get(currentTime);
        if (completeList == null) {
            return;
        }

        for (Character character : completeList) {
            removeCompletedSteps(restSteps, character);
        }

        inWork.remove(currentTime);
    }

    private void convertEventData(List<String> eventData, Map<Character, List<Character>> restSteps, TreeMap<Character, Boolean> openInstructions) {
        for (String data : eventData) {
            Character step = data.charAt(36);
            Character requirement = data.charAt(5);

            restSteps.computeIfAbsent(step, k -> new LinkedList<>()).add(requirement);

            openInstructions.put(step, true);
            openInstructions.put(requirement, true);
        }
    }

    private List<Character> getCompleteList(Map<Character, List<Character>> restSteps, TreeMap<Character, Boolean> openInstructions) {
        List<Character> responseCompleteList = new LinkedList<>();
        for (Entry<Character, Boolean> instructionSet : openInstructions.entrySet()) {
            var requirements = restSteps.get(instructionSet.getKey());
            if (requirements == null || requirements.isEmpty()) {
                responseCompleteList.add(instructionSet.getKey());
            }
        }

        return responseCompleteList;
    }

}
