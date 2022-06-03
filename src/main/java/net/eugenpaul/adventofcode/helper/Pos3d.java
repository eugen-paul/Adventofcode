package net.eugenpaul.adventofcode.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Pos3d {
    private long x;
    private long y;
    private long z;

    public static Pos3d fromPattern(String data, Pattern pattern) {
        Matcher m = pattern.matcher(data);
        m.find();
        return new Pos3d(//
                Integer.parseInt(m.group(1)), //
                Integer.parseInt(m.group(2)), //
                Integer.parseInt(m.group(3)) //
        );
    }

    public Pos3d add(Pos3d b) {
        x += b.x;
        y += b.y;
        z += b.z;
        return this;
    }

    public Pos3d addNew(Pos3d b) {
        return new Pos3d(//
                x + b.x, //
                y + b.y, //
                z + b.z //
        );
    }
}
