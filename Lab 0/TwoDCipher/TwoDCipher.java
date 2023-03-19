import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TwoDCipher {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] arrayDimensions = br.readLine().split(" ");
        int n = Integer.parseInt(arrayDimensions[0]), m = Integer.parseInt(arrayDimensions[1]);
        char[][] array = new char[n][m];

        String word = br.readLine();
        char[] letters = word.toCharArray();
        int counter = 0;
        while (counter < letters.length) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) array[i][j] = letters[counter++];
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int offset = (i + 1) * (j + 1);
                if (offset > 26) offset %= 26;
                array[i][j] = (char) (array[i][j] + offset);
                if (array[i][j] > 'z')
                    array[i][j] = (char) (array[i][j] - 26);
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }
}
