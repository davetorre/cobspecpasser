package cobspecpasser;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.nio.file.*;
import java.util.*;

public class PartialFileResponderTest {
    byte[] content;
    Path path;
    PartialFileResponder responder;

    @Before
    public void setUp() throws Exception {
        content = "Here comes\na file\nof text.".getBytes();
        path = TestHelper.makeTempFile("a-file", content).toPath();
        responder = new PartialFileResponder(path);
    }

    @Test
    public void testFindFirstRegex() throws Exception {
        String string = "bytes=123-456 23-1";
        String regex = "[0-9]+-[0-9]+";
        assertTrue("123-456".equals(responder.getFirstRegex(string, regex)));
    }

    @Test
    public void testGetRange() throws Exception {
        String rangeHeader1 = "bytes=5-26";
        String rangeHeader2 = "bytes 23-167";
        int[] range1 = {5, 26};
        int[] range2 = {23, 167};

        assertTrue(Arrays.equals(range1, responder.getRange(rangeHeader1)));
        assertTrue(Arrays.equals(range2, responder.getRange(rangeHeader2)));
    }

    @Test
    public void testGetPartialFileContent() throws Exception {
        int[] range = {3, 7};
        byte[] expected = "e co".getBytes();
        byte[] actual = responder.getPartialFileContent(range);

        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void testRespond() throws Exception {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Range", "bytes=0-6");
        HTTPRequest request = new HTTPRequest("GET /partial_content.txt",
                                              headers,
                                              new byte[0]);
        HTTPResponse response = responder.respond(request);

        assertTrue(response.statusLine.equals("HTTP/1.1 206 Partial Content"));
        assertTrue(Arrays.equals("Here c".getBytes(), response.body));
    }
}
