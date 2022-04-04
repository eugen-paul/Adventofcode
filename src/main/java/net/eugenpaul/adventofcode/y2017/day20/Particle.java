package net.eugenpaul.adventofcode.y2017.day20;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Particle {

    private static final String RULE = "p=<(\\-?\\d+),(\\-?\\d+),(\\-?\\d+)>, v=<(\\-?\\d+),(\\-?\\d+),(\\-?\\d+)>, a=<(\\-?\\d+),(\\-?\\d+),(\\-?\\d+)>";
    private static final Pattern PATTERN = Pattern.compile(RULE);

    @AllArgsConstructor
    @Data
    public static class V3 {
        private int x;
        private int y;
        private int z;

        public long abs() {
            return (long) Math.abs(x) + Math.abs(y) + Math.abs(z);
        }
    }

    private int number;
    private V3 position;
    private V3 velocity;
    private V3 acceleration;

    public static Particle fromString(String data, int number) {
        Matcher m = PATTERN.matcher(data);
        if (m.find()) {
            return new Particle(//
                    number, //
                    new V3(//
                            Integer.parseInt(m.group(1)), //
                            Integer.parseInt(m.group(2)), //
                            Integer.parseInt(m.group(3))//
                    ), //
                    new V3(//
                            Integer.parseInt(m.group(4)), //
                            Integer.parseInt(m.group(5)), //
                            Integer.parseInt(m.group(6))//
                    ), //
                    new V3(//
                            Integer.parseInt(m.group(7)), //
                            Integer.parseInt(m.group(8)), //
                            Integer.parseInt(m.group(9))//
                    )//
            );
        }
        throw new IllegalArgumentException(data);
    }

    public boolean isEqualWithoutNumber(Particle other) {
        return position.equals(other.getPosition())//
                && velocity.equals(other.getVelocity())//
                && acceleration.equals(other.getAcceleration());
    }

}
