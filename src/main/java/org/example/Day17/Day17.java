package org.example.Day17;

import org.example.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class Day17 {
    private static final Logger logger = new Logger();

    private static int resolveOperandValue(int operand, int registerA, int registerB, int registerC) {
        return switch (operand) {
            case 0, 1, 2, 3 -> operand;
            case 4 -> registerA;
            case 5 -> registerB;
            case 6 -> registerC;
            case 7 -> throw new IllegalArgumentException("Operand 7 is reserved and invalid.");
            default -> throw new IllegalArgumentException("Unknown combo operand: " + operand);
        };
    }

    private enum Opcode {
        DIV_A_BY_2_POW,   // 0
        XOR_B_WITH_OPERAND, // 1
        B_EQUALS_OPERAND_MOD_8, // 2
        JUMP_IF_A_NOT_ZERO, // 3
        XOR_B_WITH_C, // 4
        OUTPUT_OPERAND_MOD_8, // 5
        SET_B_FROM_A_DIV_BY_2_POW, // 6
        SET_C_FROM_A_DIV_BY_2_POW  // 7
    }

    private static int executeInstruction(int opcode, int operand, int[] registers, List<Integer> output) {
        final int registerA = registers[0];
        final int registerB = registers[1];
        final int registerC = registers[2];

        final Opcode op = Opcode.values()[opcode];

        switch (op) {
            case DIV_A_BY_2_POW -> {
                int denominator = resolveOperandValue(operand, registerA, registerB, registerC);
                registers[0] = registerA / (int) Math.pow(2, denominator);
            }
            case XOR_B_WITH_OPERAND -> {
                registers[1] = registerB ^ operand;
            }
            case B_EQUALS_OPERAND_MOD_8 -> {
                int operandValue = resolveOperandValue(operand, registerA, registerB, registerC);
                registers[1] = operandValue % 8;
            }
            case JUMP_IF_A_NOT_ZERO -> {
                if (registerA != 0) {
                    return operand;
                }
            }
            case XOR_B_WITH_C -> {
                registers[1] = registerB ^ registerC;
            }
            case OUTPUT_OPERAND_MOD_8 -> {
                int operandValue = resolveOperandValue(operand, registerA, registerB, registerC);
                output.add(operandValue % 8);
            }
            case SET_B_FROM_A_DIV_BY_2_POW -> {
                int denominator = resolveOperandValue(operand, registerA, registerB, registerC);
                registers[1] = registerA / (int) Math.pow(2, denominator);
            }
            case SET_C_FROM_A_DIV_BY_2_POW -> {
                int denominator = resolveOperandValue(operand, registerA, registerB, registerC);
                registers[2] = registerA / (int) Math.pow(2, denominator);
            }
            default -> throw new IllegalArgumentException("Unknown opcode: " + opcode);
        }

        return -1;
    }

    public static void main(String[] args) {
        final int[] program = {
                2, 4, 1, 5, 7, 5, 1, 6,
                4, 2, 5, 5, 0, 3, 3, 0
        };

        final int[] registers = new int[]{33940147, 0, 0};

        final List<Integer> output = new ArrayList<>();
        int pointer = 0;

        while (pointer < program.length) {
            int opcode = program[pointer];
            int operand = program[pointer + 1];

            int jumpTarget = executeInstruction(opcode, operand, registers, output);
            if (jumpTarget != -1) {
                pointer = jumpTarget;
            } else {
                pointer += 2;
            }
        }

        logger.info(String.join(",", output.stream().map(String::valueOf).toArray(String[]::new)));
    }
}
