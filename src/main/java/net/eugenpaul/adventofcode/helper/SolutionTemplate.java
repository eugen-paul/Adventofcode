package net.eugenpaul.adventofcode.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class SolutionTemplate {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = SolutionTemplate.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static Logger logger = Logger.getLogger(SolutionTemplate.class.getName());

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        return doPuzzleFromData(eventData);
    }

    public boolean doPuzzleFromData(List<String> eventData) {
        return checkData(eventData);
    }

    public boolean doPuzzleFromData(String eventData) {
        return checkData(List.of(eventData));
    }

    private boolean checkData(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        if (eventData.size() == 1) {
            return doEvent(eventData.get(0));
        } else {
            return doEvent(eventData);
        }

    }

    @SuppressWarnings("all")
    public boolean doEvent(List<String> eventData) {
        logger.log(Level.SEVERE, "!!NOT IMPLEMENTED!!");
        return false;
    }

    @SuppressWarnings("all")
    public boolean doEvent(String eventData) {
        logger.log(Level.SEVERE, "!!NOT IMPLEMENTED!!");
        return false;
    }
}
