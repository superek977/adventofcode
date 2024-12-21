package org.example.Day5;


import org.example.util.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day5Part2 {
    private static final Logger logger = new Logger();

    private static final List<List<Integer>> rules = new ArrayList<>();
    private static final List<List<Integer>> orders = new ArrayList<>();
    private static final Map<Integer, List<Integer>> adjacencyList = new HashMap<>();

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
                    int a = Integer.parseInt(pair[0].trim());
                    int b = Integer.parseInt(pair[1].trim());
                    rules.add(Arrays.asList(a, b));
                } else {
                    String[] numbers = line.split(",");
                    List<Integer> order = new ArrayList<>();
                    for (String num : numbers) {
                        order.add(Integer.parseInt(num.trim()));
                    }
                    orders.add(order);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void buildAdjacencyList() {
        for (List<Integer> rule : rules) {
            int a = rule.get(0);
            int b = rule.get(1);
            adjacencyList.putIfAbsent(a, new ArrayList<>());
            adjacencyList.get(a).add(b);

            adjacencyList.putIfAbsent(b, new ArrayList<>());
        }
    }

    private static boolean isInvalidOrder(List<Integer> order) {
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

    private static List<Integer> topologicalSortUpdate(List<Integer> update) {
        Set<Integer> pagesInUpdate = new HashSet<>(update);
        Map<Integer, List<Integer>> localAdj = new HashMap<>();

        for (int page : pagesInUpdate) {
            if (adjacencyList.containsKey(page)) {
                List<Integer> validNeighbors = new ArrayList<>();
                for (int neighbor : adjacencyList.get(page)) {
                    if (pagesInUpdate.contains(neighbor)) {
                        validNeighbors.add(neighbor);
                    }
                }
                localAdj.put(page, validNeighbors);
            } else {
                localAdj.putIfAbsent(page, new ArrayList<>());
            }
        }

        Map<Integer, Integer> inDegree = new HashMap<>();
        for (int page : pagesInUpdate) {
            inDegree.put(page, 0);
        }
        for (int page : localAdj.keySet()) {
            for (int neighbor : localAdj.get(page)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int page : inDegree.keySet()) {
            if (inDegree.get(page) == 0) {
                queue.offer(page);
            }
        }

        List<Integer> sorted = new ArrayList<>();
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            sorted.add(curr);

            for (int neighbor : localAdj.getOrDefault(curr, Collections.emptyList())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        return sorted;
    }

    public static void main(String[] args) {
        convertRulesToList("src/main/java/org/example/Day5/input.txt");
        buildAdjacencyList();

        List<List<Integer>> incorrectUpdates = new ArrayList<>();

        for (List<Integer> order : orders) {
            boolean invalid = isInvalidOrder(order);
            if (invalid) {
                incorrectUpdates.add(order);

            }
        }


        int sumPart2 = 0;
        for (List<Integer> badUpdate : incorrectUpdates) {
            List<Integer> corrected = topologicalSortUpdate(badUpdate);
            int midIndex = corrected.size() / 2;
            sumPart2 += corrected.get(midIndex);
        }

        logger.info("Part Two sum of middle pages (after reordering): " + sumPart2);
    }
}

