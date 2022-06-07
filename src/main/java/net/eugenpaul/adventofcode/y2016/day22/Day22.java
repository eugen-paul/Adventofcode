package net.eugenpaul.adventofcode.y2016.day22;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day22 extends SolutionTemplate {

    @Getter
    private int viablePairs;

    public static void main(String[] args) {
        Day22 puzzle = new Day22();
        puzzle.doPuzzleFromFile("y2016/day22/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        Map<SimplePos, ClusterNode> nodes = eventData//
                .stream()//
                .map(ClusterNode::fromString)//
                .filter(Objects::nonNull) //
                .collect(Collectors.toMap(ClusterNode::getPosition, v -> v));

        viablePairs = countViablePairs(nodes);

        printField(nodes);

        logger.log(Level.INFO, () -> "viablePairs " + getViablePairs());

        return true;
    }

    private int countViablePairs(Map<SimplePos, ClusterNode> nodes) {
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

    private void printField(Map<SimplePos, ClusterNode> nodes) {
        var maxX = nodes.keySet().stream()//
                .map(SimplePos::getX)//
                .sorted((a, b) -> b - a)//
                .collect(Collectors.toList()).get(0);

        // the are only one empty node
        var fullSize = nodes.entrySet().stream()//
                .filter(v -> v.getValue().getUsed() == 0)//
                .map(v -> v.getValue().getSize())//
                .findFirst()//
                .orElse(100) * 2;

        MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(nodes, v -> {
            if (v.getX() == 0 && v.getY() == 0) {
                return '0';
            } else if (v.getX() == maxX && v.getY() == 0) {
                return 'G';
            } else if (nodes.get(v.getPosition()).getUsed() == 0) {
                return '_';
            } else if (nodes.get(v.getPosition()).getSize() > fullSize) {
                return '#';
            } else {
                return '.';
            }
        }));
    }

}
