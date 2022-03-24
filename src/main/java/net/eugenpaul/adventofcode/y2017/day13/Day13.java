package net.eugenpaul.adventofcode.y2017.day13;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day13 extends SolutionTemplate {

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private class Firewall {
        private int depth;
        private int range;
    }

    @Getter
    private Integer severity;
    @Getter
    private Integer delay;

    public static void main(String[] args) {
        Day13 puzzle = new Day13();
        puzzle.doPuzzleFromFile("y2017/day13/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        Map<Integer, Integer> firewalls = eventData.stream()//
                .map(this::fromString)//
                .collect(Collectors.toMap(Firewall::getDepth, Firewall::getRange));

        severity = doPuzzle1(firewalls);
        delay = doPuzzle2(firewalls);

        logger.log(Level.INFO, () -> "severity : " + getSeverity());
        logger.log(Level.INFO, () -> "delay : " + getDelay());

        return true;
    }

    public int doPuzzle1(Map<Integer, Integer> firewalls) {
        return moving(firewalls, false, 0);
    }

    public int doPuzzle2(Map<Integer, Integer> firewalls) {
        int delayResponse = 0;
        while (moving(firewalls, true, delayResponse) != 0) {
            delayResponse++;
        }

        return delayResponse;
    }

    public int moving(Map<Integer, Integer> firewalls, boolean breakByCaught, int waitingPs) {
        int severityResponse = 0;
        int max = firewalls.keySet().stream().reduce(0, Math::max);

        for (int i = 0; i <= max; i++) {
            Integer fwRange = firewalls.get(i);
            if (fwRange != null && getStartPosition(i + waitingPs, fwRange)) {
                severityResponse += i * fwRange;
                if (breakByCaught) {
                    return -1;
                }
            }
        }

        return severityResponse;
    }

    private boolean getStartPosition(int picosecond, int range) {
        int pathLength = range * 2 - 2;
        return (picosecond % pathLength) == 0;
    }

    private Firewall fromString(String data) {
        String[] elements = data.split(":");
        return new Firewall(//
                Integer.parseInt(elements[0]), //
                Integer.parseInt(elements[1].trim()) //
        );
    }

}
