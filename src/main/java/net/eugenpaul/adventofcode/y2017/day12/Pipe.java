package net.eugenpaul.adventofcode.y2017.day12;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pipe {
    private Integer from;
    private List<Integer> pipes;

    public static Pipe fromString(String data) {
        String[] elements = data.split(" ");

        int fromPipe = Integer.parseInt(elements[0]);
        List<Integer> pipeList = new LinkedList<>();
        for (int i = 2; i < elements.length; i++) {
            pipeList.add(Integer.parseInt(elements[i].replace(",", "")));
        }

        return new Pipe(fromPipe, pipeList);
    }
}
