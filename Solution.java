package com.playground.java;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Solution {


    public static void main(String[] args) {

        try (Stream<String> stream = Files.lines(Paths.get("src/test/resources/input.txt"))) {
            stream.forEach(ln -> {
              List<Integer> values = Arrays.stream((ln.split(", ")[0].replaceAll("[()]","").split(","))).map(Integer::parseInt).collect(Collectors.toList());
                int targetVolume = Integer.parseInt(ln.split(", ")[1]);
                dumpDeliveryOptions(values.stream()
                        .mapToInt(Integer::intValue)
                        .toArray(), values.size(), targetVolume);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean canDeliver(int targetVolume, long index, int[] capacities) {
        assertTrue(capacities.length > 0);

        if (targetVolume <= 0 || index <= 0) {
            return targetVolume != 0;
        }

        boolean lhs = canDeliver(targetVolume, index - 1, capacities);
        boolean rhs = canDeliver(targetVolume - capacities[(int) index - 1], index, capacities);

        return (lhs || rhs);
    }

    private static void doDumpDeliveryOptions(final int index, final int targetVolume, int[] capacities, final int capacityCount, int[] tankers) {
        assertTrue(index <= capacityCount);

        if (index == (capacityCount - 1)) {
            if (targetVolume % capacities[index] == 0) {
                tankers[index] = (targetVolume / capacities[index]);

                doDumpDeliveryOptions((index + 1), 0, capacities,
                        capacityCount, tankers);
            }
        }

        // Done.
        else if (index == capacityCount) {
            System.out.print("[" + tankers[0]);

            for (int i = 1; i < capacityCount; ++i) {
                System.out.print("," + tankers[i]);
            }

            System.out.print("]");
        }

        // Try next arrangements.
        else for (int i = 0; i <= (targetVolume / capacities[index]); ++i) {
                tankers[index] = i;

                doDumpDeliveryOptions((index + 1), (targetVolume - (i * capacities[index])),
                        capacities, capacityCount, tankers);
            }

    }

    private static void dumpDeliveryOptions(int[] capacities, int capacityCount, int targetVolume) {

        // Tanker count.
        assertTrue(capacities.length > 0 && (capacityCount >= 2) && (capacityCount <= 5));

        // Target oil volume.
        assertTrue((targetVolume >= 1) && (targetVolume <= 2e5));

        int[] tankers = new int[capacityCount];

        // Options exist as is.
        if (canDeliver(targetVolume, capacityCount, capacities)) {
            doDumpDeliveryOptions(0, targetVolume, capacities,
                    capacityCount, tankers);
                System.out.println();
        }

        // Inflate target oil volume until delivery options become possible.
        else
            for (int volume = targetVolume; ; ) {
                System.out.println("targetVolume " + targetVolume);
                if (canDeliver(++volume, capacityCount, capacities)) {
                    System.out.println((volume - targetVolume));
                    break;
                }

            }

    }
}
