import java.util.*;
import java.io.*;

public class Modules {

    static HashMap<String, ArrayList<String>> graph = new HashMap<>();
    static HashMap<String, Integer> prereqMap = new HashMap<>();
    static TreeSet<String> set = new TreeSet<>();
    static ArrayList<String> ans = new ArrayList<>();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int numberOfTestCases = Integer.parseInt(br.readLine());
        int testCasesRemaining = numberOfTestCases;

        for (int i = 0; i < numberOfTestCases; i++) {
            int numberOfModules = Integer.parseInt(br.readLine());

            drawGraph(numberOfModules);

            kahn();

            if (ans.size() == numberOfModules)
                for (String mod : ans) System.out.println(mod);
            else System.out.println("BUG FOUND");
            ans.clear();

            if (--testCasesRemaining > 0) System.out.println();
        }
    }

    static void drawGraph(int numberOfModules) throws IOException {
        for (int j = 0; j < numberOfModules; j++) {
            String[] module = br.readLine().split(" ");
            if (!graph.containsKey(module[0])) graph.put(module[0], new ArrayList<>());
            if (module.length > 1) {
                prereqMap.put(module[0], module.length - 1);
                for (int index = 1; index < module.length; index++) {
                    ArrayList<String> adjList = graph.getOrDefault(module[index], new ArrayList<>());
                    adjList.add(module[0]);
                    graph.put(module[index], adjList);
                }
            } else set.add(module[0]);
        }
    }

    static void kahn() {
        while (!set.isEmpty()) {
            String mod = set.pollFirst();
            ArrayList<String> preclusion = graph.get(mod);
            if (!preclusion.isEmpty()) {
                for (String module : preclusion) {
                    int prereqsRemaining = prereqMap.get(module) - 1;
                    if (prereqsRemaining == 0) set.add(module);
                    else prereqMap.replace(module, prereqsRemaining);
                }
            }
            ans.add(mod);
        }
    }
}
