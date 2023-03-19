import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Brackets {

    private static final int MODULO = 1000000007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        String[] sequence = br.readLine().split(" ");
        LinkedList<String> stack = new LinkedList<>();
        boolean isAdding = true;

        for (String s : sequence) {

            switch (s) {
                case "(" -> {
                    isAdding = !isAdding;
                    stack.push(s);
                }
                case ")" -> {
                    computeSequence(stack, isAdding);
                    isAdding = !isAdding;
                }
                default -> stack.push(s);
            }
        }

        long answer = 0;
        long size = stack.size();
        for (int i = 0; i < size; i++) {
            answer += Long.parseLong(stack.pop());
            answer %= MODULO;
        }

        System.out.println(answer);
    }

    private static void computeSequence(LinkedList<String> stack, boolean isAdding) {
        long sum = 0;
        long product = 1;
        String token = stack.pop();
        while (!token.equals("(")) {
            if (isAdding) {
                sum += Long.parseLong(token);
                sum %= MODULO;
            } else {
                product *= Long.parseLong(token);
                product %= MODULO;
            }
            token = stack.pop();
        }
        if (isAdding) stack.push(String.valueOf(sum));
        else stack.push(String.valueOf(product));
    }
}
