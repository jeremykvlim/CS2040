import java.util.*;
import java.io.*;

public class LuckyDraw {
    private static Comparator<Difference> cmp = Comparator.comparingLong(d -> d.diff);
    private static TreeMap<Long, Long> map = new TreeMap<>();
    private static PriorityQueue<Difference> pq = new PriorityQueue<>(cmp);

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numberOfBalls = Integer.parseInt(br.readLine());

        for (int i = 0; i < numberOfBalls; i++) {
            long ballNumber = Long.parseLong(br.readLine());
            map.put(ballNumber, map.getOrDefault(ballNumber, 0L) + 1);
            if (map.getOrDefault(ballNumber, 0L) > 1)
                pq.add(new Difference(ballNumber, ballNumber, 0));
        }

        long root = map.lastKey();
        long size = map.size();
        for (int i = 0; i < size - 1; i++) {
            long rootLower = map.lowerKey(root);
            long diff = Math.abs(root - rootLower);
            pq.add(new Difference(root, rootLower, diff));
            root = rootLower;
        }

        int numberOfActions = Integer.parseInt(br.readLine());

        for (int i = 0; i < numberOfActions; i++) {
            String[] command = br.readLine().split(" ");
            int ballNumber = Integer.parseInt(command[1]);
            if (command[0].equals("ADD")) addBall(ballNumber);
            else removeBall(ballNumber);
        }
    }

    private static void addBall(long ballNumber) {
        if (map.containsKey(ballNumber)) {
            map.put(ballNumber, map.getOrDefault(ballNumber, 0L) + 1);
            pq.add(new Difference(ballNumber, ballNumber, 0));
            System.out.println(0);
        } else {
            map.put(ballNumber, map.getOrDefault(ballNumber, 0L) + 1);
            if (map.higherKey(ballNumber) != null) {
                long ballHigher = map.higherKey(ballNumber);
                long higherDiff = Math.abs(ballNumber - ballHigher);
                pq.add(new Difference(ballHigher, ballNumber, higherDiff));
            }
            if (map.lowerKey(ballNumber) != null) {
                long ballLower = map.lowerKey(ballNumber);
                long lowerDiff = Math.abs(ballNumber - ballLower);
                pq.add(new Difference(ballNumber, ballLower, lowerDiff));
            }
            getMinDiff();
        }
    }

    private static void removeBall(long ballNumber) {
        if (map.get(ballNumber) > 1) {
            map.put(ballNumber, map.get(ballNumber) - 1);
            getMinDiff();
        } else {
            map.remove(ballNumber);
            if (map.higherKey(ballNumber) != null) {
                long ballHigher = map.higherKey(ballNumber);
                long ballLower = map.lowerKey(ballHigher);
                long diff = Math.abs(ballHigher - ballLower);
                Difference absDiff = new Difference(ballHigher, ballLower, diff);
                pq.add(absDiff);
            }
            getMinDiff();
        }
    }

    private static void getMinDiff() {
        Difference minDiff = pq.poll();
        if (map.containsKey(minDiff.ball1) && (map.get(minDiff.ball1) > 1 ||
                (map.lowerKey(minDiff.ball1) != null &&
                        map.lowerKey(minDiff.ball1) == minDiff.ball2))) {
            System.out.println(minDiff.diff);
            pq.add(minDiff);
        } else getMinDiff();
    }

    static class Difference {
        long ball1;
        long ball2;
        long diff;

        Difference(long b1, long b2, long d) {
            ball1 = b1;
            ball2 = b2;
            diff = d;
        }
    }
}