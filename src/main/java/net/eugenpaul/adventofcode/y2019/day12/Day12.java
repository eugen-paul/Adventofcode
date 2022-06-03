package net.eugenpaul.adventofcode.y2019.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.MathHelper;
import net.eugenpaul.adventofcode.helper.Pos3d;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day12 extends SolutionTemplate {

    private static final String MOON_POSITION_FORMAT = "^<x=([\\-0-9]*), y=([\\-0-9]*), z=([\\-0-9]*)>$";
    private static final Pattern MOON_POSITION_PATTERN = Pattern.compile(MOON_POSITION_FORMAT);

    @Getter
    private int totalEnergy;
    @Getter
    private long repeating;

    @Setter
    private int steps = 1000;

    public static void main(String[] args) {
        Day12 puzzle = new Day12();
        puzzle.doPuzzleFromFile("y2019/day12/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        totalEnergy = doPuzzle1(eventData);

        repeating = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "totalEnergy : " + getTotalEnergy());
        logger.log(Level.INFO, () -> "repeating : " + getRepeating());

        return true;
    }

    private long doPuzzle2(List<String> eventData) {
        int[] moonsX = eventData.stream()//
                .map(v -> Pos3d.fromPattern(v, MOON_POSITION_PATTERN))//
                .mapToInt(i -> (int) i.getX())//
                .toArray();
        long repeatingX = getRepeating(moonsX);

        int[] moonsY = eventData.stream()//
                .map(v -> Pos3d.fromPattern(v, MOON_POSITION_PATTERN))//
                .mapToInt(i -> (int) i.getY())//
                .toArray();
        long repeatingY = getRepeating(moonsY);

        int[] moonsZ = eventData.stream()//
                .map(v -> Pos3d.fromPattern(v, MOON_POSITION_PATTERN))//
                .mapToInt(i -> (int) i.getZ())//
                .toArray();
        long repeatingZ = getRepeating(moonsZ);

        return MathHelper.lcm(//
                repeatingX, //
                MathHelper.lcm(repeatingY, repeatingZ));
    }

    private long getRepeating(int[] moons) {
        int[] vel = new int[moons.length];
        Arrays.fill(vel, 0);
        String hash = getStepHash(moons, vel);

        long repeatingX = 0;
        while (true) {
            doStep1D(moons, vel);
            repeatingX++;

            if (hash.equals(getStepHash(moons, vel))) {
                break;
            }
        }
        return repeatingX;
    }

    private String getStepHash(int[] moons, int[] vel) {
        StringBuilder resp = new StringBuilder();
        for (int i = 0; i < moons.length; i++) {
            resp.append(i);
            resp.append(":");
            resp.append(moons[i]);
            resp.append(":");
            resp.append(vel[i]);
        }
        return resp.toString();
    }

    private int doPuzzle1(List<String> eventData) {
        List<Pos3d> moons = eventData.stream()//
                .map(v -> Pos3d.fromPattern(v, MOON_POSITION_PATTERN))//
                .collect(Collectors.toList());

        List<Pos3d> vel = new ArrayList<>();
        for (int i = 0; i < moons.size(); i++) {
            vel.add(new Pos3d(0, 0, 0));
        }

        return getTotalEnergy(moons, vel);
    }

    private int getTotalEnergy(List<Pos3d> moons, List<Pos3d> vel) {

        for (int step = 0; step < steps; step++) {
            doStep(moons, vel);
        }

        int e = 0;
        for (int i = 0; i < moons.size(); i++) {
            e += (Math.abs(moons.get(i).getX()) //
                    + Math.abs(moons.get(i).getY()) //
                    + Math.abs(moons.get(i).getZ()) //
            ) * (Math.abs(vel.get(i).getX()) //
                    + Math.abs(vel.get(i).getY()) //
                    + Math.abs(vel.get(i).getZ()) //
            );
        }

        return e;
    }

    private void doStep(List<Pos3d> moons, List<Pos3d> vel) {
        List<Pos3d> gravities = new LinkedList<>();
        for (int i = 0; i < moons.size(); i++) {
            gravities.add(getGravity(moons, i));
        }

        for (int i = 0; i < moons.size(); i++) {
            vel.get(i).add(gravities.get(i));
            moons.get(i).add(vel.get(i));
        }
    }

    private void doStep1D(int[] moons, int[] vel) {
        int[] gravities = new int[moons.length];
        for (int i = 0; i < moons.length; i++) {
            gravities[i] = getGravity1D(moons, i);
        }

        for (int i = 0; i < moons.length; i++) {
            vel[i] = vel[i] + gravities[i];
            moons[i] = moons[i] + vel[i];
        }
    }

    private Pos3d getGravity(List<Pos3d> moons, int number) {
        Pos3d response = new Pos3d(0, 0, 0);

        Pos3d moonToCheck = moons.get(number);
        for (int i = 0; i < moons.size(); i++) {
            if (i == number) {
                continue;
            }
            Pos3d moon = moons.get(i);
            response.setX(getDelta(moonToCheck.getX(), moon.getX()) + response.getX());
            response.setY(getDelta(moonToCheck.getY(), moon.getY()) + response.getY());
            response.setZ(getDelta(moonToCheck.getZ(), moon.getZ()) + response.getZ());
        }

        return response;
    }

    private int getGravity1D(int[] moons, int number) {
        int response = 0;

        int moonToCheck = moons[number];
        for (int i = 0; i < moons.length; i++) {
            if (i == number) {
                continue;
            }
            int moon = moons[i];
            response += getDelta(moonToCheck, moon);
        }

        return response;
    }

    private int getDelta(int a, int b) {
        if (a < b) {
            return 1;
        }

        if (a > b) {
            return -1;
        }

        return 0;
    }

    private long getDelta(long a, long b) {
        if (a < b) {
            return 1L;
        }

        if (a > b) {
            return -1L;
        }

        return 0L;
    }

}
