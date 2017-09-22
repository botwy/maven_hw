import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class SolutionTest {

    @Test
    public void Test1() {
        String text = "50008\n"
                +"1 1\n"
                +"1 1\n"
                +"1 1\n"
                +"2\n"
                +"1 2\n"
                +"2\n"
                +"2\n"
                +"2\n"
                +"1 1"
                ;
        Random r = new Random();

        for (int i = 0; i < 50000; i++) {
            int r_int = r.nextInt(100)+1;
            if (r_int>50)
                text +="\n2";
            else
            text +="\n1 "+Integer.toString(r_int);
        }
SolutionQue.main(new String[]{text});
     //  System.out.println("1");
       // System.out.println("1 3");
    }
}
