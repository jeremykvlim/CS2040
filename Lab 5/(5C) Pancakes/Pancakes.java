import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Pancakes {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    //binary index tree
    static int[] fenwick;

    public static void main(String[] args) throws IOException {
        //size not needed since input is directly streamed to an array
        br.readLine();

        int[] beforeSort = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        int[] afterSort = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        long beforeInversionCount = prefixSumTotal(beforeSort);
        long afterInversionCount = prefixSumTotal(afterSort);

        if ((beforeInversionCount % 2) != (afterInversionCount % 2)) System.out.println("Impossible");
        else System.out.println("Possible");
    }

    static long prefixSumTotal(int[] array) {
        //range of numbers is 3 <= N <= 100000
        int minValue = 100001;
        int maxValue = 2;

        for (int n : array) {
            minValue = Math.min(minValue, n);
            maxValue = Math.max(maxValue, n);
        }

        int range = (maxValue + 1) - (minValue - 1);
        int offset = -(minValue - 1);

        fenwick = new int[range];
        long[] finalArray = new long[array.length];

        for (int i = 0; i < array.length; i++) {
            update(offset + array[i], 1);
            finalArray[i] = prefixSum(offset + array[i] - 1);
        }

        return LongStream.of(finalArray).sum();
    }

    static void update(int index, int value) {
        //while loop version
        //while (index < numberOfElements) {
        //    bit[index] += value;
        //    index += (index & (-index));
        //}

        for (int i = index; i < fenwick.length; i += Integer.lowestOneBit(i))
            fenwick[i] += value;
    }

    static long prefixSum(int index) {
        int sum = 0;

        //while loop version
        //while (index > 0) {
        //    sum += bit[index];
        //    index -= (index & (-index));
        //}

        for (int i = index; i > 0; i -= Integer.lowestOneBit(i))
            sum += fenwick[i];

        return sum;
    }
}