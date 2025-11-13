package net.eugenpaul.adventofcode.y2023.day2;

import static net.eugenpaul.adventofcode.helper.ConvertHelper.*;
import static net.eugenpaul.adventofcode.helper.MathHelper.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

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
            id = toInt(gameSplit[1].substring(0, gameSplit[1].length() - 1));
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
                        setMap.put(Cubes.B, toInt(sc[0]));
                        break;
                    case "red":
                        setMap.put(Cubes.R, toInt(sc[0]));
                        break;
                    case "green":
                        setMap.put(Cubes.G, toInt(sc[0]));
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
        long response = eventData.stream()
        .map(v->{
            //Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            var st = v.split(":");
            //Game 1
            var id = st[0].split(" ")[1];
            Map<String, Integer> r = new HashMap<>();
            r.put("id", toInt(id));

            // 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            var gg = st[1].trim().split(";");
            for(var g: gg){
                // 3 blue, 4 red
                var g1 = g.trim().split(",");
                for(var gg1: g1){
                    // 3 blue
                    var g2 = gg1.trim().split(" ");
                    // [3, blue]
                    r.compute(g2[1], (k,v1) -> v1 == null?toInt(g2[0]): max(v1, toInt(g2[0])));
                }
            }
            return r;
        })
                .filter(v -> v.getOrDefault("red", 0) <= 12)
                .filter(v -> v.getOrDefault("green", 0) <= 13)
                .filter(v -> v.getOrDefault("blue", 0) <= 14)
                .mapToLong(v -> v.get("id"))
                .sum()
        ;
        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    public long doPuzzle1_a(List<String> eventData) {
        int response = 0;
        List<Game> games = eventData.stream().map(Game::new).toList();
        response = games.stream().filter(this::isOk).mapToInt(g -> g.id).sum();
        return response;
    }

    public long doPuzzle2(List<String> eventData) {
        long response = eventData.stream()
        .map(v->{
            var st = v.split(":");
            var id = st[0].split(" ")[1];
            // System.out.println("id:"+id);
            var gg = st[1].trim().split(";");
            Map<String, Integer> r = new HashMap<>();
            r.put("id", toInt(id));
            for(var g: gg){
                var g1 = g.trim().split(",");
                for(var gg1: g1){
                    var g2 = gg1.trim().split(" ");
                    r.compute(g2[1], (k,v1) -> v1 == null?toInt(g2[0]): max(v1, toInt(g2[0])));
                    // System.out.println(g2[0] + ":" + g2[1]);
                }
            }
            return r;
        })
                .mapToLong(v -> v.getOrDefault("red", 1) * v.getOrDefault("green", 1) * v.getOrDefault("blue", 1))
                .sum()
        ;
        logger.log(Level.INFO, "Solution 2: " + response);
        return response;
    }
    public long doPuzzle2_a(List<String> eventData) {
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
