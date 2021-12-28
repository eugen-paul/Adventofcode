package net.eugenpaul.adventofcode.y2015.day6.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.eugenpaul.adventofcode.y2015.day6.ILightCommand;

public class CommandFactory {

    private static final String COORDINATE_PATTERN = " (\\d*),(\\d*) through (\\d*),(\\d*)$";
    private static final Pattern PATTERN = Pattern.compile(COORDINATE_PATTERN);

    private CommandFactory() {
    }

    public static ILightCommand getCommand(String command) {
        if (command.startsWith(CommandTurnOn.START_WITH)) {
            return genCommand(CommandTurnOn::new, command.substring(CommandTurnOn.START_WITH.length()));
        }

        if (command.startsWith(CommandTurnOff.START_WITH)) {
            return genCommand(CommandTurnOff::new, command.substring(CommandTurnOff.START_WITH.length()));
        }

        if (command.startsWith(CommandToggle.START_WITH)) {
            return genCommand(CommandToggle::new, command.substring(CommandToggle.START_WITH.length()));
        }

        return null;
    }

    private static ILightCommand genCommand(ILightCommandCreate reference, String command) {
        int fromX;
        int fromY;
        int toX;
        int toY;

        Matcher matcher = PATTERN.matcher(command);
        if (matcher.find()) {
            fromX = Integer.parseInt(matcher.group(1));
            fromY = Integer.parseInt(matcher.group(2));
            toX = Integer.parseInt(matcher.group(3));
            toY = Integer.parseInt(matcher.group(4));
            return reference.create(fromX, fromY, toX, toY);
        }
        return null;
    }
}
