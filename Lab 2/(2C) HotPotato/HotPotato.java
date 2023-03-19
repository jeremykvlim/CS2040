import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class HotPotato {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int numberOfPeople = input[0], countdownTimer = input[1], finalNumberOfRemainingPeople = input[2];

        ArrayList<Integer> peopleList = new ArrayList<>();
        ArrayList<Integer> peopleRemaining = new ArrayList<>();

        for (int i = 1; i <= numberOfPeople; i++) peopleList.add(i);

        int countdown = 0;
        int curPos = 0;

        while (numberOfPeople > finalNumberOfRemainingPeople) {
            for (int i = 0; i < peopleList.size(); i++) {
                countdown++;
                curPos++;

                if ((countdown == countdownTimer) && (numberOfPeople > finalNumberOfRemainingPeople)) {
                    numberOfPeople--;
                    countdown = 0;
                }

                else if ((countdown == countdownTimer) && (numberOfPeople == finalNumberOfRemainingPeople))
                    peopleRemaining.add(peopleList.get(i));

                else if (countdown != countdownTimer) peopleRemaining.add(peopleList.get(i));

                if (curPos == peopleList.size()) {
                    peopleList.clear();
                    peopleList.addAll(peopleRemaining);
                    peopleRemaining.clear();
                    curPos = 0;
                }
            }
        }
        System.out.println(peopleList);
    }
}