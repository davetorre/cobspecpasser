package cobspecpasser;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;
import org.junit.*;
import static org.junit.Assert.*;
import java.nio.file.*;
import java.io.*;
import java.util.HashMap;

public class DirectoryResponderTest {
    File file1;
    File file2;
    DirectoryResponder dr;

    @Before
    public void setUp() throws Exception {
        file1 = TestHelper.makeTempFile("a-file", new byte[0]);
        file2 = TestHelper.makeTempFile("b-file", new byte[0]);
        dr = new DirectoryResponder(Paths.get("."));
    }

    String linkForFile(File file) {
        String name = file.getName();
        return "<a href=/" + name + ">" + name + "</a";
    }

    @Test
    public void testGetDirContent() throws Exception {
        String content = new String(dr.getDirContent());
        assertTrue(content.contains(linkForFile(file1)));
        assertTrue(content.contains(linkForFile(file2)));
    }

    @Test
    public void testRespond() throws Exception {
        HTTPRequest request = new HTTPRequest("GET /dir",
                                              new HashMap<String, String>(),
                                              new byte[0]);
        HTTPResponse response = dr.respond(request);

        assertTrue(response.statusLine.equals("HTTP/1.1 200 OK"));
        assertTrue(new String(response.body).contains(linkForFile(file1)));
        assertTrue(new String(response.body).contains(linkForFile(file2)));
    }
}