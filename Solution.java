import org.junit.Assert;

import static org.junit.Assert.assertTrue;

public class Solution {

    public static void main(String[] args) {
        int[] values = new int[8];
        int valueCount = 0;
        int targetVolume = 0;
        dumpDeliveryOptions(values, valueCount, targetVolume, null);

    }

    public static boolean self(int value, long index_U, int[] capacities_U) {
        return canDeliver(value, index_U, capacities_U);
    }

    public static boolean self2(int value_U, long index_U, int[] capacities_U) {
        return canDeliver(value_U, index_U, capacities_U);
    }

    private static boolean canDeliver(int targetVolume, long index_U, int[] capacities_U) {
        assert capacities_U != null;

        if(targetVolume <= 0 || index_U == 0) {
            return targetVolume == 0;
        }

        boolean lhs = self(targetVolume, index_U - 1, capacities_U), rhs = self2(targetVolume - capacities_U[(int)(index_U - 1)], index_U, capacities_U);
        return lhs || rhs;
    }
    private static void doDumpDeliveryOptions(final int index, final int targetVolume, int[] capacities, final int capacityCount, int[] tankers, final Object outputStream){
        assertTrue(index <= capacityCount);
        if(index == (capacityCount - 1))
        {
            if(targetVolume % capacities[index]==0)
            {
                tankers[index] = (targetVolume / capacities[index]);

                doDumpDeliveryOptions((index + 1), 0, capacities,
                        capacityCount, tankers, outputStream);
            }
        }

        // Done.
        else if(index == capacityCount)
        {
            System.out.println(tankers);

            for(int i = 1; i < capacityCount; ++i)
            {
                System.out.println(tankers[i]);
            }

            System.out.println("]");
        }

        // Try next arrangements.
        else for(int i = 0; i <= (targetVolume / capacities[index]); ++i)
            {
                tankers[index] = i;

                doDumpDeliveryOptions((index + 1), (targetVolume - (i * capacities[index])),
                        capacities, capacityCount, tankers, outputStream);
            }

    }

    private static void dumpDeliveryOptions(int[] capacities, int capacityCount, int targetVolume, Object obj) {

            // Tanker count.
            assert(capacities.length>0 && (capacityCount >= 2) && (capacityCount <= 5));

            // Target oil volume.
            assert((targetVolume >= 1) && (targetVolume <= 2e5));

//            assert(outputStream && !ferror(outputStream));

            // VLAs yield a slightly better memory score on CodeEval than straight
            // [m,c]alloc. Shouldn't rely on this in C11 and onwards, though.
        int[] tankers = new int[capacityCount];

            
//            memset(tankers, 0, sizeof tankers);


            // Options exist as is.
            if(canDeliver(targetVolume, capacityCount, capacities))
            {
                doDumpDeliveryOptions(0, targetVolume, capacities,
                        new Integer(capacityCount), tankers, null);

//                System.out.println('\n', outputStream);
            }

            // Inflate target oil volume until delivery options become possible.
            else
                for(int volume = targetVolume;;){
                    if(canDeliver(++volume, new Integer(capacityCount), capacities)){
                System.out.println((volume - targetVolume));
                break;
            }

        }

}
}
