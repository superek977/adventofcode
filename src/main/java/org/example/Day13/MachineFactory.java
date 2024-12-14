package org.example.Day13;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class MachineFactory {

    private static final int BUTTON_A_COST = 3;
    private static final int BUTTON_B_COST = 1;

    public static List<Machine> parseMachinesFromFile(String filePath) {
        List<Machine> machines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Button buttonA = null, buttonB = null;
            Prize prize = null;

            Pattern buttonPattern = Pattern.compile("Button [AB]: X\\+([0-9]+), Y\\+([0-9]+)");
            Pattern prizePattern = Pattern.compile("Prize: X=([0-9]+), Y=([0-9]+)");

            while ((line = reader.readLine()) != null) {
                Matcher buttonMatcher = buttonPattern.matcher(line);
                Matcher prizeMatcher = prizePattern.matcher(line);

                if (buttonMatcher.matches()) {
                    int x = Integer.parseInt(buttonMatcher.group(1));
                    int y = Integer.parseInt(buttonMatcher.group(2));

                    if (line.startsWith("Button A")) {
                        buttonA = new Button(x, y, BUTTON_A_COST);
                    } else if (line.startsWith("Button B")) {
                        buttonB = new Button(x, y, BUTTON_B_COST);
                    }
                } else if (prizeMatcher.matches()) {
                    int x = Integer.parseInt(prizeMatcher.group(1));
                    int y = Integer.parseInt(prizeMatcher.group(2));
                    prize = new Prize(x, y);
                }

                if (buttonA != null && buttonB != null && prize != null) {
                    machines.add(new Machine(buttonA, buttonB, prize));
                    buttonA = null;
                    buttonB = null;
                    prize = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return machines;
    }
}