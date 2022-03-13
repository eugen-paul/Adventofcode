package net.eugenpaul.adventofcode.y2017.day3;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MathHelper;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

/**
 * <pre>
 * Z Z Z  Z   Z   Z   Z   Z Z Z Z
 * Z Y Y  Y   Y   Y   Y   Y Y Y Z
 * Z Y X  X   X   X   X   X X Y Z
 * Z Y X 17  16  15  14  13 X Y Z
 * Z Y X 18   5   4   3  12 X Y Z
 * Z Y X 19   6   1   2  11 X Y Z
 * Z Y X 20   7   8   9  10 X Y Z
 * Z Y X 21  22  23---> ... X Y Z
 * Z Y X  X   X   X   X   X X Y Z
 * Z Y Y  Y   Y   Y   Y   Y Y Y Z
 * Z Z Z  Z   Z   Z   Z   Z Z Z Z
 * 
 * Element pro ring
 * 1 ->  1
 * 2 ->  8 =  2*4 = 2*1*4
 * 3 -> 16 =  4*4 = 2*2*4
 * 4 -> 24 =  6*4 = 2*3*4
 * 5 -> 32 =  8*4 = 2*4*4
 * 6 -> 40 = 10*4 = 2*5*4
 * 
 * Max element of the ring
 * max(ring X) = 2*4*SUM(1,x-1) + 1
 * max(ring 3) = 2*4*sum(1,2) + 1 = 2*4*3 + 1 = 25
 * </pre>
 */
public class Day3 extends SolutionTemplate {

    @Getter
    private int steps;
    @Getter
    private int solution2;

    public static void main(String[] args) {
        Day3 puzzle = new Day3();
        puzzle.doPuzzleFromFile("y2017/day3/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        Integer inputData = Integer.parseInt(eventData);

        steps = duPuzzle1(inputData);
        solution2 = duPuzzle2(inputData);

        logger.log(Level.INFO, () -> "steps : " + getSteps());
        logger.log(Level.INFO, () -> "solution2 : " + getSolution2());

        return true;
    }

    private int duPuzzle1(Integer inputData) {
        int ring = getRing(inputData);
        int m = getMaxOfTheRing(ring);
        int s = ringSideLength(ring);
        int position = s - ((m - inputData) % s);

        return (ring - 1) + Math.abs(s / 2 - position);
    }

    private int duPuzzle2(Integer inputData) {
        Map<String, Integer> ringsData = new HashMap<>();
        ringsData.put("0:0", 1);

        int currentRing = 1;
        int currentData = 1;

        while (currentData <= inputData) {
            currentRing++;
            int s = ringSideLength(currentRing);

            int nextData = 0;

            for (int i = -s / 2 + 1; i <= s / 2 && nextData <= inputData; i++) {
                int x = s / 2;
                int y = i;
                nextData = computeData(x, y, ringsData);
            }

            for (int i = s / 2 - 1; i >= -s / 2 && nextData <= inputData; i--) {
                int x = i;
                int y = s / 2;
                nextData = computeData(x, y, ringsData);
            }

            for (int i = s / 2 - 1; i >= -s / 2 && nextData <= inputData; i--) {
                int x = -s / 2;
                int y = i;
                nextData = computeData(x, y, ringsData);
            }

            for (int i = -s / 2 + 1; i <= s / 2 && nextData <= inputData; i++) {
                int x = i;
                int y = -s / 2;
                nextData = computeData(x, y, ringsData);
            }

            currentData = nextData;
        }

        return currentData;
    }

    private int computeData(int x, int y, Map<String, Integer> ringsData) {
        int responseData = 0;

        responseData += ringsData.getOrDefault((x + 1) + ":" + (y + 0), 0);
        responseData += ringsData.getOrDefault((x + 1) + ":" + (y + 1), 0);
        responseData += ringsData.getOrDefault((x + 0) + ":" + (y + 1), 0);
        responseData += ringsData.getOrDefault((x - 1) + ":" + (y + 1), 0);
        responseData += ringsData.getOrDefault((x - 1) + ":" + (y + 0), 0);
        responseData += ringsData.getOrDefault((x - 1) + ":" + (y - 1), 0);
        responseData += ringsData.getOrDefault((x + 0) + ":" + (y - 1), 0);
        responseData += ringsData.getOrDefault((x + 1) + ":" + (y - 1), 0);

        ringsData.put(x + ":" + y, responseData);

        return responseData;
    }

    private int getRing(int input) {
        int maxOfTheRing = 1;
        int ringNumber = 1;

        while (maxOfTheRing < input) {
            ringNumber++;
            maxOfTheRing = getMaxOfTheRing(ringNumber);
        }

        return ringNumber;
    }

    private int getMaxOfTheRing(int ring) {
        return 2 * 4 * (int) MathHelper.sum(1L, ring - 1L) + 1;
    }

    private int ringSideLength(int ring) {
        return (ring - 1) * 2;
    }

}
