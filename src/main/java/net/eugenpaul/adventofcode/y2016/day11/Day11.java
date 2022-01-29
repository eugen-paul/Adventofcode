package net.eugenpaul.adventofcode.y2016.day11;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day11 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day11.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day11.class.getName());

    @Getter
    private int minSteps;

    @Getter
    private int minStepsPartTwo;

    public static void main(String[] args) {
        Day11 puzzle = new Day11();
        puzzle.doPuzzleFromFile("y2016/day11/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "minimum number of steps : " + getMinSteps());
        logger.log(Level.INFO, () -> "minimum number of steps (part two): " + getMinStepsPartTwo());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        // Part 1
        List<Floor> floors = eventData.stream().map(Floor::fromString).collect(Collectors.toList());

        Map<String, Integer> reachedStates = new HashMap<>();
        TreeMap<Integer, List<List<Floor>>> statesToCheck = new TreeMap<>();
        addToStepStates(statesToCheck, floors, 0, reachedStates);
        minSteps = doBreadthFirstSearch(statesToCheck, reachedStates);

        // Part 2
        floors = eventData.stream().map(Floor::fromString).collect(Collectors.toList());
        floors.get(0).getGenerators().add(new PuzzleElement(PuzzleObjectType.GENERATOR, "elerium"));
        floors.get(0).getMicrochips().add(new PuzzleElement(PuzzleObjectType.MICROCHIP, "elerium"));
        floors.get(0).getGenerators().add(new PuzzleElement(PuzzleObjectType.GENERATOR, "dilithium"));
        floors.get(0).getMicrochips().add(new PuzzleElement(PuzzleObjectType.MICROCHIP, "dilithium"));

        reachedStates = new HashMap<>();
        statesToCheck = new TreeMap<>();
        addToStepStates(statesToCheck, floors, 0, reachedStates);
        minStepsPartTwo = doBreadthFirstSearch(statesToCheck, reachedStates);

        return true;
    }

    private int doBreadthFirstSearch(TreeMap<Integer, List<List<Floor>>> statesToCheck, Map<String, Integer> reachedStates) {
        AtomicInteger minNumberOfSteps = new AtomicInteger(0);
        while (!statesToCheck.isEmpty()) {
            var firstEntry = statesToCheck.firstEntry();
            var states = firstEntry.getValue();

            if (minNumberOfSteps.get() > 0) {
                return minNumberOfSteps.get();
            }

            states.parallelStream().forEach(floors -> {
                if (isEnd(floors) || minNumberOfSteps.get() > 0) {
                    minNumberOfSteps.compareAndSet(0, firstEntry.getKey());
                } else {
                    int elevator = getElevator(floors);
                    List<List<PuzzleElement>> elevatorElements = floors.get(elevator).getPossibleElevatorElements();
                    elevatorElements.stream().forEach(v -> checkElevatorMoving(statesToCheck, reachedStates, firstEntry.getKey() + 1, floors, elevator, v));
                }
            });

            statesToCheck.remove(firstEntry.getKey());
        }

        return minNumberOfSteps.get();
    }

    /**
     * Get number of floor with the elevator.
     * 
     * @param step
     * @return
     */
    private int getElevator(List<Floor> step) {
        for (Floor f : step) {
            if (f.isElevator()) {
                return f.getFloorNumber();
            }
        }
        return -1;
    }

    private void checkElevatorMoving(TreeMap<Integer, List<List<Floor>>> statesToCheck, Map<String, Integer> reachedStates, int currentStepNumber,
            List<Floor> step, int elevator, List<PuzzleElement> checkingStep) {
        if (elevator > 0) {
            var bottomFloor = step.get(elevator - 1);
            if (bottomFloor.checkFloor(checkingStep)) {
                computeNextStep(statesToCheck, step, elevator, elevator - 1, currentStepNumber, checkingStep, reachedStates);
            }
        }
        if (elevator < 3) {
            var upperFloor = step.get(elevator + 1);
            if (upperFloor.checkFloor(checkingStep)) {
                computeNextStep(statesToCheck, step, elevator, elevator + 1, currentStepNumber, checkingStep, reachedStates);
            }
        }
    }

    private void computeNextStep(TreeMap<Integer, List<List<Floor>>> statesToCheck, List<Floor> floors, int elevatorFrom, int elevatorTo, int currentStepNumber,
            List<PuzzleElement> elevatorObjects, Map<String, Integer> reachedStates) {
        // copy current floor
        List<Floor> nextFloor = floors.stream()//
                .map(v -> new Floor(v, elevatorTo))//
                .collect(Collectors.toList());

        // do elevator moving
        for (Floor floor : nextFloor) {
            if (floor.getFloorNumber() == elevatorFrom) {
                floor.removeElements(elevatorObjects);
            } else if (floor.getFloorNumber() == elevatorTo) {
                floor.addObjects(elevatorObjects);
            }
        }

        addToStepStates(statesToCheck, nextFloor, currentStepNumber, reachedStates);
    }

    private boolean isEnd(List<Floor> step) {
        return step.get(0).isEmpty() //
                && step.get(1).isEmpty() //
                && step.get(2).isEmpty(); //
    }

    private synchronized void addToStepStates(TreeMap<Integer, List<List<Floor>>> statesToCheck, List<Floor> nextFloor, int currentStepNumber,
            Map<String, Integer> reachedStates) {
        String id = getState(nextFloor);
        if (reachedStates.containsKey(id)) {
            return;
        }

        reachedStates.put(id, currentStepNumber);

        statesToCheck.compute(currentStepNumber, (k, v) -> {
            if (null == v) {
                List<List<Floor>> steps = new LinkedList<>();
                steps.add(nextFloor);
                return steps;
            }
            v.add(nextFloor);
            return v;
        });
    }

    private String getState(List<Floor> floors) {
        StringBuilder response = new StringBuilder();
        floors.stream().forEach(v -> response.append(v.toFloorState()));
        return response.toString();
    }
}
