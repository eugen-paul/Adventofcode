package net.eugenpaul.adventofcode.y2016.day24;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.eugenpaul.adventofcode.helper.SimplePos;

public class DuctLayout {

    private boolean[][] layout;
    private Map<SimplePos, Integer> numbers = new HashMap<>();

    public static DuctLayout fromStringList(List<String> data) {
        int w = data.get(0).length();
        int h = data.size();

        boolean[][] layout = new boolean[w][h];

        DuctLayout response = new DuctLayout();
        response.layout = layout;

        for (int y = 0; y < data.size(); y++) {
            var line = data.get(y);
            for (int x = 0; x < line.length(); x++) {
                layout[x][y] = line.charAt(x) != '#';
                checkAndsetNumber(x, y, line.charAt(x), response.numbers);
            }
        }

        return response;
    }

    private static void checkAndsetNumber(int x, int y, char c, Map<SimplePos, Integer> numbers) {
        if (c < '0' || c > '9') {
            return;
        }

        numbers.put(new SimplePos(x, y), c - '0');
    }

    public char getCoordinates(int x, int y) {
        SimplePos p = new SimplePos(x, y);
        var number = numbers.get(p);
        if (number != null) {
            return number.toString().charAt(0);
        }

        return (layout[x][y]) ? '.' : '#';
    }

    public boolean isOk(int x, int y) {
        return layout[x][y];
    }

    public int getW() {
        return layout.length;
    }

    public int getH() {
        return layout[0].length;
    }

    public SimplePos getNumberPosition(int n) {
        var iterator = numbers.entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            if (entry.getValue().intValue() == n) {
                return new SimplePos(entry.getKey().getX(), entry.getKey().getY());
            }
        }
        return null;
    }
}
