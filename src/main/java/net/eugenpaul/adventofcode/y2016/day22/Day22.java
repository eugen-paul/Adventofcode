package net.eugenpaul.adventofcode.y2016.day22;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day22 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day22.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day22.class.getName());

    @Getter
    private int viablePairs;

    public static void main(String[] args) {
        Day22 puzzle = new Day22();
        puzzle.doPuzzleFromFile("y2016/day22/puzzle1.txt");
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

        logger.log(Level.INFO, () -> "viablePairs " + getViablePairs());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        Map<ClusterPosition, ClusterNode> nodes = eventData//
                .stream()//
                .map(ClusterNode::fromString)//
                .filter(Objects::nonNull) //
                .collect(Collectors.toMap(ClusterNode::getPosition, v -> v));

        viablePairs = countViablePairs(nodes);

        printField(nodes);

        return true;
    }

    private int countViablePairs(Map<ClusterPosition, ClusterNode> nodes) {
        var iteratorA = nodes.entrySet().iterator();
        int counter = 0;
        while (iteratorA.hasNext()) {
            var aEntry = iteratorA.next();
            var aValue = aEntry.getValue();

            var iteratorB = nodes.entrySet().iterator();
            while (iteratorB.hasNext()) {
                var bEntry = iteratorB.next();
                var bValue = bEntry.getValue();

                if (checkViable(aValue, bValue) //
                ) {
                    counter++;
                }
            }
        }

        return counter;
    }

    private boolean checkViable(ClusterNode a, ClusterNode b) {
        return b != null //
                && !a.isEmpty() //
                && a != b //
                && a.getUsed() <= b.getFree();
    }

    private void printField(Map<ClusterPosition, ClusterNode> nodes) {
        var maxX = nodes.keySet().stream()//
                .map(ClusterPosition::getX)//
                .sorted((a, b) -> b - a)//
                .collect(Collectors.toList()).get(0);

        if (maxX == 0) {
            return;
        }

        // the are only one empty node
        var fullSize = nodes.entrySet().stream()//
                .filter(v -> v.getValue().getUsed() == 0)//
                .map(v -> v.getValue().getSize())//
                .findFirst()//
                .orElse(100) * 2;

        for (int y = 0; y < nodes.size() / (maxX + 1); y++) {
            StringBuilder line = new StringBuilder(maxX);
            for (int x = 0; x <= maxX; x++) {
                if (x == 0 && y == 0) {
                    line.append("0");
                } else if (x == maxX && y == 0) {
                    line.append("G");
                } else if (nodes.get(new ClusterPosition(x, y)).getUsed() == 0) {
                    line.append("_");
                } else if (nodes.get(new ClusterPosition(x, y)).getSize() > fullSize) {
                    line.append("#");
                } else {
                    line.append(".");
                }
            }
            logger.log(Level.INFO, line::toString);
        }
    }

}
