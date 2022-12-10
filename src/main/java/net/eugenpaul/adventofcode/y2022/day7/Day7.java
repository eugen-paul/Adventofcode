package net.eugenpaul.adventofcode.y2022.day7;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.Getter;
import lombok.var;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day7 extends SolutionTemplate {

    private class Dir {
        private Dir parent;
        private Map<String, Dir> dirs;
        private Map<String, Integer> files;

        public Dir() {
            this(null);
        }

        public Dir(Dir parent) {
            this.parent = parent;
            dirs = new HashMap<>();
            files = new HashMap<>();
        }
    }

    @Getter
    private int totalSizesOfMost100k;
    @Getter
    private int sizeOfDeleteDir;

    public static void main(String[] args) {
        Day7 puzzle = new Day7();
        puzzle.doPuzzleFromFile("y2022/day7/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        totalSizesOfMost100k = doPuzzle1(eventData);
        sizeOfDeleteDir = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "totalSizesOfMost100k : " + getTotalSizesOfMost100k());
        logger.log(Level.INFO, () -> "sizeOfDeleteDir : " + getSizeOfDeleteDir());

        return true;
    }

    private int doPuzzle1(List<String> eventData) {
        Dir root = getRoot(eventData);
        print(root);
        List<Integer> sizes = new LinkedList<>();
        getTotalSizeAndSetSizes(root, sizes);
        return sizes.stream()//
                .filter(v -> v.intValue() <= 100_000)//
                .mapToInt(v -> v).sum();
    }

    private int doPuzzle2(List<String> eventData) {
        Dir root = getRoot(eventData);
        List<Integer> sizes = new LinkedList<>();
        int used = getTotalSizeAndSetSizes(root, sizes);
        int minDelete = 30000000 - (70000000 - used);
        return sizes.stream()//
                .filter(v -> v.intValue() >= minDelete)//
                .sorted()//
                .findFirst().orElseThrow();
    }

    private int getTotalSizeAndSetSizes(Dir root, List<Integer> dirSizes) {
        int fileSize = root.files.values().stream().mapToInt(Integer::intValue).sum();
        int dirSize = root.dirs.values().stream().mapToInt(v -> getTotalSizeAndSetSizes(v, dirSizes)).sum();
        int total = fileSize + dirSize;
        dirSizes.add(total);
        return total;
    }

    private Dir getRoot(List<String> eventData) {
        Dir root = new Dir();
        Dir current = root;

        var it = eventData.iterator();
        while (it.hasNext()) {
            var data = it.next();
            if (data.startsWith("$ cd")) {
                // Switch directory
                var cdData = data.split(" ");
                if (cdData[2].equals("..")) {
                    current = current.parent;
                } else if (cdData[2].equals("/")) {
                    current = root;
                } else {
                    Dir parent = current;
                    current = current.dirs.computeIfAbsent(cdData[2], v -> new Dir(parent));
                }
            } else if (data.startsWith("$ ls")) {
                // nothing todo
            } else if (data.startsWith("dir")) {
                // save name of subdir
                var dirData = data.split(" ");
                Dir parent = current;
                current.dirs.computeIfAbsent(dirData[1], v -> new Dir(parent));
            } else {
                // save name and size of file
                var fileData = data.split(" ");
                current.files.put(fileData[1], Integer.parseInt(fileData[0]));
            }
        }

        return root;
    }

    private void print(Dir root) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info("- / (dir)");
            print(root, "  ");
        }
    }

    private void print(Dir root, String offset) {
        if (logger.isLoggable(Level.INFO)) {
            for (var d : root.dirs.entrySet()) {
                logger.info(offset + "- " + d.getKey() + " (dir)");
                print(d.getValue(), offset + "  ");
            }
            for (var f : root.files.entrySet()) {
                logger.info(offset + "- " + f.getKey() + " (file, size = " + f.getValue() + ")");
            }
        }
    }

}
