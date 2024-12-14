package org.example.util;

import java.util.List;

public class utilily {
    public static int sumList(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).sum();
    }
}
