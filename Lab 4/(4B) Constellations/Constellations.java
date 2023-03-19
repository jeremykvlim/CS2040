import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Constellations {

    public static final int MODULO = 1000000007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] inputs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = inputs[0], a = inputs[1], b = inputs[2];

        System.out.println(f(n, a, b) % MODULO);
    }

    public static long C(int n, int r) { // the result of n choose r (nCr) function
        /* 50 choose 5 = 50/1 * 49/2 * 48/3 * 47/4 * 46/5
        9 choose 6 = 9/1 * 8/2 * 7/3
        limiting factor to the sequence is the numerator, stopping at (n - (r - 1)) term, denominator is independent of n
        therefore, nCr = (n - 0)/(0 + 1) * (n - 1)/(1 + 1) * (n - 2)/(2 + 1) * ... * (n - (r - 1))/(r) */

        long nCr = 1; // product function so first term is 1

        for (long i = 0; i <= (r - 1); i++) { // for i from 0 to (r - 1) inclusive
            long numerator = (n - i); // numerator of each multiplicand
            long denominator = (i + 1); // denominator of each multiplicand
            nCr *= numerator % MODULO; // modulo applied while multiplying numerator
            nCr /= denominator % MODULO; // modulo applied while dividing denominator
        }
        return nCr; // returns n choose r function answer
    }

    public static long f(int n, int a, int b) { // the result of f function used to solve problem
        /* certain constraints have already been provided
        n, a, b >= 1
        50 >= n >= b >= a
        b - a <= 5
        no need for (n < a) and (n > 0) conditions

        problem is asking for ordered partitions of integer n using integers a to b inclusive

        derivation: if you have n stars to fit into constellations of sizes a to b inclusive,
        first choose k stars and fill a constellation (n choose k)
        next you still want to fit the remaining (n - k) stars into constellations of sizes a to b inclusive
        recurse the initial function again for (n - k) stars and multiply it to nCk
        to account for the total combinations, sum the terms for constellation sizes of a to b inclusive

        the mathematical recursive formula to solve this is then
        f(n, a, b) = sum of (nCk * f(n - k, a, b)) from k = a to k = b inclusive*/

        long answer = 0; // recursive sum function so initial term is 0

        for (int k = a; k <= b; k++) { // for k from a to b inclusive
            long nCrModulo = C(n,k) % MODULO; // modulo applied to nCr result before calculation
            if (n - k > a) { // if number of remaining stars is greater than a

                /* even though question has its constraints where !(n < a), still have to account for recursion logic
                there is only one way to fill up constellation if there are fewer stars than the size (n < a)
                i.e. putting them in any constellation, note: will end up having unfilled space
                thus f(n - k, a, b) = 1 for (n - k <= a), note: since nCn is also 1
                example: f(2, 3, 6) = 1 as any constellation fits and will have unfilled space */

                long fModulo = f(n - k, a, b) % MODULO; // modulo applied to f result before calculation
                answer += nCrModulo * fModulo; // recurse the function for remaining stars and multiply to nCrModulo, add to answer
            } else  // if number of remaining stars is less than a
                answer += nCrModulo; // multiply nCrModulo by 1, add to answer
        }
        return answer; // returns total number of ordered partitions of integer n using integers from a to b inclusive
    }
}