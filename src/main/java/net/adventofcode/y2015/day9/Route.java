package net.adventofcode.y2015.day9;

public class Route {
    private int distance;
    private String city1;
    private String city2;

    public Route(int distance, String city1, String city2) {
        this.distance = distance;
        this.city1 = city1;
        this.city2 = city2;
    }

    public int getDistance() {
        return distance;
    }

    public String getCity1() {
        return city1;
    }

    public String getCity2() {
        return city2;
    }

    public static Route fromString(String data) {
        String[] elements = data.split(" ");
        return new Route(Integer.parseInt(elements[4]), elements[0], elements[2]);
    }

}
