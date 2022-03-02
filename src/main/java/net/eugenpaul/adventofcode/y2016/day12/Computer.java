package net.eugenpaul.adventofcode.y2016.day12;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.y2016.day12.instruction.Instruction;

public class Computer {

    private Map<Character, Integer> register;
    private int position;

    // we need this for Day 23
    @Getter
    private List<Instruction> instructions;

    public Computer(List<Character> registerList, int position) {
        register = registerList.stream().collect(Collectors.toMap(v -> v, v -> 0));
        this.position = position;
        this.instructions = null;
    }

    public Computer(List<Character> registerList, int position, List<Instruction> instructions) {
        register = registerList.stream().collect(Collectors.toMap(v -> v, v -> 0));
        this.position = position;
        this.instructions = instructions;
    }

    public Integer getRegister(char register) {
        return this.register.get(register).intValue();
    }

    public void setRegister(char register, int value) {
        this.register.put(register, value);
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
