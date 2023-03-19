import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Palindromes {

    private static HashMap<HashSet<Character>, Integer> map = new HashMap<>();

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numberOfBags = Integer.parseInt(br.readLine());

        addToMap(br, numberOfBags);

        System.out.println(totalPairs());
    }

    public static void addToMap(BufferedReader br, int numberOfBags) throws IOException {

        String[] bagTiles = new String[numberOfBags];

        for (int i = 0; i < numberOfBags; i++) {
            bagTiles[i] = br.readLine();
            HashSet<Character> bag = new HashSet<>();
            for (int j = 0; j < bagTiles[i].length(); j++) {
                char letter = bagTiles[i].charAt(j);
                if (bag.contains(letter)) bag.remove(letter);
                else bag.add(letter);
            }
            map.put(bag, map.getOrDefault(bag, 0) + 1);
        }
    }

    public static long totalPairs() {

        long count = 0;

        for (Map.Entry<HashSet<Character>, Integer> entry : map.entrySet()) {
            // for each entry in the entrySet of freq
            HashSet<Character> bag = entry.getKey();
            @SuppressWarnings("unchecked")
            HashSet<Character> bagClone = (HashSet<Character>) bag.clone();
            long bagFreq = entry.getValue();
            count += bagFreq*(bagFreq - 1)/2;

            for (char letter : bag) {
                bagClone.remove(letter);
                if (map.containsKey(bagClone)) count += bagFreq * map.get(bagClone);
                bagClone.add(letter);
            }
        }
        return count;
    }
}