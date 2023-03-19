import java.io.*;
import java.util.*;

public class Deliveries {
        /*
        Purpose:
        find the max revenue to be made by choosing
        certain deliveries from a delivery request list
        using a PriorityQueue or binary heap

        Pre-conditions:
        1<=n<=200000, number of delivery requests is meq to 1 and leq to 200000
        1<=T<=2419200, request deadline is meq to 1 and leq to 2419200
        1<=C<=100000, request payment is meq to 1 and leq to 100000
        only 1 delivery can be made per unit deadline

        Post-conditions:
        could not find any
        */

    // list of all delivery requests
    private static ArrayList<Delivery> delivRequestList = new ArrayList<>();

    // max heap priority queue for max payments from deliveries
    private static PriorityQueue<Long> maxHeap = new PriorityQueue<>();

    /* delivery class that implements Comparable to sort by deadline,
       or payment if deadlines of requests are equal */
    static class Delivery implements Comparable<Delivery> {

        // deadline to fulfill request
        long deadline;
        // payment for fulfilling request
        long payment;

        // class constructor
        Delivery(long dl, long pay) {
            deadline = dl;
            payment = pay;
        }

        // override default Comparator
        @Override
        // comparing between requests,
        public int compareTo(Delivery request) {
            // if delivery deadlines are different
            if (deadline != request.deadline)
                // sort by deadline
                return Long.compare(deadline, request.deadline);
            // else if delivery deadlines are the same
            else
                // sort by payment
                return Long.compare(payment, request.payment);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // parse n, the number of delivery requests
        int numberOfDelivRequests = Integer.parseInt(br.readLine());

        // for each request
        for (int i = 0; i < numberOfDelivRequests; i++) {
            // split input line into command array
            String[] command = br.readLine().split(" ");
            // add the request to a list
            addRequest(command);
        }
        // sort the list
        Collections.sort(delivRequestList);
        // print out the max revenue as answer
        System.out.println(getRevenueFromDelivs());
    }

    // method to calculate the max revenue from deliveries (answer)
    private static long getRevenueFromDelivs() {
        // for each request in the list
        for (Delivery requests : delivRequestList) {
            // add the payment of the request into the heap
            maxHeap.add(requests.payment);
            // only 1 delivery per unit deadline
            while (maxHeap.size() > requests.deadline)
                // keep only the highest paying requests per unit deadline
                maxHeap.remove();
        }
        // initialise revenue sum
        long revenue = 0;
        while (!maxHeap.isEmpty())
            // sum up all the payments from the heap
            revenue += maxHeap.remove();
        // return revenue sum
        return revenue;
    }

    // method that adds a delivery request to the delivery request list
    private static void addRequest(String[] command) {
        // deadline of request
        int requestDeadline = Integer.parseInt(command[0]);
        // payment of request
        int requestPayment = Integer.parseInt(command[1]);
        // add delivery request to list
        delivRequestList.add(new Delivery(requestDeadline, requestPayment));
    }
}
