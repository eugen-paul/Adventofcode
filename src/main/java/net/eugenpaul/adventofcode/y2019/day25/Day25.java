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
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day25 extends SolutionTemplate {

    private static final String NORTH = "north\n";
    private static final String WEST = "west\n";
    private static final String EAST = "east\n";
    private static final String SOUTH = "south\n";

    private enum Description {
        DOORS, ITEMS, EMPTY, PASS
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
    private static final String PASS = "\"Oh, hello! You should be able to get in by typing ";

    public static void main(String[] args) {
        Day25 puzzle = new Day25();
        puzzle.doPuzzleFromFile("y2019/day25/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        // There are 8 items that we can take with us. We need to test 2^8 = 256 different item combinations.
        for (int i = 0; i < 256; i++) {
            if (doPuzzle(eventData, i)) {
                break;
            }
        }

        logger.log(Level.INFO, () -> "password    : " + getPassword());
        return true;
    }

    private boolean doPuzzle(String eventData, int n) {
        Map<Long, Long> opcodesOrigin = initOpcodes(eventData);

        int pos = 0;
        IntcodeMapComputer comp;
        Map<Long, Long> opcodes;
        comp = new IntcodeMapComputer();
        opcodes = new HashMap<>(opcodesOrigin);

        StringBuilder output = new StringBuilder();

        // Way through the whole ship
        LinkedList<String> inputs = getWayThoughShip();

        Description lastDescriptionType = Description.EMPTY;

        List<Direction> doors = new LinkedList<>();
        List<String> items = new LinkedList<>();

        SimplePos areaPos = new SimplePos(0, 0);

        // Can be used at the end to draw entire ship.
        Map<SimplePos, AreaData> area = new HashMap<>();

        while (!comp.isEnd(opcodes, pos)) {
            pos = comp.runOpcodes(opcodes, pos);
            if (comp.isOutput()) {
                char nextChar = (char) comp.removeOutput().shortValue();
                lastDescriptionType = consumeCharFromOutput(lastDescriptionType, nextChar, output, doors, items);

                if (lastDescriptionType == Description.PASS) {
                    return true;
                }

                if (output.toString().endsWith("Command?")) {

                    updateArea(doors, areaPos, area);

                    output = new StringBuilder();

                    if (inputs.isEmpty()) {
                        break;
                    }

                    String nexInput = inputs.getFirst();

                    if (doTake(n, items)) {
                        comp.setInput(toAsciiList("take " + items.get(0) + "\n").stream().mapToLong(i -> i).toArray());
                    } else if (!nexInput.startsWith("inv")) {
                        Direction moveDir = Direction.fromChar(nexInput.charAt(0));
                        areaPos = areaPos.moveNew(moveDir).moveNew(moveDir);
                        comp.setInput(toAsciiList(nexInput).stream().mapToLong(i -> i).toArray());
                        inputs.removeFirst();
                    } else {
                        comp.setInput(toAsciiList(nexInput).stream().mapToLong(i -> i).toArray());
                        inputs.removeFirst();
                    }

                    doors.clear();
                    items.clear();
                }
            }
        }

        return false;
    }

    private boolean doTake(int n, List<String> items) {
        if (items.isEmpty()) {
            return false;
        }
        boolean getTambourine = (((n >>> 7) & 0x1) == 1);
        boolean getHat = (((n >>> 6) & 0x1) == 1);
        boolean getJam = (((n >>> 5) & 0x1) == 1);
        boolean getEgg = (((n >>> 4) & 0x1) == 1);
        boolean getAsterisk = (((n >>> 3) & 0x1) == 1);
        boolean getPoint = (((n >>> 2) & 0x1) == 1);
        boolean getAntenna = (((n >>> 1) & 0x1) == 1);
        boolean getHeater = ((n & 0x1) == 1);

        String itemName = items.get(0);

        return (getTambourine && itemName.equals("tambourine")) //
                || (getHat && itemName.equals("festive hat")) //
                || (getJam && itemName.equals("jam")) //
                || (getEgg && itemName.equals("easter egg")) //
                || (getAsterisk && itemName.equals("asterisk")) //
                || (getPoint && itemName.equals("fixed point")) //
                || (getAntenna && itemName.equals("antenna")) //
                || (getHeater && itemName.equals("space heater")) //
        ;
    }

    private void updateArea(List<Direction> doors, SimplePos areaPos, Map<SimplePos, AreaData> area) {
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
    }

    private Description consumeCharFromOutput(Description currentDescription, char nextChar, StringBuilder outputBuffer, List<Direction> doors,
            List<String> items) {
        Description response = currentDescription;
        if (nextChar == '\n') {
            String outputText = outputBuffer.toString();
            // logger.log(Level.INFO, outputBuffer::toString);
            switch (outputText) {
            case DOORS_HERE:
                response = Description.DOORS;
                break;
            case ITEMS_HERE:
                response = Description.ITEMS;
                break;
            case INVENTORY:
                response = Description.EMPTY;
                break;
            default:
                break;
            }

            // We found the password
            if (outputText.startsWith(PASS)) {
                password = outputText.substring(PASS.length(), PASS.length() + 10);
                return Description.PASS;
            }

            if (outputText.startsWith("- ")) {
                if (response == Description.DOORS) {
                    doors.add(Direction.fromChar(outputText.charAt(2)));
                } else if (response == Description.ITEMS) {
                    items.add(outputText.substring(2));
                }
            }

            outputBuffer.delete(0, outputBuffer.length());
        } else {
            outputBuffer.append(nextChar);
        }

        return response;
    }

    // I compute the way manualy
    private LinkedList<String> getWayThoughShip() {
        return new LinkedList<>(List.of(//
                SOUTH, //
                SOUTH, //
                SOUTH, //
                SOUTH, //
                EAST, //
                WEST, //
                WEST, //
                WEST, //
                SOUTH, //
                NORTH, //
                EAST, //
                EAST, //
                NORTH, //
                WEST, //
                EAST, //
                NORTH, //
                WEST, //
                NORTH, //
                NORTH, //
                SOUTH, //
                SOUTH, //
                EAST, //
                NORTH, //
                WEST, //
                SOUTH, //
                NORTH, //
                WEST, //
                SOUTH, //
                NORTH, //
                WEST, //
                WEST, //
                WEST, //
                "inv\n" //
        ));
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