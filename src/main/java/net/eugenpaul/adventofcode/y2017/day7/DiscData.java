package net.eugenpaul.adventofcode.y2017.day7;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DiscData {
    private String name;
    private int weight;
    private List<String> programs;

    public static DiscData fromString(String data) {
        String[] elements = data.split(" ");

        String name = elements[0];
        int weight = Integer.parseInt(elements[1].substring(1, elements[1].length() - 1));
        List<String> programs = new LinkedList<>();
        for (int i = 3; i < elements.length; i++) {
            programs.add(elements[i].replace(",", ""));
        }

        return new DiscData(name, weight, programs);
    }
}
