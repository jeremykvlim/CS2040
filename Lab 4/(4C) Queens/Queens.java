import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Queens {

    private static int n;

    private static boolean success;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] command = br.readLine().split(" ");

        n = Integer.parseInt(command[0]);
        int queensLeft = n - Integer.parseInt(command[1]);

        char[][] chessboard = new char[n][n];
        for (int i = 0; i < n; i++) {
            char[] inputCharArray = br.readLine().toCharArray();
            System.arraycopy(inputCharArray, 0, chessboard[i], 0, n);
        }

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++)  {
                if (chessboard[row][col] == 'Q') {
                    placeQueen(chessboard, row, col);
                    chessboard[row][col] = 'x';
                }
            }
        }
        depthFirstSearch(chessboard, queensLeft, 0);
        System.out.println(success);
    }

    private static void depthFirstSearch(char[][] chessboard, int queensLeft, int row) {
        boolean isInvalidRow = true;

        for (int col = 0; col < n; col++) {
            int queensLeftCheckpoint = queensLeft;
            char[][] cloneboard = new char[n][n];
            for (int i = row; i < n; i++)
                System.arraycopy(chessboard[i], 0, cloneboard[i], 0, n);

            if (chessboard[row][col] == '.') {
                isInvalidRow = false;
                placeQueen(chessboard, row, col);

                if (--queensLeft == 0) {
                    success = true;
                    return;
                }
                if (row < n - 1) depthFirstSearch(chessboard, queensLeft, row + 1);
            }

            if (success) return;
            queensLeft = queensLeftCheckpoint;
            chessboard = cloneboard;
        }
        if (isInvalidRow && row < n - 1) depthFirstSearch(chessboard, queensLeft, row + 1);
    }

    private static void placeQueen(char[][] chessboard, int row, int col) {
        for (int i = 0; i < n; i++) {
            if (chessboard[row][i] == '.') chessboard[row][i] = 'x';
            if (chessboard[i][col] == '.') chessboard[i][col] = 'x';
        }

        int r = row - 1;
        int c = col - 1;

        while (r >= 0 && c >= 0) { // 135 deg
            if (chessboard[r][c] == '.') chessboard[r][c] = 'x';
            r--;
            c--;
        }

        r = row + 1;
        c = col + 1;

        while (r < n && c < n) { // 315 deg
            if (chessboard[r][c] == '.') chessboard[r][c] = 'x';
            r++;
            c++;
        }

        r = row - 1;
        c = col + 1;

        while (r >= 0 && c < n) { // 45 deg
            if (chessboard[r][c] == '.') chessboard[r][c] = 'x';
            r--;
            c++;
        }

        r = row + 1;
        c = col - 1;

        while (r < n && c >= 0) { // 225 deg
            if (chessboard[r][c] == '.') chessboard[r][c] = 'x';
            r++;
            c--;
        }
    }
}