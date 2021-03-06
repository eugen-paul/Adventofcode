package net.eugenpaul.adventofcode.y2016.day9;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Marker {
    int charCount;
    int repetition;
    int markerLen;

    public static Marker fromString(String text, int position) {
        int eom = text.indexOf(")", position);

        String marker = text.substring(position, eom);
        String[] dec = marker.split("x");

        int charCount = Integer.parseInt(dec[0]);
        int repetition = Integer.parseInt(dec[1]);

        return new Marker(charCount, repetition, marker.length());
    }
}
