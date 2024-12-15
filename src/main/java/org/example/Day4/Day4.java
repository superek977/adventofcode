package org.example.Day4;

import org.example.Day1.Day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Day4 {
    private static final Logger logger = Logger.getLogger(Day4.class.getName());
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

    public static int findOccurrences(char[][] grid, String word) {
        int rows = grid.length;
        int cols = grid[0].length;
        int wordLength = word.length();
        int count = 0;

        // Directions: {rowIncrement, colIncrement}
        int[][] directions = {
                {0, 1},   // Right
                {1, 0},   // Down
                {0, -1},  // Left
                {-1, 0},  // Up
                {1, 1},   // Down-right
                {1, -1},  // Down-left
                {-1, 1},  // Up-right
                {-1, -1}  // Up-left
        };

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                for (int[] dir : directions) {
                    int dr = dir[0];
                    int dc = dir[1];

                    if (isWordPresent(grid, r, c, dr, dc, word)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public static boolean isWordPresent(char[][] grid, int startRow, int startCol, int dr, int dc, String word) {
        int rows = grid.length;
        int cols = grid[0].length;
        int wordLength = word.length();

        for (int i = 0; i < wordLength; i++) {
            int newRow = startRow + i * dr;
            int newCol = startCol + i * dc;

            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols || grid[newRow][newCol] != word.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        try {
            char[][] grid = readGridFromFile("src/main/java/org/example/Day4/input.txt");
            String word = "XMAS";
            int count = findOccurrences(grid, word);
            System.out.println("The word '" + word + "' appears " + count + " times in the grid.");
        } catch (IOException e) {
            System.err.println("Error reading the grid file: " + e.getMessage());
        }
    }
}
