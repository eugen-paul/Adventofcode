package net.eugenpaul.adventofcode.y2016.day10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

public class FactoryActor {

    @Getter
    private List<Integer> microchips;
    private boolean isBot;

    @Getter
    private String low;
    @Getter
    private String high;
    @Getter
    private int botNumber;

    public FactoryActor(int botNumber, boolean isBot, String low, String high) {
        this.botNumber = botNumber;
        this.isBot = isBot;
        this.low = low;
        this.high = high;
        microchips = new ArrayList<>();
    }

    public boolean isMicrochips(int lowValue, int highValue) {
        return microchips.size() == 2 && microchips.get(0) == lowValue && microchips.get(1) == highValue;
    }

    public boolean giveMicrochip(Integer value) {
        microchips.add(value);
        Collections.sort(microchips);

        return isBot && microchips.size() >= 2;
    }

    public void giveToActor(FactoryActor actorLow, FactoryActor actorHigh, List<FactoryActor> botsWithAction) {
        if (actorLow.giveMicrochip(microchips.remove(0))) {
            botsWithAction.add(actorLow);
        }

        if (actorHigh.giveMicrochip(microchips.remove(microchips.size() - 1))) {
            botsWithAction.add(actorHigh);
        }
    }
}
