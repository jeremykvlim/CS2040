import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Figurines {

    private PriorityQueue<Long> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    private PriorityQueue<Long> minHeap = new PriorityQueue<>();
    private boolean isEven = true;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String command;

        Figurines f = new Figurines();

        while ((command = br.readLine()) != null) {
            if (command.equals("INSPECT")) f.removeMedian();
            else f.addLong(Long.parseLong(command));
        }
    }
    private void addLong(long l) {
        if (isEven) {
            minHeap.add(l);
            maxHeap.add(minHeap.remove());
        }
        else {
            maxHeap.add(l);
            minHeap.add(maxHeap.remove());
        }
        isEven = !isEven;
    }

    private void removeMedian() {
        if (isEven) System.out.println(minHeap.remove());

        else System.out.println(maxHeap.remove());

        isEven = !isEven;
    }
}
