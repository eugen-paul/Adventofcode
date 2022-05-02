package net.eugenpaul.adventofcode.y2015.day19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day19 extends SolutionTemplate {

    @Getter
    private int distinctMolecules;

    @Getter
    private int fewestNumberOfSteps;
    private Object synchObj = new Object();

    public static void main(String[] args) {
        Day19 puzzle = new Day19();
        puzzle.doPuzzleFromFile("y2015/day19/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        String molecule = eventData.get(eventData.size() - 1);

        List<Replacement> replacements = eventData.stream()//
                .filter(v -> v.contains("=>"))//
                .map(Replacement::fromString)//
                .collect(Collectors.toList());

        doPuzzle1(molecule, replacements);
        doPuzzle2(molecule, replacements);

        logger.log(Level.INFO, () -> "distinctMolecules: " + getDistinctMolecules());
        logger.log(Level.INFO, () -> "fewestNumberOfSteps: " + getFewestNumberOfSteps());
        return true;
    }

    /**
     * Code doesn't always work :(. It works for me and for most Solution, not for all Solitions. Code finds the first Solution (not the best Solution). To find
     * the best Solution this function must always call "doReduceMoleculeBruteForce". But it is to slooooooooooooooooooooooow.
     * 
     * @param molecule
     * @param replacements
     */
    private void doPuzzle2(String molecule, List<Replacement> replacements) {
        fewestNumberOfSteps = Integer.MAX_VALUE;

        Map<String, Boolean> deadEndMolecules = new HashMap<>();

        if (molecule.length() < 10) {
            doReduceMoleculeBruteForce(molecule, replacements, 0);
        } else {
            doReduceMolecule(molecule, replacements, 0, deadEndMolecules);
        }
    }

    private boolean doReduceMolecule(String generatedMolecule, List<Replacement> replacements, int step, Map<String, Boolean> deadEndMolecules) {
        if (generatedMolecule.equals("e")) {
            fewestNumberOfSteps = step;
            return true;
        }
        if (generatedMolecule.length() <= 0) {
            return false;
        }
        if (deadEndMolecules.containsKey(generatedMolecule)) {
            return false;
        }
        List<String> molecules = new ArrayList<>();
        replacements.forEach(v -> doReverseReplacements(generatedMolecule, v, molecules));

        for (String string : molecules) {
            if (doReduceMolecule(string, replacements, step + 1, deadEndMolecules)) {
                return true;
            }
            deadEndMolecules.put(string, false);
        }

        return false;
    }

    private void doReduceMoleculeBruteForce(String generatedMolecule, List<Replacement> replacements, int step) {
        if (generatedMolecule.equals("e")) {
            synchronized (synchObj) {
                if (fewestNumberOfSteps > step) {
                    fewestNumberOfSteps = step;
                }
                return;
            }
        }
        if (generatedMolecule.length() <= 0) {
            return;
        }
        List<String> molecules = new ArrayList<>();
        replacements.parallelStream().forEach(v -> doReverseReplacements(generatedMolecule, v, molecules));

        molecules.parallelStream().forEach(v -> doReduceMoleculeBruteForce(v, replacements, step + 1));
    }

    private void doPuzzle1(String molecule, List<Replacement> replacements) {
        List<String> molecules = new ArrayList<>();
        replacements.forEach(v -> doReplacements(molecule, v, molecules));
        distinctMolecules = molecules.size();
    }

    private void doReplacements(String molecule, Replacement replacement, List<String> moleculesCollector) {
        int pos = molecule.indexOf(replacement.getInput());
        while (pos >= 0) {
            String newMolecule = replaceString(molecule, replacement.getInput(), replacement.getOutput(), pos);
            if (!moleculesCollector.contains(newMolecule)) {
                moleculesCollector.add(newMolecule);
            }
            pos = molecule.indexOf(replacement.getInput(), pos + 1);
        }
    }

    private void doReverseReplacements(String molecule, Replacement replacement, List<String> moleculesCollector) {
        int pos = molecule.indexOf(replacement.getOutput());
        while (pos >= 0) {
            String newMolecule = replaceString(molecule, replacement.getOutput(), replacement.getInput(), pos);
            if (!moleculesCollector.contains(newMolecule)) {
                moleculesCollector.add(newMolecule);
            }
            pos = molecule.indexOf(replacement.getOutput(), pos + 1);
        }
    }

    private String replaceString(String molecule, String input, String output, int atIndex) {
        StringBuilder responceMolecule = new StringBuilder();
        responceMolecule.append(molecule.substring(0, atIndex));
        responceMolecule.append(output);
        responceMolecule.append(molecule.substring(atIndex + input.length(), molecule.length()));
        return responceMolecule.toString();
    }

}
