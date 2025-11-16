package net.eugenpaul.adventofcode.y2023.day6;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.ConvertHelper;
import net.eugenpaul.adventofcode.helper.MathHelper;
import net.eugenpaul.adventofcode.helper.MatheclipseHelper;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day6 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    private record Race(long time, long distance) {
    }

    public static void main(String[] args) {
        Day6 puzzle = new Day6();
        puzzle.doPuzzleFromFile("y2023/day6/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long r = 1;
        var times = StringConverter.toLongArrayList(eventData.get(0).split(":")[1]);
        var dist = StringConverter.toLongArrayList(eventData.get(1).split(":")[1]);

        for (int i = 0; i < times.size(); i++) {
            int cnt = 0;
            for (int dt = 1; dt < times.get(i); dt++) {
                var d = (times.get(i) - dt) * dt;
                if (d > dist.get(i)) {
                    cnt++;
                }
            }
            if (cnt > 0) {
                r *= cnt;
            }
        }

        logger.info("Solution 1: " + r);
        return r;
    }

    public long doPuzzle1_a(List<String> eventData) {
        List<Race> races = readRaces(eventData);
        long response = 1;

        for (Race race : races) {
            int wins = 0;
            for (int i = 1; i < race.time; i++) {
                if (isWin(race, i)) {
                    wins++;
                }
            }
            if (wins > 0) {
                response *= wins;
            }
        }

        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long r = 1;
        var times = ConvertHelper.toLong(eventData.get(0).split(":")[1].replace(" ", ""));
        var dist = ConvertHelper.toLong(eventData.get(1).split(":")[1].replace(" ", ""));

        MatheclipseHelper m = new MatheclipseHelper();
        var eq = "- x * x + " + times + " * x - " + dist + " == 0";
        m.addEquation(eq);
        m.addUnknown("x");
        m.nSolve();
        var res = m.getResults("x");

        var from = (long) Math.ceil(ConvertHelper.toDouble(res.get(0)));
        var to = (long) Math.floor(ConvertHelper.toDouble(res.get(1)));
        to = MathHelper.min(to, times);

        r = to - from + 1;

        logger.info("Solution 2: " + r);
        return r;
    }

    public long doPuzzle2_b(List<String> eventData) {
        long r = 1;
        var times = ConvertHelper.toLong(eventData.get(0).split(":")[1].replace(" ", ""));
        var dist = ConvertHelper.toLong(eventData.get(1).split(":")[1].replace(" ", ""));

        int cnt = 0;
        for (int dt = 1; dt < times; dt++) {
            var d = (times - dt) * dt;
            if (d > dist) {
                cnt++;
            }
        }
        if (cnt > 0) {
            r *= cnt;
        }

        logger.info("Solution 2: " + r);
        return r;
    }

    public long doPuzzle2_a(List<String> eventData) {
        Race race = readSinleRaces(eventData);
        long minValue = 0;
        long maxValue = 0;

        long start = 0;
        long end = race.time;
        while (start <= end) {
            long mid = (start + end) / 2;
            if (isWin(race, mid)) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        minValue = start;

        start = 0;
        end = race.time;
        while (start <= end) {
            long mid = (start + end) / 2;
            if (isWin(race, mid)) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        maxValue = end;
        return maxValue - minValue + 1;
    }

    private boolean isWin(Race race, long time) {
        long d = (race.time - time) * time;
        return race.distance < d;
    }

    private List<Race> readRaces(List<String> eventData) {
        List<Race> response = new ArrayList<>();
        var timesA = eventData.get(0).split(":")[1].split(" ");
        var times = Stream.of(timesA).filter(d -> !d.isEmpty()).map(Integer::valueOf).toList();

        var distA = eventData.get(1).split(":")[1].split(" ");
        var dist = Stream.of(distA).filter(d -> !d.isEmpty()).map(Integer::valueOf).toList();

        for (int i = 0; i < times.size(); i++) {
            response.add(new Race(times.get(i), dist.get(i)));
        }
        return response;
    }

    private Race readSinleRaces(List<String> eventData) {
        var time = Long.valueOf(eventData.get(0).split(":")[1].replace(" ", ""));
        var dist = Long.valueOf(eventData.get(1).split(":")[1].replace(" ", ""));
        return new Race(time, dist);
    }
}
