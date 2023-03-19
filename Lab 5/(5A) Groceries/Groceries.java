import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

public class Groceries {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int numberOfItems = input[0], bundleSize = input[1], discountStart = input[2];

        LinkedList<Integer> itemPrices = new LinkedList<>();

        for (int i = 0; i < numberOfItems; i++) itemPrices.add(Integer.parseInt(br.readLine()));

        Collections.sort(itemPrices);

        int numberOfBundles = numberOfItems / bundleSize;
        int remainder = numberOfItems % bundleSize;
        long totalPrice = 0;

        for (int i = 0; i < remainder; i++) totalPrice += itemPrices.pop();

        for (int i = 0; i < numberOfBundles; i++) {
            for (int j = 0; j < discountStart; j++) itemPrices.pop();
            for (int k = 0; k < bundleSize - discountStart; k++) totalPrice += itemPrices.pop();
        }

        System.out.println(totalPrice);
    }
}