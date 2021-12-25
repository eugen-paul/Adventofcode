package net.adventofcode.y2015.day7;

import java.util.ArrayList;
import java.util.List;

import net.adventofcode.y2015.day7.signal.AndSignal;
import net.adventofcode.y2015.day7.signal.LshiftSignal;
import net.adventofcode.y2015.day7.signal.NotSignal;
import net.adventofcode.y2015.day7.signal.OrSignal;
import net.adventofcode.y2015.day7.signal.RshiftSignal;
import net.adventofcode.y2015.day7.signal.ValueSignal;
import net.adventofcode.y2015.day7.signal.WireSignal;

public class SignalFactory {
    private enum SignalType {
        AND, OR, LSHIFT, RSHIFT, NOT, VALUE_OR_WIRE
    }

    private SignalFactory() {
    }

    public static ISourceSignal parseSignal(String data) {
        String[] params = data.split(" ");
        List<String> wireList = new ArrayList<>();
        List<Short> digitList = new ArrayList<>();
        boolean outputFound = false;
        String outputWirename = null;
        SignalType signalType = SignalType.VALUE_OR_WIRE;

        for (String param : params) {
            Short digit = isDigit(param);
            SignalType type = isType(param);
            if (outputFound) {
                outputWirename = param;
            } else if (null != digit) {
                digitList.add(digit);
            } else if (isOutput(param)) {
                outputFound = true;
            } else if (null != type) {
                signalType = type;
            } else {
                wireList.add(param);
            }
        }

        if (null == outputWirename) {
            throw new IllegalArgumentException("Wrong inputdata: " + data + ". OutputWirename not found.");
        }

        ISourceSignal response = genSignalFromPasredData(wireList, digitList, outputWirename, signalType);
        if (null == response) {
            throw new IllegalArgumentException("Wrong inputdata: " + data + ". Unknown signalType not found.");
        }

        return response;
    }

    private static ISourceSignal genSignalFromPasredData(List<String> wireList, List<Short> digitList, String outputWirename, SignalType signalType) {
        switch (signalType) {
        case AND:
            return genAndSignal(wireList, digitList, outputWirename);
        case OR:
            return genOrSignal(wireList, digitList, outputWirename);
        case RSHIFT:
            return genRShiftSignal(wireList, digitList, outputWirename);
        case LSHIFT:
            return genLShiftSignal(wireList, digitList, outputWirename);
        case NOT:
            return genNotSignal(wireList, digitList, outputWirename);
        case VALUE_OR_WIRE:
            return genValueOrWireSignal(wireList, digitList, outputWirename);
        }

        return null;
    }

    private static ISourceSignal genValueOrWireSignal(List<String> wireList, List<Short> digitList, String outputWirename) {
        if (wireList.size() + digitList.size() != 1) {
            return null;
        }
        if (wireList.size() == 1) {
            return new WireSignal(wireList.get(0), outputWirename);
        }
        return new ValueSignal(digitList.get(0), outputWirename);
    }

    private static ISourceSignal genNotSignal(List<String> wireList, List<Short> digitList, String outputWirename) {
        if (wireList.size() + digitList.size() != 1) {
            return null;
        }
        return new NotSignal(wireList.get(0), outputWirename);
    }

    private static ISourceSignal genLShiftSignal(List<String> wireList, List<Short> digitList, String outputWirename) {
        if (wireList.size() + digitList.size() != 2) {
            return null;
        }
        return new LshiftSignal(wireList.get(0), digitList.get(0), outputWirename);
    }

    private static ISourceSignal genRShiftSignal(List<String> wireList, List<Short> digitList, String outputWirename) {
        if (wireList.size() + digitList.size() != 2) {
            return null;
        }
        return new RshiftSignal(wireList.get(0), digitList.get(0), outputWirename);
    }

    private static ISourceSignal genOrSignal(List<String> wireList, List<Short> digitList, String outputWirename) {
        if (wireList.size() + digitList.size() != 2) {
            return null;
        }
        if (wireList.size() == 2) {
            return new OrSignal(wireList.get(0), wireList.get(1), outputWirename);
        }
        if (wireList.size() == 1) {
            return new OrSignal(wireList.get(0), digitList.get(0), outputWirename);
        }
        return new OrSignal(digitList.get(0), digitList.get(1), outputWirename);
    }

    private static ISourceSignal genAndSignal(List<String> wireList, List<Short> digitList, String outputWirename) {
        if (wireList.size() + digitList.size() != 2) {
            return null;
        }
        if (wireList.size() == 2) {
            return new AndSignal(wireList.get(0), wireList.get(1), outputWirename);
        }
        if (wireList.size() == 1) {
            return new AndSignal(wireList.get(0), digitList.get(0), outputWirename);
        }
        return new AndSignal(digitList.get(0), digitList.get(1), outputWirename);
    }

    private static Short isDigit(String data) {
        try {
            return (short) Integer.parseInt(data);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static boolean isOutput(String data) {
        return data.equals("->");
    }

    private static SignalType isType(String data) {
        if (data.equalsIgnoreCase("AND")) {
            return SignalType.AND;
        }
        if (data.equalsIgnoreCase("OR")) {
            return SignalType.OR;
        }
        if (data.equalsIgnoreCase("LSHIFT")) {
            return SignalType.LSHIFT;
        }
        if (data.equalsIgnoreCase("RSHIFT")) {
            return SignalType.RSHIFT;
        }
        if (data.equalsIgnoreCase("NOT")) {
            return SignalType.NOT;
        }
        return null;
    }

}
