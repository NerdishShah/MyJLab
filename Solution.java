import org.junit.Assert;

import static org.junit.Assert.assertTrue;

public class Solution {

    public static void main(String[] args) {
        int[] values = new int[4];
        values[0] = 2;
        values[1] = 4;
        values[2] = 8;
        values[3] = 8;
        int valueCount = 3;
        int targetVolume = values[3];
        dumpDeliveryOptions(values, valueCount, targetVolume);
    }

//    public static boolean self(int value, long index_U, int[] capacities_U) {
//        return canDeliver(value, index_U, capacities_U);
//    }
//
//    public static boolean self2(int value_U, long index_U, int[] capacities_U) {
//        return canDeliver(value_U, index_U, capacities_U);
//    }

    private static boolean canDeliver(int targetVolume, long index, int[] capacities) {
        assert capacities != null;

        if (targetVolume <= 0 || index == 0) {
            return false;
        }
        
        boolean lhs = canDeliver(targetVolume, index - 1, capacities);
        boolean rhs = canDeliver(targetVolume - capacities[(int) index - 1], index, capacities);
        
        return (lhs || rhs);

//        boolean lhs = self(targetVolume, index - 1, capacities), rhs = self2(targetVolume - capacities[(int) (index - 1)], index, capacities);
//        return lhs || rhs;
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
        assert (capacities.length > 0 && (capacityCount >= 2) && (capacityCount <= 5));

        // Target oil volume.
        assert ((targetVolume >= 1) && (targetVolume <= 2e5));

//            assert(outputStream && !ferror(outputStream));

        // VLAs yield a slightly better memory score on CodeEval than straight
        // [m,c]alloc. Shouldn't rely on this in C11 and onwards, though.
        int[] tankers = new int[capacityCount];


//            memset(tankers, 0, sizeof tankers);


        // Options exist as is.
        //if (canDeliver(targetVolume, capacityCount, capacities)) {
        	if (true) {
            doDumpDeliveryOptions(0, targetVolume, capacities,
                    capacityCount, tankers);

//                System.out.println('\n', outputStream);
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
