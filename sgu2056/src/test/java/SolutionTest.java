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

        String text = "50016\n"
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
                +"2"
                ;

        Random r = new Random();


        for (int i = 0; i < 50015; i++) {
            int r_int = r.nextInt(100)+1;
            if (r_int>50)
                text +="\n2";
            else
                text +="\n1 "+Integer.toString(r_int);
        }
        return text;
    }

    public String getTextSmall(){

        String text = "dd D a Cc aa aaa aaaa\r\n"
                +"a Dd aa d aaa cC bbbb\r\n"
                +"a Dd aa d aaa   cC bbbb bbbb aaaC aaaC aaaC";

//System.out.println(text);
        return text;
    }

    public String getTextFirst(){

        String text = "dd D a Cc aa aaa aaaa";

//System.out.println(text);
        return text;
    }

    @Test
    public void testMap() {
        systemInMock.provideText(getTextFirst());
        SolutionMap.main(new String[]{});
        assertEquals("1 1 1 2 1 1 1 2", "a\r\naa\r\naaa\r\naaac\r\nbbbb\r\ncc\r\nd\r\ndd"
                , log.getLog().trim());
    }

    @Test
    public void testSet() {
        systemInMock.provideText(getTextSmall());
        SolutionSet.main(new String[]{});
        assertEquals("1 1 1 2 1 1 1 2", "a\r\naa\r\naaa\r\naaac\r\nbbbb\r\ncc\r\nd\r\ndd"
                , log.getLog().trim());
    }


}
