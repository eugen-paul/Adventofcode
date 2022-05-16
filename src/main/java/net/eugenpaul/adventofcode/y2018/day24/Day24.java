package net.eugenpaul.adventofcode.y2018.day24;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day24 extends SolutionTemplate {
    private static final String ARMY_FORMAT = "^([0-9]*) units each with ([0-9]*) hit points([\\(a-z ,;\\)])* with an attack that does ([0-9]*) ([a-z]*) damage at initiative ([0-9]*)$";
    private static final Pattern ARMY_PATTERN = Pattern.compile(ARMY_FORMAT);

    private enum Type {
        COLD("cold"), RADIATION("radiation"), FIRE("fire"), SLASHING("slashing"), BLUDGEONING("bludgeoning");

        private static final Map<String, Type> STRING_TO_TYPE = Stream.of(Type.values()).collect(Collectors.toMap(Type::getT, v -> v));

        @Getter
        private final String t;

        private Type(String t) {
            this.t = t;
        }

        private static Type fromString(String data) {
            return STRING_TO_TYPE.get(data);
        }
    }

    @AllArgsConstructor
    @Getter
    @Setter
    private class Army {
        private boolean good;
        private int unitsCount;
        private int unitHitPoints;
        private List<Type> weak;
        private List<Type> immune;
        private int dmg;
        private Type dmgType;
        private int initiative;

        private int getEffectivePower() {
            return unitsCount * dmg;
        }

        @Override
        public String toString() {
            return "" + good + "+" + initiative;
        }
    }

    @Getter
    private int units;

    @Getter
    private int unitsWithBoost;

    public static void main(String[] args) {
        Day24 puzzle = new Day24();
        puzzle.doPuzzleFromFile("y2018/day24/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Army> immuneArmy = new LinkedList<>();
        List<Army> infectionArmy = new LinkedList<>();
        initArmies(eventData, immuneArmy, infectionArmy);

        units = combat(immuneArmy, infectionArmy);
        logger.log(Level.INFO, () -> "units : " + getUnits());

        int boost = 1;
        while (true) {
            immuneArmy = new LinkedList<>();
            infectionArmy = new LinkedList<>();
            initArmies(eventData, immuneArmy, infectionArmy);

            boostArmy(immuneArmy, boost);

            unitsWithBoost = combat(immuneArmy, infectionArmy);

            if (!immuneArmy.isEmpty() && unitsWithBoost > 0) {
                break;
            }
            boost += 1;
        }
        logger.log(Level.INFO, () -> "unitsWithBoost : " + getUnitsWithBoost());

        return true;
    }

    private void boostArmy(List<Army> army, int boost) {
        for (Army a : army) {
            a.dmg += boost;
        }
    }

    private int combat(List<Army> immuneArmy, List<Army> infectionArmy) {

        boolean unitsAreKilled = true;
        while (!immuneArmy.isEmpty() && !infectionArmy.isEmpty() && unitsAreKilled) {
            List<Army> armyOrder = new LinkedList<>();
            armyOrder.addAll(immuneArmy);
            armyOrder.addAll(infectionArmy);

            sortByEffectivePowerAndInitiative(armyOrder);

            List<Army> immuneArmyRest = new LinkedList<>(immuneArmy);
            List<Army> infectionArmyRest = new LinkedList<>(infectionArmy);

            Map<String, Army> targets = new HashMap<>();
            targetSelectionPhase(armyOrder, immuneArmyRest, infectionArmyRest, targets);

            Collections.sort(armyOrder, (a, b) -> {
                return b.getInitiative() - a.getInitiative();
            });

            unitsAreKilled = false;
            unitsAreKilled = combatPhase(immuneArmy, infectionArmy, unitsAreKilled, armyOrder, targets);
        }

        if (!unitsAreKilled) {
            // endless combat
            return -1;
        }

        int responseCount1 = immuneArmy.stream().map(Army::getUnitsCount).reduce(0, (a, b) -> a + b);
        int responseCount2 = infectionArmy.stream().map(Army::getUnitsCount).reduce(0, (a, b) -> a + b);

        return responseCount1 + responseCount2;
    }

    private boolean combatPhase(List<Army> immuneArmy, List<Army> infectionArmy, boolean unitsAreKilled, List<Army> armyOrder, Map<String, Army> targets) {
        for (Army attacker : armyOrder) {
            Army target = targets.get(attacker.toString());

            if (attacker.getUnitsCount() > 0 && target != null) {

                int dmg = attacker.unitsCount * attacker.dmg;
                if (target.getWeak().contains(attacker.getDmgType())) {
                    dmg *= 2;
                }

                int unitsKilled = dmg / target.unitHitPoints;
                if (unitsKilled > 0) {
                    unitsAreKilled = true;
                }
                target.unitsCount = target.unitsCount - unitsKilled;

                if (target.unitsCount <= 0) {
                    immuneArmy.remove(target);
                    infectionArmy.remove(target);
                }
            }
        }
        return unitsAreKilled;
    }

    private void targetSelectionPhase(List<Army> armyOrder, List<Army> immuneArmyRest, List<Army> infectionArmyRest, Map<String, Army> targets) {
        for (Army attacker : armyOrder) {
            Army target = selectTarget((attacker.isGood()) ? infectionArmyRest : immuneArmyRest, attacker);
            if (target != null) {
                if (attacker.isGood()) {
                    infectionArmyRest.remove(target);
                } else {
                    immuneArmyRest.remove(target);
                }
                targets.put(attacker.toString(), target);
            }
        }
    }

    private void sortByEffectivePowerAndInitiative(List<Army> armyOrder) {
        Collections.sort(armyOrder, (a, b) -> {
            int epDiff = b.getEffectivePower() - a.getEffectivePower();
            if (epDiff != 0) {
                return epDiff;
            }
            return b.getInitiative() - a.getInitiative();
        });
    }

    private Army selectTarget(List<Army> armyRest, Army attacker) {
        List<Army> weak = armyRest.stream()//
                .filter(v -> v.getWeak().contains(attacker.getDmgType()))//
                .collect(Collectors.toList());
        if (!weak.isEmpty()) {
            return getBestTarget(weak);
        }
        List<Army> std = armyRest.stream()//
                .filter(v -> !v.getImmune().contains(attacker.getDmgType())) //
                .collect(Collectors.toList());
        if (!std.isEmpty()) {
            return getBestTarget(std);
        }

        return null;
    }

    private Army getBestTarget(List<Army> army) {
        sortByEffectivePowerAndInitiative(army);
        return army.get(0);
    }

    private void initArmies(List<String> eventData, List<Army> immuneArmy, List<Army> infectionArmy) {
        List<Army> currentArmy = null;
        boolean good = false;
        for (String data : eventData) {
            if (data.startsWith("Immune System")) {
                currentArmy = immuneArmy;
                good = true;
            } else if (data.startsWith("Infection")) {
                currentArmy = infectionArmy;
                good = false;
            } else if (data.isBlank() || data.isEmpty()) {
                // do nothing
            } else if (currentArmy != null) {
                currentArmy.add(toArmy(data, good));
            }
        }
    }

    private Army toArmy(String data, boolean good) {
        Matcher m = ARMY_PATTERN.matcher(data);
        if (m.find()) {
            int unitsCount = Integer.parseInt(m.group(1));
            int unitHitPoints = Integer.parseInt(m.group(2));
            int dgm = Integer.parseInt(m.group(4));
            Type dgmType = Type.fromString(m.group(5));
            int initiative = Integer.parseInt(m.group(6));

            List<Type> weakList = new LinkedList<>();
            List<Type> immuneList = new LinkedList<>();

            readWeakImmuneData(data, weakList, immuneList);

            return new Army(good, unitsCount, unitHitPoints, weakList, immuneList, dgm, dgmType, initiative);

        }
        throw new IllegalArgumentException(data);
    }

    private void readWeakImmuneData(String data, List<Type> weakList, List<Type> immuneList) {
        String[] propsArray = data.split("[\\(\\)]");
        if (propsArray.length == 3) {
            String[] props = propsArray[1].split("[ ;,]");
            List<Type> current = null;
            for (String prop : props) {
                if (prop.startsWith("weak")) {
                    current = weakList;
                } else if (prop.startsWith("immune")) {
                    current = immuneList;
                } else if (!prop.equals("to") && !prop.isEmpty() && !prop.isBlank()) {
                    Type testTyp = Type.fromString(prop);
                    if (testTyp != null && current != null) {
                        current.add(testTyp);
                    } else {
                        throw new IllegalArgumentException(data);
                    }
                }
            }
        }
    }

}
