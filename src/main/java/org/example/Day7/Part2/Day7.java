package org.example.Day7.Part2;


import org.example.util.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7 {
    private static final Logger logger = new Logger();

    private static Map<Long, List<Integer>> convertInputToMap(String filePath) {
        Map<Long, List<Integer>> testValueAndConstants = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    long key = Long.parseLong(parts[0].trim());
                    String[] values = parts[1].trim().split("\\s+");

                    List<Integer> valueList = new ArrayList<>();
                    for (String value : values) {
                        valueList.add(Integer.parseInt(value));
                    }

                    testValueAndConstants.put(key, valueList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return testValueAndConstants;
    }

    public static long checkCombinations(List<Integer> values, long testValue) {
        return calculate(values, 0, values.getFirst(), testValue);
    }

    private static long calculate(List<Integer> values, int index, long currentValue, long testValue) {
        if (index == values.size() - 1) {
            return currentValue == testValue ? currentValue : 0;
        }

        long nextValue = values.get(index + 1);

        long sumResult = calculate(values, index + 1, currentValue + nextValue, testValue);
        long multiplyResult = calculate(values, index + 1, currentValue * nextValue, testValue);
        String concatString = Long.toString(currentValue) + nextValue;
        long concatResult = calculate(values, index +1, Long.parseLong(concatString), testValue);

        if (sumResult != 0) {
            return sumResult;
        } else if(concatResult != 0) {
            return concatResult;
        }
        return multiplyResult;
    }

    public static void main(String[] args) {
        Map<Long, List<Integer>> testValueAndConstants = convertInputToMap("src/main/java/org/example/Day7/input.txt");
        long total = 0;
        for (Map.Entry<Long, List<Integer>> entry : testValueAndConstants.entrySet()) {
            long testValue = entry.getKey();
            List<Integer> values = entry.getValue();
            total += checkCombinations(values, testValue);
        }
        logger.info(total);
    }
}
