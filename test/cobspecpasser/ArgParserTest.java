package cobspecpasser;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArgParserTest {

    @Test
    public void returnsPort() throws Exception {
        String[] args = {"something", "25", "-p", "5000"};
        assertEquals(5000, ArgParser.getPort(args));
    }

    @Test
    public void returnsDirectory() throws Exception {
        String[] args = {"-p", "2340", "thing", "-d", "/Users/dtorre/holygrail"};
        assertEquals("/Users/dtorre/holygrail", ArgParser.getDirectory(args));
    }
}
