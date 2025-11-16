package net.eugenpaul.adventofcode.y2020.day20;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.MatrixHelper;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day20 extends SolutionTemplate {

    @AllArgsConstructor
    private class Tile {
        private int number;
        @Getter
        private Character[][] data;

        private String getHashL() {
            StringBuilder response = new StringBuilder(data.length);

            for (Character[] characters : data) {
                response.append(characters[0]);
            }

            return response.toString();
        }

        private String getHashR() {
            StringBuilder response = new StringBuilder(data.length);

            for (Character[] characters : data) {
                response.append(characters[characters.length - 1]);
            }

            return response.toString();
        }

        private String getHashU() {
            StringBuilder response = new StringBuilder(data.length);

            for (Character character : data[0]) {
                response.append(character);
            }

            return response.toString();
        }

        private String getHashD() {
            StringBuilder response = new StringBuilder(data.length);

            for (Character character : data[data.length - 1]) {
                response.append(character);
            }

            return response.toString();
        }
    }

    private List<String> monsterImg = List.of(//
            "                  # ", //
            "#    ##    ##    ###", //
            " #  #  #  #  #  #   "//
    );

    @Getter
    private long mult;
    @Getter
    private long monsterFree;

    public static void main(String[] args) {
        Day20 puzzle = new Day20();
        puzzle.doPuzzleFromFile("y2020/day20/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Tile> tiles = fromStringList(eventData);
        Map<SimplePos, Tile> fullArea = getFullArea(tiles);
        mult = doPuzzle1(fullArea);

        monsterFree = doPuzzle2(fullArea);

        logger.log(Level.INFO, () -> "mult : " + getMult());
        logger.log(Level.INFO, () -> "monsterFree : " + getMonsterFree());

        return true;
    }

    private long doPuzzle1(Map<SimplePos, Tile> fullArea) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (var pos : fullArea.keySet()) {
            minX = Math.min(minX, pos.getX());
            maxX = Math.max(maxX, pos.getX());
            minY = Math.min(minY, pos.getY());
            maxY = Math.max(maxY, pos.getY());
        }

        return (long) fullArea.get(new SimplePos(minX, minY)).number //
                * fullArea.get(new SimplePos(minX, maxY)).number //
                * fullArea.get(new SimplePos(maxX, minY)).number //
                * fullArea.get(new SimplePos(maxX, maxY)).number //
        ;
    }

    private long doPuzzle2(Map<SimplePos, Tile> fullArea) {
        Tile monsterTile = new Tile(0, toArray(monsterImg));

        Character[][] area = toOneArea(fullArea);

        List<Tile> monsters = getAllVariations(monsterTile);

        for (Tile tile : monsters) {
            checkMonster(area, tile.data);
        }

        int response = 0;
        for (int x = 0; x < area.length; x++) {
            for (int y = 0; y < area[0].length; y++) {
                if (area[x][y].equals('#')) {
                    response++;
                }
            }
        }

        return response;
    }

    private Character[][] toOneArea(Map<SimplePos, Tile> fullArea) {
        int a = (int) Math.sqrt(fullArea.size());
        int size = a * fullArea.get(new SimplePos(0, 0)).data.length - 2 * a;
        Character[][] area = new Character[size][size];

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (var pos : fullArea.keySet()) {
            minX = Math.min(minX, pos.getX());
            maxX = Math.max(maxX, pos.getX());
            minY = Math.min(minY, pos.getY());
            maxY = Math.max(maxY, pos.getY());
        }

        int posX = 0;
        int posY = 0;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                Tile tile = fullArea.get(new SimplePos(x, y));
                for (int i = 0; i < tile.data.length - 2; i++) {
                    System.arraycopy(//
                            tile.data[i + 1], //
                            1, //
                            area[posY * (tile.data.length - 2) + i], //
                            (posX * (tile.data.length - 2)), //
                            tile.data.length - 2//
                    );
                }
                posY++;
            }
            posX++;
            posY = 0;
        }

        return area;
    }

    private void checkMonster(Character[][] area, Character[][] monster) {
        for (int startX = 0; startX <= area.length - monster.length; startX++) {
            for (int startY = 0; startY <= area[0].length - monster[0].length; startY++) {
                if (isMonsterOnPosition(area, monster, startX, startY)) {
                    drawMonster(area, monster, startX, startY);
                }
            }
        }
    }

    private void drawMonster(Character[][] area, Character[][] monster, int startX, int startY) {
        for (int x = 0; x < monster.length; x++) {
            for (int y = 0; y < monster[0].length; y++) {
                if (monster[x][y].equals('#')) {
                    area[startX + x][startY + y] = 'O';
                }
            }
        }
    }

    private boolean isMonsterOnPosition(Character[][] area, Character[][] monster, int startX, int startY) {
        boolean found = true;

        for (int x = 0; x < monster.length; x++) {
            boolean next = false;
            for (int y = 0; y < monster[0].length; y++) {
                if (monster[x][y].charValue() != ' ' //
                        && area[startX + x][startY + y].charValue() != '#' //
                        && area[startX + x][startY + y].charValue() != 'O' //
                ) {
                    next = true;
                    found = false;
                    break;
                }
            }
            if (next) {
                break;
            }
        }
        return found;
    }

    private Map<SimplePos, Tile> getFullArea(List<Tile> tiles) {
        Map<SimplePos, Tile> fullArea = new HashMap<>();

        LinkedList<Tile> restTiles = new LinkedList<>(tiles);

        fullArea.put(new SimplePos(0, 0), restTiles.remove());

        while (!restTiles.isEmpty()) {
            Tile tile = restTiles.remove();
            if (!addToMap(fullArea, tile)) {
                restTiles.addLast(tile);
            }
        }

        return fullArea;
    }

    private boolean addToMap(Map<SimplePos, Tile> fullArea, Tile tile) {

        List<Tile> tilesRotatedAndFliped = getAllVariations(tile);

        for (var areaTile : fullArea.entrySet()) {
            String l = areaTile.getValue().getHashL();
            String r = areaTile.getValue().getHashR();
            String u = areaTile.getValue().getHashU();
            String d = areaTile.getValue().getHashD();
            for (Tile testTile : tilesRotatedAndFliped) {
                if (testTile.getHashR().equals(l)) {
                    fullArea.put(areaTile.getKey().moveNew(Direction.W), testTile);
                    return true;
                }

                if (testTile.getHashL().equals(r)) {
                    fullArea.put(areaTile.getKey().moveNew(Direction.E), testTile);
                    return true;
                }

                if (testTile.getHashU().equals(d)) {
                    fullArea.put(areaTile.getKey().moveNew(Direction.S), testTile);
                    return true;
                }

                if (testTile.getHashD().equals(u)) {
                    fullArea.put(areaTile.getKey().moveNew(Direction.N), testTile);
                    return true;
                }
            }
        }

        return false;
    }

    private List<Tile> getAllVariations(Tile tile) {
        List<Tile> tilesRotatedAndFliped = new LinkedList<>();
        tilesRotatedAndFliped.add(tile);

        tilesRotatedAndFliped.add(new Tile(tile.number, MatrixHelper.flipH(tile.data)));
        tilesRotatedAndFliped.add(new Tile(tile.number, MatrixHelper.flipV(tile.data)));

        tilesRotatedAndFliped.add(new Tile(tile.number, MatrixHelper.rotateR(tile.data, 1)));
        tilesRotatedAndFliped.add(new Tile(tile.number, MatrixHelper.rotateR(tile.data, 2)));
        tilesRotatedAndFliped.add(new Tile(tile.number, MatrixHelper.rotateR(tile.data, 3)));

        tilesRotatedAndFliped.add(new Tile(tile.number, MatrixHelper.rotateR(MatrixHelper.flipV(tile.data))));
        tilesRotatedAndFliped.add(new Tile(tile.number, MatrixHelper.rotateR(MatrixHelper.flipV(tile.data), 1)));
        tilesRotatedAndFliped.add(new Tile(tile.number, MatrixHelper.rotateR(MatrixHelper.flipV(tile.data), 2)));
        tilesRotatedAndFliped.add(new Tile(tile.number, MatrixHelper.rotateR(MatrixHelper.flipV(tile.data), 3)));

        tilesRotatedAndFliped.add(new Tile(tile.number, MatrixHelper.rotateR(MatrixHelper.flipH(tile.data))));
        tilesRotatedAndFliped.add(new Tile(tile.number, MatrixHelper.rotateR(MatrixHelper.flipH(tile.data), 1)));
        tilesRotatedAndFliped.add(new Tile(tile.number, MatrixHelper.rotateR(MatrixHelper.flipH(tile.data), 2)));
        tilesRotatedAndFliped.add(new Tile(tile.number, MatrixHelper.rotateR(MatrixHelper.flipH(tile.data), 3)));
        return tilesRotatedAndFliped;
    }

    private List<Tile> fromStringList(List<String> eventData) {
        List<Tile> response = new LinkedList<>();

        List<String> data = new LinkedList<>();
        int number = 0;

        for (String input : eventData) {
            if (input.isEmpty()) {
                if (!data.isEmpty()) {
                    response.add(new Tile(number, toArray(data)));
                    data.clear();
                }
                continue;
            }

            if (input.startsWith("Tile ")) {
                number = Integer.parseInt(input.substring("Tile ".length(), input.indexOf(":")));
            } else {
                data.add(input);
            }
        }

        return response;
    }

    private Character[][] toArray(List<String> data) {
        Character[][] response = new Character[data.size()][data.get(0).length()];

        for (int i = 0; i < data.size(); i++) {
            List<Character> dataChars = StringConverter.toCharStream(data.get(i)).collect(Collectors.toList());
            System.arraycopy(//
                    dataChars.toArray(), //
                    0, //
                    response[i], //
                    0, //
                    response[i].length//
            );
        }

        return response;
    }

}
