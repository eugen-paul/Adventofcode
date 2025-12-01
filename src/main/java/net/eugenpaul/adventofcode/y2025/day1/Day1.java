package net.eugenpaul.adventofcode.y2025.day1;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MatrixHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;
import static net.eugenpaul.adventofcode.helper.StringConverter.*;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day1 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzleFromFile("y2025/day1/puzzle1.txt");
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

        var r = 50;
        for (var s : eventData) {
            var d = toInt(s.substring(1));
            if (s.charAt(0) == 'L') {
                r -= d;
                if (r == 0) {
                    response++;
                }
                if (r < 0) {
                    while (r < 0) {
                        r += 100;
                    }
                    if (r == 0) {
                        response++;
                    }
                }
            } else {
                r += d;
                if (r == 0) {
                    response++;
                }
                if (r > 99) {
                    r = r % 100;
                    if (r == 0) {
                        response++;
                    }
                }
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        // var r = 50;
        // for (var s : eventData) {
        // var d = toInt(s.substring(1));
        // if (s.charAt(0) == 'L') {
        // for (int i = 0; i < d; i++) {
        // r--;
        // if (Math.abs(r) % 100 == 0) {
        // response++;
        // }
        // }
        // while (r < 0) {
        // r += 100;
        // }
        // } else {
        // r += d;
        // if (r == 0) {
        // response++;
        // }
        // if (r > 99) {
        // response += r / 100;
        // r = r % 100;
        // }
        // }
        // }

        var r = 50;
        for (var s : eventData) {
            var d = toInt(s.substring(1));
            if (s.charAt(0) == 'L') {
                boolean z = r == 0;
                r -= d;
                if (r == 0) {
                    response++;
                } else if (r < 0) {
                    while (r < 0) {
                        r += 100;
                        response++;
                        if (r == 0) {
                            response++;
                        }
                    }
                    if (z) {
                        response--;
                    }
                }
            } else {
                r += d;
                if (r == 0) {
                    response++;
                }
                else if (r > 99) {
                    response += r / 100;
                    r = r % 100;
                }
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

}
