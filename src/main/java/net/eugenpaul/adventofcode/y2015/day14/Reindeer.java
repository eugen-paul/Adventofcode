package net.eugenpaul.adventofcode.y2015.day14;

public class Reindeer {

    private String name;
    private int maxSpeed;
    private int flyTime;
    private int restTime;

    public Reindeer(String name, int maxSpeed, int flyTime, int restTime) {
        this.name = name;
        this.maxSpeed = maxSpeed;
        this.flyTime = flyTime;
        this.restTime = restTime;
    }

    public static Reindeer fromString(String data) {
        // Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
        String[] elements = data.split(" ");
        return new Reindeer(//
                elements[0], //
                Integer.parseInt(elements[3]), //
                Integer.parseInt(elements[6]), //
                Integer.parseInt(elements[13])//
        );
    }

    public String getName() {
        return this.name;
    }

    public int getMaxSpeed() {
        return this.maxSpeed;
    }

    public int getFlyTime() {
        return this.flyTime;
    }

    public int getRestTime() {
        return this.restTime;
    }

    public int computeDistance(int time) {
        int roundTime = getFlyTime() + getRestTime();
        int rounds = time / roundTime;
        int remainderTime = time % roundTime;
        if (remainderTime > getFlyTime()) {
            remainderTime = getFlyTime();
        }
        return rounds * getFlyTime() * getMaxSpeed() + remainderTime * getMaxSpeed();
    }

}
