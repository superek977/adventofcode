package org.example.Day5;


import org.example.util.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5Part2 {
    private static final Logger logger = new Logger();
    private static final List<List<Integer>> rules = new ArrayList<>();
    private static final List<List<Integer>> orders = new ArrayList<>();

    private static void convertRulesToList(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean readingPairs = true;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {

                    readingPairs = false;
                    continue;
                }

                if (readingPairs) {
                    String[] pair = line.split("\\|");
                    List<Integer> pairList = Arrays.asList(
                            Integer.parseInt(pair[0].trim()),
                            Integer.parseInt(pair[1].trim())
                    );
                    rules.add(pairList);
                } else {
                    String[] numbers = line.split(",");
                    List<Integer> numberList = new ArrayList<>();
                    for (String num : numbers) {
                        numberList.add(Integer.parseInt(num.trim()));
                    }
                    orders.add(numberList);
                }
            }


        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    private static boolean isValidOrder(List<Integer> order) {
        for (List<Integer> rule : rules) {
            int a = rule.get(0);
            int b = rule.get(1);

            if (order.contains(a) && order.contains(b)) {
                if (order.indexOf(a) >= order.indexOf(b)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        convertRulesToList("src/main/java/org/example/Day5/input.txt");

        List<List<Integer>> invalidOrders = new ArrayList<>();
        for(List<Integer> order : orders) {
            if(isValidOrder(order)) {
                invalidOrders.add(order);
            }
        }



        int wrongSum = 0;
        for(List<Integer> invalidOrder : invalidOrders) {
            int middleIndex = invalidOrder.size() / 2;
            wrongSum += invalidOrder.get(middleIndex);
        }



        logger.info(wrongSum);
    }
}
