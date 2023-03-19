import java.util.*;
import java.io.*;

public class Pipes {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int stations;
    static int pipes;
    static int[] capacity;
    static int[] from;
    static int start = 0;
    static int end;
    static ArrayList<Pipe>[] graph;
    static PriorityQueue<Station> pq = new PriorityQueue<>();
    static TreeSet<Integer> blockedPipes = new TreeSet<>();
    static ArrayList<Integer> maximinPath = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        getParameters();

        capacity = new int[stations];
        from = new int[stations];
        Arrays.fill(capacity, Integer.MIN_VALUE);
        Arrays.fill(from, -1);
        graph = new ArrayList[stations];
        for (int i = 0; i < graph.length; i++) graph[i] = new ArrayList<>();

        drawGraph();

        dijkstra();

        getMaximinPath();

        getBlockedPipes();
    }

    static void getParameters() throws IOException {
        String[] firstLineInput = br.readLine().split(" ");
        stations = Integer.parseInt(firstLineInput[0]);
        pipes = Integer.parseInt(firstLineInput[1]);
        end = stations - 1;
    }

    static void drawGraph() throws IOException {
        for (int i = 0; i < pipes; i++) {
            int[] pipeline = Arrays.stream(br.readLine().split(" ")).
                    mapToInt(Integer::parseInt).toArray();
            int from = pipeline[0];
            int to = pipeline[1];
            int capacity = pipeline[2];

            graph[from].add(new Pipe(to, capacity, i));
            graph[to].add(new Pipe(from, capacity, i));
        }
    }

    static void dijkstra() {
        capacity[start] = Integer.MAX_VALUE;
        pq.add(new Station(start, capacity[start]));
        while (!pq.isEmpty()) {
            Station curr = pq.poll();
            if (curr.capacity == capacity[curr.stationID]) {
                for (Pipe p : graph[curr.stationID]) {
                    int c = Math.max(capacity[p.to], Math.min(capacity[curr.stationID], p.capacity));
                    if (capacity[p.to] < c) {
                        capacity[p.to] = c;
                        from[p.to] = curr.stationID;
                        pq.add(new Station(p.to, capacity[p.to]));
                    }
                }
            }
        }
    }

    static void getMaximinPath() {
        if (from[end] != -1) {
            maximinPath.add(end);
            int curr = from[end];
            while (curr != -1) {
                maximinPath.add(curr);
                curr = from[curr];
            }
        }

        Collections.reverse(maximinPath);
    }

    static void getBlockedPipes() {
        for (int i = 0; i < maximinPath.size(); i++) {
            for (Pipe p : graph[maximinPath.get(i)]) {
                if (i > 0 && i < maximinPath.size() - 1) {
                    if (p.to != maximinPath.get(i + 1) && p.to != maximinPath.get(i - 1))
                        blockedPipes.add(p.pipeID);
                }
                if (i == 0)
                    if (p.to != maximinPath.get(i + 1)) blockedPipes.add(p.pipeID);
                if (i == maximinPath.size() - 1)
                    if (p.to != maximinPath.get(i - 1)) blockedPipes.add(p.pipeID);
            }
        }
        System.out.println(blockedPipes.isEmpty() ? "none" : (blockedPipes + " ").replaceAll("[\\[\\]]", ""));
    }

    private static class Station implements Comparable<Station> {
        int stationID;
        int capacity;

        Station(int id, int c) {
            stationID = id;
            capacity = c;
        }

        @Override
        public int compareTo(Station s) {
            return s.capacity - capacity;
        }
    }

    private static class Pipe {
        int to;
        int capacity;
        int pipeID;

        Pipe(int t, int c, int id) {
            to = t;
            capacity = c;
            pipeID = id;
        }
    }
}