package net.adventofcode.y2015.day3;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import net.adventofcode.helper.FileReaderHelper;

public class Day3 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = Day3.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger(Day3.class.getName());

    private Integer visitedHouseCountSantaAlone = null;
    private Integer visitedHouseCountSantaAndRobo = null;

    public Integer getVisitedHouseCountSantaAlone() {
        return this.visitedHouseCountSantaAlone;
    }

    public Integer getVisitedHouseCountSantaAndRobo() {
        return this.visitedHouseCountSantaAndRobo;
    }

    public static void main(String[] args) {
        Day3 puzzle = new Day3();
        puzzle.doPuzzleFromFile("y2015/day3/puzzle1.txt");
    }

    public boolean doPuzzleFromFile(String filename) {
        String eventData = FileReaderHelper.readStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(String eventData) {
        if (!doEvent(eventData)) {
            return false;
        }

        logger.log(Level.INFO, () -> "Houses with at least one present (Santa alone) " + visitedHouseCountSantaAlone);
        logger.log(Level.INFO, () -> "Houses with at least one present (Santa and Robo) " + visitedHouseCountSantaAndRobo);

        return true;
    }

    private boolean doEvent(String eventData) {
        if (null == eventData) {
            return false;
        }

        CityMap citySantaAlone = new CityMap();
        doDelivery(eventData, 0, 1, citySantaAlone);
        visitedHouseCountSantaAlone = citySantaAlone.getHouseCount();

        CityMap citySantaAndRobo = new CityMap();
        doDelivery(eventData, 0, 2, citySantaAndRobo);
        doDelivery(eventData, 1, 2, citySantaAndRobo);
        visitedHouseCountSantaAndRobo = citySantaAndRobo.getHouseCount();
        return true;
    }

    private void doDelivery(String deliveryPath, int startPositon, int deliveryStep, CityMap city) {
        int positionX = 0;
        int positionY = 0;

        city.addVisitedHouse(positionX, positionY);

        for (int i = startPositon; i < deliveryPath.length(); i += deliveryStep) {
            char step = deliveryPath.charAt(i);
            switch (step) {
            case '^':
                positionY++;
                break;
            case 'v':
                positionY--;
                break;
            case '>':
                positionX++;
                break;
            case '<':
                positionX--;
                break;
            default:
            }
            city.addVisitedHouse(positionX, positionY);
        }
    }
}
