package net.eugenpaul.adventofcode.y2021.day20;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day20 extends SolutionTemplate {

    @Getter
    private long lightPixels;
    @Getter
    private long lightPixels2;

    public static void main(String[] args) {
        Day20 puzzle = new Day20();
        puzzle.doPuzzleFromFile("y2021/day20/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        boolean[] enhancementAlgorithm = new boolean[512];
        List<String> imageBuffer = new LinkedList<>();
        int i = 0;

        var itarator = eventData.iterator();
        while (itarator.hasNext()) {
            String line = itarator.next();
            if (i < 512) {
                for (char c : line.toCharArray()) {
                    enhancementAlgorithm[i] = (c == '#');
                    i++;
                }
            } else if (!line.isBlank()) {
                imageBuffer.add(line);
            }
        }

        lightPixels = enhance(imageBuffer, enhancementAlgorithm, 2).size();
        lightPixels2 = enhance(imageBuffer, enhancementAlgorithm, 50).size();

        logger.log(Level.INFO, () -> "lightPixels  : " + getLightPixels());
        logger.log(Level.INFO, () -> "lightPixels2 : " + getLightPixels2());

        return true;
    }

    private Set<SimplePos> enhance(List<String> imageBuffer, boolean[] enhancementAlgorithm, int rounds) {
        Set<SimplePos> image = StringConverter.toSet(imageBuffer, '#');

        Set<SimplePos> imageTemp = new HashSet<>();
        for (int round = 0; round < rounds; round++) {
            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxY = Integer.MIN_VALUE;

            for (var pos : image) {
                minX = Math.min(minX, pos.getX());
                maxX = Math.max(maxX, pos.getX());

                minY = Math.min(minY, pos.getY());
                maxY = Math.max(maxY, pos.getY());
            }

            int delta = 4;
            if (round % 2 == 1 && enhancementAlgorithm[0]) {
                delta = -2;
            }

            for (int x = minX - delta; x <= maxX + delta; x++) {
                for (int y = minY - delta; y <= maxY + delta; y++) {
                    SimplePos pos = new SimplePos(x, y);
                    int index = getIndex(image, pos);
                    boolean value = enhancementAlgorithm[index];
                    if (value) {
                        imageTemp.add(pos);
                    }
                }
            }

            image = imageTemp;
            imageTemp = new HashSet<>();
        }

        return image;
    }

    private int getIndex(Set<SimplePos> image, SimplePos pos) {
        int response = 0;
        if (image.contains(pos.moveNew(Direction.N).move(Direction.W))) {
            response += 256;
        }
        if (image.contains(pos.moveNew(Direction.N))) {
            response += 128;
        }
        if (image.contains(pos.moveNew(Direction.N).move(Direction.E))) {
            response += 64;
        }

        if (image.contains(pos.moveNew(Direction.W))) {
            response += 32;
        }
        if (image.contains(pos)) {
            response += 16;
        }
        if (image.contains(pos.moveNew(Direction.E))) {
            response += 8;
        }

        if (image.contains(pos.moveNew(Direction.S).move(Direction.W))) {
            response += 4;
        }
        if (image.contains(pos.moveNew(Direction.S))) {
            response += 2;
        }
        if (image.contains(pos.moveNew(Direction.S).move(Direction.E))) {
            response += 1;
        }

        return response;
    }

}
