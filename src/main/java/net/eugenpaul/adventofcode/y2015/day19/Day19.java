package net.eugenpaul.adventofcode.y2015.day19;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import net.eugenpaul.adventofcode.helper.FileReaderHelper;

public class Day19 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day19.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day19.class.getName());

    private int distinctMolecules;
    private int fewestNumberOfSteps;
    private Object synchObj = new Object();

    public long getDistinctMolecules() {
        return distinctMolecules;
    }

    public long getFewestNumberOfSteps() {
        return fewestNumberOfSteps;
    }

    public static void main(String[] args) {
        Day19 puzzle = new Day19();
        puzzle.doPuzzleFromFile("y2015/day19/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        if (!doEvent(eventData)) {
            logger.log(Level.INFO, () -> "Solution not found :(");
            return false;
        }

        logger.log(Level.INFO, () -> "distinctMolecules: " + getDistinctMolecules());
        logger.log(Level.INFO, () -> "fewestNumberOfSteps: " + getFewestNumberOfSteps());

        return true;
    }

    private boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        String molecule = eventData.get(eventData.size() - 1);

        List<Replacement> replacements = eventData.stream()//
                .filter(v -> v.contains("=>"))//
                .map(Replacement::fromString)//
                .collect(Collectors.toList());

        doPuzzle1(molecule, replacements);
        doPuzzle2(molecule, replacements);

        return true;
    }

    /**
     * Code doesn't always work :(. It works for me and for most Solution, not for all Solitions. Code finds the first Solution (not the best Solution). To find
     * the best Solution this function must call "doReduceMoleculeBruteForce". But it is to slooooooooooooooooooooooow.
     * 
     * @param molecule
     * @param replacements
     */
    private void doPuzzle2(String molecule, List<Replacement> replacements) {
        fewestNumberOfSteps = Integer.MAX_VALUE;

        Map<String, Boolean> deadEndMolecules = new HashMap<>();
        doReduceMolecule(molecule, replacements, 0, deadEndMolecules);
        // doReduceMoleculeBruteForce(molecule, replacements, 0);
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
