package org.example.util;

import java.util.List;

public class Utility {
    public static int sumList(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).sum();
    }
}
