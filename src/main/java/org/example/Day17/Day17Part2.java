package org.example.Day17;

import org.example.util.Logger;

import java.util.HashSet;
import java.util.Set;

public class Day17Part2 {
    private static final Logger logger = new Logger();

    private static final int[] PROGRAM = {2,4,1,5,7,5,1,6,4,2,5,5,0,3,3,0};
    private static final int PROGRAM_LENGTH = PROGRAM.length;

    private static final int MODULO = 8;
    private static final int XOR_FIVE = 5;
    private static final int XOR_SIX = 6;

    public static void main(String[] args) {
        Set<Long> possibleValues = initializePossibleValuesFromLastInstruction();
        if (possibleValues.isEmpty()) {
            logger.info("No solution found from the final output constraint.");
            return;
        }

        for (int i = PROGRAM_LENGTH - 2; i >= 0; i--) {
            int desiredOut = PROGRAM[i];
            possibleValues = backtrackOneStep(possibleValues, desiredOut);

            if (possibleValues.isEmpty()) {
                logger.info("No solution found.");
                return;
            }
        }

        long answer = findSmallestPositiveValue(possibleValues);

        if (answer == Long.MAX_VALUE) {
            logger.info("No positive solution found.");
        } else {
            logger.info("Lowest positive A: " + answer);
        }
    }

    private static Set<Long> initializePossibleValuesFromLastInstruction() {
        Set<Long> possible = new HashSet<>();
        int finalDesiredOutput = PROGRAM[PROGRAM_LENGTH - 1];
        for (long candidateA = 0; candidateA < MODULO; candidateA++) {
            if (producesDesiredOutput(candidateA, finalDesiredOutput)) {
                possible.add(candidateA);
            }
        }
        return possible;
    }

    private static Set<Long> backtrackOneStep(Set<Long> nextStepValues, int desiredOut) {
        Set<Long> newPossible = new HashSet<>();
        for (long aNext : nextStepValues) {
            long base = aNext * MODULO;
            for (int remainder = 0; remainder < MODULO; remainder++) {
                long aCandidate = base + remainder;
                if (producesDesiredOutput(aCandidate, desiredOut)) {
                    newPossible.add(aCandidate);
                }
            }
        }
        return newPossible;
    }

    private static long findSmallestPositiveValue(Set<Long> values) {
        long answer = Long.MAX_VALUE;
        for (long a : values) {
            if (a > 0 && a < answer) {
                answer = a;
            }
        }
        return answer;
    }


    private static boolean producesDesiredOutput(long A, int desiredOut) {
        long B = A % MODULO;
        B = B ^ XOR_FIVE;
        long exp = B;
        long divisor = 1L << exp;
        long C = A / divisor;

        long B_step4 = ((A % MODULO) ^ XOR_FIVE) ^ XOR_SIX;
        B = B_step4 ^ C;

        long out = B % MODULO;
        return out == desiredOut;
    }
}
