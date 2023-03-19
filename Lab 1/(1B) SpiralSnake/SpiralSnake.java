import java.io.*;

public class SpiralSnake {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) throws IOException {
        String[] command = br.readLine().split(" ");

        int m = Integer.parseInt(command[0]), n = Integer.parseInt(command[1]);

        char[][] grid = new char[m][n];
        for (int i = 0; i < m; i++) {
            char[] inputCharArray = br.readLine().toCharArray();
            System.arraycopy(inputCharArray, 0, grid[i], 0, n);
        }

        int r = 0, c = 0, counter = 0;

        while (r < m && c < n) {
            for (int i = c; i < n; i++) {
                counter++;
                if (grid[r][i] == 'X') System.out.println("Apple at ("+i+", "+r+") eaten at step "+counter);
            }
            r++;

            for (int i = r; i < m; i++) {
                counter++;
                if (grid[i][n - 1] == 'X') System.out.println("Apple at ("+(n - 1)+", "+i+") eaten at step "+counter);
            }
            n--;

            for (int i = n - 1; i >= c; i--) {
                counter++;
                if (grid[m - 1][i] == 'X') System.out.println("Apple at ("+i+", "+(m - 1)+") eaten at step "+counter);
            }
            m--;

            for (int i = m - 1; i >= r; i--) {
                counter++;
                if (grid[i][c] == 'X') System.out.println("Apple at ("+c+", "+i+") eaten at step "+counter);
            }
            c++;
        }
    }
}