package net.eugenpaul.adventofcode.y2024.day4;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day4 extends SolutionTemplate {

    @Getter
    private long totalScore;
    @Getter
    private long totalScore2;

    public static void main(String[] args) {
        Day4 puzzle = new Day4();
        puzzle.doPuzzleFromFile("y2024/day4/puzzle1.txt");
    }

    @Override
    public boolean doEvent(String eventData) {
        return doEvent(List.of(eventData));
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        totalScore = doPuzzle1(eventData);
        totalScore2 = doPuzzle2(eventData);
        return true;
    }

    public long doPuzzle1(List<String> eventData) {
        long response = 0;

        for(int lineIndex = 0; lineIndex < eventData.size(); lineIndex++) {
            String line = eventData.get(lineIndex);
            for(int colIndex = 0; colIndex < line.length(); colIndex++) {
                char ch = line.charAt(colIndex);
                if(ch  == 'X'){
                    if(isLeftToRight(eventData, lineIndex, colIndex)) {
                        response++;
                    }
                    if(isRightToLeft(eventData, lineIndex,  colIndex)) {
                        response++;
                    }
                    if(isUpToDown(eventData, lineIndex,  colIndex)) {
                        response++;
                    }
                    if(isDownToUp(eventData, lineIndex,  colIndex)) {
                        response++;
                    }
                    if(isDiagonalLeftToRightUp(eventData, lineIndex,  colIndex)) {
                        response++;
                    }
                    if(isDiagonalLeftToRightDown(eventData, lineIndex,  colIndex)) {
                        response++;
                    }
                    if(isDiagonalRightToLeftUp(eventData, lineIndex,  colIndex)) {
                        response++;
                    }
                    if(isDiagonalRightToLeftDown(eventData, lineIndex,  colIndex)) {
                        response++;
                    }
                }
            }
        }

        logger.log(Level.INFO, "Solution 1 " + response);
        return response;
    }

    private boolean isLeftToRight(List<String> eventData, int lineIndex, int colIndex){
        try{
            return eventData.get(lineIndex).charAt(colIndex)     == 'X'
                && eventData.get(lineIndex).charAt(colIndex + 1) == 'M'
                && eventData.get(lineIndex).charAt(colIndex + 2) == 'A'
                && eventData.get(lineIndex).charAt(colIndex + 3) == 'S';
        } catch (Exception e){
            return false;
        }
    }

    private boolean isRightToLeft(List<String> eventData, int lineIndex, int colIndex){
        try{
            return eventData.get(lineIndex).charAt(colIndex)     == 'X'
                && eventData.get(lineIndex).charAt(colIndex - 1) == 'M'
                && eventData.get(lineIndex).charAt(colIndex - 2) == 'A'
                && eventData.get(lineIndex).charAt(colIndex - 3) == 'S';
        } catch (Exception e){
            return false;
        }
    }

    private boolean isUpToDown(List<String> eventData, int lineIndex, int colIndex){
        try{
            return eventData.get(lineIndex).charAt(colIndex)   == 'X'
                && eventData.get(lineIndex+1).charAt(colIndex) == 'M'
                && eventData.get(lineIndex+2).charAt(colIndex) == 'A'
                && eventData.get(lineIndex+3).charAt(colIndex) == 'S';
        } catch (Exception e){
            return false;
        }
    }

    private boolean isDownToUp(List<String> eventData, int lineIndex, int colIndex){
        try{
            return eventData.get(lineIndex).charAt(colIndex)   == 'X'
                && eventData.get(lineIndex-1).charAt(colIndex) == 'M'
                && eventData.get(lineIndex-2).charAt(colIndex) == 'A'
                && eventData.get(lineIndex-3).charAt(colIndex) == 'S';
        } catch (Exception e){
            return false;
        }
    }

    private boolean isDiagonalLeftToRightUp(List<String> eventData, int lineIndex, int colIndex){
        try{
            return eventData.get(lineIndex).charAt(colIndex)   == 'X'
                && eventData.get(lineIndex-1).charAt(colIndex+1) == 'M'
                && eventData.get(lineIndex-2).charAt(colIndex+2) == 'A'
                && eventData.get(lineIndex-3).charAt(colIndex+3) == 'S';
        } catch (Exception e){
            return false;
        }
    }

    private boolean isDiagonalLeftToRightDown(List<String> eventData, int lineIndex, int colIndex){
        try{
            return eventData.get(lineIndex).charAt(colIndex)   == 'X'
                && eventData.get(lineIndex+1).charAt(colIndex+1) == 'M'
                && eventData.get(lineIndex+2).charAt(colIndex+2) == 'A'
                && eventData.get(lineIndex+3).charAt(colIndex+3) == 'S';
        } catch (Exception e){
            return false;
        }
    }

    private boolean isDiagonalRightToLeftUp(List<String> eventData, int lineIndex, int colIndex){
        try{
            return eventData.get(lineIndex).charAt(colIndex)   == 'X'
                && eventData.get(lineIndex-1).charAt(colIndex-1) == 'M'
                && eventData.get(lineIndex-2).charAt(colIndex-2) == 'A'
                && eventData.get(lineIndex-3).charAt(colIndex-3) == 'S';
        } catch (Exception e){
            return false;
        }
    }

    private boolean isDiagonalRightToLeftDown(List<String> eventData, int lineIndex, int colIndex){
        try{
            return eventData.get(lineIndex).charAt(colIndex)   == 'X'
                && eventData.get(lineIndex+1).charAt(colIndex-1) == 'M'
                && eventData.get(lineIndex+2).charAt(colIndex-2) == 'A'
                && eventData.get(lineIndex+3).charAt(colIndex-3) == 'S';
        } catch (Exception e){
            return false;
        }
    }

    public long doPuzzle2(List<String> eventData) {
        long response = 0;

            for(int lineIndex = 0; lineIndex < eventData.size(); lineIndex++) {
            String line = eventData.get(lineIndex);
            for(int colIndex = 0; colIndex < line.length(); colIndex++) {
                char ch = line.charAt(colIndex);
                if(ch  == 'A'){
                    if(isMMSS(eventData, lineIndex, colIndex)) {
                        response++;
                    }
                }
            }
        }

        logger.log(Level.INFO, "Solution 2 " + response);
        return response;
    }

    private boolean isMMSS(List<String> eventData, int lineIndex, int colIndex){
        boolean result = false;
        try{
            result = eventData.get(lineIndex-1).charAt(colIndex-1) == 'M'
                  && eventData.get(lineIndex-1).charAt(colIndex+1) == 'M'
                  && eventData.get(lineIndex+1).charAt(colIndex+1) == 'S'
                  && eventData.get(lineIndex+1).charAt(colIndex-1) == 'S';
        } catch (Exception e){
            return false;
        }

        if(result) {
            return true;
        }

        try{
            result = eventData.get(lineIndex-1).charAt(colIndex-1) == 'S'
                  && eventData.get(lineIndex-1).charAt(colIndex+1) == 'M'
                  && eventData.get(lineIndex+1).charAt(colIndex+1) == 'M'
                  && eventData.get(lineIndex+1).charAt(colIndex-1) == 'S';
        } catch (Exception e){
            return false;
        }

        if(result) {
            return true;
        }

        try{
            result = eventData.get(lineIndex-1).charAt(colIndex-1) == 'S'
                  && eventData.get(lineIndex-1).charAt(colIndex+1) == 'S'
                  && eventData.get(lineIndex+1).charAt(colIndex+1) == 'M'
                  && eventData.get(lineIndex+1).charAt(colIndex-1) == 'M';
        } catch (Exception e){
            return false;
        }

        if(result) {
            return true;
        }

        try{
            result = eventData.get(lineIndex-1).charAt(colIndex-1) == 'M'
                  && eventData.get(lineIndex-1).charAt(colIndex+1) == 'S'
                  && eventData.get(lineIndex+1).charAt(colIndex+1) == 'S'
                  && eventData.get(lineIndex+1).charAt(colIndex-1) == 'M';
        } catch (Exception e){
            return false;
        }

        return result;
    }

}
