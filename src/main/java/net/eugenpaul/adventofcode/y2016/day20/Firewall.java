package net.eugenpaul.adventofcode.y2016.day20;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class Firewall {
    private long from;
    private long to;

    public static Firewall fromString(String data) {
        String[] elements = data.split("-");
        return new Firewall(Long.parseLong(elements[0]), Long.parseLong(elements[1]));
    }
}
