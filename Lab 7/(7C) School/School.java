import java.io.*;
import java.util.*;

public class School {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter pw = new PrintWriter(System.out);
    static HashMap<Integer, Integer> compressAge = new HashMap<>();
    static HashMap<String, Integer> compressName = new HashMap<>();
    static Student[] studentArray;
    static Student[] segTree;
    static int[] ageArray;
    static int students;

    public static void main(String[] args) throws IOException {
        students = Integer.parseInt(br.readLine());
        //non-zero indexing question since no student of age 0
        studentArray = new Student[students + 1];
        ageArray = new int[students + 1];
        segTree = new Student[2*students];

        //ignore index 0
        studentArray[0] = new Student(" ", 0, 0);

        for (int i = 1; i <= students; i++) {
            String[] studentDetails = br.readLine().split(" ");
            String name = studentDetails[0];
            int age = Integer.parseInt(studentDetails[1]);
            long coolness = Long.parseLong(studentDetails[2]);
            studentArray[i] = new Student(name, age, coolness);
            ageArray[i] = age;
        }

        //sort by increasing order of age
        Arrays.sort(ageArray);
        Arrays.sort(studentArray);

        compressCoordinates();

        //buildSegTree(1, 1, students);
        buildSegTree();

        int numberOfQueries = Integer.parseInt(br.readLine());

        for (int i = 0; i < numberOfQueries; i++) {
            String[] query = br.readLine().split(" ");

            switch (query[0]) {
                //coolest student range query of age 1 - queried age
                case "QUERY" -> {
                    int age = Integer.parseInt(query[1]);
                    //if no student of this age, get floor of age
                    if (!compressAge.containsKey(age)) age = binarySearch(age);
                    //get compressed age index
                    int compressedAge = compressAge.get(age);
                    //Student s = query(1, 1, students, compressedAge);
                    Student s = query(compressedAge);
                    pw.println(s.name+" "+s.coolness);
                }
                //increment coolness
                case "INCREMENT" -> {
                    int compressedAge = compressName.get(query[1]);
                    long coolnessChange = Long.parseLong(query[2]);
                    //update(1, 1, students, compressedAge, coolnessChange);
                    update(compressedAge, coolnessChange);
                }
                //decrement coolness
                default -> {
                    int compressedAge = compressName.get(query[1]);
                    long coolnessChange = Long.parseLong(query[2]) * -1;
                    //update(1, 1, students, compressedAge, coolnessChange);
                    update(compressedAge, coolnessChange);
                }
            }
        }
        pw.flush();
    }

    //compress ages into indices
    static void compressCoordinates() {
        for (int i = 1; i <= students; i++) {
            int age = studentArray[i].age;
            String name = studentArray[i].name;
            compressAge.put(age, i);
            //compress names for query command
            compressName.put(name, i);
        }
    }

    //binary search for floor of age
    static int binarySearch(int age) {
        int floor = 0;
        int l = 0;
        int r = ageArray.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (ageArray[m] >= age) r = m - 1;
            else {
                floor = ageArray[m];
                l = m + 1;
            }
        }
        return floor;
    }

    //custom student class
    static class Student implements Comparable<Student> {
        String name;
        int age;
        long coolness;

        Student(String n, int a, long c) {
            name = n;
            age = a;
            coolness = c;
        }

        @Override
        public int compareTo(Student s) {
            return age - s.age;
        }
    }

    //build segment tree array recursively
    static void buildSegTree(int index, int l, int r) {
        //if l == r, means at range (l - l), where age = l
        //index of leaf node that represents student of age l
        if (l == r) segTree[index] = studentArray[l];

        else {
            int m = l + (r - l) / 2;
            //recurse on left subtree to find appropriate leaf
            buildSegTree(2*index, l, m);
            //recurse on right subtree to find appropriate leaf
            buildSegTree(2*index + 1, m + 1, r);
            //update coolest student of current tree node after recursing subtrees
            segTree[index] = compare2Students(segTree[2*index], segTree[2*index + 1]);
        }
    }

    //build segment tree array iteratively
    static void buildSegTree() {
        //start building from leaves
        if (students >= 0) System.arraycopy(studentArray, 1, segTree, students, students);
        //for loop version
        //for (int i = 1; i <= students; i++) segTree[students + i - 1] = studentArray[i];
        //build parent of leaves
        for (int i = students - 1; i > 0; i--) segTree[i] = compare2Students(segTree[2*i], segTree[2*i + 1]);
    }

    //update student coolness recursively
    static void update(int index, int l, int r, int age, long c) {
        //if l == r, means at range (age - age), i.e. the student
        //add coolness change
        if (l == r) studentArray[l].coolness += c;

        else {
            int m = l + (r - l) / 2;
            //if left subtree contains the student, recurse on left subtree
            if (l <= age && age <= m) update(2*index, l, m, age, c);
            //else recurse on right subtree
            else update(2*index + 1, m + 1, r, age, c);
            //update coolest student of current tree node after recursing subtrees
            segTree[index] = compare2Students(segTree[2*index], segTree[2*index + 1]);
        }
    }

    //update student coolness iteratively
    static void update(int age, long c) {
        //get index of leaf containing student of age
        age += students - 1;
        //add coolness
        segTree[age].coolness += c;
        //update parent
        for (int i = age; i > 1; i /= 2) segTree[i / 2] = compare2Students(segTree[i], segTree[i ^ 1]);
    }

    //compare 2 students to see who is cooler
    static Student compare2Students(Student s1, Student s2) {
        //return cooler student
        if (s1.coolness > s2.coolness) return s1;
        else if (s1.coolness < s2.coolness) return s2;
            //return younger student if same coolness
        else return s1.age > s2.age ? s2 : s1;
    }

    //query coolest student of certain age recursively
    static Student query(int index, int l, int r, int age) {
        //no student if out of range
        if (age < l) return null;

        //range query is always (1 - age)
        //so if tree node represents (1 - age) then return node
        if (1 <= l && r <= age) return segTree[index];

        int m = l + (r - l) / 2;
        //recurse on left subtree
        Student s1 = query(2*index, l, m, age);
        //recurse on right subtree
        Student s2 = query(2*index + 1, m + 1, r, age);

        //if a subtree recursion returns null, return non-null result
        if (s1 == null) return s2;
        if (s2 == null) return s1;

        //return cooler student of the 2 subtree recursions
        return compare2Students(s1, s2);
    }

    //query coolest student of certain age iteratively
    static Student query(int age) {
        Student s = null;
        //left-bound of range is always 1
        int l = 1;

        //while loop version
        ////left index is first leaf node
        //l += students - 1;
        ////right index is leaf node of youngest student older than age
        //age += students;

        //while (l < age) {
        //    //if left index is right child
        //    if (l % 2 == 1) {
        //        if (s == null) s = segTree[l];
        //        else s = compare2Students(s, segTree[l]);
        //        //visit neighbouring subtree
        //        l++;
        //    }
        //    //if right index is right child
        //    if (age % 2 == 1) {
        //        //swap to left child
        //        age--;
        //        if (s == null) s = segTree[age];
        //        else s = compare2Students(s, segTree[age]);
        //    }

        //    //traverse up to parent
        //    l /= 2;
        //    age /= 2;
        //}

        //for loop version
        for (l += students - 1, age += students; l < age; l >>= 1, age >>= 1) {
            if ((l & 1) == 1) {
                if (s == null) s = segTree[l++];
                else s = compare2Students(s, segTree[l++]);
            }
            if ((age & 1) == 1) {
                if (s == null) s = segTree[--age];
                else s = compare2Students(s, segTree[--age]);
            }
        }

        return s;
    }
}