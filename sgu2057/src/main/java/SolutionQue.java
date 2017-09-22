import java.util.*;

public class SolutionQue {
    public static void main(String[] args) {
        String text = args[0];
        Scanner scanner = new Scanner(text);
        int count = Integer.parseInt(scanner.nextLine());

        PriorityQueue<Integer> que = new PriorityQueue<>();
        List<Integer> result_list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String[] arr = scanner.nextLine().split("[\\s]+");

            if (Integer.parseInt(arr[0]) == 1) {
                int number = Integer.parseInt(arr[1]);

                que.add(number);
            } else if (Integer.parseInt(arr[0]) == 2) {


                if (que.size() > 0) {
                    result_list.add(que.poll());

                }

            }
        }

        for (Integer result : result_list
                ) {
            System.out.println(result);
        }

    }


}