import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Macarons {
    public static void main(String[] args) throws IOException {
        /*
        Purpose:
        find the number of continguous subsets of an array of given numbers
        with a sum divisible by a given number

        Pre-conditions:
        1<=n<=50000, number of macarons is meq to 1 and leq to 50000
        1<=d<=1000000, divisor is meq to 1 and leq to 1000000
        second line of input contains same number of integers as the number of macarons

        Post-conditions:
        could not find any
         */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // BufferedReader
        String firstLineInput = br.readLine();
        // reading first line of input
        String[] firstLineArray = firstLineInput.split(" ");
        // split numbers by space and store as elements in array
        int numberOfMacarons = Integer.parseInt(firstLineArray[0]);
        // obtaining n, number of macarons in box
        int divisor = Integer.parseInt(firstLineArray[1]);
        // obtaining d, the divisor

        if (numberOfMacarons < 1 || numberOfMacarons > 50000 || divisor < 1 || divisor > 1000000) return;
            // precondition: 1<=n<=50000, 1<=d<=1000000

        String secondLineInput = br.readLine();
        // reading second line of input
        String[] secondLineArray = secondLineInput.split(" ");
        // split numbers by space and store as elements in array
        if (secondLineArray.length != numberOfMacarons) return;
            // precondition: number of space separated integers == number of macarons in box

        Integer[] macaronArray = new Integer[secondLineArray.length];
        // make an integer array of equivalent size

        for (int i = 0; i < secondLineArray.length; i++) {
            // parse all string elements as integers into integer array
            macaronArray[i] = Integer.parseInt(secondLineArray[i]);

            if (macaronArray[i] < 1 || macaronArray[i] > Math.pow(10, 9)) return;
                // precondition: each integer between 1 and 10^9 inclusive
        }
        int answer = numberOfDivisibleSubseq(macaronArray, divisor);
        // call function
        System.out.println(answer);
        // print answer
    }
    public static int numberOfDivisibleSubseq(Integer[] testArray, int testDivisor) {
        int divisibleCount = 0;
        // initialise a count for number of divisible subsequences
        int remainder = 0;
        // initialise a remainder tracker
        HashMap<Integer, Integer> remainderMap = new HashMap<>();
        // create a hashmap to store remainders

        for (int i : testArray) {
            // iterating through all elements in the array
            remainderMap.put(remainder, remainderMap.getOrDefault(remainder, 0) + 1);
            /* map the current remainder to its current key value + 1
            this means if a remainder appears, its key value goes up by 1, even after its first appearance
            accounts for repeat remainders because of the modulo distributive laws as seen in lab 3
            (a-b)%c = ((a%c)-(b%c))%c
            (a+b)%c = ((a%c)+(b%c))%c
            the laws implicate any subsequence that is a subset of a larger subsequence, so observe that
            for n number of array elements, there can only be n number of remainders of ANY subsequence
            even though there are more than n number of subsequences, the remainders will repeat themselves
            since remainders can show up more than once, we add 1 to a remainder's key value whenever it shows up
            the key value is then later added to the count to account for repetition */

            remainder = (remainder + i) % testDivisor;
            /* updating the remainder for the next element, accurate because of
            (a+b)%c = ((a%c)+(b%c))%c */

            divisibleCount += remainderMap.getOrDefault(remainder, 0);
            // increment count based on its key value
        }
        return divisibleCount;
        // return the counter when function is called
    }
}
