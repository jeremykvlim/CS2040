import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Recipe {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        HashMap<Long, Integer> map = new HashMap<>();

        long[] entryArray = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        long[] exitArray = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();

        for (long exitTime : exitArray) {
            for (long entryTime : entryArray) {
                long difference = exitTime - entryTime;
                if (difference >= 0) map.put(difference, map.getOrDefault(difference, 0) + 1);
            }
        }

        ArrayList<Long> list = new ArrayList<>();
        if (map.size() != 0) {
            for (Map.Entry<Long, Integer> entry : map.entrySet())
                if (entry.getValue() == Collections.max(map.values())) list.add(entry.getKey());
            Collections.sort(list);
            System.out.println(list.get(0));
        }
        else System.out.println(0);
    }
}
