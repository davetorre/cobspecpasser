package cobspecpasser;

import org.junit.Test;
import static org.junit.Assert.*;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;
import java.util.*;


public class FormRespondersTest {
    Form form = new Form();
    ReadFormResponder reader = new ReadFormResponder(form);
    WriteFormResponder writer = new WriteFormResponder(form);
    DeleteFormResponder deleter = new DeleteFormResponder(form);

    @Test
    public void testReaderWithEmptyForm() throws Exception {
        HTTPResponse response = reader.respond(null);

        assertTrue(response.statusLine.equals("HTTP/1.1 200 OK"));
        assertTrue(Arrays.equals(new byte[0], response.body));
    }

    @Test
    public void testWriter() throws Exception {
        HTTPRequest request = new HTTPRequest("PUT /something HTTP/1.1",
                                              new HashMap<String, String>(),
                                              "contents".getBytes());
        HTTPResponse response = writer.respond(request);

        assertTrue(response.statusLine.equals("HTTP/1.1 200 OK"));
        assertTrue(Arrays.equals(new byte[0], response.body));
        assertTrue(Arrays.equals("contents".getBytes(), form.read()));
    }

    @Test
    public void testReaderWithNonEmptyForm() throws Exception {
        form.write("contents".getBytes());
        HTTPResponse response = reader.respond(null);

        assertTrue(response.statusLine.equals("HTTP/1.1 200 OK"));
        assertTrue(Arrays.equals("contents".getBytes(), response.body));
    }

    @Test
    public void testDeleter() throws Exception {
        form.write("contents".getBytes());
        HTTPResponse response = deleter.respond(null);

        assertTrue(response.statusLine.equals("HTTP/1.1 200 OK"));
        assertTrue(Arrays.equals(new byte[0], response.body));
    }
}
