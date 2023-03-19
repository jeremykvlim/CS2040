/**
 * Name         : Jeremy Lim
 * Matric. No   : A0226684W
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Counting {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputParameters = br.readLine().split(" ");
        long greaterOrEqualToMe = Long.parseLong(inputParameters[1]);

        long[] array = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();

        System.out.println(slidingWindow(array, greaterOrEqualToMe));
    }

    private static long slidingWindow(long[] array, long greaterOrEqToMe) {
        int windowStart = 0;
        int windowEnd = 0;
        long prefixSum = array[0];
        long invalidSubsequences = 0;
        long totalSubsequences = (long) array.length * (array.length + 1) / 2;

        while (windowEnd < array.length && windowStart < array.length) {
            if (prefixSum < greaterOrEqToMe) {
                windowEnd++;
                if (windowEnd >= windowStart) invalidSubsequences += windowEnd - windowStart;
                if (windowEnd < array.length) prefixSum += array[windowEnd];
            } else prefixSum -= array[windowStart++];
        }
        return totalSubsequences - invalidSubsequences;
    }


}
