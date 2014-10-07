package cobspecpasser;

import org.junit.Test;
import static org.junit.Assert.*;

public class LogTest {
    @Test
    public void testReadEmptyLog() throws Exception {
        Log myLog = new Log();
        assertTrue("".equals(myLog.getContents()));
    }

    @Test
    public void testWriteAtEnd() throws Exception {
        Log myLog = new Log();
        myLog.writeAtEnd("HTTP request 1");
        assertTrue("HTTP request 1\n".equals(myLog.getContents()));
        myLog.writeAtEnd("HTTP request 2");
        assertTrue("HTTP request 1\nHTTP request 2\n".equals(myLog.getContents()));
    }
}
