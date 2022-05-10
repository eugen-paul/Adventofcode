package net.eugenpaul.adventofcode.y2018.day15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

/**
 * It not the cleanest code, but it work. Some times i will refactor it, i hope :D
 */
public class Day15 extends SolutionTemplate {

    @Getter
    private int outcome;
    @Getter
    private int outcome2;

    public static void main(String[] args) {
        Day15 puzzle = new Day15();
        puzzle.doPuzzleFromFile("y2018/day15/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        doPuzzle1(eventData);
        doPuzzle2(eventData);

        return true;
    }

    private void doPuzzle1(List<String> eventData) {
        Map<SimplePos, Boolean> grid = new HashMap<>();
        List<Entity> players = new ArrayList<>();

        initData(eventData, grid, players, 3);

        sortEntitesByPosList(players);

        int turnCount = 0;
        while (!doTurn(grid, players)) {
            turnCount++;

            sortEntitesByPosList(players);
        }
        int hitPoints = players.stream().filter(v -> v.getHitPoints() > 0).mapToInt(Entity::getHitPoints).sum();
        outcome = turnCount * hitPoints;

        logger.log(Level.INFO, () -> "outcome  : " + getOutcome());
    }

    private void doPuzzle2(List<String> eventData) {

        int elfsAttackPower = 3;
        while (true) {
            Map<SimplePos, Boolean> grid = new HashMap<>();
            List<Entity> players = new ArrayList<>();

            initData(eventData, grid, players, elfsAttackPower);

            long elfsCount = players.stream().filter(Entity::isGood).count();

            sortEntitesByPosList(players);

            int turnCount = 0;
            while (!doTurn(grid, players)) {
                turnCount++;

                sortEntitesByPosList(players);
            }
            int hitPoints = players.stream().filter(v -> v.getHitPoints() > 0).mapToInt(Entity::getHitPoints).sum();
            outcome2 = turnCount * hitPoints;

            if (elfsCount == players.stream().filter(Entity::isGood).filter(v -> v.getHitPoints() > 0).count()) {
                break;
            }
            elfsAttackPower++;
        }

        logger.log(Level.INFO, () -> "outcome2  : " + getOutcome2());
    }

    private List<String> printBoolMap(Map<SimplePos, Boolean> map, List<Entity> players) {
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;

        Map<SimplePos, Entity> playersPositions = players.stream()//
                .filter(v -> v.getHitPoints() > 0)//
                .collect(Collectors.toMap(Entity::getPos, v -> v));

        for (var entry : map.entrySet()) {
            xMin = Math.min(xMin, entry.getKey().getX());
            xMax = Math.max(xMax, entry.getKey().getX());
            yMin = Math.min(yMin, entry.getKey().getY());
            yMax = Math.max(yMax, entry.getKey().getY());
        }

        List<String> response = new LinkedList<>();

        for (int y = yMin; y <= yMax; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = xMin; x <= xMax; x++) {
                var value = map.getOrDefault(new SimplePos(x, y), false);

                if (playersPositions.get(new SimplePos(x, y)) != null) {
                    line.append(playersPositions.get(new SimplePos(x, y)).isGood() ? 'E' : 'G');
                } else {
                    line.append(value.booleanValue() ? '#' : '.');
                }

            }
            logger.log(Level.INFO, line::toString);
            response.add(line.toString());
        }

        return response;
    }

    private boolean doTurn(Map<SimplePos, Boolean> grid, List<Entity> players) {
        for (Entity player : players) {
            if (player.getHitPoints() <= 0) {
                continue;
            }
            if (!targetsRemaining(players, !player.isGood())) {
                return true;
            }
            Entity inRange = enemyInRange(players, player.getPos(), !player.isGood());
            if (inRange != null) {
                inRange.setHitPoints(inRange.getHitPoints() - player.getAttackPower());
            } else {
                SimplePos moveTo = move(grid, players, player);
                if (moveTo != null) {
                    player.setPos(moveTo);
                }

                inRange = enemyInRange(players, player.getPos(), !player.isGood());
                if (inRange != null) {
                    inRange.setHitPoints(inRange.getHitPoints() - player.getAttackPower());
                }
            }
        }

        var iterator = players.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getHitPoints() <= 0) {
                iterator.remove();
            }
        }

        return false;
    }

    private void sortEntitesByPosList(List<Entity> players) {
        Collections.sort(players, (a, b) -> {
            if (a.getPos().getY() == b.getPos().getY()) {
                return a.getPos().getX() - b.getPos().getX();
            }
            return a.getPos().getY() - b.getPos().getY();
        });
    }

    private void sortPosList(List<SimplePos> pos) {
        Collections.sort(pos, (a, b) -> {
            if (a.getY() == b.getY()) {
                return a.getX() - b.getX();
            }
            return a.getY() - b.getY();
        });
    }

    private boolean targetsRemaining(List<Entity> players, boolean isGood) {
        for (Entity entity : players) {
            if (entity.isGood() == isGood && entity.getHitPoints() > 0) {
                return true;
            }
        }
        return false;
    }

    private Entity enemyInRange(List<Entity> players, SimplePos pos, boolean isGood) {
        Map<SimplePos, Entity> playersPositions = players.stream()//
                .filter(v -> v.getHitPoints() > 0)//
                .collect(Collectors.toMap(Entity::getPos, v -> v));

        Entity posibleEnemies = null;

        SimplePos n = pos.moveNew(Direction.N);
        Entity e = playersPositions.get(n);
        if (e != null && e.getHitPoints() > 0 && e.isGood() == isGood) {
            if (posibleEnemies == null) {
                posibleEnemies = e;
            }
        }
        n = pos.moveNew(Direction.W);
        e = playersPositions.get(n);
        if (e != null && e.getHitPoints() > 0 && e.isGood() == isGood) {
            if (posibleEnemies == null || posibleEnemies.getHitPoints() > e.getHitPoints()) {
                posibleEnemies = e;
            }
        }
        n = pos.moveNew(Direction.E);
        e = playersPositions.get(n);
        if (e != null && e.getHitPoints() > 0 && e.isGood() == isGood) {
            if (posibleEnemies == null || posibleEnemies.getHitPoints() > e.getHitPoints()) {
                posibleEnemies = e;
            }
        }
        n = pos.moveNew(Direction.S);
        e = playersPositions.get(n);
        if (e != null && e.getHitPoints() > 0 && e.isGood() == isGood) {
            if (posibleEnemies == null || posibleEnemies.getHitPoints() > e.getHitPoints()) {
                posibleEnemies = e;
            }
        }

        return posibleEnemies;

    }

    private SimplePos move(Map<SimplePos, Boolean> grid, List<Entity> players, Entity player) {
        Map<SimplePos, Integer> stepsMap = new HashMap<>();
        stepsMap.put(player.getPos(), 0);

        TreeMap<Integer, List<SimplePos>> next = new TreeMap<>();

        next.put(1, getSteps(grid, stepsMap, player.getPos(), 1, players));
        if (next.get(1).isEmpty()) {
            return null;
        }

        while (!next.isEmpty()) {
            List<SimplePos> nextList = next.firstEntry().getValue();
            if (nextList.isEmpty()) {
                next.pollFirstEntry();
                continue;
            }
            sortPosList(nextList);

            SimplePos step = nextList.remove(0);
            if (enemyInRange(players, step, !player.isGood()) != null) {
                return getMoveDirection(stepsMap, step);
            }

            next.computeIfAbsent(stepsMap.get(step) + 1, v -> new ArrayList<>())//
                    .addAll(getSteps(grid, stepsMap, step, stepsMap.get(step) + 1, players));
        }
        return null;
    }

    private SimplePos getMoveDirection(Map<SimplePos, Integer> stepsMap, SimplePos pos) {
        int cost = stepsMap.get(pos);
        SimplePos response = pos;
        while (cost > 1) {
            SimplePos stepN = response.moveNew(Direction.N);
            Integer costN = stepsMap.get(stepN);
            if (costN != null && costN < cost) {
                cost = costN;
                response = stepN;
                continue;
            }
            SimplePos stepW = response.moveNew(Direction.W);
            Integer costW = stepsMap.get(stepW);
            if (costW != null && costW < cost) {
                cost = costW;
                response = stepW;
                continue;
            }
            SimplePos stepO = response.moveNew(Direction.E);
            Integer costO = stepsMap.get(stepO);
            if (costO != null && costO < cost) {
                cost = costO;
                response = stepO;
                continue;
            }
            SimplePos stepS = response.moveNew(Direction.S);
            Integer costS = stepsMap.get(stepS);
            if (costS != null && costS < cost) {
                cost = costS;
                response = stepS;
                continue;
            }
            throw new IllegalArgumentException();
        }
        return response;
    }

    private List<SimplePos> getSteps(Map<SimplePos, Boolean> grid, Map<SimplePos, Integer> stepsMap, SimplePos pos, int currentCost, List<Entity> players) {
        List<SimplePos> responseSteps = new LinkedList<>();
        Map<SimplePos, Boolean> playersPositions = players.stream().filter(v -> v.getHitPoints() > 0).collect(Collectors.toMap(Entity::getPos, v -> true));

        SimplePos stepN = pos.moveNew(Direction.N);
        if (!grid.get(stepN).booleanValue() && stepsMap.get(stepN) == null && playersPositions.get(stepN) == null) {
            responseSteps.add(stepN);
            stepsMap.put(stepN, currentCost);
        }
        SimplePos stepW = pos.moveNew(Direction.W);
        if (!grid.get(stepW).booleanValue() && stepsMap.get(stepW) == null && playersPositions.get(stepW) == null) {
            responseSteps.add(stepW);
            stepsMap.put(stepW, currentCost);
        }
        SimplePos stepO = pos.moveNew(Direction.E);
        if (!grid.get(stepO).booleanValue() && stepsMap.get(stepO) == null && playersPositions.get(stepO) == null) {
            responseSteps.add(stepO);
            stepsMap.put(stepO, currentCost);
        }
        SimplePos stepS = pos.moveNew(Direction.S);
        if (!grid.get(stepS).booleanValue() && stepsMap.get(stepS) == null && playersPositions.get(stepS) == null) {
            responseSteps.add(stepS);
            stepsMap.put(stepS, currentCost);
        }

        return responseSteps;
    }

    private void initData(List<String> data, Map<SimplePos, Boolean> grid, List<Entity> players, int elfsAttackPower) {
        for (int y = 0; y < data.size(); y++) {
            for (int x = 0; x < data.get(0).length(); x++) {
                var currentPos = new SimplePos(x, y);
                char cp = data.get(y).charAt(x);
                switch (cp) {
                case 'G':
                    players.add(//
                            new Entity(new SimplePos(x, y), false, 200, 3)//
                    );
                    grid.put(currentPos, false);
                    break;
                case 'E':
                    players.add(//
                            new Entity(new SimplePos(x, y), true, 200, elfsAttackPower)//
                    );
                    grid.put(currentPos, false);
                    break;
                default:
                    var value = data.get(y).charAt(x) == '#';
                    grid.put(currentPos, value);
                    break;
                }
            }
        }
    }

}
