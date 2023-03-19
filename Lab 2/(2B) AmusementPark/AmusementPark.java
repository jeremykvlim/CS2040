import java.io.*;
import java.util.*;

public class AmusementPark {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int dayCount = 1;
        String command;
        ArrayDeque<Integer> rides = new ArrayDeque<>();
        ArrayList<ArrayDeque<Integer>> ridesList = new ArrayList<>();

        while (!(command = br.readLine()).equals("END")) {
            String[] commandSplit = command.split(" ");
            switch (commandSplit[0]) {
                case "START" -> rides.add(Integer.parseInt(commandSplit[2]));

                case "NEXT" -> {
                    if (commandSplit[1].equals("RIDE:")) rides.add(Integer.parseInt(commandSplit[2]));
                    else {
                        ridesList.add(new ArrayDeque<>(rides));
                        rides.clear();
                    }
                }

                case "DELETE" -> {
                    if (commandSplit[1].equals("FRONT")) {
                        if (isInvalid(rides, Integer.parseInt(commandSplit[3]))) continue;
                        for (int i = 0; i < Integer.parseInt(commandSplit[3]); i++) rides.poll();
                    }
                    else {
                        if (isInvalid(rides, Integer.parseInt(commandSplit[3]))) continue;
                        for (int i = 0; i < Integer.parseInt(commandSplit[3]); i++) rides.pollLast();
                    }
                }

                default -> {
                    if (commandSplit[1].equals("FRONT")) {
                        if (isInvalid(rides, Integer.parseInt(commandSplit[3]))) continue;
                        for (int i = 0; i < Integer.parseInt(commandSplit[3]); i++) rides.poll();
                        rides.addFirst(Integer.parseInt(commandSplit[4]));
                    }
                    else {
                        if (isInvalid(rides, Integer.parseInt(commandSplit[3]))) continue;
                        for (int i = 0; i < Integer.parseInt(commandSplit[3]); i++) rides.pollLast();
                        rides.add(Integer.parseInt(commandSplit[4]));
                    }
                }
            }
        }
        ridesList.add(new ArrayDeque<>(rides));
        for (ArrayDeque<Integer> ridesByDay : ridesList) System.out.println("Day " + dayCount++ + ": " + ridesByDay);
    }

    static boolean isInvalid(ArrayDeque<Integer> deq, int i) {
        if (deq.size() < i) {
            System.out.println("Invalid command");
            return true;
        }
        return false;
    }
}
