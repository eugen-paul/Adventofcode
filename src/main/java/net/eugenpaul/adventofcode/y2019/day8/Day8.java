package net.eugenpaul.adventofcode.y2019.day8;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day8 extends SolutionTemplate {

    @Getter
    private int response1;
    @Getter
    private String message;

    @Setter
    private int width = 25;
    @Setter
    private int height = 6;

    public static void main(String[] args) {
        Day8 puzzle = new Day8();
        puzzle.doPuzzleFromFile("y2019/day8/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        List<int[]> layers = initLayers(eventData);

        response1 = doPuzzle1(layers);
        message = doPuzzle2(layers);

        logger.log(Level.INFO, () -> "response1   : " + getResponse1());

        logger.log(Level.INFO, () -> "message   :");
        logger.log(Level.INFO, message);

        return true;
    }

    private List<int[]> initLayers(String eventData) {
        List<int[]> layers = new LinkedList<>();
        int[] currentLayer = new int[width * height];

        for (int i = 0; i < eventData.length(); i++) {
            int pos = i % (width * height);
            if (pos == 0) {
                currentLayer = new int[width * height];
                layers.add(currentLayer);
            }
            currentLayer[pos] = eventData.charAt(i) - '0';
        }

        return layers;
    }

    private int doPuzzle1(List<int[]> layers) {
        int fewestZero = Integer.MAX_VALUE;
        int response = 0;
        for (var layer : layers) {
            int zeros = count(layer, 0);
            if (fewestZero > zeros) {
                fewestZero = zeros;
                response = count(layer, 1) * count(layer, 2);
            }
        }
        return response;
    }

    private String doPuzzle2(List<int[]> layers) {
        int[] msg = new int[width * height];
        Arrays.fill(msg, 2);
        for (var layer : layers) {
            for (int i = 0; i < layer.length; i++) {
                if (msg[i] == 2) {
                    msg[i] = layer[i];
                }
            }
        }

        int pos = 0;
        StringBuilder line = new StringBuilder(width * height + height);
        for (int i : msg) {
            if (pos % width == 0 && pos > 0) {
                line.append("\n");
            }
            switch (i) {
            case 0:
                line.append(" ");
                break;
            case 1:
                line.append("#");
                break;
            default:
                break;
            }
            pos++;
        }

        return line.toString();

    }

    private int count(int[] l, int c) {
        int number = 0;
        for (int i : l) {
            if (i == c) {
                number++;
            }
        }
        return number;
    }

}
