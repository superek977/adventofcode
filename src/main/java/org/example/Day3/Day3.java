package org.example.Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.util.Utility.sumList;

public class Day3 {
    private static final Logger logger = Logger.getLogger(Day3.class.getName());

    public static String readFile(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }
        return contentBuilder.toString();
    }

    public static Map<String, List<List<Integer>>> extractFunctions(String text) {
        Map<String, List<List<Integer>>> functionMap = new HashMap<>();

        String regex = "(\\w+)\\((\\d+),(\\d+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String functionName = matcher.group(1);
            int param1 = Integer.parseInt(matcher.group(2));
            int param2 = Integer.parseInt(matcher.group(3));
            logger.info("Found: " + functionName + "(" + param1 + "," + param2 + ")");

            if (Objects.equals(functionName, "mul")) {
                functionMap.computeIfAbsent(functionName, k -> new ArrayList<>())
                        .add(Arrays.asList(param1, param2));
            }

        }

        return functionMap;
    }


    private static void calculateAndLogTotal(Map<String, List<List<Integer>>> functionMap) {
        List<Integer> listOfSums = calculateSums(functionMap);
        int totalSum = sumList(listOfSums);
        logger.info("Total sum: " + totalSum);
    }

    private static List<Integer> calculateSums(Map<String, List<List<Integer>>> functionMap) {
        List<Integer> listOfSums = new ArrayList<>();
        for (Map.Entry<String, List<List<Integer>>> entry : functionMap.entrySet()) {
            List<List<Integer>> paramsList = entry.getValue();
            for (List<Integer> params : paramsList) {
                int result = params.get(0) * params.get(1);
                listOfSums.add(result);
            }
            System.out.println(entry.getKey() + " -> " + paramsList);
        }
        return listOfSums;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "src/main/java/org/example/Day3/input.txt";
        String fileContent = readFile(filePath);
        Map<String, List<List<Integer>>> functionMap = extractFunctions(fileContent);
        calculateAndLogTotal(functionMap);
    }
}
