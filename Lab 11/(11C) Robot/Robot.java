import java.io.*;
import java.util.*;

public class Robot {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int stations;
    static int roads;
    //graph of all stations
    static Station[] stationGraph;
    //priority queue, using TreeSet for decrease key operation in O(logn)
    static TreeSet<State> pq = new TreeSet<>();

    public static void main(String[] args) throws IOException {
        int[] inputs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        stations = inputs[0];
        roads = inputs[1];
        //instantiate station graph space
        stationGraph = new Station[stations];

        int[] batteryPrices = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        //instantiate stations
        for (int i = 0; i < stations; i++) stationGraph[i] = new Station(batteryPrices[i], 0, new ArrayList<>());

        drawGraph();

        int queries = Integer.parseInt(br.readLine());

        for (int i = 0; i < queries; i++) {
            int[] query = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            int minCost = getMinCost(query);

            if (minCost == -1) System.out.println("impossible");
            else System.out.println(minCost);
            pq.clear();
        }
    }

    static void drawGraph() throws IOException {
        for (int i = 0; i < roads; i++) {
            int[] road = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int from = road[0];
            int to = road[1];
            int distance = road[2];

            //add roads to stations
            stationGraph[from].roads.add(new Road(to, distance));
            stationGraph[to].roads.add(new Road(from, distance));

            //update min distance between station and any other station
            if (stationGraph[from].roads.size() == 1) stationGraph[from].minDistance = distance;
            else stationGraph[from].minDistance = Math.min(stationGraph[from].minDistance, distance);

            if (stationGraph[to].roads.size() == 1) stationGraph[to].minDistance = distance;
            else stationGraph[to].minDistance = Math.min(stationGraph[to].minDistance, distance);
        }
    }

    static int getMinCost(int[] query) {
        //read query inputs
        int capacity = query[0];
        int source = query[1];
        int destination = query[2];

        //if no travelling needed
        if (source == destination) return 0;

        //visited matrix for states
        boolean[][] visited = new boolean[stations][capacity + 1];
        //state matrix
        //stores states of the robot at a certain station but having bought different # of batteries
        //e.g. robot can reach station n with m batteries, stored as stateMatrix[n][m]
        State[][] stateMatrix = new State[stations][capacity + 1];

        //instantiate state matrix
        for (int i = 0; i < stations; i++)
            for (int j = 0; j < capacity + 1; j++) stateMatrix[i][j] = new State(0, 0, 0);

        //push starting state into pq and mark as visited
        State start = new State(source, 0, 0);
        stateMatrix[source][0] = start;
        pq.add(start);
        visited[source][0] = true;

        //dijkstra
        while (!pq.isEmpty()) {
            State current = pq.pollFirst();

            //if the robot has reached the destination and has bought the minimum number of batteries to travel
            if (current.stationID == destination && current.batteries == 0)
                //return min cost of getting to destination
                return stateMatrix[destination][0].minCost;

            //batteries to buy at the station
            int boughtBatteries;
            //if current batteries is not enough to travel to the next station
            if (current.batteries < stationGraph[current.stationID].minDistance)
                //buy only the minimum required amount of batteries to travel
                boughtBatteries = stationGraph[current.stationID].minDistance - current.batteries;
                //else just buy 1 battery and propagate resultant valid states
            else boughtBatteries = 1;

            //cost = min cost so far of getting to this state + cost of batteries
            int cost = stateMatrix[current.stationID][current.batteries].minCost +
                    boughtBatteries * stationGraph[current.stationID].batteryPrice;

            //relax the roads for this station
            relax(stateMatrix, visited, capacity, current.stationID, current.batteries + boughtBatteries, cost);

            //update cost of getting to this state after relaxing
            cost = stateMatrix[current.stationID][current.batteries].minCost;

            //for all the roads connected to the station
            for (Road road : stationGraph[current.stationID].roads)
                //relax the connected stations, taking into account the batteries consumed for the travel
                relax(stateMatrix, visited, capacity, road.to, current.batteries - road.distance, cost);
        }

        //if robot cannot reach destination (i.e. destination has no road connections), return -1
        return -1;
    }

    static void relax(State[][] stateMatrix, boolean[][] visited, int capacity,
                      int destination, int batteries, int cost) {

        //if batteries are within the stored capacity bounds and
        //the state has not been visited before OR the state has been visited before but using a less efficient path
        if (batteries >= 0 && batteries <= capacity &&
                (!visited[destination][batteries] || stateMatrix[destination][batteries].minCost > cost)) {

            //if first time visiting state
            if (!visited[destination][batteries]) {
                //inherit state in state matrix and mark as visited
                stateMatrix[destination][batteries].stationID = destination;
                stateMatrix[destination][batteries].batteries = batteries;
                visited[destination][batteries] = true;
            }

            //decrease key in pq by removing from TreeSet
            pq.remove(stateMatrix[destination][batteries]);
            //update the min cost (key) needed to get to this state
            stateMatrix[destination][batteries].minCost = cost;
            //push back into pq
            pq.add(stateMatrix[destination][batteries]);
        }
    }

    //custom class for road (edge)
    static class Road {
        //destination
        int to;
        //weight
        int distance;

        Road(int t, int d) {
            to = t;
            distance = d;
        }
    }

    //custom class for station
    static class Station {
        //price of batteries at station
        int batteryPrice;
        //minimum distance required to reach station from any other station
        //basically minimum element in the adjacency list
        int minDistance;
        //adjacency list of station
        ArrayList<Road> roads;

        //constructor
        Station(int bp, int md, ArrayList<Road> r) {
            batteryPrice = bp;
            minDistance = md;
            roads = r;
        }
    }

    //custom class for state
    //state represents the circumstance of the robot
    static class State implements Comparable<State> {
        //station that the robot is at
        int stationID;
        //batteries that the robot has
        int batteries;
        //cost of getting to this state
        int minCost;

        //constructor
        State(int id, int batt, int mc) {
            stationID = id;
            batteries = batt;
            minCost = mc;
        }

        //comparator for priority queue
        @Override
        public int compareTo(State s) {
            //sort states by min cost
            if (minCost != s.minCost) return minCost - s.minCost;
                //if same min cost sort by less batteries used
            else if (batteries != s.batteries) return batteries - s.batteries;
                //if same batteries sort by station id
            else return stationID - s.stationID;
        }
    }
}