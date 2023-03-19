import java.io.*;
import java.util.*;

public class Elementals {

    //purpose rephrased: draw a right-angled triangle of hypotenuse R
    //each elemental contributes a certain length to the height and width of the triangle
    //can reuse elementals, find minimum number of elementals used
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String[] ans = new String[1];
    static int elementals;
    static int hypotenuse;

    public static void main(String[] args) throws IOException {
        int testCases = Integer.parseInt(br.readLine());

        for (int i = 0; i < testCases; i++) {
            String[] parameters = br.readLine().split(" ");
            elementals = Integer.parseInt(parameters[0]);
            hypotenuse = Integer.parseInt(parameters[1]);

            ArrayList<Elemental> elementalList = new ArrayList<>(elementals);

            for (int j = 0; j < elementals; j++) {
                String[] elemental = br.readLine().split(" ");
                int height = Integer.parseInt(elemental[0]);
                int width = Integer.parseInt(elemental[1]);
                elementalList.add(new Elemental(height, width, 1));
            }

            bfs(elementalList);

            System.out.println(ans[0]);
        }
    }


    static void bfs(ArrayList<Elemental> elementalList) {
        boolean[][] visited = new boolean[hypotenuse+1][hypotenuse+1];
        LinkedList<Elemental> queue = new LinkedList<>();

        if (searchSuccessful(new Elemental(0, 0, 0), elementalList, visited, queue)) return;

        while (!queue.isEmpty()) {
            Elemental upgradedElemental = queue.poll();
            if (searchSuccessful(upgradedElemental, elementalList, visited, queue)) return;
        }

        ans[0] = "not possible";
    }

    static boolean searchSuccessful(Elemental upgradedElemental, ArrayList<Elemental> elementalList,
                                    boolean[][] visited, LinkedList<Elemental> queue) {
        for (Elemental e : elementalList) {
            int newHeight = e.height + upgradedElemental.height;
            int newWidth = e.width + upgradedElemental.width;
            int currHypotenuse = newHeight*newHeight + newWidth*newWidth;
            if (currHypotenuse == hypotenuse*hypotenuse) {
                ans[0] = String.valueOf(upgradedElemental.pass + 1);
                return true;
            }
            else if (currHypotenuse < hypotenuse*hypotenuse &&
                    !visited[newHeight][newWidth]) {
                queue.add(new Elemental(newHeight, newWidth, upgradedElemental.pass + 1));
                visited[newHeight][newWidth] = true;
            }
        }
        return false;
    }

    static class Elemental {
        int height;
        int width;
        int pass;

        Elemental(int h, int w, int p) {
            height = h;
            width = w;
            pass = p;
        }
    }
}
