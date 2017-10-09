import java.util.HashMap;

public class Loop {

    public static void main(String... args) {
        HashMap<Integer, String> map = new HashMap<>();

        for (int i = 0; i < 100000; i++) {
            map.put(i, "value" + i);
        }
    }
}
