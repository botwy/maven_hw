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



    public String getTextSmall(){

        String text = "5\r\n"
                +"4 3 1 2 1\r\n"
              ;

//System.out.println(text);
        return text;
    }

    public String getTextSecond(){

        String text = "5\r\n"
                +"5 4 3 2 1\r\n"
                ;
        return text;
    }


    @Test
    public void testList() {
        systemInMock.provideText(getTextSmall());
        Solution.main(new String[]{});
        assertEquals("-1 4 3 3 2", "-1 4 3 3 2"
                , log.getLog().trim());
        System.out.println();
    }

    @Test
    public void testListSecond() {
        systemInMock.provideText(getTextSecond());
        Solution.main(new String[]{});
        assertEquals("-1 5 4 3 2 ", "-1 5 4 3 2"
                , log.getLog().trim());
    }


}
