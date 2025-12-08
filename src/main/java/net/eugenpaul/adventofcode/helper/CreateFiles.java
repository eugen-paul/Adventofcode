package net.eugenpaul.adventofcode.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class CreateFiles {
    public static void main(String[] args) throws IOException {
        System.out.print("Enter year:");
        int year = Integer.parseInt(System.console().readLine());

        System.out.print("Enter day:");
        int day = Integer.parseInt(System.console().readLine());

        createFiles(year, day);
    }

    private static void createFiles(int year, int day) throws IOException, FileNotFoundException {
        String daySrc = Files.readString(Path.of("template\\DayX.java.template"));
        daySrc = daySrc.replace("#YEAR#", year + "").replace("#DAY#", day + "");
        new File("src\\main\\java\\net\\eugenpaul\\adventofcode\\y" + year + "\\day" + day).mkdirs();
        PrintWriter out = new PrintWriter("src\\main\\java\\net\\eugenpaul\\adventofcode\\y" + year + "\\day" + day + "\\Day" + day + ".java");
        out.write(daySrc);
        out.close();

        PrintWriter outRM = new PrintWriter("src\\main\\java\\net\\eugenpaul\\adventofcode\\y" + year + "\\day" + day + "\\README.md");
        outRM.write("[Advent of Code Year " + year + " Day " + day + "](https://adventofcode.com/" + year + "/day/" + day + ")");
        outRM.close();

        new File("src\\main\\resources\\y" + year + "\\day" + day).mkdirs();
        PrintWriter outRes = new PrintWriter("src\\main\\resources\\y" + year + "\\day" + day + "\\puzzle1.txt");
        outRes.close();

        String testDay = Files.readString(Path.of("template\\DayXTest.java.template"));
        testDay = testDay.replace("#YEAR#", year + "").replace("#DAY#", day + "");
        new File("src\\test\\java\\net\\eugenpaul\\adventofcode\\y" + year + "\\day" + day).mkdirs();
        PrintWriter outTest = new PrintWriter("src\\test\\java\\net\\eugenpaul\\adventofcode\\y" + year + "\\day" + day + "\\Day" + day + "Test.java");
        outTest.write(testDay);
        outTest.close();

        new File("src\\test\\resources\\y" + year + "\\day" + day).mkdirs();
        PrintWriter outTestRes = new PrintWriter("src\\test\\resources\\y" + year + "\\day" + day + "\\test_puzzle.txt");
        outTestRes.close();
    }
}
