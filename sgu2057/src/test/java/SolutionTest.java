import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Rule
public final TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();
@Rule
public final StandardOutputStreamLog log = new StandardOutputStreamLog();

    public String getText(){

        String text = "16\n"
                +"1 1\n"
                +"1 1\n"
                +"1 1\n"
                +"2\n"
                +"1 2\n"
                +"2\n"
                +"2\n"
                +"2\n"
                +"1 1\n"
                +"1 1\n"
                +"1 1\n"
                +"2\n"
                +"1 2\n"
                +"2\n"
                +"2\n"
                +"2\n"
                ;

        Random r = new Random();


      /*  for (int i = 0; i < 50000; i++) {
            int r_int = r.nextInt(100)+1;
            if (r_int>50)
                text +="\n2";
            else
                text +="\n1 "+Integer.toString(r_int);
        }*/
        return text;
    }

    @Test
    public void testMap() {
    SolutionMap.main(new String[]{getText()});

    }

    @Test
    public void testQue() {
        SolutionQue.main(new String[]{getText()});

    }

    @Test
    public void testList() {
systemInMock.provideText(getText());
        SolutionList.main(new String[]{});
        assertEquals("1 1 1 2 1 1 1 2", "1\r\n1\r\n1\r\n2\r\n1\r\n1\r\n1\r\n2"
             , log.getLog().trim());

    }

    @Test
    public void testArr() {

        SolutionArr.main(new String[]{getText()});

    }
}
