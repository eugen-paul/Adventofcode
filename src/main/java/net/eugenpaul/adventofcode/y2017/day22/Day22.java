package net.eugenpaul.adventofcode.y2017.day22;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day22 extends SolutionTemplate {

    private enum Flag {
        CLEAN, WEAKENED, INFECTED, FLAGGED;

        public Flag getNextFlag() {
            switch (this) {
            case CLEAN:
                return WEAKENED;
            case WEAKENED:
                return INFECTED;
            case INFECTED:
                return FLAGGED;
            case FLAGGED:
                return CLEAN;
            default:
                break;
            }
            throw new IllegalArgumentException();
        }
    }

    @Getter
    private int becomeInfected;
    @Getter
    private int becomeInfected2;

    public static void main(String[] args) {
        Day22 puzzle = new Day22();
        puzzle.doPuzzleFromFile("y2017/day22/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        doPuzzle1(eventData);
        doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "becomeInfected: " + getBecomeInfected());
        logger.log(Level.INFO, () -> "becomeInfected2: " + getBecomeInfected2());

        return true;
    }

    private void doPuzzle1(List<String> eventData) {
        var map = StringConverter.toBoolMap(eventData, '#');
        Direction direction = Direction.N;
        int iteration = 10_000;
        becomeInfected = 0;
        SimplePos pos = new SimplePos(eventData.get(0).length() / 2, eventData.size() / 2);

        for (int i = 0; i < iteration; i++) {
            direction = doStep(map, pos, direction);
        }
    }

    private Direction doStep(Map<SimplePos, Boolean> map, SimplePos pos, Direction direction) {
        Direction responseDirection;
        if (map.getOrDefault(pos, false).booleanValue()) {
            responseDirection = direction.turnRight();
            map.put(new SimplePos(pos.getX(), pos.getY()), false);
        } else {
            responseDirection = direction.turnLeft();
            map.put(new SimplePos(pos.getX(), pos.getY()), true);
            becomeInfected++;
        }

        pos.move(responseDirection);

        return responseDirection;
    }

    private void doPuzzle2(List<String> eventData) {
        var map = StringConverter.toBoolMap(eventData, '#');

        Map<SimplePos, Flag> mapExt = new HashMap<>();
        for (var entry : map.entrySet()) {
            mapExt.put(entry.getKey(), entry.getValue().booleanValue() ? Flag.INFECTED : Flag.CLEAN);
        }

        Direction direction = Direction.N;
        int iteration = 10_000_000;
        becomeInfected2 = 0;
        SimplePos pos = new SimplePos(eventData.get(0).length() / 2, eventData.size() / 2);

        for (int i = 0; i < iteration; i++) {
            direction = doStep2(mapExt, pos, direction);
        }
    }

    private Direction doStep2(Map<SimplePos, Flag> mapExt, SimplePos pos, Direction direction) {
        Direction responseDirection;

        var node = mapExt.getOrDefault(pos, Flag.CLEAN);
        switch (node) {
        case CLEAN:
            responseDirection = direction.turnLeft();
            break;
        case WEAKENED:
            responseDirection = direction;
            becomeInfected2++;
            break;
        case INFECTED:
            responseDirection = direction.turnRight();
            break;
        case FLAGGED:
            responseDirection = direction.reverse();
            break;
        default:
            throw new IllegalArgumentException();
        }
        mapExt.put(new SimplePos(pos.getX(), pos.getY()), node.getNextFlag());
        pos.move(responseDirection);

        return responseDirection;
    }

}
