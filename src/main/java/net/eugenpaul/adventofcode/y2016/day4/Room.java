package net.eugenpaul.adventofcode.y2016.day4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class Room {

    private static final String REGEX = "([a-z\\-]*)\\-(\\d+)\\[([a-z]*)\\]$";
    private static final Pattern pattern = Pattern.compile(REGEX);

    private String encryptedName;
    private String decryptedName;
    private int id;
    private String checksum;

    private Boolean real;

    public Room(String encryptedName, int id, String checksum) {
        this.encryptedName = encryptedName;
        this.decryptedName = null;
        this.id = id;
        this.checksum = checksum;
        this.real = null;
    }

    public static Room fromString(String data) {
        Matcher matcher = pattern.matcher(data);
        if (!matcher.find() || matcher.groupCount() != 3) {
            return null;
        }

        return new Room(//
                matcher.group(1), //
                Integer.parseInt(matcher.group(2)), //
                matcher.group(3) //
        );
    }

    public boolean isReal() {
        if (null != real) {
            return real.booleanValue();
        }

        checkRoom();

        return real;
    }

    private void checkRoom() {
        Map<Character, Integer> charCounter = new HashMap<>();

        // count chars
        for (int i = 0; i < encryptedName.length(); i++) {
            if (encryptedName.charAt(i) == '-') {
                continue;
            }
            charCounter.compute(encryptedName.charAt(i), (k, v) -> (v == null) ? 1 : v + 1);
        }

        real = checksum.equals(computeCheckSum(charCounter));
    }

    private String computeCheckSum(Map<Character, Integer> charCounter) {
        List<Map.Entry<Character, Integer>> sortByValueReverse = charCounter.entrySet()//
                .stream()//
                .sorted(Map.Entry.comparingByValue((a, b) -> b - a))//
                .collect(Collectors.toList());

        int lastCharCounter = Integer.MAX_VALUE;
        StringBuilder responseChecksum = new StringBuilder();

        List<Character> sameCountCharacters = new LinkedList<>();

        for (Map.Entry<Character, Integer> entry : sortByValueReverse) {
            if (lastCharCounter == entry.getValue()) {
                sameCountCharacters.add(entry.getKey());
                continue;
            }
            lastCharCounter = entry.getValue();
            sameCountCharacters.stream().sorted().forEach(responseChecksum::append);
            sameCountCharacters.clear();
            sameCountCharacters.add(entry.getKey());
        }
        sameCountCharacters.stream().sorted().forEach(responseChecksum::append);

        return responseChecksum.toString().substring(0, 5);
    }

    public String getDecryptedName() {
        if (decryptedName != null) {
            return decryptedName;
        }

        decryptName();

        return decryptedName;
    }

    private void decryptName() {
        int shift = id % 26;
        StringBuilder responseName = new StringBuilder();
        for (int i = 0; i < encryptedName.length(); i++) {
            if (encryptedName.charAt(i) == '-') {
                responseName.append(" ");
                continue;
            }
            char c = (char) (encryptedName.charAt(i) + shift);
            if (c > 'z') {
                c = (char) (c - 26);
            }
            responseName.append(c);
        }

        decryptedName = responseName.toString();
    }

}
