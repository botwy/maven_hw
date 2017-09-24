import java.util.*;

public class SolutionMap {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = "";
        int max = 1;
        while (scanner.hasNextLine())
            text += " " + scanner.nextLine();

        String[] arr = text.trim().split("[\\n \\r \\t]+");
        HashMap<String, Integer> hashMap_word = new HashMap<String, Integer>();

        for (String word : arr) {
            word = word.toLowerCase();
            int curr_count = 1;
            if (hashMap_word.containsKey(word)) {
                curr_count = hashMap_word.get(word) + 1;
                if (curr_count > max) max = curr_count;
            }
            hashMap_word.put(word, curr_count);
        }
        final int mf = max;
        hashMap_word.entrySet()
                .stream().filter(x -> x.getValue() == mf)
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                .forEach(x -> System.out.println(x.getKey()));
    }
}