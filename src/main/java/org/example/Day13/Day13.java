package org.example.Day13;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.example.Day13.MachineFactory.parseMachinesFromFile;
import static org.example.util.Utility.sumList;

public class Day13 {
    private static final Logger logger = Logger.getLogger(Day13.class.getName());

    public static void main(String[] args) {

        String filePath = "src/main/java/org/example/Day13/input.txt";

        List<Machine> machines = parseMachinesFromFile(filePath);

        List<Integer> listOfSums = new ArrayList<>();

        for (Machine machine : machines) {
            listOfSums.add(machine.findCheapestWayToPrize());
        }
        int result = sumList(listOfSums);
        logger.info("Result " + result);
    }
}
