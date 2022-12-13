package net.eugenpaul.adventofcode.y2022.day13;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.StringConverter;

public class Day13 extends SolutionTemplate {

    private enum Comp {
        SM, EQ, GR;
    }

    @Getter
    private long inTheRightOrder;
    @Getter
    private long decoderKey;

    public static void main(String[] args) {
        Day13 puzzle = new Day13();
        puzzle.doPuzzleFromFile("y2022/day13/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        inTheRightOrder = doPuzzle1(eventData);
        decoderKey = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "inTheRightOrder : " + getInTheRightOrder());
        logger.log(Level.INFO, () -> "decoderKey : " + getDecoderKey());

        return true;
    }

    private long doPuzzle1(List<String> eventData) {
        var sum = 0;
        var counter = 1;

        var it = eventData.iterator();
        while (it.hasNext()) {
            String left = it.next();
            String right = it.next();

            if (isRightOrder(left, right) == Comp.SM) {
                sum += counter;
            }

            if (it.hasNext()) {
                it.next();
            }
            counter++;
        }

        return sum;
    }

    private long doPuzzle2(List<String> eventData) {
        List<String> with2And6 = new LinkedList<>(eventData);
        with2And6.add("[[2]]");
        with2And6.add("[[6]]");

        List<String> data = with2And6.stream()//
                .filter(v -> !v.isBlank())//
                .sorted((a, b) -> {
                    switch (isRightOrder(a, b)) {
                    case EQ:
                        return 0;
                    case SM:
                        return -1;
                    default:
                        return 1;
                    }
                })//
                .collect(Collectors.toList());

        var response = 1;
        var counter = 1;

        for (var sortedItem : data) {
            if (sortedItem.equals("[[2]]") || sortedItem.equals("[[6]]")) {
                response *= counter;
            }
            counter++;
        }

        return response;
    }

    private Comp isRightOrder(String left, String right) {
        List<String> leftItems = extractItems(left);
        List<String> rightItems = extractItems(right);

        int leftPos = 0;
        int rigthPos = 0;

        while (leftPos < leftItems.size()) {
            if (rigthPos >= rightItems.size()) {
                // right list runs out of items
                return Comp.GR;
            }

            try {
                // if both items are numbers then compare it
                Integer a = Integer.parseInt(leftItems.get(leftPos));
                Integer b = Integer.parseInt(rightItems.get(rigthPos));
                if (a < b) {
                    return Comp.SM;
                }

                if (a > b) {
                    return Comp.GR;
                }

                leftPos++;
                rigthPos++;
                continue;
            } catch (Exception e) {
                // items are not are nnumbers.
            }

            // Compare subitems
            var compData = isRightOrder(leftItems.get(leftPos), rightItems.get(rigthPos));

            if (compData != Comp.EQ) {
                return compData;
            }

            leftPos++;
            rigthPos++;
        }

        if (leftItems.size() < rightItems.size()) {
            // left list runs out of items first
            return Comp.SM;
        }

        if (leftItems.size() > rightItems.size()) {
            // unreachable
            return Comp.GR;
        }

        return Comp.EQ;
    }

    private List<String> extractItems(String data) {
        var singleNumber = toInt(data);
        if (singleNumber != null) {
            List<String> response = new LinkedList<>();
            response.add(data);
            return response;
        }

        return StringConverter.splitStrings(data, ',', '[', ']', true);
    }

    private Integer toInt(String data) {
        try {
            return Integer.parseInt(data);
        } catch (Exception e) {
            return null;
        }
    }

}
