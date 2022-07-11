package net.eugenpaul.adventofcode.y2020.day8;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;
import net.eugenpaul.adventofcode.helper.computer.InstructionFactory;
import net.eugenpaul.adventofcode.helper.computer.InstructionJmp;
import net.eugenpaul.adventofcode.helper.computer.InstructionNop;

public class Day8 extends SolutionTemplate {

    @Getter
    private long acc;
    @Getter
    private Long acc2;

    public static void main(String[] args) {
        Day8 puzzle = new Day8();
        puzzle.doPuzzleFromFile("y2020/day8/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<Instruction> instructions = eventData.stream()//
                .map(InstructionFactory::fromString)//
                .collect(Collectors.toList());

        // Part 1
        Computer computer = new Computer(Collections.emptyList(), 0);
        run(computer, instructions);
        acc = computer.getSpecialRegister("acc");

        // Part 2
        acc2 = doPuzzle2(eventData, i -> changeJmpToNop(instructions, i));
        if (acc2 == null) {
            acc2 = doPuzzle2(eventData, i -> changeNopToJmp(instructions, i, eventData.get(i)));
        }

        logger.log(Level.INFO, () -> "acc  : " + getAcc());
        logger.log(Level.INFO, () -> "acc2 : " + getAcc2());

        return true;
    }

    private Long doPuzzle2(List<String> eventData, IntFunction<List<Instruction>> genInstructions) {
        // try change jmp to nop
        for (int i = 0; i < eventData.size(); i++) {
            List<Instruction> nInstructions = genInstructions.apply(i);
            if (!nInstructions.isEmpty()) {
                Computer computer = new Computer(Collections.emptyList(), 0);
                int lastPos = run(computer, nInstructions);

                if (lastPos == nInstructions.size()) {
                    return computer.getSpecialRegister("acc");
                }
            }
        }
        return null;
    }

    private int run(Computer computer, List<Instruction> instructions) {
        int pos = 0;
        Set<Integer> visited = new HashSet<>();
        while (!visited.contains(pos) && pos >= 0 && pos < instructions.size()) {
            visited.add(pos);
            Instruction ins = instructions.get(pos);
            ins.doInstruction(computer);
            pos = computer.getPosition();
        }

        return computer.getPosition();
    }

    private List<Instruction> changeJmpToNop(List<Instruction> instructions, int jmpPos) {
        if (!(instructions.get(jmpPos) instanceof InstructionJmp)) {
            return Collections.emptyList();
        }
        List<Instruction> response = new LinkedList<>();
        response.addAll(instructions);
        response.set(jmpPos, new InstructionNop());
        return response;
    }

    private List<Instruction> changeNopToJmp(List<Instruction> instructions, int nopPos, String originalInstruction) {
        if (!(instructions.get(nopPos) instanceof InstructionNop)) {
            return Collections.emptyList();
        }
        List<Instruction> response = new LinkedList<>();
        response.addAll(instructions);
        response.set(nopPos, InstructionFactory.fromString("jmp " + originalInstruction.substring(4)));
        return response;
    }

}
