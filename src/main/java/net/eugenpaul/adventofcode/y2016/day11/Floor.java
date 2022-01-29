package net.eugenpaul.adventofcode.y2016.day11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Floor {

    @Getter
    private int floorNumber;

    @Getter
    @Setter
    private boolean elevator;

    @Getter
    private List<PuzzleElement> generators;
    @Getter
    private List<PuzzleElement> microchips;

    private Floor() {
        elevator = false;
        microchips = new ArrayList<>();
        generators = new ArrayList<>();
    }

    public Floor(Floor floor, int elevator) {
        this.floorNumber = floor.getFloorNumber();
        if (elevator == floorNumber) {
            this.elevator = true;
        } else {
            this.elevator = false;
        }
        this.microchips = new ArrayList<>(floor.getMicrochips());
        this.generators = new ArrayList<>(floor.getGenerators());
    }

    public static Floor fromString(String data) {
        String[] elements = data.split(" ");

        Floor responseFloor = new Floor();

        for (int i = 1; i < elements.length; i++) {
            if (elements[i].equals("floor")) {
                responseFloor.floorNumber = stringToFloorNumber(elements[i - 1]);
            } else if (elements[i].startsWith("microchip")) {
                responseFloor.microchips.add(//
                        new PuzzleElement(PuzzleObjectType.MICROCHIP, elements[i - 1].substring(0, elements[i - 1].indexOf("-"))));
            } else if (elements[i].startsWith("generator")) {
                responseFloor.generators.add(//
                        new PuzzleElement(PuzzleObjectType.GENERATOR, elements[i - 1]));
            }
        }

        if (responseFloor.floorNumber == 0) {
            responseFloor.elevator = true;
        }

        return responseFloor;
    }

    private static int stringToFloorNumber(String data) {
        switch (data) {
        case "first":
            return 0;
        case "second":
            return 1;
        case "third":
            return 2;
        case "fourth":
            return 3;
        default:
            throw new IllegalArgumentException("Wrong Floor number: " + data);
        }
    }

    @Override
    public String toString() {
        StringBuilder response = new StringBuilder();
        response.append("F").append(floorNumber);

        if (elevator) {
            response.append(" E ");
        } else {
            response.append("   ");
        }

        generators.stream().forEach(v -> response.append(" ").append(v.toString()));
        microchips.stream().forEach(v -> response.append(" ").append(v.toString()));

        return response.toString();
    }

    public String toFloorState() {
        StringBuilder response = new StringBuilder();
        response.append("F").append(floorNumber);

        if (elevator) {
            response.append("E");
        } else {
            response.append(" ");
        }

        generators.stream().forEach(v -> response.append("G"));
        microchips.stream().forEach(v -> response.append("M"));

        return response.toString();
    }

    public boolean isEmpty() {
        return microchips.isEmpty() && generators.isEmpty();
    }

    public List<List<PuzzleElement>> getPossibleElevatorElements() {
        List<List<PuzzleElement>> responseList = new LinkedList<>();

        putMicrochipsToPossibleSteps(responseList);

        putGeneratorsToPossibleSteps(responseList);

        return responseList;
    }

    private void putGeneratorsToPossibleSteps(List<List<PuzzleElement>> responseList) {
        for (int i = 0; i < generators.size(); i++) {
            List<PuzzleElement> tempGenerators = new LinkedList<>(generators);

            var generator = generators.get(i);
            tempGenerators.remove(generator);

            // add current generator if the rest is OK
            if (checkFloor(List.of(generator), tempGenerators, microchips)) {
                responseList.add(List.of(generator));
            }

            // add two generators if the rest is OK
            for (int j = i + 1; j < generators.size(); j++) {
                var generator2 = generators.get(j);
                List<PuzzleElement> tempGenerators2 = new LinkedList<>(generators);
                tempGenerators2.remove(generator);
                tempGenerators2.remove(generator2);

                if (checkFloor(List.of(generator), tempGenerators2, microchips)) {
                    responseList.add(List.of(generator, generator2));
                }
            }

            // add current generator and a microchip if the rest is OK
            for (var microchip : microchips) {
                List<PuzzleElement> tempMicrochips = new LinkedList<>(microchips);
                tempMicrochips.remove(microchip);

                if (checkFloor(List.of(generator, microchip), tempGenerators, tempMicrochips)) {
                    responseList.add(List.of(generator, microchip));
                }
            }
        }
    }

    private void putMicrochipsToPossibleSteps(List<List<PuzzleElement>> responseList) {
        for (int i = 0; i < microchips.size(); i++) {
            var microchip = microchips.get(i);
            responseList.add(List.of(microchip));

            for (int j = i + 1; j < microchips.size(); j++) {
                responseList.add(List.of(microchip, microchips.get(j)));
            }
        }
    }

    private static boolean isGeneratorInList(String name, List<PuzzleElement> list) {
        for (PuzzleElement generator : list) {
            if (generator.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param components
     * @return true if the components can be added to floor
     */
    public boolean checkFloor(List<PuzzleElement> components) {
        List<PuzzleElement> tempGenerators = new LinkedList<>(generators);
        List<PuzzleElement> tempMicrochips = new LinkedList<>(microchips);
        return checkFloor(components, tempGenerators, tempMicrochips);
    }

    /**
     * 
     * @param components
     * @param generatorList
     * @param microchipList
     * @return true if the components can be added to lists
     */
    public static boolean checkFloor(List<PuzzleElement> components, List<PuzzleElement> generatorList, List<PuzzleElement> microchipList) {
        for (PuzzleElement component : components) {
            if (component.getType() == PuzzleObjectType.GENERATOR) {
                generatorList.add(component);
            } else {
                microchipList.add(component);
            }
        }

        if (generatorList.isEmpty()) {
            return true;
        }

        for (PuzzleElement microchip : microchipList) {
            if (!isGeneratorInList(microchip.getName(), generatorList)) {
                return false;
            }
        }

        return true;
    }

    public void removeElements(List<PuzzleElement> elementList) {
        for (PuzzleElement element : elementList) {
            if (element.getType() == PuzzleObjectType.GENERATOR) {
                removePuzzleElementFromList(element, generators);
            } else {
                removePuzzleElementFromList(element, microchips);
            }
        }
    }

    private void removePuzzleElementFromList(PuzzleElement obj, List<PuzzleElement> list) {
        Iterator<PuzzleElement> it = list.iterator();
        while (it.hasNext()) {
            PuzzleElement gen = it.next();
            if (gen.getName().equals(obj.getName())) {
                it.remove();
            }
        }
    }

    public void addObjects(List<PuzzleElement> elementList) {
        for (PuzzleElement element : elementList) {
            if (element.getType() == PuzzleObjectType.GENERATOR) {
                generators.add(element);
            } else {
                microchips.add(element);
            }
        }
    }

}
