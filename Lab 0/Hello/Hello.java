import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hello {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int helloCounter = 0;
        int worldCounter = 0;

        String[] words = br.readLine().split(" ");
        for (String word : words) {
            if (word.equals("Hello")) helloCounter++;
            else if (word.equals("World")) worldCounter++;
        }

        System.out.println(helloCounter - worldCounter);
    }
}
