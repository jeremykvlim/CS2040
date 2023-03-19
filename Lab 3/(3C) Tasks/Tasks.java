import java.io.*;

public class Tasks {

    private static final int SIZE = (int) Math.pow(10, 6);
    private static String[] frontHalf;
    private static String[] backHalf;
    private static int backHead;
    private static int backTail;
    private static int frontHead;
    private static int frontTail;
    private static int frontSize = 0;
    private static int backSize = 0;
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static PrintWriter pw = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        frontHalf = new String[SIZE];
        backHalf = new String[SIZE];
        int middleIndex = SIZE / 2;
        frontHead = middleIndex - 1;
        backTail = middleIndex + 1;
        backHead = middleIndex;
        frontTail = middleIndex;

        int numberOfLines = Integer.parseInt(br.readLine());

        String[] command;

        for (int i = 0; i < numberOfLines; i++) {
            command = br.readLine().split(" ");
            switch (command[0]) {
                case ("ADD_FRONT") -> addFront(command[1]);
                case ("ADD_BACK") -> addBack(command[1]);
                case ("ADD_MIDDLE") -> addMiddle(command[1]);
                case ("REMOVE_FRONT") -> removeFront();
                case ("REMOVE_BACK") -> removeBack();
                case ("REMOVE_MIDDLE") -> removeMiddle();
                default -> pw.println(getString(Integer.parseInt(command[1])));
            }
        }
        pw.flush();
    }

    private static void balance() {
        if (frontSize > backSize + 1) {
            backHalf[backHead--] = frontHalf[frontTail - 1];
            frontHalf[frontTail-- - 1] = null;
            backSize++;
            frontSize--;
        }
        else if (frontSize + 1 < backSize) {
            frontHalf[frontTail++] = backHalf[backHead + 1];
            backHalf[backHead++ + 1] = null;
            backSize--;
            frontSize++;
        }
    }

    private static void addFront(String toBeAdded) {
        frontHalf[frontHead--] = toBeAdded;
        frontSize++;
        balance();
    }

    private static void addBack(String toBeAdded) {
        backHalf[backTail++] = toBeAdded;
        backSize++;
        balance();
    }

    private static void addMiddle(String toBeAdded) {
        if (frontSize >= backSize) {
            backHalf[backHead--] = toBeAdded;
            backSize++;
        } else {
            frontHalf[frontTail++] = backHalf[backHead + 1];
            backHalf[backHead + 1] = toBeAdded;
            frontSize++;
        }
        balance();
    }

    private static void removeFront() {
        frontHalf[frontHead++ + 1] = null;
        frontSize--;
        balance();
    }

    private static void removeBack() {
        backHalf[backTail-- - 1] = null;
        backSize--;
        balance();
    }

    private static void removeMiddle() {
        if (frontSize <= backSize) {
            backHalf[backHead++ + 1] = null;
            backSize--;
        } else {
            frontHalf[frontTail-- - 1] = null;
            frontSize--;
        }
        balance();
    }

    private static String getString(int index) {
        String answer;
        if (index > frontSize - 1) {
            index -= frontSize;
            answer = backHalf[backHead + 1 + index];
        } else answer = frontHalf[frontHead + 1 + index];
        return answer;
    }

}