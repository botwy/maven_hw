import java.util.*;

public class SolutionSet {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = "";
        while (scanner.hasNextLine())
            text += " "+scanner.nextLine();

        String[] arr = text.split("[\\n \\r \\t]+");
        HashSet<String> set = new HashSet<>();
        PriorityQueue<String> que = new PriorityQueue<>();
        for (String word : arr) {
            word = word.toLowerCase();
            int prev = set.size();
            que.add(word);

        }



        for (String str : que)
            System.out.println(str);


    }
}