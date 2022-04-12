package net.eugenpaul.adventofcode.y2015.day3;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day3 extends SolutionTemplate {

    @Getter
    private Integer visitedHouseCountSantaAlone = null;
    @Getter
    private Integer visitedHouseCountSantaAndRobo = null;

    public static void main(String[] args) {
        Day3 puzzle = new Day3();
        puzzle.doPuzzleFromFile("y2015/day3/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        Map<SimplePos, Boolean> cityMap = new HashMap<>();
        doDelivery(eventData, 0, 1, cityMap);
        visitedHouseCountSantaAlone = cityMap.size();

        cityMap.clear();
        doDelivery(eventData, 0, 2, cityMap);
        doDelivery(eventData, 1, 2, cityMap);
        visitedHouseCountSantaAndRobo = cityMap.size();

        logger.log(Level.INFO, () -> "Houses with at least one present (Santa alone) " + visitedHouseCountSantaAlone);
        logger.log(Level.INFO, () -> "Houses with at least one present (Santa and Robo) " + visitedHouseCountSantaAndRobo);

        return true;
    }

    private void doDelivery(String deliveryPath, int startPositon, int deliveryStep, Map<SimplePos, Boolean> cityMap) {
        SimplePos pos = new SimplePos(0, 0);
        cityMap.putIfAbsent(pos, true);

        for (int i = startPositon; i < deliveryPath.length(); i += deliveryStep) {
            char step = deliveryPath.charAt(i);
            pos = pos.moveNew(Direction.fromArrow(step));
            cityMap.putIfAbsent(pos, true);
        }
    }

}
