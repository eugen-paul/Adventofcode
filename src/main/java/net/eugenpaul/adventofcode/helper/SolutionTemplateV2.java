package net.eugenpaul.adventofcode.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SolutionTemplateV2 {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try (InputStream is = SolutionTemplateV2.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static class MyMatch {
        Pattern pattern;
        Matcher m = null;

        public MyMatch(String format) {
            pattern = Pattern.compile(format);
        }

        public MyMatch setFormat(String format) {
            pattern = Pattern.compile(format);
            return this;
        }

        public MyMatch match(String data) {
            m = pattern.matcher(data);
            if (!m.find()) {
                throw new IllegalArgumentException();
            }
            return this;
        }

        public long asLong(int matchPos) {
            return Long.parseLong(m.group(matchPos));
        }

        public int asInt(int matchPos) {
            return Integer.parseInt(m.group(matchPos));
        }

        public String asString(int matchPos) {
            return m.group(matchPos);
        }

        public List<Integer> asIntList(int matchPos, String delimer) {
            return Arrays.stream(m.group(matchPos).split(delimer))//
                    .map(v -> Integer.parseInt(v.trim()))//
                    .toList();
        }

        public List<Long> asLongList(int matchPos, String delimer) {
            return Arrays.stream(m.group(matchPos).split(delimer))//
                    .map(v -> Long.parseLong(v.trim()))//
                    .toList();
        }

        public List<String> asStringListTrim(int matchPos, String delimer) {
            return Arrays.stream(m.group(matchPos).split(delimer))//
                    .map(String::trim)//
                    .toList();
        }

        public List<String> asStringList(int matchPos, String delimer) {
            return Arrays.stream(m.group(matchPos).split(delimer))//
                    .toList();
        }
    }

    protected static Logger logger = Logger.getLogger(SolutionTemplateV2.class.getName());

    public boolean doPuzzleFromFile(String filename) {
        List<String> eventData = FileReaderHelper.readListStringFromFile(filename);
        if (null == eventData) {
            return false;
        }

        doPuzzleFromData(eventData);
        return true;
    }

    public void doPuzzleFromData(List<String> eventData) {
        doEvent(eventData);
    }

    public void doPuzzleFromData(String eventData) {
        doEvent(List.of(eventData));
    }

    public abstract void doEvent(List<String> eventData);

    public List<Long> inputAsLongList(List<String> eventData) {
        return eventData.stream().mapToLong(Long::parseLong).boxed().toList();
    }

    public Long inputAsLong(List<String> eventData) {
        return Long.parseLong(eventData.get(0));
    }

    public List<List<Long>> inputAsListListLong(List<String> eventData) {
        List<List<Long>> response = new LinkedList<>();
        List<Long> subList = new LinkedList<>();
        for (var line : eventData) {
            if (line.isBlank()) {
                response.add(subList);
                subList = new LinkedList<>();
            } else {
                subList.add(Long.parseLong(line));
            }
        }
        if (!subList.isEmpty()) {
            response.add(subList);
        }
        return response;
    }

    public List<List<String>> inputAsListListString(List<String> eventData) {
        List<List<String>> response = new LinkedList<>();
        List<String> subList = new LinkedList<>();
        for (var line : eventData) {
            if (line.isBlank()) {
                response.add(subList);
                subList = new LinkedList<>();
            } else {
                subList.add(line);
            }
        }
        if (!subList.isEmpty()) {
            response.add(subList);
        }
        return response;
    }

    public long toLong(String data) {
        return Long.parseLong(data);
    }
}
