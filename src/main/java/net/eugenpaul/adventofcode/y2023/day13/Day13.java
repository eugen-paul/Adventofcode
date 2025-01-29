package net.eugenpaul.adventofcode.y2023.day13;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day13 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day13 puzzle = new Day13();
        puzzle.doPuzzleFromFile("y2023/day13/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        List<String> area = new ArrayList<>();
        for (String data : eventData) {
            if (data.isBlank()) {
                response += getAreaNumber(area);
                area.clear();
            } else {
                area.add(data);
            }
        }
        response += getAreaNumber(area);

        logger.info("Solution 1 " + response);
        return response;
    }

    private int getAreaNumber(List<String> lines) {
        int response = 0;

        int n = checkHor(lines);
        if (n == 0) {
            n = checkVer(lines);
        } else {
            n *= 100;
        }

        response += n;

        return response;
    }

    private int checkHor(List<String> lines) {
        List<String> up = new ArrayList<>();
        List<String> down = new ArrayList<>(lines);

        up.add(down.remove(0));

        while (!down.isEmpty()) {
            List<String> b = (up.size() >= down.size()) ? up : down;
            List<String> l = (up.size() >= down.size()) ? down : up;
            boolean mirr = true;
            for (int i = 0; i < l.size(); i++) {
                if (!l.get(i).equals(b.get(i))) {
                    mirr = false;
                    break;
                }
            }
            if (mirr) {
                return up.size();
            }
            up.add(0, down.remove(0));
        }
        return 0;
    }

    private List<Integer> checkHor2(List<String> lines) {
        List<String> up = new ArrayList<>();
        List<String> down = new ArrayList<>(lines);

        up.add(down.remove(0));

        List<Integer> resp = new ArrayList<>();

        while (!down.isEmpty()) {
            List<String> b = (up.size() >= down.size()) ? up : down;
            List<String> l = (up.size() >= down.size()) ? down : up;
            boolean mirr = true;
            for (int i = 0; i < l.size(); i++) {
                if (!l.get(i).equals(b.get(i))) {
                    mirr = false;
                    break;
                }
            }
            if (mirr) {
                resp.add(up.size());
            }
            up.add(0, down.remove(0));
        }
        return resp;
    }

    private int checkVer(List<String> lines) {
        List<String> lines2 = new ArrayList<>();
        for (int i = 0; i < lines.get(0).length(); i++) {
            lines2.add("");
        }
        for (int k = lines.size() - 1; k >= 0; k--) {
            for (int i = 0; i < lines.get(0).length(); i++) {
                lines2.set(i, lines2.get(i) + lines.get(k).charAt(i));
            }
        }

        return checkHor(lines2);
    }

    private List<Integer> checkVer2(List<String> lines) {
        List<String> lines2 = new ArrayList<>();
        for (int i = 0; i < lines.get(0).length(); i++) {
            lines2.add("");
        }
        for (int k = lines.size() - 1; k >= 0; k--) {
            for (int i = 0; i < lines.get(0).length(); i++) {
                lines2.set(i, lines2.get(i) + lines.get(k).charAt(i));
            }
        }

        return checkHor2(lines2);
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

        List<String> area = new ArrayList<>();
        for (String data : eventData) {
            if (data.isBlank()) {
                response += smudgeAndGetAreaNumber(area);
                area.clear();
            } else {
                area.add(data);
            }
        }
        response += smudgeAndGetAreaNumber(area);

        logger.info("Solution 2 " + response);
        return response;
    }

    private int smudgeAndGetAreaNumber(List<String> lines) {
        int response = 0;

        int oldH = checkHor(lines);
        int oldV = checkVer(lines);

        boolean found = false;
        int newH = 0;
        int newV = 0;

        for (int lineNr = 0; lineNr < lines.size(); lineNr++) {
            String line = lines.get(lineNr);
            for (int i = 0; i < line.length(); i++) {
                char c = (line.charAt(i) == '#') ? '.' : '#';
                String tmpLine = line.substring(0, i) + c + line.substring(i + 1);
                lines.set(lineNr, tmpLine);

                var nh = checkHor2(lines);
                var nv = checkVer2(lines);

                if (nh.stream().anyMatch(v -> v != oldH)) {
                    newH = nh.stream().filter(v -> v != oldH).findFirst().orElseThrow();
                    found = true;
                    break;
                }
                if (nv.stream().anyMatch(v -> v != oldV)) {
                    newV = nv.stream().filter(v -> v != oldV).findFirst().orElseThrow();
                    found = true;
                    break;
                }
            }
            lines.set(lineNr, line);
            if (found) {
                break;
            }
        }

        if (newH != 0) {
            response = newH * 100;
        } else {
            response = newV;
        }

        return response;
    }

}
