package net.eugenpaul.adventofcode.y2015.day16;

import java.util.HashMap;
import java.util.Map;

public class Aunt {

    private int number;
    private Map<String, Integer> props;

    private Aunt() {
        number = -1;
        props = new HashMap<>();
    }

    public int getNumber() {
        return this.number;
    }

    public Integer getPropValue(String propName) {
        return props.get(propName);
    }

    public static Aunt fromString(String data) {
        Aunt responseAunt = new Aunt();
        responseAunt.number = Integer.parseInt(data.substring(4, data.indexOf(":")));

        String[] elements = data.substring(data.indexOf(":") + 1).split(",");

        for (String element : elements) {
            String key = getElementKey(element);
            Integer value = getElementValue(element);
            responseAunt.props.put(key, value);
        }

        return responseAunt;
    }

    private static String getElementKey(String element) {
        return element.split(":")[0].strip();
    }

    private static Integer getElementValue(String element) {
        return Integer.parseInt(element.split(":")[1].strip());
    }

}
