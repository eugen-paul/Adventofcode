package net.eugenpaul.adventofcode.y2018.day13;

import java.util.ArrayList;
import java.util.Arrays;
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
import net.eugenpaul.adventofcode.helper.SimplePos;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day13 extends SolutionTemplate {

    @AllArgsConstructor
    @Data
    private class Car {
        private SimplePos pos;
        private Direction dir;
        private int turns;
    }

    @Getter
    private SimplePos firstCrash;
    @Getter
    private SimplePos lastCarPos;

    public static void main(String[] args) {
        Day13 puzzle = new Day13();
        puzzle.doPuzzleFromFile("y2018/day13/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        firstCrash = doPuzzle1(eventData);

        lastCarPos = doPuzzle2(eventData);

        logger.log(Level.INFO, () -> "firstCrash : " + firstCrash.getX() + "," + firstCrash.getY());
        logger.log(Level.INFO, () -> "lastCarPos  : " + lastCarPos.getX() + "," + lastCarPos.getY());

        return true;
    }

    public SimplePos doPuzzle1(List<String> eventData) {
        return doPuzzle(eventData, false);
    }

    public SimplePos doPuzzle2(List<String> eventData) {
        return doPuzzle(eventData, true);
    }

    private SimplePos doPuzzle(List<String> eventData, boolean removeCrashed) {
        char[][] track = new char[eventData.get(0).length()][eventData.size()];
        List<Car> cars = new ArrayList<>();
        Map<SimplePos, Car> carPositions = new HashMap<>();

        initTrackAndCarsData(eventData, track, cars, carPositions);

        while (true) {
            List<Car> carsToRemove = new LinkedList<>();

            Collections.sort(cars, (a, b) -> a.getPos().getY() - b.getPos().getY());

            for (Car car : cars) {
                carPositions.remove(car.getPos());

                char road = track[car.getPos().getX()][car.getPos().getY()];
                turnCar(car, road);

                car.setPos(car.getPos().moveNew(car.getDir()));

                if (carPositions.get(car.getPos()) != null) {
                    if (!removeCrashed) {
                        return car.getPos();
                    } else {
                        carsToRemove.add(car);
                        carsToRemove.add(carPositions.get(car.getPos()));
                    }
                } else {
                    carPositions.put(car.getPos(), car);
                }
            }

            carsToRemove.stream().forEach(v -> {
                cars.remove(v);
                carPositions.remove(v.getPos());
            });

            if (cars.size() == 1) {
                return cars.get(0).getPos();
            }
        }
    }

    private void turnCar(Car car, char road) {
        switch (road) {
        case '-', '|':
            break;
        case '/', '\\':
            car.setDir(car.getDir().turnOnCurve(road));
            break;
        case '+':
            turnOnIntersection(car);
            break;
        default:
            break;
        }
    }

    private void turnOnIntersection(Car car) {
        switch (car.getTurns()) {
        case 0:
            car.setDir(car.getDir().turnLeft());
            break;
        case 2:
            car.setDir(car.getDir().turnRight());
            break;
        default:
            break;
        }
        car.setTurns(car.getTurns() + 1);
        if (car.getTurns() == 3) {
            car.setTurns(0);
        }
    }

    private void initTrackAndCarsData(List<String> eventData, char[][] track, List<Car> cars, Map<SimplePos, Car> carPositions) {
        cars.clear();
        for (char[] trackLine : track) {
            Arrays.fill(trackLine, ' ');
        }

        for (int y = 0; y < eventData.size(); y++) {
            String line = eventData.get(y);
            for (int x = 0; x < line.length(); x++) {
                switch (line.charAt(x)) {
                case '-', '|', '\\', '/', '+':
                    track[x][y] = line.charAt(x);
                    break;
                case '<':
                    track[x][y] = '-';
                    cars.add(new Car(new SimplePos(x, y), Direction.W, 0));
                    break;
                case '>':
                    track[x][y] = '-';
                    cars.add(new Car(new SimplePos(x, y), Direction.E, 0));
                    break;
                case '^':
                    track[x][y] = '|';
                    cars.add(new Car(new SimplePos(x, y), Direction.N, 0));
                    break;
                case 'v':
                    track[x][y] = '|';
                    cars.add(new Car(new SimplePos(x, y), Direction.S, 0));
                    break;
                default:
                    track[x][y] = ' ';
                    break;
                }
            }
        }

        for (Car car : cars) {
            carPositions.put(car.getPos(), car);
        }
    }
}
