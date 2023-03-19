import java.io.*;

public class List {

    private static final int SIZE = (int) Math.pow(10, 6);

    private static int head;
    private static int tail;
    private static long sum = 0;
    private static int size;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long[] array = new long[SIZE];
        int middleIndex = SIZE / 2;
        head = middleIndex;
        tail = middleIndex + 1;

        int numberOfLines = Integer.parseInt(br.readLine());

        String[] command;

        for (int i = 0; i < numberOfLines; i++) {
            command = br.readLine().split(" ");
            switch (command[0]) {
                case ("pfront") -> pFront(Long.parseLong(command[1]), array);
                case ("pback") -> pBack(Long.parseLong(command[1]), array);
                case ("dfront") -> dFront(array);
                case ("dback") -> dBack(array);
                case ("cal") -> System.out.println(sum);
                default -> System.out.println(array[head + Integer.parseInt(command[1])]);
            }
        }
    }

    private static void pFront(long addend, long[] array) {
        sum = -sum;
        sum += addend;
        array[head--] = addend;
        size++;
    }

    private static void pBack(long addend, long[] array) {
        sum += (size++ % 2 == 0) ? addend : -addend;
        array[tail++] = addend;
    }

    private static void dFront(long[] array) {
        sum = -sum;
        sum += array[head + 1];
        System.out.println(array[head++ + 1]);
        size--;
    }

    private static void dBack(long[] array) {
        sum += (size-- % 2 == 0) ? array[tail - 1] : -array[tail - 1];
        System.out.println(array[tail-- - 1]);
    }
}