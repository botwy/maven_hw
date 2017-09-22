import java.util.*;

public class Solution {
    public static void main(String[] args) {
        String text = args[0];
        Scanner scanner = new Scanner(text);
        int count = Integer.parseInt(scanner.nextLine());

        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        List<Integer> result_list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String[] arr = scanner.nextLine().split("[\\s]+");

            if (Integer.parseInt(arr[0]) == 1) {
                int number = Integer.parseInt(arr[1]);

                if (!map.containsKey(number))
                    map.put(number, 1);
                else map.put(number, map.get(number) + 1);
            } else if (Integer.parseInt(arr[0]) == 2) {


                if (map.size() > 0) {
                    Map.Entry<Integer, Integer> first = map.entrySet().iterator().next();
                    result_list.add(first.getKey());

                    if (first.getValue() > 1)
                        map.put(first.getKey(), first.getValue() - 1);
                    else
                        map.remove(first.getKey());

                }

            }
        }

        for (Integer result : result_list
                ) {
            System.out.println(result);
        }

    }


}