package net.eugenpaul.adventofcode.y2019.day20;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.dijkstra.Dijkstra;
import net.eugenpaul.adventofcode.helper.dijkstra.Maze;

public class Day20 extends SolutionTemplate {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private enum TileType {
        WALL('#'), EMPTY('.'), CHAR('A'), NONE(' ');

        @Getter
        private final char value;

        public static TileType fromChar(Character c) {
            switch (c.charValue()) {
            case '#':
                return WALL;
            case '.':
                return EMPTY;
            case ' ':
                return NONE;
            default:
                return CHAR;
            }
        }
    }

    @AllArgsConstructor
    @Data
    private class Tile {
        private TileType type;
        private Portal portal;
    }

    @AllArgsConstructor
    @Data
    private class Portal {
        private String name;
        private SimplePos in;
        private SimplePos out;
    }

    @Getter
    private int wayAZ;
    @Getter
    private int wayAZ2;

    @Setter
    private boolean doStep2 = true;

    public static void main(String[] args) {
        Day20 puzzle = new Day20();
        puzzle.doPuzzleFromFile("y2019/day20/puzzle1.txt");
    }

    @AllArgsConstructor
    private static class Area implements Maze {
        private Map<SimplePos, Tile> tiles;

        @Override
        public List<SimplePos> getNextSteps(SimplePos from) {
            List<SimplePos> response = new LinkedList<>();

            for (Direction direction : Direction.values()) {
                Tile tile = tiles.get(from.moveNew(direction));
                if (tile != null//
                        && tile.getType() == TileType.EMPTY) {
                    response.add(from.moveNew(direction));
                } else if (tile != null //
                        && tile.getType() == TileType.CHAR //
                        && tile.getPortal().getOut() != null) {
                    response.add(tile.getPortal().getOut());
                }
            }

            return response;
        }
    }

    private static class AreaWithLevels implements Maze {
        private Map<SimplePos, Tile> tiles;
        private int outerX1 = Integer.MAX_VALUE;
        private int outerX2 = Integer.MIN_VALUE;
        private int outerY1 = Integer.MAX_VALUE;
        private int outerY2 = Integer.MIN_VALUE;

        public AreaWithLevels(Map<SimplePos, Tile> tiles) {
            this.tiles = tiles;
            for (var tile : tiles.entrySet()) {
                if (tile.getValue().getType() != TileType.CHAR) {
                    continue;
                }
                outerX1 = Math.min(outerX1, tile.getKey().getX());
                outerX2 = Math.max(outerX2, tile.getKey().getX());
                outerY1 = Math.min(outerY1, tile.getKey().getY());
                outerY2 = Math.max(outerY2, tile.getKey().getY());
            }
        }

        @Override
        public List<SimplePos> getNextSteps(SimplePos from) {
            List<SimplePos> response = new LinkedList<>();

            int level = from.getX() / 1000;

            SimplePos fromIntern = from.copy();
            fromIntern.setX(fromIntern.getX() % 1000);

            for (Direction direction : Direction.values()) {
                Tile tile = tiles.get(fromIntern.moveNew(direction));
                if (tile != null//
                        && tile.getType() == TileType.EMPTY) {
                    // move on same Level
                    response.add(from.moveNew(direction));
                } else if (tile != null //
                        && tile.getType() == TileType.CHAR //
                        && tile.getPortal().getOut() != null //
                ) {
                    // move to new Level
                    if (fromIntern.getX() == outerX1 + 1//
                            || fromIntern.getX() == outerX2 - 1//
                            || fromIntern.getY() == outerY1 + 1//
                            || fromIntern.getY() == outerY2 - 1//
                    ) {
                        if (level > 0) {
                            // go to lager level
                            SimplePos newPos = new SimplePos(//
                                    tile.getPortal().getOut().getX() + (level - 1) * 1000, //
                                    tile.getPortal().getOut().getY() //
                            );
                            response.add(newPos);
                        }
                    } else {
                        // go to smaller level
                        SimplePos newPos = new SimplePos(//
                                tile.getPortal().getOut().getX() + (level + 1) * 1000, //
                                tile.getPortal().getOut().getY() //
                        );
                        response.add(newPos);
                    }
                }
            }

            return response;
        }
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        Map<SimplePos, Tile> m = MapOfSimplePos.initMap(eventData, v -> {
            TileType type = TileType.fromChar(v);
            if (type == TileType.CHAR) {
                Portal p = new Portal(Character.toString(v), null, null);
                return new Tile(type, p);
            }
            return new Tile(type, null);
        });

        Set<SimplePos> portals = m.entrySet().stream()//
                .filter(v -> v.getValue().getType() == TileType.CHAR)//
                .map(Entry::getKey)//
                .collect(Collectors.toSet());

        setRealPortals(m, portals);

        SimplePos start = m.values().stream()//
                .filter(v -> v.type == TileType.CHAR && v.getPortal().getName().equals("AA"))//
                .findFirst().orElseThrow()//
                .getPortal().getIn();
        SimplePos end = m.values().stream()//
                .filter(v -> v.type == TileType.CHAR && v.getPortal().getName().equals("ZZ"))//
                .findFirst().orElseThrow()//
                .getPortal().getIn();

        Dijkstra dijkstra = new Dijkstra();
        wayAZ = dijkstra.getSteps(new Area(m), start, end);

        if (doStep2) {
            Dijkstra dijkstra2 = new Dijkstra();
            wayAZ2 = dijkstra2.getSteps(new AreaWithLevels(m), start, end);
        }

        logger.log(Level.INFO, () -> "wayAZ  : " + getWayAZ());
        logger.log(Level.INFO, () -> "wayAZ2 : " + getWayAZ2());

        return true;
    }

    private void setRealPortals(Map<SimplePos, Tile> m, Set<SimplePos> portals) {
        Map<SimplePos, Tile> origin = new HashMap<>(m);

        Map<String, Portal> portalsFound = new HashMap<>();

        for (var portalPos : portals) {
            Entry<SimplePos, Portal> p = getPortal(origin, portals, portalPos);
            if (p != null) {
                m.put(p.getKey(), new Tile(TileType.CHAR, p.getValue()));

                if (portalsFound.containsKey(p.getValue().getName())) {
                    Portal out = portalsFound.get(p.getValue().getName());
                    out.setOut(p.getValue().getIn().copy());
                    p.getValue().setOut(out.getIn());
                } else {
                    portalsFound.put(p.getValue().getName(), p.getValue());
                }

            } else {
                m.put(portalPos.copy(), new Tile(TileType.NONE, null));
            }
        }
    }

    private Entry<SimplePos, Portal> getPortal(Map<SimplePos, Tile> m, Set<SimplePos> portals, SimplePos pos) {
        SimplePos posN = pos.moveNew(Direction.N);
        SimplePos posS = pos.moveNew(Direction.S);
        SimplePos posE = pos.moveNew(Direction.E);
        SimplePos posW = pos.moveNew(Direction.W);

        if (portals.contains(posN) //
                && m.get(posS) != null //
                && m.get(posS).getType() == TileType.EMPTY //
        ) {
            Portal p = new Portal(//
                    m.get(posN).getPortal().getName() + m.get(pos).getPortal().getName(), //
                    posS, //
                    null //
            );
            return Map.entry(pos.copy(), p);
        }

        if (portals.contains(posS) //
                && m.get(posN) != null //
                && m.get(posN).getType() == TileType.EMPTY //
        ) {
            Portal p = new Portal(//
                    m.get(pos).getPortal().getName() + m.get(posS).getPortal().getName(), //
                    posN, //
                    null //
            );
            return Map.entry(pos.copy(), p);
        }

        if (portals.contains(posW) //
                && m.get(posE) != null //
                && m.get(posE).getType() == TileType.EMPTY //
        ) {
            Portal p = new Portal(//
                    m.get(posW).getPortal().getName() + m.get(pos).getPortal().getName(), //
                    posE, //
                    null //
            );
            return Map.entry(pos.copy(), p);
        }

        if (portals.contains(posE) //
                && m.get(posW) != null //
                && m.get(posW).getType() == TileType.EMPTY //
        ) {
            Portal p = new Portal(//
                    m.get(pos).getPortal().getName() + m.get(posE).getPortal().getName(), //
                    posW, //
                    null //
            );
            return Map.entry(pos.copy(), p);
        }

        return null;
    }

}