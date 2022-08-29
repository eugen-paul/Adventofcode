package net.eugenpaul.adventofcode.y2021.day22;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.eugenpaul.adventofcode.helper.Pos3d;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day22 extends SolutionTemplate {

    private static final String FORMAT = "^([onf]*) x=([\\-0-9]*)\\.\\.([\\-0-9]*),y=([\\-0-9]*)\\.\\.([\\-0-9]*),z=([\\-0-9]*)\\.\\.([\\-0-9]*)$";
    private static final Pattern PATTERN = Pattern.compile(FORMAT);

    @NoArgsConstructor
    private class Cube {
        private boolean status;

        private int xMin;
        private int xMax;
        private int yMin;
        private int yMax;
        private int zMin;
        private int zMax;

        public Cube(boolean status) {
            this.status = status;
        }

        private boolean isForPuzzle1() {
            return xMin >= -50 && xMax <= 50 //
                    && yMin >= -50 && yMax <= 50 //
                    && zMin >= -50 && zMax <= 50 //
            ;
        }

        private void forEach(BiConsumer<Pos3d, Boolean> consumer) {
            for (int x = xMin; x <= xMax; x++) {
                for (int y = yMin; y <= yMax; y++) {
                    for (int z = zMin; z <= zMax; z++) {
                        consumer.accept(new Pos3d(x, y, z), status);
                    }
                }
            }
        }

        private long size() {
            return (xMax - xMin + 1L) * (yMax - yMin + 1L) * (zMax - zMin + 1L);
        }

        private boolean isOk() {
            return xMin <= xMax && yMin <= yMax && zMin <= zMax;
        }
    }

    @Getter
    private long cubesOn1;
    @Getter
    private long cubesOn2;

    public static void main(String[] args) {
        Day22 puzzle = new Day22();
        puzzle.doPuzzleFromFile("y2021/day22/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Cube> cubes = eventData.stream().map(this::fromString).collect(Collectors.toList());
        cubesOn1 = doPuzzle1(cubes);
        cubesOn2 = doPuzzle2(cubes);
        logger.log(Level.INFO, () -> "cubesOn1  : " + getCubesOn1());
        logger.log(Level.INFO, () -> "cubesOn2  : " + getCubesOn2());

        return true;
    }

    private int doPuzzle1(List<Cube> cubes) {
        List<Cube> cubesPuzzle1 = cubes.stream().filter(Cube::isForPuzzle1).collect(Collectors.toList());
        Set<Pos3d> status = new HashSet<>();

        for (var cube : cubesPuzzle1) {
            cube.forEach((pos, isOn) -> {
                if (isOn.booleanValue()) {
                    status.add(pos);
                } else {
                    status.remove(pos);
                }
            });
        }

        return status.size();
    }

    private long doPuzzle2(List<Cube> cubes) {
        List<Cube> onCubes = new LinkedList<>();

        for (Cube cube : cubes) {
            List<Cube> onCubesCurrent = new LinkedList<>();
            for (Cube oldCube : onCubes) {
                onCubesCurrent.addAll(sub(oldCube, cube));
            }
            if (cube.status) {
                onCubesCurrent.add(cube);
            }
            onCubes = onCubesCurrent;
        }

        return onCubes.stream().mapToLong(Cube::size).sum();
    }

    // Function to calculate a - b.
    // As a result you get the subcubes of A that are not part of B.
    // Is there an elegant solution?
    private List<Cube> sub(Cube a, Cube b) {
        if (a.xMax < b.xMin || a.xMin > b.xMax //
                || a.yMax < b.yMin || a.yMin > b.yMax //
                || a.zMax < b.zMin || a.zMin > b.zMax //
        ) {
            return List.of(a);
        }

        List<Cube> response = new LinkedList<>();

        // ---------------------------------------
        Cube cut1 = new Cube(true);
        cut1.xMin = a.xMin;
        cut1.xMax = b.xMin - 1;
        cut1.yMin = a.yMin;
        cut1.yMax = b.yMin - 1;
        cut1.zMin = a.zMin;
        cut1.zMax = b.zMin - 1;

        if (cut1.isOk()) {
            response.add(cut1);
        }

        Cube cut2 = new Cube(true);
        cut2.xMin = Math.max(b.xMin, a.xMin);
        cut2.xMax = Math.min(b.xMax, a.xMax);
        cut2.yMin = a.yMin;
        cut2.yMax = b.yMin - 1;
        cut2.zMin = a.zMin;
        cut2.zMax = b.zMin - 1;

        if (cut2.isOk()) {
            response.add(cut2);
        }

        Cube cut3 = new Cube(true);
        cut3.xMin = b.xMax + 1;
        cut3.xMax = a.xMax;
        cut3.yMin = a.yMin;
        cut3.yMax = b.yMin - 1;
        cut3.zMin = a.zMin;
        cut3.zMax = b.zMin - 1;

        if (cut3.isOk()) {
            response.add(cut3);
        }

        // ---------------------------------------
        Cube cut4 = new Cube(true);
        cut4.xMin = a.xMin;
        cut4.xMax = b.xMin - 1;
        cut4.yMin = Math.max(b.yMin, a.yMin);
        cut4.yMax = Math.min(b.yMax, a.yMax);
        cut4.zMin = a.zMin;
        cut4.zMax = b.zMin - 1;

        if (cut4.isOk()) {
            response.add(cut4);
        }

        Cube cut5 = new Cube(true);
        cut5.xMin = Math.max(b.xMin, a.xMin);
        cut5.xMax = Math.min(b.xMax, a.xMax);
        cut5.yMin = Math.max(b.yMin, a.yMin);
        cut5.yMax = Math.min(b.yMax, a.yMax);
        cut5.zMin = a.zMin;
        cut5.zMax = b.zMin - 1;

        if (cut5.isOk()) {
            response.add(cut5);
        }

        Cube cut6 = new Cube(true);
        cut6.xMin = b.xMax + 1;
        cut6.xMax = a.xMax;
        cut6.yMin = Math.max(b.yMin, a.yMin);
        cut6.yMax = Math.min(b.yMax, a.yMax);
        cut6.zMin = a.zMin;
        cut6.zMax = b.zMin - 1;

        if (cut6.isOk()) {
            response.add(cut6);
        }

        // ---------------------------------------
        Cube cut7 = new Cube(true);
        cut7.xMin = a.xMin;
        cut7.xMax = b.xMin - 1;
        cut7.yMin = b.yMax + 1;
        cut7.yMax = a.yMax;
        cut7.zMin = a.zMin;
        cut7.zMax = b.zMin - 1;

        if (cut7.isOk()) {
            response.add(cut7);
        }

        Cube cut8 = new Cube(true);
        cut8.xMin = Math.max(b.xMin, a.xMin);
        cut8.xMax = Math.min(b.xMax, a.xMax);
        cut8.yMin = b.yMax + 1;
        cut8.yMax = a.yMax;
        cut8.zMin = a.zMin;
        cut8.zMax = b.zMin - 1;

        if (cut8.isOk()) {
            response.add(cut8);
        }

        Cube cut9 = new Cube(true);
        cut9.xMin = b.xMax + 1;
        cut9.xMax = a.xMax;
        cut9.yMin = b.yMax + 1;
        cut9.yMax = a.yMax;
        cut9.zMin = a.zMin;
        cut9.zMax = b.zMin - 1;

        if (cut9.isOk()) {
            response.add(cut9);
        }

        // ---------------------------------------
        Cube cut10 = new Cube(true);
        cut10.xMin = a.xMin;
        cut10.xMax = b.xMin - 1;
        cut10.yMin = a.yMin;
        cut10.yMax = b.yMin - 1;
        cut10.zMin = Math.max(b.zMin, a.zMin);
        cut10.zMax = Math.min(b.zMax, a.zMax);

        if (cut10.isOk()) {
            response.add(cut10);
        }

        Cube cut11 = new Cube(true);
        cut11.xMin = Math.max(b.xMin, a.xMin);
        cut11.xMax = Math.min(b.xMax, a.xMax);
        cut11.yMin = a.yMin;
        cut11.yMax = b.yMin - 1;
        cut11.zMin = Math.max(b.zMin, a.zMin);
        cut11.zMax = Math.min(b.zMax, a.zMax);

        if (cut11.isOk()) {
            response.add(cut11);
        }

        Cube cut12 = new Cube(true);
        cut12.xMin = b.xMax + 1;
        cut12.xMax = a.xMax;
        cut12.yMin = a.yMin;
        cut12.yMax = b.yMin - 1;
        cut12.zMin = Math.max(b.zMin, a.zMin);
        cut12.zMax = Math.min(b.zMax, a.zMax);

        if (cut12.isOk()) {
            response.add(cut12);
        }
        // ---------------------------------------
        Cube cut13 = new Cube(true);
        cut13.xMin = a.xMin;
        cut13.xMax = b.xMin - 1;
        cut13.yMin = Math.max(b.yMin, a.yMin);
        cut13.yMax = Math.min(b.yMax, a.yMax);
        cut13.zMin = Math.max(b.zMin, a.zMin);
        cut13.zMax = Math.min(b.zMax, a.zMax);

        if (cut13.isOk()) {
            response.add(cut13);
        }

        // This cube is subtracted from a. Don't add it to result
        // Cube cut14 = new Cube(true);
        // cut14.xMin = Math.max(b.xMin, a.xMin);
        // cut14.xMax = Math.min(b.xMax, a.xMax);
        // cut14.yMin = Math.max(b.yMin, a.yMin);
        // cut14.yMax = Math.min(b.yMax, a.yMax);
        // cut14.zMin = Math.max(b.zMin, a.zMin);
        // cut14.zMax = Math.min(b.zMax, a.zMax);

        // if (cut14.isOk()) {
        // response.add(cut14);
        // }

        Cube cut15 = new Cube(true);
        cut15.xMin = b.xMax + 1;
        cut15.xMax = a.xMax;
        cut15.yMin = Math.max(b.yMin, a.yMin);
        cut15.yMax = Math.min(b.yMax, a.yMax);
        cut15.zMin = Math.max(b.zMin, a.zMin);
        cut15.zMax = Math.min(b.zMax, a.zMax);

        if (cut15.isOk()) {
            response.add(cut15);
        }
        // ---------------------------------------
        Cube cut16 = new Cube(true);
        cut16.xMin = a.xMin;
        cut16.xMax = b.xMin - 1;
        cut16.yMin = b.yMax + 1;
        cut16.yMax = a.yMax;
        cut16.zMin = Math.max(b.zMin, a.zMin);
        cut16.zMax = Math.min(b.zMax, a.zMax);

        if (cut16.isOk()) {
            response.add(cut16);
        }

        Cube cut17 = new Cube(true);
        cut17.xMin = Math.max(b.xMin, a.xMin);
        cut17.xMax = Math.min(b.xMax, a.xMax);
        cut17.yMin = b.yMax + 1;
        cut17.yMax = a.yMax;
        cut17.zMin = Math.max(b.zMin, a.zMin);
        cut17.zMax = Math.min(b.zMax, a.zMax);

        if (cut17.isOk()) {
            response.add(cut17);
        }

        Cube cut18 = new Cube(true);
        cut18.xMin = b.xMax + 1;
        cut18.xMax = a.xMax;
        cut18.yMin = b.yMax + 1;
        cut18.yMax = a.yMax;
        cut18.zMin = Math.max(b.zMin, a.zMin);
        cut18.zMax = Math.min(b.zMax, a.zMax);

        if (cut18.isOk()) {
            response.add(cut18);
        }

        // ---------------------------------------
        Cube cut19 = new Cube(true);
        cut19.xMin = a.xMin;
        cut19.xMax = b.xMin - 1;
        cut19.yMin = a.yMin;
        cut19.yMax = b.yMin - 1;
        cut19.zMin = b.zMax + 1;
        cut19.zMax = a.zMax;

        if (cut19.isOk()) {
            response.add(cut19);
        }

        Cube cut20 = new Cube(true);
        cut20.xMin = Math.max(b.xMin, a.xMin);
        cut20.xMax = Math.min(b.xMax, a.xMax);
        cut20.yMin = a.yMin;
        cut20.yMax = b.yMin - 1;
        cut20.zMin = b.zMax + 1;
        cut20.zMax = a.zMax;

        if (cut20.isOk()) {
            response.add(cut20);
        }

        Cube cut21 = new Cube(true);
        cut21.xMin = b.xMax + 1;
        cut21.xMax = a.xMax;
        cut21.yMin = a.yMin;
        cut21.yMax = b.yMin - 1;
        cut21.zMin = b.zMax + 1;
        cut21.zMax = a.zMax;

        if (cut21.isOk()) {
            response.add(cut21);
        }
        // ---------------------------------------
        Cube cut22 = new Cube(true);
        cut22.xMin = a.xMin;
        cut22.xMax = b.xMin - 1;
        cut22.yMin = Math.max(b.yMin, a.yMin);
        cut22.yMax = Math.min(b.yMax, a.yMax);
        cut22.zMin = b.zMax + 1;
        cut22.zMax = a.zMax;

        if (cut22.isOk()) {
            response.add(cut22);
        }

        Cube cut23 = new Cube(true);
        cut23.xMin = Math.max(b.xMin, a.xMin);
        cut23.xMax = Math.min(b.xMax, a.xMax);
        cut23.yMin = Math.max(b.yMin, a.yMin);
        cut23.yMax = Math.min(b.yMax, a.yMax);
        cut23.zMin = b.zMax + 1;
        cut23.zMax = a.zMax;

        if (cut23.isOk()) {
            response.add(cut23);
        }

        Cube cut24 = new Cube(true);
        cut24.xMin = b.xMax + 1;
        cut24.xMax = a.xMax;
        cut24.yMin = Math.max(b.yMin, a.yMin);
        cut24.yMax = Math.min(b.yMax, a.yMax);
        cut24.zMin = b.zMax + 1;
        cut24.zMax = a.zMax;

        if (cut24.isOk()) {
            response.add(cut24);
        }
        // ---------------------------------------
        Cube cut25 = new Cube(true);
        cut25.xMin = a.xMin;
        cut25.xMax = b.xMin - 1;
        cut25.yMin = b.yMax + 1;
        cut25.yMax = a.yMax;
        cut25.zMin = b.zMax + 1;
        cut25.zMax = a.zMax;

        if (cut25.isOk()) {
            response.add(cut25);
        }

        Cube cut26 = new Cube(true);
        cut26.xMin = Math.max(b.xMin, a.xMin);
        cut26.xMax = Math.min(b.xMax, a.xMax);
        cut26.yMin = b.yMax + 1;
        cut26.yMax = a.yMax;
        cut26.zMin = b.zMax + 1;
        cut26.zMax = a.zMax;

        if (cut26.isOk()) {
            response.add(cut26);
        }

        Cube cut27 = new Cube(true);
        cut27.xMin = b.xMax + 1;
        cut27.xMax = a.xMax;
        cut27.yMin = b.yMax + 1;
        cut27.yMax = a.yMax;
        cut27.zMin = b.zMax + 1;
        cut27.zMax = a.zMax;

        if (cut27.isOk()) {
            response.add(cut27);
        }

        return response;
    }

    private Cube fromString(String data) {
        Cube response = new Cube();

        Matcher m = PATTERN.matcher(data);

        if (!m.find()) {
            throw new IllegalArgumentException(data);
        }

        response.status = m.group(1).equals("on");
        response.xMin = Integer.parseInt(m.group(2));
        response.xMax = Integer.parseInt(m.group(3));
        response.yMin = Integer.parseInt(m.group(4));
        response.yMax = Integer.parseInt(m.group(5));
        response.zMin = Integer.parseInt(m.group(6));
        response.zMax = Integer.parseInt(m.group(7));

        return response;
    }

}
