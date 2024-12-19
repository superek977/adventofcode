package org.example.Day6;

import javax.swing.*;
import java.awt.*;


public class GridVisualizer extends JPanel {
    private char[][] grid;

    public GridVisualizer(char[][] grid) {
        this.grid = grid;
    }

    public void updateGrid(char[][] newGrid) {
        this.grid = newGrid;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = 30;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                int x = c * cellSize;
                int y = r * cellSize;

                g.drawRect(x, y, cellSize, cellSize);

                g.drawString(String.valueOf(grid[r][c]), x + cellSize / 3, y + cellSize / 2);
            }
        }
    }
}
