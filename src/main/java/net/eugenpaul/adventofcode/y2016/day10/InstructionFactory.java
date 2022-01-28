package net.eugenpaul.adventofcode.y2016.day10;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;

public class InstructionFactory {

    private Map<String, List<Integer>> initialMap;
    private Map<String, FactoryActor> actorsMap;

    private LinkedList<FactoryActor> botsWithAction;
    private int lowValue;
    private int highValue;
    @Getter
    private int botnumber;
    @Getter
    private int multiplyValues;

    public InstructionFactory(int lowValue, int highValue) {
        this.lowValue = lowValue;
        this.highValue = highValue;
        initialMap = new HashMap<>();
        actorsMap = new HashMap<>();

        botsWithAction = new LinkedList<>();
    }

    public void readInstruction(String data) {
        String[] elements = data.split(" ");

        if (elements[0].equals("value")) {
            createInitialStep(elements);
        } else if (elements[0].equals("bot")) {
            createBots(elements);
        } else {
            throw new IllegalArgumentException("Wrong DATA: " + data);
        }
    }

    private void createBots(String[] elements) {
        String actorName = elements[0] + " " + elements[1];
        String actorLowName = elements[5] + " " + elements[6];
        String actorHighName = elements[10] + " " + elements[11];

        FactoryActor actor = new FactoryActor(Integer.parseInt(elements[1]), true, actorLowName, actorHighName);
        actorsMap.put(actorName, actor);

        // Create a bummy bot. Not all bots have instructions.
        FactoryActor actorLow = new FactoryActor(Integer.parseInt(elements[6]), elements[5].equals("bot"), null, null);
        actorsMap.computeIfAbsent(actorLowName, k -> actorLow);

        FactoryActor actorHigh = new FactoryActor(Integer.parseInt(elements[11]), elements[10].equals("bot"), null, null);
        actorsMap.computeIfAbsent(actorHighName, k -> actorHigh);

    }

    private void createInitialStep(String[] elements) {
        String botName = elements[4] + " " + elements[5];
        initialMap.compute(botName, (k, v) -> {
            if (null == v) {
                List<Integer> microchips = new LinkedList<>();
                microchips.add(Integer.parseInt(elements[1]));
                return microchips;
            }
            v.add(Integer.parseInt(elements[1]));
            return v;
        });
    }

    public void doInit() {
        for (Entry<String, List<Integer>> init : initialMap.entrySet()) {
            for (Integer microchip : init.getValue()) {
                if (actorsMap.get(init.getKey()).giveMicrochip(microchip)) {
                    botsWithAction.addLast(actorsMap.get(init.getKey()));
                }
            }
        }
    }

    public void doInstructions() {
        while (!botsWithAction.isEmpty()) {
            FactoryActor bot = botsWithAction.pop();
            if (bot.isMicrochips(lowValue, highValue)) {
                botnumber = bot.getBotNumber();
            }
            bot.giveToActor(actorsMap.get(bot.getLow()), actorsMap.get(bot.getHigh()), botsWithAction);
        }

        multiplyValues = actorsMap.get("output 0").getMicrochips().get(0) //
                * actorsMap.get("output 1").getMicrochips().get(0) //
                * actorsMap.get("output 2").getMicrochips().get(0) //
        ;
    }
}
