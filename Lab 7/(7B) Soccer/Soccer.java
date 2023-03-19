import java.io.*;
import java.util.*;

public class Soccer {

    public static HashMap<String, SoccerTeam> leagueMap = new HashMap<>();
    public static AVLTree<SoccerTeam, Integer> tree = new AVLTree<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        int numberOfCommands = Integer.parseInt(br.readLine());

        for (int i = 0; i < numberOfCommands; i++) {
            String[] command = br.readLine().split(" ");

            if (command[0].equals("ADD")) addTeam(command, i + 1);
            else if (command[0].equals("QUERY")) pw.println(teamRanking(command));
            else matchTeams(command);
        }
        pw.flush();
    }

    public static void addTeam(String[] command, int entryIndex) {
        String teamName = command[1];
        long teamRating = Long.parseLong(command[2]);
        SoccerTeam teamDetails = new SoccerTeam(teamRating, entryIndex + 1);
        leagueMap.put(teamName, teamDetails);
        tree.put(teamDetails, 0);
    }

    public static void pointsChange(long goalsX, long goalsY, SoccerTeam firstTeam, SoccerTeam secondTeam,
                                    String secondTeamName) {
        long goalDifference = Math.abs(goalsX - goalsY);
        firstTeam.points += goalDifference;
        secondTeam.points -= goalDifference;
        tree.put(firstTeam, 0);
        if (secondTeam.points <= 0) leagueMap.remove(secondTeamName);
        else tree.put(secondTeam, 0);
    }

    public static String teamRanking(String[] command) {
        String teamName = command[1];

        if (!leagueMap.containsKey(teamName)) return "Team "+teamName+" is ELIMINATED";
        else {
            SoccerTeam teamDetails = leagueMap.get(teamName);
            long points = teamDetails.points;
            int rank = tree.getRank(teamDetails);
            return "Team "+teamName+": "+points+" points, rank "+rank;
        }
    }

    public static void matchTeams(String[] command) {
        String teamNameX = command[1];
        String teamNameY = command[2];
        long goalsX = Long.parseLong(command[3]);
        long goalsY = Long.parseLong(command[4]);
        SoccerTeam teamDetailsX = leagueMap.get(teamNameX);
        SoccerTeam teamDetailsY = leagueMap.get(teamNameY);
        tree.delete(teamDetailsX);
        tree.delete(teamDetailsY);

        if (goalsX > goalsY) pointsChange(goalsX, goalsY, teamDetailsX, teamDetailsY, teamNameY);
        else if (goalsX < goalsY) pointsChange(goalsX, goalsY, teamDetailsY, teamDetailsX, teamNameX);
    }

    public static class SoccerTeam implements Comparable<SoccerTeam> {

        long points;
        int index;

        public SoccerTeam(long p, int i) {
            points = p;
            index = i;
        }

        @Override
        public int compareTo(SoccerTeam key) {
            if (key.points == points) return index - key.index;
            else return Long.compare(key.points, points);
        }
    }



    private static class AVLTree<Key extends Comparable<Key>, Value>{
        private Node root;

        private class Node {
            private final Key key;
            private Value val;
            private int height;
            private int size;
            private Node left;
            private Node right;
            private Node parent;


            public Node(Key key, Value val, int height, int size) {
                this.key = key;
                this.val = val;
                this.size = size;
                this.height = height;
            }
        }

        public int size() {
            return size(root);
        }

        private int size(Node x) {
            if (x == null)
                return 0;
            return x.size;
        }

        private int height(Node x) {
            if (x == null)
                return -1;
            return x.height;
        }

        public Value get(Key key) {
            Node x = get(root, key);
            if (x == null)
                return null;
            return x.val;
        }

        private Node get(Node x, Key key) {
            if (x == null)
                return null;
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                return get(x.left, key);
            else if (cmp > 0)
                return get(x.right, key);
            else
                return x;
        }

        private Node successor(Node x) {
            if (x.right != null) {
                Node y = x.right;
                while (y.left != null)
                    y = y.left;
                return y;
            }
            Node y = x;
            while (y.parent != null) {
                if (y == y.parent.left)
                    return y.parent;
                y = y.parent;
            }
            return null;
        }

        private Node predecessor(Node x) {
            if (x.left != null) {
                Node y = x.left;
                while (y.right != null)
                    y = y.right;
                return y;
            }
            Node y = x;
            while (y.parent != null) {
                if (y == y.parent.right)
                    return y.parent;
                y = y.parent;
            }
            return null;
        }

        public int getRank(Key key) {
            return rank(root, key);
        }

        private int rank(Node x, Key key) {
            if (x == null)
                return 0;
            int cmp = key.compareTo(x.key);
            if (cmp == 0)
                if (x.left == null)
                    return 1;
                else
                    return x.left.size + 1;
            else if (cmp < 0)
                return rank(x.left, key);
            else
                if (x.left == null)
                    return rank(x.right, key) + 1;
                else
                    return rank(x.right, key) + x.left.size + 1;
        }

        public boolean contains(Key key) {
            return get(key) != null;
        }

        public void put(Key key, Value val) {
            if (val == null) {
                delete(key);
                return;
            }
            root = put(root, key, val);
        }

        private Node put(Node x, Key key, Value val) {
            if (x == null)
                return new Node(key, val, 0, 1);
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x.left = put(x.left, key, val);
                x.left.parent = x;
            } else if (cmp > 0) {
                x.right = put(x.right, key, val);
                x.right.parent = x;
            } else {
                x.val = val;
                return x;
            }
            x.size = 1 + size(x.left) + size(x.right);
            x.height = 1 + Math.max(height(x.left), height(x.right));
            return balance(x);
        }

        private Node balance(Node x) {
            if (balanceFactor(x) < -1) {
                if (balanceFactor(x.right) > 0)
                    x.right = rotateRight(x.right);
                x = rotateLeft(x);
            } else if (balanceFactor(x) > 1) {
                if (balanceFactor(x.left) < 0)
                    x.left = rotateLeft(x.left);
                x = rotateRight(x);
            }
            return x;
        }

        private int balanceFactor(Node x) {
            return height(x.left) - height(x.right);
        }

        private Node rotateRight(Node x) {
            Node y = x.left;
            x.left = y.right;
            if (y.right != null)
                y.right.parent = x;
            y.right = x;
            y.parent = x.parent;
            x.parent = y;
            updateHeight(x, y);
            return y;
        }

        private void updateHeight(Node x, Node y) {
            y.size = x.size;
            x.size = 1 + size(x.left) + size(x.right);
            x.height = 1 + Math.max(height(x.left), height(x.right));
            y.height = 1 + Math.max(height(y.left), height(y.right));
        }

        private Node rotateLeft(Node x) {
            Node y = x.right;
            x.right = y.left;
            if (y.left != null)
                y.left.parent = x;
            y.left = x;
            y.parent = x.parent;
            x.parent = y;
            updateHeight(x, y);
            return y;
        }

        public void delete(Key key) {
            if (!contains(key))
                return;
            root = delete(root, key);
        }

        private Node delete(Node x, Key key) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                x.left = delete(x.left, key);
            else if (cmp > 0)
                x.right = delete(x.right, key);
            else {
                if (x.left == null) {
                    if (x.right != null)
                        x.right.parent = x.parent;
                    return x.right;
                } else if (x.right == null) {
                    x.left.parent = x.parent;
                    return x.left;
                } else {
                    Node y = x;
                    x = min(y.right);
                    x.right = deleteMin(y.right);
                    x.left = y.left;
                    x.parent = y.parent;
                    if (x.left != null)
                        x.left.parent = x;
                    if (x.right != null)
                        x.right.parent = x;
                }
            }
            x.size = 1 + size(x.left) + size(x.right);
            x.height = 1 + Math.max(height(x.left), height(x.right));
            return balance(x);
        }

        private Node deleteMin(Node x) {
            if (x.left == null)
                return x.right;
            x.left = deleteMin(x.left);
            x.size = 1 + size(x.left) + size(x.right);
            x.height = 1 + Math.max(height(x.left), height(x.right));
            return balance(x);
        }

        private Node min(Node x) {
            if (x.left == null)
                return x;
            return min(x.left);
        }

        private Node max(Node x) {
            return x.right == null ? x : max(x.right);
        }
    }
}
