import java.util.*;
import java.io.*;

public class Fishing {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] inputs = br.readLine().split(" ");
        int k = Integer.parseInt(inputs[1]);

        long[] fishQualityArray = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        long maximumValue = 0;

        for (int i = 0; i < k; i++) maximumValue += fishQualityArray[i];

        long currentSum = maximumValue;
        for (int i = k; i < fishQualityArray.length; i++) {
            currentSum += fishQualityArray[i] - fishQualityArray[i - k];
            maximumValue = Math.max(currentSum, maximumValue);
        }

        System.out.println(maximumValue < 0 ? 0 : maximumValue);
    }
}