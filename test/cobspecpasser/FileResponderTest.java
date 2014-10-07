package cobspecpasser;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.nio.file.*;
import java.util.*;

public class FileResponderTest {
    byte[] content;
    Path path;
    FileResponder responder;

    @Before
    public void setUp() throws Exception {
        content = "Here comes\na file\nof text.".getBytes();
        path = TestHelper.makeTempFile("a-file", content).toPath();
        responder = new FileResponder(path);
    }

    @Test
    public void testGetFileContent() throws Exception {
        assertTrue(Arrays.equals(content, (responder.getFileContent())));
    }

    @Test
    public void testRespond() throws Exception {
        HTTPRequest request = new HTTPRequest("GET /file",
                                              new HashMap<String, String>(),
                                              new byte[0]);
        HTTPResponse response = responder.respond(request);

        assertTrue(response.statusLine.equals("HTTP/1.1 200 OK"));
        assertTrue(Arrays.equals(content, response.body));
    }
}
