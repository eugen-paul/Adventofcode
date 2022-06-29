package net.eugenpaul.adventofcode.y2019.day25;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.IntcodeMapComputer;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day25 extends SolutionTemplate {

    private enum Description {
        DOORS, ITEMS, EMPTY,
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private enum AreaData {
        EMPTY('.'), SENSOR('s'), WALL('#'), UNKNOW('?'), VISITED('!');

        @Getter
        private final char value;
    }

    @Getter
    private String password;

    private static final String DOORS_HERE = "Doors here lead:";
    private static final String ITEMS_HERE = "Items here:";
    private static final String INVENTORY = "Items in your inventory:";
    private static final String ALERT = "A loud, robotic voice says \"Alert! Droids on this ship are";

    public static void main(String[] args) {
        Day25 puzzle = new Day25();
        puzzle.doPuzzleFromFile("y2019/day25/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        for (int i = 0; i < 256; i++) {
            doEventz(eventData, i);
        }
        return true;
    }

    private boolean doEventz(String eventData, int n) {
        Map<Long, Long> opcodesOrigin = initOpcodes(eventData);

        int pos = 0;
        IntcodeMapComputer comp;
        Map<Long, Long> opcodes;
        comp = new IntcodeMapComputer();
        opcodes = new HashMap<>(opcodesOrigin);

        StringBuilder output = new StringBuilder();

        int inputNumber = 0;
        // Way through the whole ship
        List<String> inputs = List.of(//
                "south\n", //
                "south\n", //
                "south\n", //
                "south\n", //
                "east\n", //
                "west\n", //
                "west\n", //
                "west\n", //
                "south\n", //
                "north\n", //
                "east\n", //
                "east\n", //
                "north\n", //
                "west\n", //
                "east\n", //
                "north\n", //
                "west\n", //
                "north\n", //
                "north\n", //
                "south\n", //
                "south\n", //
                "east\n", //
                "north\n", //
                "west\n", //
                "south\n", //
                "north\n", //
                "west\n", //
                "south\n", //
                "north\n", //
                "west\n", //
                "west\n", //
                "west\n", //
                "inv\n" //
        );

        Description description = Description.EMPTY;

        List<Direction> doors = new LinkedList<>();
        List<String> items = new LinkedList<>();

        SimplePos areaPos = new SimplePos(0, 0);
        Map<SimplePos, String> itemsMem = new HashMap<>();
        Map<SimplePos, AreaData> area = new HashMap<>();

        boolean isAlert = false;

        while (!comp.isEnd(opcodes, pos)) {
            pos = comp.runOpcodes(opcodes, pos);
            if (comp.isOutput()) {
                char nextChar = (char) comp.removeOutput().shortValue();
                if (nextChar == '\n') {
                    String outputText = output.toString();
                    logger.log(Level.INFO, output::toString);
                    switch (outputText) {
                    case DOORS_HERE:
                        description = Description.DOORS;
                        break;
                    case ITEMS_HERE:
                        description = Description.ITEMS;
                        break;
                    case INVENTORY:
                        description = Description.EMPTY;
                        break;
                    default:
                        break;
                    }

                    if (outputText.startsWith(ALERT)) {
                        isAlert = true;
                    }

                    if (outputText.startsWith("- ")) {
                        if (description == Description.DOORS) {
                            doors.add(Direction.fromChar(outputText.charAt(2)));
                        } else if (description == Description.ITEMS) {
                            items.add(outputText.substring(2));
                            itemsMem.put(areaPos.copy(), outputText.substring(2));
                        }
                    }

                    output = new StringBuilder();
                } else {
                    output.append(nextChar);
                }

                if (output.toString().endsWith("Command?")) {

                    area.putIfAbsent(areaPos.moveNew(Direction.N).moveNew(Direction.W), AreaData.WALL);
                    area.putIfAbsent(areaPos.moveNew(Direction.N), AreaData.WALL);
                    area.putIfAbsent(areaPos.moveNew(Direction.N).moveNew(Direction.E), AreaData.WALL);
                    area.putIfAbsent(areaPos.moveNew(Direction.W), AreaData.WALL);
                    area.putIfAbsent(areaPos.moveNew(Direction.E), AreaData.WALL);
                    area.putIfAbsent(areaPos.moveNew(Direction.S).moveNew(Direction.W), AreaData.WALL);
                    area.putIfAbsent(areaPos.moveNew(Direction.S), AreaData.WALL);
                    area.putIfAbsent(areaPos.moveNew(Direction.S).moveNew(Direction.E), AreaData.WALL);
                    for (Direction d : doors) {
                        area.put(areaPos.moveNew(d), AreaData.EMPTY);
                    }

                    output = new StringBuilder();
                    // MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(area, v -> (v == null) ? ' ' : v.getValue()));

                    if (inputNumber == inputs.size()) {
                        // logger.log(Level.INFO, () -> "All items : ");
                        // for (var entry : itemsMem.entrySet()) {
                        // logger.log(Level.INFO, "pos : " + entry.getKey() + ", item : " + entry.getValue());
                        // }
                        if (isAlert == false) {
                            System.out.println("FOUND!");
                        }
                        break;
                    }

                    boolean getTambourine = (((n >>> 7) & 0x1) == 1);
                    boolean getHat = (((n >>> 6) & 0x1) == 1);
                    boolean getJam = (((n >>> 5) & 0x1) == 1);
                    boolean getEgg = (((n >>> 4) & 0x1) == 1);
                    boolean getAsterisk = (((n >>> 3) & 0x1) == 1);
                    boolean getPoint = (((n >>> 2) & 0x1) == 1);
                    boolean getAntenna = (((n >>> 1) & 0x1) == 1);
                    boolean getHeater = (((n) & 0x1) == 1);

                    if (!items.isEmpty() //
                            && (//
                            (getTambourine && items.get(0).equals("tambourine")) //
                                    || (getHat && items.get(0).equals("festive hat")) //
                                    || (getJam && items.get(0).equals("jam")) //
                                    || (getEgg && items.get(0).equals("easter egg")) //
                                    || (getAsterisk && items.get(0).equals("asterisk")) //
                                    || (getPoint && items.get(0).equals("fixed point")) //
                                    || (getAntenna && items.get(0).equals("antenna")) //
                                    || (getHeater && items.get(0).equals("space heater")) //
                            ) //
                    ) {
                        comp.setInput(toAsciiList("take " + items.get(0) + "\n").stream().mapToLong(i -> i).toArray());
                    } else if (!inputs.get(inputNumber).startsWith("inv")) {
                        Direction moveDir = Direction.fromChar(inputs.get(inputNumber).charAt(0));
                        areaPos = areaPos.moveNew(moveDir).moveNew(moveDir);
                        comp.setInput(toAsciiList(inputs.get(inputNumber)).stream().mapToLong(i -> i).toArray());
                        inputNumber++;
                    } else {
                        comp.setInput(toAsciiList(inputs.get(inputNumber)).stream().mapToLong(i -> i).toArray());
                        inputNumber++;
                    }

                    doors.clear();
                    items.clear();

                }
            }
        }

        logger.log(Level.INFO, () -> "password    : " + getPassword());

        return true;
    }

    private Map<Long, Long> initOpcodes(String eventData) {
        Map<Long, Long> opcodesOrigin;

        // init Intcode
        long[] opcodesArray = StringConverter.toLongArray(eventData);
        opcodesOrigin = new HashMap<>();

        long counter = 0;
        for (long l : opcodesArray) {
            opcodesOrigin.put(counter, l);
            counter++;
        }
        return opcodesOrigin;
    }

    private List<Integer> toAsciiList(String in) {
        return in.chars().boxed().collect(Collectors.toList());
    }

}