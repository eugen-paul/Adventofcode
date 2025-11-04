package net.eugenpaul.adventofcode.y2024.day13;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day13 extends SolutionTemplate {

    private static class Game {
        SimplePos a;
        SimplePos b;
        SimplePos prize;

        public static Game read(String aIn, String bIn, String pIn) {
            var r = new Game();
            r.a = readButton(aIn);
            r.b = readButton(bIn);
            r.prize = readPrize(pIn);
            return r;
        }

        private static SimplePos readButton(String in) {
            String[] splits = in.split(" ");
            int x = Integer.parseInt(splits[2].substring(2, splits[2].length() - 1));
            int y = Integer.parseInt(splits[3].substring(2, splits[3].length()));
            return new SimplePos(x, y);
        }

        private static SimplePos readPrize(String in) {
            String[] splits = in.split(" ");
            int x = Integer.parseInt(splits[1].substring(2, splits[1].length() - 1));
            int y = Integer.parseInt(splits[2].substring(2, splits[2].length()));
            return new SimplePos(x, y);
        }
    }

    private static class Game2 {
        long aX;
        long aY;
        long bX;
        long bY;
        long prizeX;
        long prizeY;

        public Game2(String aIn, String bIn, String pIn) {
            String[] splits = aIn.split(" ");
            aX = Integer.parseInt(splits[2].substring(2, splits[2].length() - 1));
            aY = Integer.parseInt(splits[3].substring(2, splits[3].length()));

            splits = bIn.split(" ");
            bX = Integer.parseInt(splits[2].substring(2, splits[2].length() - 1));
            bY = Integer.parseInt(splits[3].substring(2, splits[3].length()));

            splits = pIn.split(" ");
            prizeX = Integer.parseInt(splits[1].substring(2, splits[1].length() - 1)) + 10000000000000L;
            prizeY = Integer.parseInt(splits[2].substring(2, splits[2].length())) + 10000000000000L;
        }
    }

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day13 puzzle = new Day13();
        puzzle.doPuzzleFromFile("y2024/day13/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        return doEvent(List.of(eventData));
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        List<Game> games = new LinkedList<>();
        for (int i = 2; i < eventData.size(); i += 4) {
            games.add(Game.read(eventData.get(i - 2), eventData.get(i - 1), eventData.get(i)));
        }

        for (var game : games) {
            int minCost = Integer.MAX_VALUE;
            for (int i = 0; i <= 100; i++) {
                int bCount = (game.prize.getX() - game.a.getX() * i) / game.b.getX();
                if (//
                bCount > 100 //
                        || game.a.getX() * i + game.b.getX() * bCount != game.prize.getX() //
                        || game.a.getY() * i + game.b.getY() * bCount != game.prize.getY() //
                ) {
                    continue;
                }
                int curCost = i * 3 + bCount;
                minCost = Math.min(minCost, curCost);
            }
            if (minCost != Integer.MAX_VALUE) {
                response += minCost;
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        List<Game2> games = new LinkedList<>();
        for (int i = 2; i < eventData.size(); i += 4) {
            games.add(new Game2(eventData.get(i - 2), eventData.get(i - 1), eventData.get(i)));
        }

        for (var game : games) {
            /**
             * to reach the Target we do push A 'k' times and B 'n' times. <br/>
             * Button A: X+94, Y+34 => ax = 94, ay = 34 <br/>
             * Button B: X+22, Y+67 => bx = 22, by = 67 <br/>
             * Prize: X=8400, Y=5400 => px=8400, py = 5400 <br/>
             * let total cost be 'c'
             * <p>
             * we know that: <br/>
             * (1) ax * k + bx * n = px <br/>
             * (2) ay * k + by * n = py <br/>
             * (3) 3 * k + n = c <br/>
             * in 1 and 2 move n to left: <br/>
             * (1) n = (px - ax*k) / bx <br/>
             * (2) n = (py - ay*k) / by <br/>
             * put 1 and 2 in 3: <br/>
             * (1) 3*k + px/bx - ax/bx * k = c <br/>
             * (2) 3*k + py/by - ay/by * k = c <br/>
             * sub 1 from 2 and move k to left: <br/>
             * k=(py*bx - px*by) / (ay*bx - ax*by) <--- solution <br/>
             * check if k i ok.
             */
            long aCount = (game.prizeY * game.bX - game.prizeX * game.bY) / (game.aY * game.bX - game.aX * game.bY);
            long bCount = (game.prizeX - game.aX * aCount) / game.bX;
            if (//
            aCount < 0 //
                    || bCount < 0 //
                    || game.aX * aCount + game.bX * bCount != game.prizeX //
                    || game.aY * aCount + game.bY * bCount != game.prizeY //
            ) {
                continue;
            }
            response += aCount * 3 + bCount;
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
