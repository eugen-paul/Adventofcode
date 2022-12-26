package net.eugenpaul.adventofcode.helper.computer.instruction;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.eugenpaul.adventofcode.helper.computer.Computer;
import net.eugenpaul.adventofcode.helper.computer.Instruction;

@AllArgsConstructor
public class InstructionTgl implements Instruction {

    @Getter
    private char register;

    public static InstructionTgl fromString(String data) {
        if (!data.startsWith("tgl ")) {
            throw new IllegalArgumentException("Wrong InstructionCpy: " + data);
        }
        String[] elements = data.split(" ");

        return new InstructionTgl(elements[1].charAt(0));
    }

    @Override
    public void doInstruction(Computer computer) {
        int valueRegister = computer.getRegister(register).intValue();
        int position = computer.getPosition();

        List<Instruction> instructions = computer.getInstructions();

        try {
            Instruction target = instructions.get(valueRegister + position);
            Instruction newInst = null;
            if (target instanceof InstructionInc) {
                InstructionInc oldInstr = (InstructionInc) target;
                newInst = new InstructionDec(oldInstr.getRegister());
            } else if (target instanceof InstructionDec) {
                InstructionDec oldInstr = (InstructionDec) target;
                newInst = new InstructionInc(oldInstr.getRegister());
            } else if (target instanceof InstructionTgl) {
                InstructionTgl oldInstr = (InstructionTgl) target;
                newInst = new InstructionInc(oldInstr.getRegister());
            } else if (target instanceof InstructionCpy) {
                InstructionCpy oldInstr = (InstructionCpy) target;
                if (oldInstr.getValue() == null) {
                    newInst = new InstructionJnz(oldInstr.getFrom(), oldInstr.getTo());
                } else {
                    newInst = new InstructionJnz(oldInstr.getValue(), oldInstr.getTo());
                }
            } else if (target instanceof InstructionJnz) {
                InstructionJnz oldInstr = (InstructionJnz) target;
                if (oldInstr.getCheckRegister() != null && oldInstr.getRegisterValue() != null) {
                    newInst = new InstructionCpy(oldInstr.getCheckRegister(), oldInstr.getRegisterValue());
                } else if (oldInstr.getValue() != null && oldInstr.getRegisterValue() != null) {
                    newInst = new InstructionCpy(oldInstr.getValue(), oldInstr.getRegisterValue());
                }
            }

            if (newInst != null) {
                instructions.set(valueRegister + position, newInst);
            }
        } catch (IndexOutOfBoundsException e) {
            // Ignore
        }
        computer.setPosition(computer.getPosition() + 1);
    }
}
