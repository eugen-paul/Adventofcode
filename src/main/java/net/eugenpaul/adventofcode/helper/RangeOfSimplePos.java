package net.eugenpaul.adventofcode.helper;

import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class RangeOfSimplePos {

    @Getter
    @Setter
    private SimplePos from;

    @Getter
    @Setter
    private SimplePos to;

    public static RangeOfSimplePos fromString(String data, String posDelimer, String coordinatesDelimer) {
        var positions = data.split(posDelimer);
        if (positions.length != 2) {
            throw new IllegalArgumentException("illegal data for RangeOfSimplePos. data: " + data + " posDelimer: " + posDelimer);
        }

        SimplePos from = SimplePos.fromData(positions[0], coordinatesDelimer);
        SimplePos to = SimplePos.fromData(positions[1], coordinatesDelimer);

        return new RangeOfSimplePos(from, to);
    }

    public boolean isHorizontalOrVertical() {
        return isHorizontal() || isVertical();
    }

    private boolean isHorizontal() {
        return from.getY() == to.getY();
    }

    private boolean isVertical() {
        return from.getX() == to.getX();
    }

    /**
     * call consumer for each point on box
     * 
     * @param to
     * @param consumer
     */
    public void forEach(Consumer<SimplePos> consumer) {
        to.forEach(from, consumer);
    }

    /**
     * call consumer for each point on box
     * 
     * @param to
     * @param consumer
     */
    public void forEachDiagonaly(Consumer<SimplePos> consumer) {
        to.forEachDiagonaly(from, consumer);
    }
}
