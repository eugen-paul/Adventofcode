package net.eugenpaul.adventofcode.y2023.day2;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day2 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public enum Cubes {
        B, G, R
    }

    @AllArgsConstructor
    private class Game {
        int id;
        List<EnumMap<Cubes, Integer>> gameSets;

        public Game(String data) {
            var gameSplit = data.split(" ");
            id = Integer.valueOf(gameSplit[1].substring(0, gameSplit[1].length() - 1));
            gameSets = new ArrayList<>();

            var onlySets = data.substring(data.indexOf(":") + 1);
            var sets = onlySets.split(";");
            for (var set : sets) {
                EnumMap<Cubes, Integer> setMap = new EnumMap<>(Cubes.class);
                gameSets.add(setMap);
                var c = set.split(",");
                for (var color : c) {
                    String col = color.trim();
                    var sc = col.split(" ");
                    switch (sc[1]) {
                    case "blue":
                        setMap.put(Cubes.B, Integer.valueOf(sc[0]));
                        break;
                    case "red":
                        setMap.put(Cubes.R, Integer.valueOf(sc[0]));
                        break;
                    case "green":
                        setMap.put(Cubes.G, Integer.valueOf(sc[0]));
                        break;
                    default:
                        break;
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        Day2 puzzle = new Day2();
        puzzle.doPuzzleFromFile("y2023/day2/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        int response = 0;
        List<Game> games = eventData.stream().map(Game::new).toList();
        response = games.stream().filter(this::isOk).mapToInt(g -> g.id).sum();
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        List<Game> games = eventData.stream().map(Game::new).toList();
        return games.stream().mapToLong(this::getPower).sum();
    }

    public boolean isOk(Game game) {
        return game.gameSets.stream().filter(s -> s.getOrDefault(Cubes.R, 0) > 12 || s.getOrDefault(Cubes.G, 0) > 13 || s.getOrDefault(Cubes.B, 0) > 14)
                .findAny().isEmpty();
    }

    public long getPower(Game game) {
        long r = 1;
        long g = 1;
        long b = 1;

        for (var s : game.gameSets) {
            r = Math.max(r, s.getOrDefault(Cubes.R, 1));
            g = Math.max(g, s.getOrDefault(Cubes.G, 1));
            b = Math.max(b, s.getOrDefault(Cubes.B, 1));
        }
        return r * g * b;
    }

}
