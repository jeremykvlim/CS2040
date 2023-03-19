import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Candyland {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long numberOfCandyPieces = Long.parseLong(br.readLine());
        if (numberOfCandyPieces > 0) numberOfCandyPieces++;
        System.out.println(tetranacci(numberOfCandyPieces));
    }

    public static HashMap<Long, Long> tetranacciMap = new HashMap<>();

    public static long tetranacci(long i) {
        if (i == 0) return 0;
        else if (i == 1 || i == 2) return 1;
        else if (i == 3) return 2;

        if (tetranacciMap.get(i) != null) return tetranacciMap.get(i);
        else {
            long n = tetranacci(i - 1) + tetranacci(i - 2) + tetranacci(i - 3) + tetranacci(i - 4);
            tetranacciMap.put(i, n);
            return n;
        }

    }
}