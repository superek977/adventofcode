package org.example.Day2;

import org.example.Day1.Day1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Day2 {
    private static final Logger logger = Logger.getLogger(Day2.class.getName());

    private Map<Integer, List<Integer>> convertInputToMap(String filePath) {
        Map<Integer, List<Integer>> reportsAndLevels = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 1;

            while ((line = br.readLine()) != null) {
                String[] numbers = line.trim().split("\\s+");

                List<Integer> numberList = new ArrayList<>();
                for (String number : numbers) {
                    numberList.add(Integer.parseInt(number));
                }

                reportsAndLevels.put(lineNumber, numberList);
                lineNumber++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<Integer, List<Integer>> entry : reportsAndLevels.entrySet()) {
            System.out.println("(" + entry.getKey() + ", " + entry.getValue() + ")");
        }

        return reportsAndLevels;
    }

    public boolean isSafe(List<Integer> list) {
        if (list.size() < 2) {
            return false;
        }

        boolean isIncreasing = true;
        boolean isDecreasing = true;


        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) >= list.get(i + 1)) {
                isIncreasing = false;
            }
            if (list.get(i) <= list.get(i + 1)) {
                isDecreasing = false;
            }
        }


        if (!isIncreasing && !isDecreasing) {
            return false;
        }


        for (int i = 0; i < list.size() - 1; i++) {
            int gap = Math.abs(list.get(i) - list.get(i + 1));
            if (gap < 1 || gap > 3) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Day2 day2 = new Day2();
        String filePath = "src/main/java/org/example/Day2/input.txt";
        Map<Integer, List<Integer>> reportsAndLevels = day2.convertInputToMap(filePath);
        int safeCounter = 0;
        for (List<Integer> value : reportsAndLevels.values()) {
            if(day2.isSafe(value)) {
                safeCounter++;
            }
        }

        logger.info(String.valueOf(safeCounter)); // 230
    }
}