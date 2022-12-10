package net.eugenpaul.adventofcode.y2022.day10;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day10 extends SolutionTemplate {

    @Getter
    private int sumSixSiglanStrengths;
    @Getter
    private int tailPositions10;

    public static void main(String[] args) {
        Day10 puzzle = new Day10();
        puzzle.doPuzzleFromFile("y2022/day10/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        sumSixSiglanStrengths = doPuzzle1(eventData);
        tailPositions10 = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "sumSixSiglanStrengths : " + getSumSixSiglanStrengths());
        logger.log(Level.INFO, () -> "tailPositions10 : " + getTailPositions10());

        return true;
    }

    private int doPuzzle1(List<String> eventData) {
        int cycles = 0;
        int waitingCycles = 20;
        int x = 1;
        int response = 0;

        for (var data : eventData) {
            int xBevore = x;
            var inst = data.split(" ");
            switch (inst[0]) {
            case "noop":
                cycles++;
                break;
            case "addx":
                x += Integer.parseInt(inst[1]);
                cycles += 2;
                break;
            default:
                break;
            }
            if (cycles >= waitingCycles) {
                response += waitingCycles * xBevore;
                waitingCycles += 40;
            }
        }

        return response;
    }

    private int doPuzzle2(List<String> eventData) {
        int cycles = 0;
        int x = 1;
        Set<SimplePos> display = new HashSet<>();

        for (var data : eventData) {
            var inst = data.split(" ");
            switch (inst[0]) {
            case "noop":
                drawPixel(cycles, x, display);
                cycles++;
                break;
            case "addx":
                drawPixel(cycles, x, display);
                cycles++;
                drawPixel(cycles, x, display);
                cycles++;
                x += Integer.parseInt(inst[1]);
                break;
            default:
                break;
            }
        }

        MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(display));

        return 0;
    }

    private void drawPixel(int cycles, int x, Set<SimplePos> display) {
        if (Math.abs(x - (cycles % 40)) <= 1) {
            display.add(new SimplePos(cycles % 40, cycles / 40));
        }
    }

}
