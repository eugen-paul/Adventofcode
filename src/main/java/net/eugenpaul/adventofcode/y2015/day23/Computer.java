package net.eugenpaul.adventofcode.y2015.day23;

public class Computer {

    private long a;
    private long b;
    private int position;

    public Computer(long a, long b, int position) {
        this.a = a;
        this.b = b;
        this.position = position;
    }

    public long getRegister(char register) {
        if (register == 'a') {
            return this.a;
        }
        if (register == 'b') {
            return this.b;
        }
        throw new IllegalArgumentException("There is no register " + register);
    }

    public void setRegister(char register, long value) {
        if (register == 'a') {
            this.a = value;
            return;
        }
        if (register == 'b') {
            this.b = value;
            return;
        }
        throw new IllegalArgumentException("There is no register " + register);
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
