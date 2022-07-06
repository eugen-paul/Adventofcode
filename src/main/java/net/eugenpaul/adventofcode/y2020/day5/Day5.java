package net.eugenpaul.adventofcode.y2020.day5;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day5 extends SolutionTemplate {

    private class Id {
        private String data;
        @Getter
        private Integer seatId;

        public Id(String data) {
            this.data = data;
            compSeatId();
        }

        private void compSeatId() {
            String row = data.substring(0, 7);
            row = row.replace('F', '0');
            row = row.replace('B', '1');
            Integer rowInt = Integer.parseInt(row, 2);

            String column = data.substring(7);
            column = column.replace('R', '1');
            column = column.replace('L', '0');
            Integer columnInt = Integer.parseInt(column, 2);

            seatId = rowInt * 8 + columnInt;
        }
    }

    @Getter
    private long maxId;
    @Getter
    private long id;

    public static void main(String[] args) {
        Day5 puzzle = new Day5();
        puzzle.doPuzzleFromFile("y2020/day5/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Id> ids = eventData.stream().map(Id::new).collect(Collectors.toList());

        LinkedList<Integer> givenIds = ids.stream()//
                .mapToInt(Id::getSeatId)//
                .sorted()//
                .boxed().collect(Collectors.toCollection(LinkedList::new));

        maxId = givenIds.getLast();

        id = -1;
        for (int i = 0; i < 1024; i++) {
            if (!givenIds.contains(Integer.valueOf(i)) //
                    && givenIds.contains(Integer.valueOf(i - 1)) //
                    && givenIds.contains(Integer.valueOf(i + 1)) //
            ) {
                id = i;
                break;
            }
        }

        logger.log(Level.INFO, () -> "maxId  : " + getMaxId());
        logger.log(Level.INFO, () -> "id     : " + getId());

        return true;
    }

}
