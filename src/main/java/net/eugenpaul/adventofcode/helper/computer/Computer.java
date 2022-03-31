package net.eugenpaul.adventofcode.helper.computer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;

public class Computer {
    private Map<Character, Long> register;
    private Map<String, Long> specialRegister;
    private LinkedList<Long> sndQueue;
    private LinkedList<Long> rcvQueue;
    private int position;
    private long registerInitValue;

    @Getter
    private List<Instruction> instructions;

    public Computer(long registerInitValue, int position) {
        register = new HashMap<>();
        specialRegister = new HashMap<>();
        specialRegister = new HashMap<>();
        sndQueue = new LinkedList<>();
        rcvQueue = sndQueue;
        this.position = position;
        this.instructions = null;
        this.registerInitValue = registerInitValue;
    }

    public Computer(long registerInitValue, int position, LinkedList<Long> sndQueue, LinkedList<Long> rcvQueue) {
        register = new HashMap<>();
        specialRegister = new HashMap<>();
        specialRegister = new HashMap<>();
        this.sndQueue = sndQueue;
        this.rcvQueue = rcvQueue;
        this.position = position;
        this.instructions = null;
        this.registerInitValue = registerInitValue;
    }

    public Computer(List<Character> registerList, int position) {
        register = registerList.stream().collect(Collectors.toMap(v -> v, v -> 0L));
        specialRegister = new HashMap<>();
        sndQueue = new LinkedList<>();
        rcvQueue = sndQueue;
        this.position = position;
        this.instructions = null;
        this.registerInitValue = 0;
    }

    public Computer(List<Character> registerList, int position, List<Instruction> instructions) {
        register = registerList.stream().collect(Collectors.toMap(v -> v, v -> 0L));
        specialRegister = new HashMap<>();
        sndQueue = new LinkedList<>();
        rcvQueue = sndQueue;
        this.position = position;
        this.instructions = instructions;
        this.registerInitValue = 0;
    }

    public Long getRegister(char register) {
        return this.register.computeIfAbsent(register, k -> registerInitValue);
    }

    public void setRegister(char register, long value) {
        this.register.put(register, value);
    }

    public Long getSpecialRegister(String register) {
        return this.specialRegister.computeIfAbsent(register, k -> registerInitValue);
    }

    public void setSpecialRegister(String register, long value) {
        this.specialRegister.put(register, value);
    }

    public Long pop() {
        if (rcvQueue.isEmpty()) {
            return null;
        }
        return rcvQueue.remove();
    }

    public void push(long value) {
        sndQueue.add(value);
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
