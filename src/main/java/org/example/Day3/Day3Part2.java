package org.example.Day3;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day3Part2 {
    private static final Logger logger = Logger.getLogger(Day3Part2.class.getName());

    public static String readFile(String filePath) throws IOException {
        return Day3.readFile(filePath);
    }

    public static int processInstructions(String text) {
        Pattern pattern = Pattern.compile(
                "(do\\(\\)|don't\\(\\)|mul\\((\\d+),(\\d+)\\))"
        );
        Matcher matcher = pattern.matcher(text);

        boolean mulEnabled = true;
        int totalSum = 0;

        while (matcher.find()) {
            String token = matcher.group(1);


            if (token.equals("do()")) {
                mulEnabled = true;

            } else if (token.equals("don't()")) {
                mulEnabled = false;

            } else {
                int param1 = Integer.parseInt(matcher.group(2));
                int param2 = Integer.parseInt(matcher.group(3));

                if (mulEnabled) {
                    int product = param1 * param2;
                    totalSum += product;
                    logger.info("mul(" + param1 + "," + param2 + ") = " + product + " (ENABLED)");
                } else {
                    logger.info("mul(" + param1 + "," + param2 + ") is DISABLED");
                }
            }
        }

        return totalSum;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "src/main/java/org/example/Day3/input.txt";
        String fileContent = readFile(filePath);
        int result = processInstructions(fileContent);
        logger.info("Final sum of enabled mul calls: " + result);
        System.out.println("Final sum = " + result);
    }
}
