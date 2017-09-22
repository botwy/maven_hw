import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class SolutionArr {
    public static void main(String[] args) {
        String text = args[0];
        Scanner scanner = new Scanner(text);
        int count = Integer.parseInt(scanner.nextLine());

        int[] arr_int = new int[count];
        List<Integer> result_list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String[] arr = scanner.nextLine().split("[\\s]+");

            if (Integer.parseInt(arr[0]) == 1) {
                int number = Integer.parseInt(arr[1]);

                arr_int[i] = number;
            } else if (Integer.parseInt(arr[0]) == 2) {
                int min = 0;
                for (int j = 0; j <= i; j++) {
                    if (arr_int[min]==0 && arr_int[j]!=0)
                    min=j;
                    if (arr_int[j] != 0 && arr_int[j] < arr_int[min])
                        min = j;
                }
                result_list.add(arr_int[min]);
                arr_int[min] = 0;


            }
        }
        for (Integer result : result_list
                ) {
            System.out.println(result);
        }
    }
}