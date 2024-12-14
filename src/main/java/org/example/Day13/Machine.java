package org.example.Day13;

public class Machine {
    Button buttonA;
    Button buttonB;
    Prize prize;

    public Machine(Button buttonA, Button buttonB, Prize prize) {
        this.buttonA = buttonA;
        this.buttonB = buttonB;
        this.prize = prize;
    }

    public int findCheapestWayToPrize() {
        int targetX = prize.x;
        int targetY = prize.y;

        int x1 = buttonA.x(),  y1 = buttonA.y(),  cost1 = buttonA.cost();
        int x2 = buttonB.x(),  y2 = buttonB.y(),  cost2 = buttonB.cost();

        int bestA = -1, bestB = -1;
        int minCost = Integer.MAX_VALUE;

        int maxB = Math.min(targetX / x2, targetY / y2);

        for (int b = 0; b <= maxB; b++) {
            int remainingX = targetX - b*x2;
            int remainingY = targetY - b*y2;

            if (remainingX < 0 || remainingY < 0) {
                break;
            }

            if (remainingX % x1 == 0 && remainingY % y1 == 0) {
                int a = remainingX / x1;
                if (a >= 0 && a*y1 == remainingY) {
                    int cost = cost1*a + cost2*b;
                    if (cost < minCost) {
                        minCost = cost;
                        bestA = a;
                        bestB = b;
                    }
                }
            }
        }

        if (minCost == Integer.MAX_VALUE) {
            System.out.println("No valid solution found.");
            return 0;
        } else {
            return minCost;
        }
    }
}
