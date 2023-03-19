import java.util.*;
import java.io.*;

public class Students {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static HashSet<String> studentSet = new HashSet<>();
    static HashSet<String> unknownSet = new HashSet<>();
    static HashMap<String, LinkedList<String>> adjListMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");
        int participants = Integer.parseInt(input[0]);
        int entries = Integer.parseInt(input[1]);

        for (int i = 0; i < participants; i++) fillSetsOfPeople();

        for (int i = 0; i < entries; i++) processEntry();

        System.out.println(getOutcome());
    }

    static void fillSetsOfPeople() throws IOException {

        //read and split participant details
        String[] participantDetails = br.readLine().split(" ");

        String name = participantDetails[0];
        String status = participantDetails[1];

        //cases for the 3 different statuses
        switch (status) {
            //if status.equals("n"), add person to non-student list
            case "s" -> studentSet.add(name);
            //if status.equals("?"), add person to unknown set
            case "?" -> unknownSet.add(name);
            //if status.equals("s"), add person to student set
            default -> {}
        }
    }

    static void processEntry() throws IOException {
        String[] entry = br.readLine().split(" ");
        String participant1 = entry[0];
        String participant2 = entry[2];

        LinkedList<String> adjList = adjListMap.getOrDefault(participant1, new LinkedList<>());
        adjList.add(participant2);
        adjListMap.put(participant1, adjList);
    }

    static String getOutcome() {
        HashSet<String> visited = new HashSet<>();
        boolean isUnclear = false;
        LinkedList<String> q = new LinkedList<>();

        for (String student : studentSet) {
            if (visited.contains(student)) continue;
            q.add(student);
            visited.add(student);

            while (!q.isEmpty()) {
                String curr = q.poll();
                LinkedList<String> adjList = adjListMap.getOrDefault(curr, new LinkedList<>());
                for (String adj : adjList) {
                    if (visited.contains(adj)) continue;
                    if (unknownSet.contains(adj)) isUnclear = true;
                    else if (!studentSet.contains(adj)) return "VICTORY";

                    visited.add(adj);
                    q.add(adj);
                }
            }
        }

        if (isUnclear) return "OUTCOME UNCLEAR";

        for (String unknown : unknownSet) {
            if (visited.contains(unknown)) continue;
            q.add(unknown);
            visited.add(unknown);

            while (!q.isEmpty()) {
                String curr = q.poll();
                LinkedList<String> adjList = adjListMap.getOrDefault(curr, new LinkedList<>());
                for (String adj : adjList) {
                    if (visited.contains(adj)) continue;
                    if (unknownSet.contains(adj)) return "OUTCOME UNCLEAR";
                    else if (!studentSet.contains(adj)) return "OUTCOME UNCLEAR";

                    visited.add(adj);
                    q.add(adj);
                }
            }
        }
        return "EVERYONE LOSES";
    }
}