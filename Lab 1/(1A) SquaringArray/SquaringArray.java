import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SquaringArray {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int sqArrayDimension = Integer.parseInt(br.readLine());
        int[] arrayElements = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[][] sqArray = new int[sqArrayDimension][sqArrayDimension];

        System.arraycopy(arrayElements, 0, sqArray[0], 0, arrayElements.length);

        for (int i = 1; i < sqArrayDimension; i++) {
            for (int j = 0; j < sqArrayDimension; j++)
                sqArray[i][j] = (i % 2 != 0) ? sqArray[0][sqArrayDimension - 1 - j] * (i + 1)
                        : sqArray[0][j] * (i + 1);
        }

        System.out.println(Arrays.deepToString(sqArray).replaceAll("\\D+", " ").trim());
    }
}
