package net.eugenpaul.adventofcode.y2017.day21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.Getter;

public class Rule {

    private static Logger logger = Logger.getLogger(Rule.class.getName());

    @Getter
    private int size;
    private boolean[][] input;
    private boolean[][] output;

    public static Rule initRule() {
        Rule response = new Rule();
        response.input = new boolean[][] { //
                { false, false, true }, //
                { true, false, true }, //
                { false, true, true }, //
        };
        response.size = 3;
        return response;
    }

    public Rule getOutputRule() {
        Rule response = new Rule();
        response.input = output.clone();
        response.size = output[0].length;
        return response;
    }

    public static Rule fromString(String data) {
        String[] elements = data.split(" ");

        boolean[][] input = parseData(elements[0]);
        boolean[][] output = parseData(elements[2]);

        Rule response = new Rule();
        response.input = input;
        response.output = output;
        response.size = input.length;

        return response;
    }

    public static boolean[][] parseData(String data) {
        String[] elements = data.split("/");
        int size = elements.length;

        boolean[][] response = new boolean[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                response[x][y] = elements[y].charAt(x) == '#';
            }
        }

        return response;
    }

    public boolean isRule(Rule rule) {
        if (this.size != rule.size) {
            return false;
        }

        boolean[][] matcher = this.input;
        if (isEq(rule, matcher)) {
            return true;
        }

        matcher = flip(matcher);
        if (isEq(rule, matcher)) {
            return true;
        }

        matcher = rotate(this.input);
        if (isEq(rule, matcher)) {
            return true;
        }

        matcher = flip(matcher);
        if (isEq(rule, matcher)) {
            return true;
        }

        matcher = rotate(rotate(this.input));
        if (isEq(rule, matcher)) {
            return true;
        }

        matcher = flip(matcher);
        if (isEq(rule, matcher)) {
            return true;
        }

        matcher = rotate(rotate(rotate(this.input)));
        if (isEq(rule, matcher)) {
            return true;
        }

        matcher = flip(matcher);
        if (isEq(rule, matcher)) {
            return true;
        }

        return false;
    }

    private boolean isEq(Rule rule, boolean[][] matcher) {
        for (int i = 0; i < size; i++) {
            if (Arrays.compare(matcher[i], rule.input[i]) != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean[][] rotate(boolean[][] data) {
        if (size == 2) {
            return new boolean[][] { //
                    { data[1][0], data[0][0] }, //
                    { data[1][1], data[0][1] }, //
            };
        }
        return new boolean[][] { //
                { data[2][0], data[1][0], data[0][0] }, //
                { data[2][1], data[1][1], data[0][1] }, //
                { data[2][2], data[1][2], data[0][2] }, //
        };
    }

    public boolean[][] flip(boolean[][] data) {
        if (size == 2) {
            return new boolean[][] { //
                    { data[1][0], data[1][1] }, //
                    { data[0][0], data[0][1] }, //
            };
        }
        return new boolean[][] { //
                { data[2][0], data[2][1], data[2][2] }, //
                { data[1][0], data[1][1], data[1][2] }, //
                { data[0][0], data[0][1], data[0][2] }, //
        };
    }

    public List<Rule> divide() {
        if (size % 2 == 0) {
            return divide(2);
        } else if (size % 3 == 0) {
            return divide(3);
        }

        throw new IllegalArgumentException("size = " + size);
    }

    private List<Rule> divide(int divSize) {
        List<Rule> response = new ArrayList<>();
        for (int i = 0; i < (size / divSize) * (size / divSize); i++) {
            Rule r = new Rule();
            r.size = divSize;
            r.input = new boolean[divSize][divSize];
            response.add(r);
        }

        int squaresProLine = (int) (Math.sqrt(response.size()));

        for (int i = 0; i < response.size(); i++) {
            Rule r = response.get(i);
            for (int y = 0; y < divSize; y++) {
                for (int x = 0; x < divSize; x++) {
                    int fromX = (x + (i * divSize)) % size;
                    int fromY = y + (i / squaresProLine) * divSize;
                    r.input[x][y] = input[fromX][fromY];
                }
            }
        }

        return response;
    }

    public static Rule concat(List<Rule> rules) {
        int squaresProLine = (int) (Math.sqrt(rules.size()));
        int size = squaresProLine * rules.get(0).size;
        int innerSize = rules.get(0).size;

        Rule r = new Rule();
        r.size = size;
        r.input = new boolean[size][size];

        for (int i = 0; i < rules.size(); i++) {
            Rule el = rules.get(i);
            for (int y = 0; y < innerSize; y++) {
                for (int x = 0; x < innerSize; x++) {
                    int toX = (x + (i * innerSize)) % size;
                    int toY = y + (i / squaresProLine) * innerSize;
                    r.input[toX][toY] = el.input[x][y];
                }
            }
        }

        return r;
    }

    public void print() {
        for (int y = 0; y < size; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < size; x++) {
                line.append((input[x][y]) ? '#' : '.');
            }
            logger.log(Level.INFO, line::toString);
        }
    }

    public int getOn() {
        int response = 0;
        for (boolean[] line : input) {
            for (boolean b : line) {
                if (b) {
                    response++;
                }
            }
        }

        return response;
    }
}
