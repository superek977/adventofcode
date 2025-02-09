package org.example.Day6;


import org.example.util.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Day6 {
    private static final Logger logger = new Logger();
    static char[][] grid;
    static int rows;
    static int cols;
    private static final char UP = '^';
    private static final char DOWN = 'v';
    private static final char LEFT = '<';
    private static final char RIGHT = '>';
    static char direction = UP;
    static boolean isFinished = false;

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

    private static void printGrid() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                System.out.print(grid[r][c] + " ");
            }
            System.out.println();
        }
    }

    public static int[] findGuard(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (String.valueOf(grid[row][col]).matches("[\\^v<>]")) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    public static void moveGuard() {
        int[] position = findGuard(grid);
        if (position == null) {
            logger.info("Guard not found!");
            return;
        }

        int currentRow = position[0];
        int currentCol = position[1];
        int newRow = currentRow;
        int newCol = currentCol;

        switch (direction) {
            case UP:
                newRow = currentRow - 1;
                break;
            case DOWN:
                newRow = currentRow + 1;
                break;
            case LEFT:
                newCol = currentCol - 1;
                break;
            case RIGHT:
                newCol = currentCol + 1;
                break;
            default:
                logger.info("Invalid direction!");
                return;
        }

        if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
            logger.info("Move out of bounds! Removing the guard.");
            grid[currentRow][currentCol] = 'X'; // Remove the guard
            isFinished = true;
            return;
        }
        if (grid[newRow][newCol] == '#') {
            logger.info("Move blocked by a wall (#). Stop and turn right.");
            if(direction == UP) {
                direction = RIGHT;
            } else if (direction == RIGHT) {
                direction = DOWN;
            } else if (direction == DOWN) {
                direction = LEFT;
            } else if (direction == LEFT) {
                direction = UP;
            }
            return;
        }

        grid[currentRow][currentCol] = 'X'; // Clear the old position
        grid[newRow][newCol] = direction;  // Place guard at the new position
    }
    public static int countX() {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'X') {
                    count++;
                }
            }
        }
        return count;
    }
    public static void main(String[] args) throws IOException {
        grid = readGridFromFile("src/main/java/org/example/Day6/input.txt");
        rows = grid.length;
        cols = grid[0].length;

        while(!isFinished) {
            moveGuard();
            printGrid();
        }
        logger.info(countX());
    }
}
