
import java.io.*;
import java.util.*;

public class Supermarket {

    static class CustomerQueue {
        LinkedList<Integer> queue;
        int queueNumber;
        int queueSize;
        int queuePeek;

        CustomerQueue(LinkedList<Integer> q, int qNumber, int qSize, int qPeek) {
            queue = q;
            queueNumber = qNumber;
            queueSize = qSize;
            queuePeek = qPeek;
        }
    }

    public static Comparator<CustomerQueue> comparator1 = (q1, q2) -> {
        if (q1.queueSize != q2.queueSize) return q1.queueSize - q2.queueSize;
        else return q1.queueNumber - q2.queueNumber;
    };

    public static Comparator<CustomerQueue> comparator2 = (q1, q2) -> {
        if (q1.queuePeek != q2.queuePeek) return q1.queuePeek - q2.queuePeek;
        else return q1.queueNumber - q2.queueNumber;
    };

    public static TreeMap<CustomerQueue, Integer> sortBySize = new TreeMap<>(comparator1);
    public static TreeMap<CustomerQueue, Integer> sortByPeek = new TreeMap<>(comparator2);
    public static HashMap<Integer, CustomerQueue> cqMapping = new HashMap<>();

    public static final int NULL_AVOIDANCE = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputParameters = br.readLine().split(" ");
        int numberOfQueues = Integer.parseInt(inputParameters[0]);

        for (int i = 1 ; i <= numberOfQueues; i++) {
            LinkedList<Integer> queue = new LinkedList<>();
            sortBySize.put(new CustomerQueue(queue, i, 0, NULL_AVOIDANCE), i);
            sortByPeek.put(new CustomerQueue(queue, i, 0, NULL_AVOIDANCE), i);
            cqMapping.put(i, new CustomerQueue(queue, i, 0, NULL_AVOIDANCE));
        }

        int numberOfQueries = Integer.parseInt(inputParameters[1]);

        for (int i = 0; i < numberOfQueries; i++) {
            String[] command = br.readLine().split(" ");

            switch (command[0]) {
                case "join" -> {
                    int angriness = Integer.parseInt(command[1]);
                    System.out.println(joinQueue(angriness));
                }
                case "serve" -> {
                    CustomerQueue cq = sortByPeek.firstKey();
                    int index = sortBySize.get(cq);
                    System.out.println(serveQueue(cq, index));
                }
                default -> {
                    int index = Integer.parseInt(command[1]);
                    CustomerQueue cq = cqMapping.get(index);
                    System.out.println(joinQueue(serveQueue(cq, index)));
                }
            }
        }
    }

    private static int joinQueue(int angriness) {
        CustomerQueue cq = sortBySize.firstKey();
        sortByPeek.remove(cq);
        int index = sortBySize.remove(cq);
        cq.queue.add(angriness);
        cq.queuePeek = cq.queue.peek();
        cq.queueSize++;
        sortBySize.put(cq, index);
        sortByPeek.put(cq, index);
        cqMapping.replace(index, cq);
        return index;
    }

    private static int serveQueue(CustomerQueue cq, int index) {
        sortByPeek.remove(cq);
        sortBySize.remove(cq);
        int angriness = cq.queue.pop();
        if (cq.queue.peek() != null) cq.queuePeek = cq.queue.peek();
        else cq.queuePeek = NULL_AVOIDANCE;
        cq.queueSize--;
        sortByPeek.put(cq, index);
        sortBySize.put(cq, index);
        cqMapping.replace(index, cq);
        return angriness;
    }
}
