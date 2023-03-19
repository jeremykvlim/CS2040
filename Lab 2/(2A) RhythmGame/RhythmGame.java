import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class RhythmGame {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numberOfTurns = Integer.parseInt(br.readLine());
        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        LinkedList<Integer> comboList = new LinkedList<>();
        ArrayList<Integer> maxComboList = new ArrayList<>();
        boolean addRight = true;
        boolean isAlternating = false;
        int maxComboSize = 0;

        for (int i = 0; i < numberOfTurns; i++) {
            if (isAlternating) addRight = !addRight;

            if (input[i] == 0) {
                int currentComboSize = comboList.size();
                if (currentComboSize > maxComboSize) {
                    maxComboSize = currentComboSize;
                    maxComboList.clear();
                    maxComboList.addAll(comboList);
                }
                comboList.clear();
            }
            else if (input[i] == 5) addRight = !addRight;

            else if (input[i] == 6) isAlternating = !isAlternating;

            else if (addRight) comboList.add(input[i]);

            else comboList.addFirst(input[i]);
        }
        int currentComboSize = comboList.size();
        if (currentComboSize > maxComboSize) {
            maxComboSize = currentComboSize;
            maxComboList.clear();
            maxComboList.addAll(comboList);
        }
        System.out.println(maxComboSize);
        System.out.println(maxComboList);
    }
}
