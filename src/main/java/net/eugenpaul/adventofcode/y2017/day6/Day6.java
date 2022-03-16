package net.eugenpaul.adventofcode.y2017.day6;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.NumberArrayHelper;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day6 extends SolutionTemplate {

    @Getter
    private int steps;
    @Getter
    private int cycles;

    public static void main(String[] args) {
        Day6 puzzle = new Day6();
        puzzle.doPuzzleFromFile("y2017/day6/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        List<Long> data = StringConverter.toNumberArrayList(eventData);

        Map<String, Integer> hashs = new HashMap<>();

        while (true) {
            doReallocation(data);
            String hash = getHash(data);
            if (hashs.containsKey(hash)) {
                break;
            }
            hashs.put(hash, hashs.size());
        }

        steps = hashs.size() + 1;
        cycles = hashs.size() - hashs.get(getHash(data));

        logger.log(Level.INFO, () -> "steps : " + getSteps());
        logger.log(Level.INFO, () -> "cycles : " + getCycles());

        return true;
    }

    private void doReallocation(List<Long> data) {
        int realocPosition = NumberArrayHelper.getPositionOfMaxElement(data);

        long blocks = data.get(realocPosition);
        data.set(realocPosition, 0L);

        for (long i = 0; i < blocks; i++) {
            int currentPosition = (int) ((i + realocPosition + 1) % data.size());
            data.set(currentPosition, data.get(currentPosition) + 1);
        }
    }

    private String getHash(List<Long> data) {
        StringBuilder response = new StringBuilder();
        data.stream().forEach(v -> {
            response.append("_");
            response.append(v);
        });
        return response.toString();
    }

}
