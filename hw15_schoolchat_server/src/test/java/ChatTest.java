import com.botwy.chat_server.ServerMain;
import org.junit.Test;

public class ChatTest {

    @Test
    public  void test() {
        ServerMain server = new ServerMain();
        server.start();
    }
}
