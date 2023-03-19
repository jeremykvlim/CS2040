import java.io.*;
import java.util.*;

public class Playground {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] firstLineInput = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        //number of playgrounds, tunnels, friends
        int playgrounds = firstLineInput[0], tunnels = firstLineInput[1], friends = firstLineInput[2];

        //non-zero indexing so arrays have to be of size playgrounds + 1

        //distance of the shortest paths for playgrounds
        int[] playgroundShortestPaths = new int[playgrounds + 1];
        //array of whether a playground passed through magic tunnel
        boolean[] isMagical = new boolean[playgrounds + 1];
        //graph of the playground complex
        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] graph = new ArrayList[playgrounds + 1];
        //fill the graph with edge lists for each playground
        for (int i = 0; i < graph.length; i++) graph[i] = new ArrayList<>();
        //friends that are on shortest and also magical path
        ArrayList<Integer> magicalFriends = new ArrayList<>();

        int[] secondLineInput = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        //source and magic tunnel
        int startingPlayground = secondLineInput[0], magicFront = secondLineInput[1], magicBack = secondLineInput[2];

        //draw graph of playground complex
        drawGraph(graph, br, tunnels);

        //calculate the shortest paths to the playgrounds and whether they are magical
        shortestPathFaster(startingPlayground, playgrounds, playgroundShortestPaths,
                graph, magicFront, magicBack, isMagical);

        for (int i = 0; i < friends; i++) {
            //playground that friend is at
            int playgroundNumber = Integer.parseInt(br.readLine());
            //if playground is magical, add friend to magic friend list
            if (isMagical[playgroundNumber]) magicalFriends.add(playgroundNumber);
        }

        //sort by increasing order
        Collections.sort(magicalFriends);

        System.out.println(magicalFriends.toString().replaceAll("\\D+", " ").trim());
    }

    //class for edge
    static class Edge {
        //destination vertex
        int to;
        //distance to destination
        int weight;

        //constructor
        Edge(int t, int w) {
            to = t;
            weight = w;
        }
    }

    //calculate the shortest paths to the playgrounds and whether they are magical
    static void shortestPathFaster(int source, int vertices, int[] shortestPathsArray,
                                   ArrayList<Edge>[] graph, int magicFront,
                                   int magicBack, boolean[] isMagical) {
        //check if a vertex is in queue
        boolean[] inQueue = new boolean[vertices + 1];

        //make all vertexes start with max integer distance
        for (int i = 0; i <= vertices; i++) shortestPathsArray[i] = Integer.MAX_VALUE;
        //source to source distance = 0
        shortestPathsArray[source] = 0;

        //queue to consider edges of a vertex
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(source);
        //first vertex is always the source
        inQueue[source] = true;

        //while queue has vertices
        while (!queue.isEmpty()) {
            //get vertex
            int from = queue.poll();
            //vertex is no longer in queue
            inQueue[from] = false;

            //for all the edges of this vertex
            for (int i = 0; i < graph[from].size(); i++) {
                //destination of edge
                int to = graph[from].get(i).to;
                //weight of edge
                int weight = graph[from].get(i).weight;

                //if edge is the magic tunnel
                boolean isMagicTunnel = to == magicFront && from == magicBack ||
                        to == magicBack && from == magicFront;

                //if the distance to destination is greater than if this edge was used
                if (shortestPathsArray[to] > shortestPathsArray[from] + weight) {
                    //update distance to destination
                    shortestPathsArray[to] = shortestPathsArray[from] + weight;
                    //if the path was magical, extend the magical path to include this vertex
                    isMagical[to] = isMagical[from];

                    //the start of any magical path
                    if (isMagicTunnel) isMagical[to] = true;

                    //if the destination vertex is not already queued up
                    if (!inQueue[to]) {
                        //add destination vertex to queue
                        queue.add(to);
                        //destination vertex is in queue
                        inQueue[to] = true;
                    }
                }

                //if the distance to destination is the same as if this edge was used
                if (shortestPathsArray[to] == shortestPathsArray[from] + weight) {
                    //as long as one of the same distance paths is magical,
                    //extend the magical path to include this vertex
                    if (isMagical[from]) isMagical[to] = true;

                    //the start of any magical path
                    if (isMagicTunnel) isMagical[to] = true;

                    //if the destination vertex is not already queued up
                    if (!inQueue[to]) {
                        //add destination vertex to queue
                        queue.add(to);
                        //destination vertex is in queue
                        inQueue[to] = true;
                    }
                }
            }
        }
    }

    //draw graph of playground complex
    static void drawGraph(ArrayList<Edge>[] graph, BufferedReader br,
                          int tunnels) throws IOException {

        //draw all the tunnels
        for (int i = 0; i < tunnels; i++) {
            //parse tunnel from string input
            int[] tunnel = Arrays.stream(br.readLine().split(" ")).
                    mapToInt(Integer::parseInt).toArray();
            //tunnel front
            int from = tunnel[0];
            //tunnel end
            int to = tunnel[1];
            //tunnel distance
            int weight = tunnel[2];
            //draw bidirectional edges on graph
            graph[from].add(new Edge(to, weight));
            graph[to].add(new Edge(from, weight));
        }
    }

}
