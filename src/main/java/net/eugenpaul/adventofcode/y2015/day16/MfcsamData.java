package net.eugenpaul.adventofcode.y2015.day16;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MfcsamData {

    private Map<String, Integer> props;

    private MfcsamData() {
        props = new HashMap<>();
    }

    public static MfcsamData fromList(List<String> data) {
        MfcsamData responseData = new MfcsamData();

        for (String element : data) {
            String[] el = element.split(":");
            responseData.props.put(el[0], Integer.parseInt(el[1].strip()));
        }

        return responseData;
    }

    public boolean chechAunt(Aunt aunt) {
        for (Entry<String, Integer> element : props.entrySet()) {
            Integer auntPropValue = aunt.getPropValue(element.getKey());
            if (auntPropValue != null && !auntPropValue.equals(element.getValue())) {
                return false;
            }
        }
        return true;
    }

    public boolean chechAuntRanges(Aunt aunt) {
        for (Entry<String, Integer> element : props.entrySet()) {
            Integer auntPropValue = aunt.getPropValue(element.getKey());
            Integer checkValue = element.getValue();

            switch (element.getKey()) {
            case "children", "samoyeds", "akitas", "vizslas", "cars", "perfumes":
                if (auntPropValue != null && !auntPropValue.equals(checkValue)) {
                    return false;
                }
                break;
            case "cats", "trees":
                if (auntPropValue != null && auntPropValue.compareTo(checkValue) <= 0) {
                    return false;
                }
                break;
            case "pomeranians", "goldfish":
                if (auntPropValue != null && auntPropValue.compareTo(checkValue) >= 0) {
                    return false;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong property: " + element.getKey());
            }

        }
        return true;
    }
}
