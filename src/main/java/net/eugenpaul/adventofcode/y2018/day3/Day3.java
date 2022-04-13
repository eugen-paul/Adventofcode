package net.eugenpaul.adventofcode.y2018.day3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Area;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day3 extends SolutionTemplate {
    private static final String CLAIM_FORMAT = "^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)$";
    private static final Pattern PATTERN = Pattern.compile(CLAIM_FORMAT);

    @AllArgsConstructor
    @Data
    private class Claim {
        private int number;

        private Area area;

        public Claim(String data) {
            Matcher m = PATTERN.matcher(data);
            if (m.find()) {
                number = Integer.parseInt(m.group(1));
                area = new Area(//
                        Integer.parseInt(m.group(2)), //
                        Integer.parseInt(m.group(3)), //
                        Integer.parseInt(m.group(4)), //
                        Integer.parseInt(m.group(5))//
                );
            } else {
                throw new IllegalArgumentException(data);
            }
        }
    }

    @Getter
    private long overlap;
    @Getter
    private long intactClaim;

    public static void main(String[] args) {
        Day3 puzzle = new Day3();
        puzzle.doPuzzleFromFile("y2018/day3/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        var claims = eventData.stream().map(Claim::new).collect(Collectors.toList());

        Map<SimplePos, Integer> fabric = new HashMap<>();

        overlap = doPuzzle1(claims, fabric);
        intactClaim = doPuzzel2(claims, fabric);

        logger.log(Level.INFO, () -> "overlap  : " + getOverlap());
        logger.log(Level.INFO, () -> "intactClaim  : " + getIntactClaim());

        return true;
    }

    private long doPuzzle1(List<Claim> claims, Map<SimplePos, Integer> fabric) {
        for (Claim claim : claims) {
            claim.area.forEach((x, y) -> fabric.compute(new SimplePos(x, y), (k, v) -> (v == null) ? 1 : ++v));
        }
        return fabric.values().stream().filter(v -> v > 1).count();
    }

    private long doPuzzel2(List<Claim> claims, Map<SimplePos, Integer> fabric) {
        for (Claim claim : claims) {
            AtomicBoolean goodClaim = new AtomicBoolean(true);
            claim.area.forEach((x, y) -> {
                if (fabric.get(new SimplePos(x, y)) > 1) {
                    goodClaim.set(false);
                }
            }, //
                    () -> !goodClaim.get()//
            );
            if (goodClaim.get()) {
                return claim.number;
            }
        }
        return -1;
    }

}
