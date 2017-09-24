import java.util.*;

public class SolutionQue {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int count = Integer.parseInt(scanner.nextLine());
        PriorityQueue<Integer> que = new PriorityQueue<>();
        List<Integer> result_list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String[] arr = scanner.nextLine().split("[\\s]+");
            if (Integer.parseInt(arr[0]) == 1)
                que.add(Integer.parseInt(arr[1]));
            else
                if (que.size() > 0)
                    result_list.add(que.poll());

        }

        for (Integer result : result_list)
            System.out.println(result);


    }


}