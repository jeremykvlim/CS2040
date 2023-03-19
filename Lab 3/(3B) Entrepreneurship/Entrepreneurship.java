import java.io.*;
import java.util.*;

public class Entrepreneurship {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int[] inputs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = inputs[0], m = inputs[1];
        ArrayDeque<ArrayList<Pair>> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            String[] command = br.readLine().split(" ");

            if (command[0].equals("ADD")) {
                String[] orderBatch = br.readLine().split(" ");
                ArrayList<Pair> orders = new ArrayList<>();
                if (command[2].equals("L"))
                    for (int j = 0; j <= 2 * Integer.parseInt(command[1]) - 2; j += 2)
                        orders.add(new Pair(Integer.parseInt(orderBatch[j]), Double.parseDouble(orderBatch[j + 1])));
                else
                    for (int j = 2*Integer.parseInt(command[1]) - 2; j >= 0; j -= 2)
                        orders.add(new Pair(Integer.parseInt(orderBatch[j]), Double.parseDouble(orderBatch[j + 1])));
                queue.add(orders);
            }
            else
                for (int j = 0; j < Integer.parseInt(command[1]); j++) queue.removeLast();
        }
        double revenue = 0;

        while (!queue.isEmpty()) {
            ArrayList<Pair> orderBatch = queue.poll();
            for (Pair order : orderBatch) {
                if (m >= order.amount) {
                    revenue += order.price * order.amount;
                    m -= order.amount;
                }
            }
        }
        System.out.printf("%.1f%n", revenue);
    }

    private static class Pair {
        int amount;
        double price;

        Pair(int a, double p) {
            amount = a;
            price = p;
        }
    }
}
