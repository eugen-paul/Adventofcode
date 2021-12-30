package net.eugenpaul.adventofcode.y2015.day12;

import java.util.ArrayList;
import java.util.List;

public class DocumentParser {
    private DocumentParser() {
    }

    public static DocumentObject parseData(String data) {
        DocumentObject response = new DocumentObject();

        parseData(data, 0, response);

        return response;
    }

    private static int parseData(String data, int startPosition, DocumentObject output) {
        int position = startPosition;
        int length = data.length();

        while (position < length) {
            switch (data.charAt(position)) {
            case '[':
                List<DocumentObject> list = new ArrayList<>();
                position = parseArray(data, position + 1, list);
                output.setArrays(list);
                break;
            case '{':
                List<DocumentObject> objects = new ArrayList<>();
                position = parseObjectArray(data, position + 1, objects);
                output.setObjects(objects);
                break;
            case '"':
                StringBuilder stringData = new StringBuilder();
                position = parseString(data, position + 1, stringData);
                output.setValueString(stringData.toString());
                break;
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-':
                StringBuilder digitData = new StringBuilder();
                position = parseDigit(data, position, digitData);
                output.setValueInt(Integer.parseInt(digitData.toString()));
                break;
            default:
                return position;
            }
        }

        return position;
    }

    private static int parseArray(String data, int startPosition, List<DocumentObject> output) {
        int position = startPosition;
        while (data.charAt(position) != ']') {
            DocumentObject element = new DocumentObject();
            position = parseData(data, position, element);
            output.add(element);
            if (data.charAt(position) == ',') {
                position++;
            }
        }
        return position + 1;
    }

    private static int parseObjectArray(String data, int startPosition, List<DocumentObject> outputValue) {
        int position = startPosition;
        while (data.charAt(position) != '}') {
            DocumentObject element = new DocumentObject();
            position = parseObjectElement(data, position, element);
            outputValue.add(element);
            if (data.charAt(position) == ',') {
                position++;
            }
        }
        return position + 1;
    }

    private static int parseObjectElement(String data, int startPosition, DocumentObject output) {
        StringBuilder name = new StringBuilder();
        int position = parseString(data, startPosition + 1, name) + 1;
        output.setName(name.toString());

        position = parseData(data, position, output);

        return position;
    }

    private static int parseString(String data, int startPosition, StringBuilder output) {
        int position = startPosition;
        while (data.charAt(position) != '"') {
            output.append(data.charAt(position));
            position++;
        }
        return position + 1;
    }

    private static int parseDigit(String data, int startPosition, StringBuilder output) {
        int position = startPosition;
        while (isDigid(data.charAt(position))) {
            output.append(data.charAt(position));
            position++;
        }
        return position;
    }

    private static boolean isDigid(char c) {
        return ('0' <= c && c <= '9') || c == '-';
    }
}
