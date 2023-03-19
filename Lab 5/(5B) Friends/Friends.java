import java.io.*;
import java.util.*;

public class Friends {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");

        int n = Integer.parseInt(input[0]);
        long m = Long.parseLong(input[1]);

        int currentMaxFriends = 0;

        TreeSet<String> cafeList = new TreeSet<>();
        ArrayList<long[]> timings = new ArrayList<>();
        Comparator<long[]> sortByFirstArrayElement = Comparator.comparingLong(array -> array[0]);

        for (int i = 0; i < n; i++) {
            String[] cafeDetails = br.readLine().split(" ");
            int k = Integer.parseInt(cafeDetails[1]);

            for (int j = 0; j < k; j++) {
                long[] visitorTiming = Arrays.stream(br.readLine().split(" ")).
                        mapToLong(Long::parseLong).toArray();
                timings.add(new long[]{visitorTiming[0], 1});
                timings.add(new long[]{visitorTiming[1], -1});
            }

            timings.sort(sortByFirstArrayElement);

            int friendCount = 0, maxFriends = 0, windowStart = 0;

            for (int windowEnd = 0; windowEnd < timings.size() - 1; windowEnd++) {
                friendCount += Math.max(0, timings.get(windowEnd)[1]);
                maxFriends = Math.max(friendCount, maxFriends);

                while (timings.get(windowEnd)[0] - timings.get(windowStart)[0] > m) {
                    friendCount += Math.min(0, timings.get(windowStart++)[1]);
                    maxFriends = Math.max(friendCount, maxFriends);
                }
            }

            timings.clear();

            if (currentMaxFriends < maxFriends) {
                currentMaxFriends = maxFriends;
                cafeList.clear();
            }
            if (currentMaxFriends == maxFriends) cafeList.add(cafeDetails[0]);
        }

        System.out.println(currentMaxFriends);

        for (String cafe : cafeList) System.out.println(cafe);
    }
}

