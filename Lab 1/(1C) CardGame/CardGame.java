import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class CardGame {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<int[]> fullDeck = new ArrayList<>();

        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = input[0], a = input[1], b = input[2];
        int[] cardArray = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int[] deckA = new int[a];
        int[] deckB = new int[b];
        int[] deckC = new int[n - a - b];

        System.arraycopy(cardArray, 0, deckA, 0, a);
        System.arraycopy(cardArray, a, deckB, 0, b);
        System.arraycopy(cardArray, a + b, deckC, 0, n - a - b);

        int[] positions = new int[3];
        boolean[] reversed = new boolean[3];
        int[] increments = new int[3];

        positions[0] = 0;
        positions[1] = 1;
        positions[2] = 2;

        int q = Integer.parseInt(br.readLine());

        for (int j = 0; j < q; j++) {
            String command = br.readLine();

            if (command.contains("SWAP")) {
                String[] swap = command.split(" ");
                int swapX = Integer.parseInt(swap[1]), swapY = Integer.parseInt(swap[2]);
                swap(swapX, swapY, positions, reversed, increments);
            }

            if ((command.contains("REVERSE")) && (!command.contains("ALL"))) {
                String[] reverseX = command.split(" ");
                int deckX = Integer.parseInt(reverseX[1]) - 1;
                reversed[deckX] = !reversed[deckX];
            }

            if ((command.contains("REVERSE ALL"))) {
                swap(1, 3, positions, reversed, increments);
                for (int i = 0; i < reversed.length; i++) reversed[i] = !reversed[i];
            }

            if ((command.contains("INCREMENT")) && (!command.contains("ALL"))) {
                String[] incrementX = command.split(" ");
                int deckX = Integer.parseInt(incrementX[1]) - 1;
                int addend = Integer.parseInt(incrementX[2]);
                increments[deckX] += addend;
            }

            if (command.contains("INCREMENT ALL")) {
                String[] incrementAll = command.split(" ");
                int addend = Integer.parseInt(incrementAll[2]);
                for (int i = 0; i < increments.length; i++) increments[i] += addend;
            }
        }

        for (int i = 0; i < positions.length; i++) {
            if (positions[i] == 0) performChanges(deckA, reversed, increments, i);
            else if (positions[i] == 1) performChanges(deckB, reversed, increments, i);
            else performChanges(deckC, reversed, increments, i);
        }

        for (int position : positions) {
            if (position == 0) fullDeck.add(deckA);
            else if (position == 1) fullDeck.add(deckB);
            else fullDeck.add(deckC);
        }

        String answer = Arrays.toString(fullDeck.get(0))
                +Arrays.toString(fullDeck.get(1))
                +Arrays.toString(fullDeck.get(2));
        String ans = answer.replaceAll("\\D+", " ").trim();
        System.out.println(ans);

    }

    private static void performChanges(int[] deckA, boolean[] reversed, int[] increments, int i) {
        for (int k = 0; k < deckA.length; k++) deckA[k] += increments[i];

        if (reversed[i]) {
            for (int k = 0; k < (deckA.length / 2); k++) {
                int temp = deckA[k];
                deckA[k] = deckA[deckA.length - k - 1];
                deckA[deckA.length - k - 1] = temp;
            }
        }
    }

    private static void swap(int x, int y, int[] positions, boolean[] reversed,
                            int[] increments) {

        int[] tempInt = new int[1];
        boolean[] tempBool = new boolean[1];
        int[] tempInc = new int[1];

        if (x + y == 3) { // 1, 2 or 2, 1
            tempInt[0] = positions[0];
            positions[0] = positions[1];
            positions[1] = tempInt[0];

            tempBool[0] = reversed[0];
            reversed[0] = reversed[1];
            reversed[1] = tempBool[0];

            tempInc[0] = increments[0];
            increments[0] = increments[1];
            increments[1] = tempInc[0];
        }
        else if (x + y == 4) { // 1, 3 or 3, 1
            tempInt[0] = positions[0];
            positions[0] = positions[2];
            positions[2] = tempInt[0];

            tempBool[0] = reversed[0];
            reversed[0] = reversed[2];
            reversed[2] = tempBool[0];

            tempInc[0] = increments[0];
            increments[0] = increments[2];
            increments[2] = tempInc[0];
        }

        else if (x + y == 5) { // 2, 3 or 3, 2
            tempInt[0] = positions[1];
            positions[1] = positions[2];
            positions[2] = tempInt[0];

            tempBool[0] = reversed[1];
            reversed[1] = reversed[2];
            reversed[2] = tempBool[0];

            tempInc[0] = increments[1];
            increments[1] = increments[2];
            increments[2] = tempInc[0];
        }
    }
}
