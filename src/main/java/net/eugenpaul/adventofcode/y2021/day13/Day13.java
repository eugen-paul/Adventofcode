package net.eugenpaul.adventofcode.y2021.day13;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day13 extends SolutionTemplate {

    private class FoldAlong {
        private boolean x;
        private int coordinate;

        public FoldAlong(String data) {
            x = data.charAt(11) == 'x';
            coordinate = Integer.parseInt(data.substring(13));
        }
    }

    @Getter
    private long visibleAfterFirst;

    public static void main(String[] args) {
        Day13 puzzle = new Day13();
        puzzle.doPuzzleFromFile("y2021/day13/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        Set<SimplePos> paper = eventData.stream()//
                .filter(v -> v.contains(",")) //
                .map(v -> SimplePos.fromData(v, ","))//
                .collect(Collectors.toSet());

        List<FoldAlong> folds = eventData.stream()//
                .filter(v -> v.startsWith("fold along"))//
                .map(FoldAlong::new)//
                .collect(Collectors.toList());

        visibleAfterFirst = -1;
        for (var fold : folds) {
            if (fold.x) {
                paper = foldLeft(paper, fold.coordinate);
            } else {
                paper = foldUp(paper, fold.coordinate);
            }

            if (visibleAfterFirst == -1) {
                visibleAfterFirst = paper.size();
            }
        }

        MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(paper));

        logger.log(Level.INFO, () -> "visibleAfterFirst   : " + getVisibleAfterFirst());

        return true;
    }

    private Set<SimplePos> foldUp(Set<SimplePos> paper, int coordinate) {
        Set<SimplePos> response = new HashSet<>();

        for (var pos : paper) {
            if (pos.getY() < coordinate) {
                response.add(pos.copy());
            } else {
                int delta = pos.getY() - coordinate;
                response.add(new SimplePos(//
                        pos.getX(), //
                        pos.getY() - delta * 2 //
                ));
            }
        }

        return response;
    }

    private Set<SimplePos> foldLeft(Set<SimplePos> paper, int coordinate) {
        Set<SimplePos> response = new HashSet<>();

        for (var pos : paper) {
            if (pos.getX() < coordinate) {
                response.add(pos);
            } else {
                int delta = pos.getX() - coordinate;
                response.add(new SimplePos(//
                        pos.getX() - delta * 2, //
                        pos.getY() //
                ));
            }
        }

        return response;
    }
}
