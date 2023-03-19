import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Monsters {

    private static Comparator<Card> comparator1 = (card1, card2) -> {
        if (card1.power != card2.power) return Long.compare(card1.power, card2.power);
        else return Long.compare(card1.index, card2.index);
    };

    private static Comparator<Card> comparator2 = Comparator.comparingLong(card -> card.index);

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long numberOfCards = Long.parseLong(br.readLine());
        long[] cards = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        PriorityQueue<Card> pq = new PriorityQueue<>(comparator1);
        ArrayList<Card> list = new ArrayList<>();

        for (int i = 0; i < numberOfCards; i++) pq.add(new Card(cards[i], i));

        assert pq.peek() != null;
        Card lowestPowerCard = pq.poll();

        while (pq.size() != 0) {
            if (pq.peek().power == lowestPowerCard.power) {
                Card nextCard = pq.poll();
                pq.add(new Card(nextCard.power * 2, lowestPowerCard.index));
                if (pq.peek() != null) lowestPowerCard = pq.poll();
                else break;
            }
            else {
                list.add(lowestPowerCard);
                lowestPowerCard = pq.poll();
            }
        }
        list.add(lowestPowerCard);
        list.sort(comparator2);

        System.out.println(list.size());

        for (int i = 0; i < list.size() - 1; i++) System.out.print(list.get(i).power+" ");
        System.out.println(list.get(list.size() - 1).power);
    }


    static class Card {

        long power;
        long index;

        Card(long p, long i) {
            power = p;
            index = i;
        }
    }
}
