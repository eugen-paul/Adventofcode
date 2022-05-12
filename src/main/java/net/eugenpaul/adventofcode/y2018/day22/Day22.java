package net.eugenpaul.adventofcode.y2018.day22;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.Direction;
import net.eugenpaul.adventofcode.helper.MapOfSimplePos;
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day22 extends SolutionTemplate {

    private enum Type {
        ROCKY('.', 0), WET('=', 1), NARROW('|', 2);

        @Getter
        private final char charValue;
        @Getter
        private final int riskLevel;

        private Type(char c, int riskLevel) {
            this.charValue = c;
            this.riskLevel = riskLevel;
        }

        public static Type fromErosionLevel(int erosionLevel) {
            switch (erosionLevel % 3) {
            case 0:
                return ROCKY;
            case 1:
                return WET;
            case 2:
                return NARROW;
            default:
                break;
            }
            throw new IllegalArgumentException();
        }

        public boolean idToolOk(Tool tool) {
            switch (this) {
            case ROCKY:
                return tool == Tool.CLIMBING || tool == Tool.TORCH;
            case WET:
                return tool == Tool.CLIMBING || tool == Tool.NEITHER;
            case NARROW:
                return tool == Tool.NEITHER || tool == Tool.TORCH;
            default:
                break;
            }

            throw new IllegalArgumentException();
        }
    }

    private enum Tool {
        TORCH, CLIMBING, NEITHER
    }

    @Data
    private class State {
        private boolean torch = false;
        private int torchCost = 0;

        private boolean climbing = false;
        private int climbingCost = 0;

        private boolean neither = false;
        private int neitherCost = 0;

        public int getCost(Tool tool) {
            switch (tool) {
            case CLIMBING:
                return climbingCost;
            case TORCH:
                return torchCost;
            case NEITHER:
                return neitherCost;
            default:
                break;
            }
            throw new IllegalArgumentException();
        }

        public void setTool(Tool tool, int cost) {
            switch (tool) {
            case CLIMBING:
                climbing = true;
                climbingCost = cost;
                break;
            case TORCH:
                torch = true;
                torchCost = cost;
                break;
            case NEITHER:
                neither = true;
                neitherCost = cost;
                break;
            default:
                break;
            }
        }

        public boolean isVisited(Tool tool) {
            switch (tool) {
            case CLIMBING:
                return climbing;
            case TORCH:
                return torch;
            case NEITHER:
                return neither;
            default:
                break;
            }
            throw new IllegalArgumentException();
        }
    }

    @AllArgsConstructor
    @Data
    private class StepData {
        private SimplePos pos;
        private Tool tool;
        private int cost;
    }

    @AllArgsConstructor
    @Data
    private class IndexData {
        private int geologicIndex;
        private int erosionLevel;
        private Type type;
    }

    @Getter
    private long riskLevel;
    @Getter
    private int cost;

    private static final int MOD = 20183;

    public static void main(String[] args) {
        Day22 puzzle = new Day22();
        puzzle.doPuzzleFromFile("y2018/day22/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        int depth = Integer.parseInt(eventData.get(0).substring(7));
        SimplePos target = new SimplePos(//
                Integer.parseInt(eventData.get(1).substring(8, eventData.get(1).indexOf(','))), //
                Integer.parseInt(eventData.get(1).substring(eventData.get(1).indexOf(',') + 1))//
        );

        var cave = computeCave(depth, target);

        if (target.getX() == 10 && target.getY() == 10) {
            MapOfSimplePos.printList(MapOfSimplePos.mapToPrintList(cave, v -> v.type.getCharValue()));
        }

        riskLevel = cave.entrySet().stream()//
                .filter(v -> v.getKey().getX() <= target.getX())//
                .filter(v -> v.getKey().getY() <= target.getY())//
                .map(v -> v.getValue().type.riskLevel)//
                .reduce(0, (a, b) -> a + b);

        cost = pathFinding(cave, target);

        logger.log(Level.INFO, () -> "lowestRegister0 : " + getRiskLevel());
        logger.log(Level.INFO, () -> "cost : " + getCost());

        return true;
    }

    private int pathFinding(Map<SimplePos, IndexData> cave, SimplePos target) {
        StepData begin = new StepData(new SimplePos(0, 0), Tool.TORCH, 0);
        LinkedList<StepData> nextSteps = new LinkedList<>();
        nextSteps.add(begin);

        Map<SimplePos, State> visitedRooms = new HashMap<>();

        int responseCost = 0;
        while (!nextSteps.isEmpty()) {
            StepData step = nextSteps.pollFirst();

            if (step.pos.equals(target) && step.tool == Tool.TORCH) {
                responseCost = step.cost;
                break;
            }

            nextSteps.addAll(getSteps(cave, visitedRooms, step));
            Collections.sort(nextSteps, (a, b) -> a.cost - b.cost);
        }

        return responseCost;
    }

    private List<StepData> getSteps(Map<SimplePos, IndexData> area, Map<SimplePos, State> visitedRooms, StepData stepData) {
        List<StepData> responseSteps = new LinkedList<>();

        State currentState = visitedRooms.computeIfAbsent(stepData.pos, k -> new State());

        if (currentState.isVisited(stepData.tool) && currentState.getCost(stepData.tool) <= stepData.cost) {
            return responseSteps;
        }

        currentState.setTool(stepData.tool, stepData.cost);

        Type currentType = area.get(stepData.pos).type;

        Tool changeTool = null;

        changeTool = canSwitchToTool(stepData, currentType, changeTool);

        boolean doChange = false;
        doChange = doToolSwitch(stepData, currentState, changeTool, doChange);

        if (doChange) {
            StepData switchToolStep = new StepData(stepData.getPos().copy(), changeTool, stepData.cost + 7);
            responseSteps.add(switchToolStep);
        }

        for (var d : Direction.values()) {
            SimplePos step = stepData.pos.moveNew(d);
            if (step.getX() < 0 || step.getY() < 0) {
                continue;
            }

            var indexData = area.get(step);
            if (indexData == null) {
                continue;
            }

            var newType = indexData.type;
            if (newType.idToolOk(stepData.tool)) {
                StepData newStep = new StepData(step, stepData.tool, stepData.cost + 1);
                responseSteps.add(newStep);
            }
        }
        return responseSteps;
    }

    private boolean doToolSwitch(StepData stepData, State currentState, Tool changeTool, boolean doChange) {
        switch (changeTool) {
        case CLIMBING:
            if (!currentState.isClimbing() || currentState.climbingCost >= stepData.cost + 7) {
                doChange = true;
            }
            break;
        case TORCH:
            if (!currentState.isTorch() || currentState.torchCost >= stepData.cost + 7) {
                doChange = true;
            }
            break;
        case NEITHER:
            if (!currentState.isNeither() || currentState.neitherCost >= stepData.cost + 7) {
                doChange = true;
            }
            break;
        default:
            break;
        }
        return doChange;
    }

    private Tool canSwitchToTool(StepData stepData, Type currentType, Tool changeTool) {
        switch (currentType) {
        case ROCKY:
            if (stepData.tool == Tool.CLIMBING) {
                changeTool = Tool.TORCH;
            } else {
                changeTool = Tool.CLIMBING;
            }
            break;
        case WET:
            if (stepData.tool == Tool.CLIMBING) {
                changeTool = Tool.NEITHER;
            } else {
                changeTool = Tool.CLIMBING;
            }
            break;
        case NARROW:
            if (stepData.tool == Tool.TORCH) {
                changeTool = Tool.NEITHER;
            } else {
                changeTool = Tool.TORCH;
            }
            break;
        default:
            break;
        }
        return changeTool;
    }

    private Map<SimplePos, IndexData> computeCave(int depth, SimplePos target) {
        Map<SimplePos, IndexData> cave = new HashMap<>();

        for (int x = 0; x <= target.getX() + 50; x++) {
            for (int y = 0; y <= target.getY() + 50; y++) {
                int geologicIndex;

                if ((x == 0 && y == 0) || (x == target.getX() && y == target.getY())) {
                    geologicIndex = 0;
                } else if (y == 0) {
                    geologicIndex = (x * 16807) % MOD;
                } else if (x == 0) {
                    geologicIndex = (y * 48271) % MOD;
                } else {
                    int geologicIndexX = cave.get(new SimplePos(x - 1, y)).getErosionLevel();
                    int geologicIndexY = cave.get(new SimplePos(x, y - 1)).getErosionLevel();
                    geologicIndex = (geologicIndexX * geologicIndexY) % MOD;
                }
                int erosionLevel = compErosionLevel(depth, geologicIndex);
                IndexData pointData = new IndexData(geologicIndex, erosionLevel, Type.fromErosionLevel(erosionLevel));

                cave.put(new SimplePos(x, y), pointData);
            }
        }

        return cave;
    }

    private int compErosionLevel(int depth, int geologicIndex) {
        return (geologicIndex + depth) % MOD;
    }

}
