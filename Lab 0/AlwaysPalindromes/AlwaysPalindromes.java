import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AlwaysPalindromes {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String word = br.readLine();
        StringBuilder wordReverse = new StringBuilder();

        for (int i = word.length() - 1; i >= 0; i--) wordReverse.append(word.charAt(i));

        System.out.println(word.equals(wordReverse.toString()) ? word : word+wordReverse);
    }
}
