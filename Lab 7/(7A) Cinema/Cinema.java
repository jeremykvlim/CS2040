import java.io.*;
import java.util.*;


public class Cinema {

    private static final long ENJOYMENT_BOUND = 100000;
    private static final int INDEX_BOUND = 1000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int numberOfInputs = Integer.parseInt(br.readLine());
        long salary = 0;
        long enjoyment = 0;
        TreeSet<Movie> movieSet = new TreeSet<>();

        for (int i = 0; i < numberOfInputs; i++) {
            String[] command = br.readLine().split(" ");

            if (command[0].equals("ADD")) {
                long moviePrice = Integer.parseInt(command[1]);
                long movieEnjoyment = Integer.parseInt(command[2]);
                movieSet.add(new Movie(moviePrice, movieEnjoyment, i));
            }
            else if (command[0].equals("SALARY")) salary += Integer.parseInt(command[1]);
            else {
                while (salary > 0) {
                    Movie highestPriceMovie = movieSet.floor(new Movie(salary, ENJOYMENT_BOUND, INDEX_BOUND));
                    if (highestPriceMovie != null) {
                        salary -= highestPriceMovie.price;
                        enjoyment += highestPriceMovie.enjoyment;
                        movieSet.remove(highestPriceMovie);
                    } else break;
                }
                bw.write(enjoyment+"\n");
                enjoyment = 0;
            }
        }
        bw.flush();
    }

    static class Movie implements Comparable<Movie> {
        long price;
        long enjoyment;
        int index;

        public Movie(long price, long enjoyment, int index) {
            this.price = price;
            this.enjoyment = enjoyment;
            this.index = index;
        }

        @Override public int compareTo(Movie movie) {
            if (this.price != movie.price) return Long.compare(this.price, movie.price);
            else if (this.enjoyment != movie.enjoyment) return Long.compare(this.enjoyment, movie.enjoyment);
            else return this.index - movie.index;
        }
    }
}







