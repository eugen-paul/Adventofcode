package net.adventofcode.y2015.day1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day1 {

    private Integer firstPositionOfbasement = null;

    public static void main(String[] args) {
        Day1 puzzle = new Day1();
        puzzle.doPuzzle("y2015/day1/puzzle1.txt");
    }

    public boolean doPuzzle(String filename) {
        String eventData = readDataFromFile(filename);
        if (null == eventData) {
            return false;
        }

        System.out.println("Santa on flor " + getFloor(0, eventData));
        System.out.println("Enter the basement on step " + firstPositionOfbasement);

        return true;
    }

    private String readDataFromFile(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());

        if (!file.canRead() || !file.isFile()) {
            return null;
        }

        StringBuilder responseData = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
                responseData.append(zeile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseData.toString();
    }

    private Integer getFloor(int startFloor, String data) {
        int responseFlor = startFloor;
        for (int i = 0; i < data.length(); i++) {
            char step = data.charAt(i);
            switch (step) {
                case '(':
                    responseFlor++;
                    break;
                case ')':
                    responseFlor--;
                    break;
                default:
                    return null;
            }

            if (null == firstPositionOfbasement && -1 == responseFlor) {
                firstPositionOfbasement = i + 1;
            }
        }
        return responseFlor;
    }
}
