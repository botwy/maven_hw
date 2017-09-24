import java.util.*;

public class Solution {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        sc.nextInt();

        TreeSet<Integer> list = new TreeSet<>();
        while (sc.hasNextInt()) {
            int curr = sc.nextInt();
            list.add(curr);

            System.out.print((list.higher(curr) == null ? -1 : list.higher(curr)) + " ");

        }


    }
}
