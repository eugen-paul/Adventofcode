package net.eugenpaul.adventofcode.y2018.day8;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day8 extends SolutionTemplate {

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private class Node {
        private int totalLen;
        private List<Node> children;
        private List<Integer> metadata;
    }

    @Getter
    private int metadataSum;
    @Getter
    private int rootValue;

    public static void main(String[] args) {
        Day8 puzzle = new Day8();
        puzzle.doPuzzleFromFile("y2018/day8/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        List<Integer> data = Stream.of(eventData.split(" "))//
                .map(Integer::parseInt)//
                .collect(Collectors.toCollection(ArrayList::new));

        Node root = readNode(data, 0);

        metadataSum = getMetaSum(root);
        rootValue = getNodeValue(root);

        logger.log(Level.INFO, () -> "metadataSum  : " + getMetadataSum());
        logger.log(Level.INFO, () -> "rootValue    : " + getRootValue());

        return true;
    }

    private Node readNode(List<Integer> data, int fromPosition) {
        Node response = new Node();
        int childCount = data.get(fromPosition);
        int metaDataCount = data.get(fromPosition + 1);
        int currentSize = 2;

        List<Node> children = new ArrayList<>();
        List<Integer> metadata = new ArrayList<>();

        for (int i = 0; i < childCount; i++) {
            Node child = readNode(data, fromPosition + currentSize);
            currentSize += child.getTotalLen();
            children.add(child);
        }

        for (int i = 0; i < metaDataCount; i++) {
            metadata.add(data.get(currentSize + fromPosition));
            currentSize++;
        }

        response.setTotalLen(currentSize);
        response.setChildren(children);
        response.setMetadata(metadata);

        return response;
    }

    private int getMetaSum(Node root) {
        return root.getMetadata().stream().reduce(0, (a, b) -> a + b)//
                + root.getChildren().stream().map(v -> getMetaSum(v)).reduce(0, (a, b) -> a + b);
    }

    private int getNodeValue(Node root) {
        if (root.getChildren().isEmpty()) {
            return root.getMetadata().stream().reduce(0, (a, b) -> a + b);
        }

        int currentValue = 0;
        for (Integer metaData : root.getMetadata()) {
            if (root.getChildren().size() > metaData - 1) {
                currentValue += getNodeValue(root.getChildren().get(metaData - 1));
            }
        }

        return currentValue;
    }

}
