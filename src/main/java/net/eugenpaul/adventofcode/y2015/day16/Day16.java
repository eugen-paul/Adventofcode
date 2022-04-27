package net.eugenpaul.adventofcode.y2015.day16;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day16 extends SolutionTemplate {

    @Getter
    private int sueNumber;
    @Getter
    private int sueNumberRanges;

    @Setter
    private List<String> mfcsamData = List.of(//
            "children: 3", //
            "cats: 7", //
            "samoyeds: 2", //
            "pomeranians: 3", //
            "akitas: 0", //
            "vizslas: 0", //
            "goldfish: 5", //
            "trees: 3", //
            "cars: 2", //
            "perfumes: 1" //
    );

    public static void main(String[] args) {
        Day16 puzzle = new Day16();
        puzzle.doPuzzleFromFile("y2015/day16/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        List<Aunt> aunts = eventData.stream()//
                .map(Aunt::fromString)//
                .collect(Collectors.toList());

        MfcsamData mfcsam = MfcsamData.fromList(mfcsamData);

        sueNumber = aunts.stream().filter(mfcsam::chechAunt).findFirst().orElseThrow().getNumber();

        sueNumberRanges = aunts.stream().filter(mfcsam::chechAuntRanges).findFirst().orElseThrow().getNumber();

        logger.log(Level.INFO, () -> "sueNumber: " + getSueNumber());
        logger.log(Level.INFO, () -> "sueNumberRanges: " + getSueNumberRanges());

        return sueNumber != -1 && sueNumberRanges != -1;
    }
}
