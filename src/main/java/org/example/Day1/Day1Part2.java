package org.example.Day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Day1Part2 {
    private static final Logger logger = Logger.getLogger(Day1Part2.class.getName());

    private final List<Integer> leftList = new ArrayList<>();
    private final List<Integer> rightList = new ArrayList<>();

    public Day1Part2() {
        splitInputIntoRows();
    }

    private void splitInputIntoRows() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/org/example/Day1/input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.trim().split("\\s+");
                if (numbers.length == 2) {
                    leftList.add(Integer.parseInt(numbers[0]));
                    rightList.add(Integer.parseInt(numbers[1]));
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading file: " + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info(leftList.toString());
        logger.info(rightList.toString());
    }

    private int findHowManyTimesItAppears(int number, List<Integer> rightList) {
        int count = 0;
        for (int rightNumber : rightList) {
            if (number == rightNumber) {
                count++;
            }
        }
        return count;
    }

    public int sumList(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).sum();
    }

    public static void main(String[] args) {
        Day1Part2 day1 = new Day1Part2();
        List<Integer> listOfResults = new ArrayList<>();
        for (int number : day1.leftList) {
            int numberOfTimes = day1.findHowManyTimesItAppears(number, day1.rightList);
            int result = number * numberOfTimes;
            listOfResults.add(result);
        }
        int result = day1.sumList(listOfResults);
        logger.info(String.valueOf(result));
    }
}
