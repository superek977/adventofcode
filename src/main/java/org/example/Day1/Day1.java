package org.example.Day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Day1 {
    private static final Logger logger = Logger.getLogger(Day1.class.getName());

    public List<Integer> row1 = new ArrayList<>();
    public List<Integer> row2 = new ArrayList<>();

    public Day1() {
        splitInputIntoRow("src/main/java/org/example/Day1/input.txt");
    }

    public void splitInputIntoRow(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.trim().split("\\s+");
                if (numbers.length == 2) {
                    row1.add(Integer.parseInt(numbers[0]));
                    row2.add(Integer.parseInt(numbers[1]));
                }
            }
        } catch (IOException e) {
           logger.severe("Error reading file: " + e.getMessage());
        }
        logger.info(row1.toString());
        logger.info(row2.toString());
    }

    private int calculateBothSums(int x, int y) {
        if (x > y) {
            return x - y;
        } else {
            return y - x;
        }
    }

    public List<Integer> listOfResults(List<Integer> row1, List<Integer> row2) {
        int combinedLength = row1.size() + row2.size();
        List<Integer> newList = new ArrayList<>();
        row1.sort(Integer::compareTo);
        row2.sort(Integer::compareTo);
        for (int i = 0; i < combinedLength; i++) {
            if(i < row1.size() || i < row2.size()) {
                int lowestInRow1 = row1.get(i);
                int lowestInRow2 = row2.get(i);
                int subtractedNumber = calculateBothSums(lowestInRow1, lowestInRow2);
                newList.add(subtractedNumber);
            }
        }
        return newList;
    }

    public int sumList(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).sum();
    }

    public static void main(String[] args) {
        Day1 day1 = new Day1();
        List<Integer> listOfResults = day1.listOfResults(day1.row1, day1.row2);
        int result = day1.sumList(listOfResults);
        logger.info(String.valueOf(result));
    }
}


