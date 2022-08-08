package net.eugenpaul.adventofcode.y2018.day15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

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
        Set<SimplePos> grid = new HashSet<>();
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
            Set<SimplePos> grid = new HashSet<>();
            List<Entity> players = new ArrayList<>();

            initData(eventData, grid, players, elfsAttackPower);

            long elfsCount = players.stream().filter(Entity::isGood).count();

            sortEntitesByPosList(players);

            int turnCount = 0;
            while (!doTurn(grid, players)) {
                turnCount++;

                sortEntitesByPosList(players);
            }

            if (elfsCount == players.stream().filter(Entity::isGood).filter(v -> v.getHitPoints() > 0).count()) {
                // all the elves are alive
                int hitPoints = players.stream().filter(v -> v.getHitPoints() > 0).mapToInt(Entity::getHitPoints).sum();
                outcome2 = turnCount * hitPoints;
                break;
            }
            elfsAttackPower++;
        }

        logger.log(Level.INFO, () -> "outcome2  : " + getOutcome2());
    }

    private boolean doTurn(Set<SimplePos> grid, List<Entity> players) {
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

        // remove death palyers from list
        players.removeIf(v -> v.getHitPoints() < 0);

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

    /**
     * The function sorts the possible steps by priority. Priority of the steps: up, left, rigth, down
     * 
     * @param pos
     */
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

        List<Entity> neighbors = new LinkedList<>();

        for (Direction d : Direction.values()) {
            var neighborPos = pos.moveNew(d);
            var entity = playersPositions.get(neighborPos);
            if (entity != null) {
                neighbors.add(entity);
            }
        }

        return neighbors.stream()//
                .filter(v -> v.getHitPoints() > 0) // get only alive neighbors
                .filter(v -> v.isGood() == isGood) // get only needed neighbors
                .sorted((a, b) -> a.getHitPoints() - b.getHitPoints()) // get neighbor with min hitPoints
                .findFirst() //
                .orElse(null);
    }

    private SimplePos move(Set<SimplePos> grid, List<Entity> players, Entity player) {
        Map<SimplePos, Integer> stepsMap = new HashMap<>();
        stepsMap.put(player.getPos(), 0);

        // Map of all positions that can be reached by the player in x steps.
        TreeMap<Integer, List<SimplePos>> next = new TreeMap<>();

        // add first step to map
        next.put(1, getSteps(grid, stepsMap, player.getPos(), 1, players));
        if (next.get(1).isEmpty()) {
            // no steps are possible
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

    /**
     * returns best direction of motion
     */
    private SimplePos getMoveDirection(Map<SimplePos, Integer> stepsMap, SimplePos targetPosition) {
        int cost = stepsMap.get(targetPosition);
        SimplePos response = targetPosition;
        while (cost > 1) {
            for (var d : List.of(Direction.N, Direction.W, Direction.E, Direction.S)) {
                var stepDirection = response.moveNew(d);
                Integer costDirection = stepsMap.get(stepDirection);
                if (costDirection != null && costDirection < cost) {
                    cost = costDirection;
                    response = stepDirection;
                    break;
                }
            }
        }
        return response;
    }

    /**
     * returns positions in which the player can move.
     */
    private List<SimplePos> getSteps(Set<SimplePos> grid, Map<SimplePos, Integer> stepsMap, SimplePos pos, int currentCost, List<Entity> players) {
        List<SimplePos> responseSteps = new LinkedList<>();
        Set<SimplePos> playersPositions = players.stream()//
                .filter(v -> v.getHitPoints() > 0)//
                .map(Entity::getPos)//
                .collect(Collectors.toSet());

        for (Direction d : Direction.values()) {
            var stepDirection = pos.moveNew(d);
            if (grid.contains(stepDirection) //
                    && stepsMap.get(stepDirection) == null //
                    && !playersPositions.contains(stepDirection) //
            ) {
                responseSteps.add(stepDirection);
                stepsMap.put(stepDirection, currentCost);
            }
        }

        return responseSteps;
    }

    private void initData(List<String> data, Set<SimplePos> grid, List<Entity> players, int elfsAttackPower) {
        for (int y = 0; y < data.size(); y++) {
            for (int x = 0; x < data.get(0).length(); x++) {
                var currentPos = new SimplePos(x, y);
                char cp = data.get(y).charAt(x);
                switch (cp) {
                case 'G':
                    players.add(//
                            new Entity(new SimplePos(x, y), false, 200, 3)//
                    );
                    grid.add(currentPos);
                    break;
                case 'E':
                    players.add(//
                            new Entity(new SimplePos(x, y), true, 200, elfsAttackPower)//
                    );
                    grid.add(currentPos);
                    break;
                case '.':
                    grid.add(currentPos);
                    break;
                default:
                    break;
                }
            }
        }
    }

}
