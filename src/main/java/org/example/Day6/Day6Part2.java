package org.example.Day6;


import org.example.util.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class State {
    int row;
    int col;
    char direction; // '^', 'v', '<', or '>'

    // equals() and hashCode() should be overridden
    // so we can store these in a Set<State>.
}

public class Day6Part2 {
    private static final Logger logger = new Logger();
    static char[][] grid;
    static int rows;
    static int cols;

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

    private static boolean guardGetsStuckInLoopWithNewWall(
            int obstructRow, int obstructCol,
            int startRow, int startCol,
            char startDir) {

        char originalChar = grid[obstructRow][obstructCol];

        grid[obstructRow][obstructCol] = '#';

        int row = startRow;
        int col = startCol;
        char direction = startDir;

        Set<String> visitedStates = new HashSet<>();

        visitedStates.add(row + "," + col + "," + direction);

        while (true) {
            int newRow = row;
            int newCol = col;

            switch (direction) {
                case '^': newRow = row - 1; break;
                case 'v': newRow = row + 1; break;
                case '<': newCol = col - 1; break;
                case '>': newCol = col + 1; break;
            }

            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
                grid[obstructRow][obstructCol] = originalChar;
                return false;
            }

            if (grid[newRow][newCol] == '#') {
                direction = turnRight(direction);
            } else {
                // Move forward
                row = newRow;
                col = newCol;
            }

            String state = row + "," + col + "," + direction;
            if (visitedStates.contains(state)) {
                grid[obstructRow][obstructCol] = originalChar;
                return true;
            }
            visitedStates.add(state);
        }
    }

    private static char turnRight(char direction) {
        if (direction == '^') return '>';
        if (direction == '>') return 'v';
        if (direction == 'v') return '<';
        if (direction == '<') return '^';
        // fallback
        return direction;
    }


    public static void main(String[] args) throws IOException {
        grid = readGridFromFile("src/main/java/org/example/Day6/input.txt");
        rows = grid.length;
        cols = grid[0].length;

        int[] position = findGuard(grid);
        int startRow = position[0];
        int startCol = position[1];
        char startDir = grid[startRow][startCol];

        int loopCount = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != '#' && !(r == startRow && c == startCol)) {
                    if (guardGetsStuckInLoopWithNewWall(r, c, startRow, startCol, startDir)) {
                        loopCount++;
                    }
                }
            }
        }
        logger.info("Number of positions causing a loop = " + loopCount);
    }


}
