package org.example.Day4;

import org.example.util.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day4Part2 {
    private static final Logger logger = new Logger();

    public static char[][] readGridFromFile(String fileName) throws IOException {
        List<char[]> gridList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                gridList.add(line.toCharArray());
            }
        }
        return gridList.toArray(new char[0][]);
    }

    public static int findXMAsPatterns(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;

        for (int r = 1; r < rows - 1; r++) {
            for (int c = 1; c < cols - 1; c++) {
                if (isXMASPattern(grid, r, c)) {
                    count++;
                }
            }
        }

        return count;
    }

    public static boolean isXMASPattern(char[][] grid, int centerRow, int centerCol) {
        // The center must be 'A'
        if (grid[centerRow][centerCol] != 'A') {
            return false;
        }

        // Check diagonals for "MAS"
        String[] masPermutations = {"MAS", "SAM"};
        for (String topLeft : masPermutations) {
            for (String bottomRight : masPermutations) {
                if (matchesDiagonals(grid, centerRow, centerCol, topLeft, bottomRight)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean matchesDiagonals(char[][] grid, int centerRow, int centerCol, String topLeft, String bottomRight) {
        // Top-left to bottom-right diagonal
        if (grid[centerRow - 1][centerCol - 1] == topLeft.charAt(0) &&
                grid[centerRow][centerCol] == topLeft.charAt(1) &&
                grid[centerRow + 1][centerCol + 1] == topLeft.charAt(2)) {
            // Top-right to bottom-left diagonal
            if (grid[centerRow - 1][centerCol + 1] == bottomRight.charAt(0) &&
                    grid[centerRow][centerCol] == bottomRight.charAt(1) &&
                    grid[centerRow + 1][centerCol - 1] == bottomRight.charAt(2)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        try {
            char[][] grid = readGridFromFile("src/main/java/org/example/Day4/input.txt");
            int count = findXMAsPatterns(grid);
            logger.info("The 'X-MAS' pattern appears " + count + " times in the grid.");
        } catch (IOException e) {
            logger.error("Error reading the grid file: " + e.getMessage());
        }
    }
}
