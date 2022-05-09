package net.eugenpaul.adventofcode.y2018.day19;

import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import net.eugenpaul.adventofcode.helper.DevicesOpcodes;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day19 extends SolutionTemplate {

    @Getter
    private long register0a;
    @Getter
    private long register0b;

    @Setter
    private boolean doPuzzle2 = true;

    public static void main(String[] args) {
        Day19 puzzle = new Day19();
        puzzle.doPuzzleFromFile("y2018/day19/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        register0a = doPuzzle(eventData);
        register0b = puzzle2();

        logger.log(Level.INFO, () -> "register0a : " + getRegister0a());
        logger.log(Level.INFO, () -> "register0b : " + getRegister0b());

        return true;
    }

    private long doPuzzle(List<String> eventData) {
        long[] register = { 0, 0, 0, 0, 0, 0 };
        int ipReg = Integer.parseInt(eventData.get(0).split(" ")[1]);
        int ip = (int) (register[ipReg] + 1);
        while (ip < eventData.size()) {
            register = DevicesOpcodes.execute(eventData.get(ip), register);
            register[ipReg]++;
            ip = (int) (register[ipReg] + 1);
        }
        return register[0];
    }

    /**
     * <code>
     * line instruction          clear Text                             some logic
     * 00   addi 5 16 5          R5 = R5+16                    Jump A:  R5 = R5+16
     * 01   seti 1 1 2           R2 = 1                             D:  R2 = 1
     * 02   seti 1 8 1           R1 = 1                             C:  R1 = 1
     * 03   mulr 2 1 3           R3 = R2 * R1                       B:  R3 = R2 * R1
     * 04   eqrr 3 4 3           R3 = ( R3 == R4 )? 1 : 0               if ( R3 == R4 ) THEN R3 = 1 ELSE R3 = 0
     * 05   addr 3 5 5           R5 = R3 + R5                           R5 = R3 + R5
     * 06   addi 5 1 5           R5 = R5 + 1                            R5 = R5 + 1
     * 07   addr 2 0 0           R0 = R2 + R0                           R0 = R2 + R0
     * 08   addi 1 1 1           R1 = R1 + 1                            R1++
     * 09   gtrr 1 4 3           R3 = ( R1 > R4 )? 1 : 0                if ( R1 > R4 ) THEN R3 = 1 ELSE R3 = 0
     * 10   addr 5 3 5           R5 = R5 + R3                           R5 = R5 + R3
     * 11   seti 2 6 5           R5 = 2                        Jump B:  R5 = 2 
     * 12   addi 2 1 2           R2 = R2 + 1                            R2++
     * 13   gtrr 2 4 3           R3 = ( R2 > R4 )? 1 : 0                if ( R2 > R4 ) THEN R3 = 1 ELSE R3 = 0
     * 14   addr 3 5 5           R5 = R3 + R5                           R5 = R3 + R5
     * 15   seti 1 2 5           R5 = 1                        Jump C:  R5 = 1
     * 16   mulr 5 5 5           R5 = R5 * R5                           R5 = R5 * R5 = 16 * 16 => exit
     * 17   addi 4 2 4           R4 = R4 + 2                        A:  R4 = R4 + 2                          
     * 18   mulr 4 4 4           R4 = R4 * R4                           R4 = R4 * R4
     * 19   mulr 5 4 4           R4 = R5 * R4                           R4 = R5 * R4 = 19 * R4
     * 20   muli 4 11 4          R4 = R4 * 11                           R4 = R4 * 11                         
     * 21   addi 3 2 3           R3 = R3 + 2                            R3 = R3 + 2
     * 22   mulr 3 5 3           R3 = R3 * R5                           R3 = R3 * R5 = R3 * 22
     * 23   addi 3 13 3          R3 = R3 + 13                           R3 = R3 + 13                         
     * 24   addr 4 3 4           R4 = R4 + R3                           R4 = R4 + R3                         
     * 25   addr 5 0 5           R5 = R5 + R0                           R5 = R5 + R0 = 25 + R0
     * 26   seti 0 8 5           R5 = 0                        Jump D:  R5 = 0
     * 27   setr 5 5 3           R3 = R5                                R3 = 27
     * 28   mulr 3 5 3           R3 = R3 * R5                           R3 = R3 * 28
     * 29   addr 5 3 3           R3 = R5 + R3                           R3 = 29 + R3
     * 30   mulr 5 3 3           R3 = R5 * R3                           R3 = 30 * R3
     * 31   muli 3 14 3          R3 = R3 * 14                           R3 = R3 * 14
     * 32   mulr 3 5 3           R3 = R3 * R5                           R3 = R3 * 32
     * 33   addr 4 3 4           R4 = R4 + R3                           R4 = R4 + R3
     * 34   seti 0 9 0           R0 = 0                                 R0 = 0
     * 35   seti 0 9 5           R5 = 0                                 R5 = 0
     * </code>
     * 
     * Second part (lines 17 - 35) is initialisation of program: register 4 will be set to 10_551_293 First part is the code, that will be running: <code>
     * line         some logic                                      programcode
     * 00  Jump A:  R5 = R5+16
     * 01       D:  R2 = 1                                          from R2 = 1 to R4 {
     * 02       C:  R1 = 1                                               from R1 = 1 to R4 {
     * 03       B:  R3 = R2 * R1                                            if( R1 * R2 == R4) {
     * 04           if ( R3 == R4 ) THEN R3 = 1 ELSE R3 = 0
     * 05           R5 = R3 + R5
     * 06           R5 = R5 + 1
     * 07           R0 = R2 + R0                                            R0 = R1
     * 08           R1++                                                 }
     * 09           if ( R1 > R4 ) THEN R3 = 1 ELSE R3 = 0
     * 10           R5 = R5 + R3
     * 11  Jump B:  R5 = 2                                               
     * 12           R2++                                            }
     * 13           if ( R2 > R4 ) THEN R3 = 1 ELSE R3 = 0
     * 14           R5 = R3 + R5
     * 15  Jump C:  R5 = 1
     * 16           R5 = R5 * R5 = 16 * 16 => exit
     * </code>
     * 
     * the program count the sum of all divider of 10_551_293
     */
    private long puzzle2() {
        long response = 0;
        long number = 10_551_293;
        double sqrt = Math.sqrt(number);
        for (int i = 1; i <= sqrt; i++) {
            if (number % i == 0) {
                response += i;
                if (number / i != i) {
                    response += number / i;
                }
            }
        }
        return response;
    }
}
