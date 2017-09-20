import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MyStreamTest {

    @Test
    public void TestOf() {
        List<Integer> list = Arrays.asList(1,2,3);
       MyStream<Integer> my_stream = MyStream.of(list);
my_stream.filter(a->a==2);
    }
}
