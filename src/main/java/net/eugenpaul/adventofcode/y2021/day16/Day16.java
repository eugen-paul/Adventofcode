package net.eugenpaul.adventofcode.y2021.day16;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day16 extends SolutionTemplate {

    private class Packet {
        private int version;
        private int id;
        private Long value;
        private List<Packet> subpacket;
    }

    @Getter
    private long versionssum;
    @Getter
    private long solution2;

    public static void main(String[] args) {
        Day16 puzzle = new Day16();
        puzzle.doPuzzleFromFile("y2021/day16/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {

        String binary = toBinary(eventData);

        var packets = decodedTransmission(binary);

        versionssum = doPuzzle1(packets);
        solution2 = evaluateExpression(packets);

        logger.log(Level.INFO, () -> "versionssum   : " + getVersionssum());
        logger.log(Level.INFO, () -> "solution2     : " + getSolution2());

        return true;
    }

    private int doPuzzle1(Packet packet) {
        return packet.version + getVersionSum(packet.subpacket);
    }

    private int getVersionSum(List<Packet> packets) {
        if (packets == null) {
            return 0;
        }

        int response = 0;

        for (var packet : packets) {
            response += packet.version;
            response += getVersionSum(packet.subpacket);
        }

        return response;
    }

    private long evaluateExpression(Packet packet) {
        long currentValue = 0;

        switch (packet.id) {
        case 0:
            currentValue = packet.subpacket.stream().mapToLong(this::evaluateExpression).sum();
            break;
        case 1:
            currentValue = packet.subpacket.stream().mapToLong(this::evaluateExpression).reduce(1L, (a, b) -> a * b);
            break;
        case 2:
            currentValue = packet.subpacket.stream().mapToLong(this::evaluateExpression).reduce(Long.MAX_VALUE, Math::min);
            break;
        case 3:
            currentValue = packet.subpacket.stream().mapToLong(this::evaluateExpression).reduce(Long.MIN_VALUE, Math::max);
            break;
        case 4:
            currentValue = packet.value;
            break;
        case 5:
            currentValue = (evaluateExpression(packet.subpacket.get(0)) > evaluateExpression(packet.subpacket.get(1))) ? 1L : 0L;
            break;
        case 6:
            currentValue = (evaluateExpression(packet.subpacket.get(0)) < evaluateExpression(packet.subpacket.get(1))) ? 1L : 0L;
            break;
        case 7:
            currentValue = (evaluateExpression(packet.subpacket.get(0)) == evaluateExpression(packet.subpacket.get(1))) ? 1L : 0L;
            break;
        default:
            break;
        }

        return currentValue;
    }

    private Packet decodedTransmission(String binary) {
        List<Packet> response = new LinkedList<>();
        readData(binary, 0, response);
        return response.get(0);
    }

    private int readData(String binary, int startPos, List<Packet> packets) {

        int version = read3Bits(binary, startPos);
        int id = read3Bits(binary, startPos + 3);

        Packet p = new Packet();
        p.version = version;
        p.id = id;

        int endPos;

        if (id == 4) {
            List<Long> literals = new LinkedList<>();
            endPos = readLiterals(binary, startPos + 6, literals);
            p.value = literals.get(0);
        } else {
            List<Packet> subPackets = new LinkedList<>();
            p.subpacket = subPackets;
            endPos = readSubPackets(binary, startPos + 6, subPackets);
        }

        packets.add(p);

        return endPos;
    }

    private int readSubPackets(String binary, int startPos, List<Packet> packets) {

        char lengthTypeId = binary.charAt(startPos);

        if (lengthTypeId == '0') {
            int lenOfSubs = read15Bits(binary, startPos + 1);
            int currentPos = startPos + 1 + 15;
            while (currentPos < startPos + 1 + 15 + lenOfSubs) {
                currentPos = readData(binary, currentPos, packets);
            }
            return currentPos;
        }

        int numberOfSubs = read11Bits(binary, startPos + 1);
        int currentPos = startPos + 1 + 11;
        for (int i = 0; i < numberOfSubs; i++) {
            currentPos = readData(binary, currentPos, packets);
        }
        return currentPos;
    }

    private int readLiterals(String binary, int pos, List<Long> literals) {
        int currentPos = pos;

        StringBuilder response = new StringBuilder(64);

        boolean last = false;
        while (!last) {
            response.append(binary.charAt(currentPos + 1));
            response.append(binary.charAt(currentPos + 2));
            response.append(binary.charAt(currentPos + 3));
            response.append(binary.charAt(currentPos + 4));
            last = binary.charAt(currentPos) == '0';
            currentPos += 5;
        }
        literals.add(Long.parseLong(response.toString(), 2));

        return currentPos;
    }

    private int read3Bits(String binary, int pos) {
        return readXBits(binary, pos, 3);
    }

    private int read11Bits(String binary, int pos) {
        return readXBits(binary, pos, 11);
    }

    private int read15Bits(String binary, int pos) {
        return readXBits(binary, pos, 15);
    }

    private int readXBits(String binary, int pos, int length) {
        StringBuilder version = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            version.append(binary.charAt(pos + i));
        }

        return Integer.parseInt(version.toString(), 2);
    }

    private String toBinary(String eventData) {
        StringBuilder response = new StringBuilder(eventData.length() * 4);
        for (var c : eventData.toCharArray()) {
            response.append(String.format("%4s", Integer.toBinaryString(Integer.parseInt(c + "", 16))));
        }
        return response.toString().replace(" ", "0");
    }
}
