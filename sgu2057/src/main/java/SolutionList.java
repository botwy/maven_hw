import java.util.*;

public class SolutionList {
    public static void main(String[] args) {
        Long startTime = System.nanoTime();
       // String text = args[0];
        Scanner scanner = new Scanner(System.in);
        int count = Integer.parseInt(scanner.nextLine());
        int previous_action =-1;
       List<Integer> list = new ArrayList<>();
        List<Integer> result_list = new ArrayList<>();
        String result_str="";

        for (int i = 0; i < count; i++) {
            String[] arr = scanner.nextLine().split("[\\s]+");
     //         System.out.println("i= "+i+" 1 or 2 "+arr[0]);
            if (Integer.parseInt(arr[0]) == 1) {

                int number = Integer.parseInt(arr[1]);

                list.add(number);
                previous_action=1;
            } else if (Integer.parseInt(arr[0]) == 2) {


                if (list.size() > 0) {
                    if (previous_action==1)
                        list.sort(new Comparator<Integer>() {
                            @Override
                            public int compare(Integer o1, Integer o2) {
                                return o1.compareTo(o2);
                            }
                        });
                  /*  if(result_str.equals(""))
                   result_str +=list.get(0).toString();
                    else result_str +="\r\n"+list.get(0).toString();*/

                  result_list.add(list.get(0));
                    list.remove(0);

                }
               previous_action=2;
            }
        }

        for (Integer result : result_list
                ) {
            System.out.println(result);
        }
      //  System.out.println(result_str);
        Long time_interval = System.nanoTime()-startTime;
        System.out.println(time_interval/1_000_000+" mls");

    }
}
