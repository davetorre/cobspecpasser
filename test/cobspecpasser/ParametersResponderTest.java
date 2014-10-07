package cobspecpasser;

import org.junit.Test;
import static org.junit.Assert.*;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;

import java.util.Arrays;
import java.util.HashMap;

public class ParametersResponderTest {
    ParametersResponder responder = new ParametersResponder();

    @Test
    public void testDecodeOneParameter() throws Exception {
        String input = "variable_1=Operators";
        String expected = "variable_1 = Operators";
        String actual = responder.decode(input);

        assertTrue(expected.equals(actual));
    }

    @Test
    public void testDecodeWithoutPercents() throws Exception {
        String input = "variable_1=Operators&variable_2=stuff";
        String expected = "variable_1 = Operators\nvariable_2 = stuff";
        String actual = responder.decode(input);
        assertTrue(expected.equals(actual));
    }

    @Test
    public void testDecodeWithPercents() throws Exception {
        String input = "variable_1=Operators%20%3C%20%22is%20that%20all%22%3F&variable_2=stuff";
        String expected = "variable_1 = Operators < \"is that all\"?\nvariable_2 = stuff";
        String actual = responder.decode(input);
        assertTrue(expected.equals(actual));
    }

    @Test
    public void testRespond() throws Exception {
        String routeName = "GET /parameters";
        String params = "variable_1=Operators%20%3C%20%22is%20that%20all%22%3F&variable_2=stuff";
        HTTPRequest request = new HTTPRequest(routeName,
                                              params,
                                              new HashMap<String, String>(),
                                              new byte[0]);
        byte[] body = "variable_1 = Operators < \"is that all\"?\nvariable_2 = stuff".getBytes();
        HTTPResponse expected = new HTTPResponse("HTTP/1.1 200 OK",
                                                 new HashMap<String, String>(),
                                                 body);
        HTTPResponse actual = responder.respond(request);
        
        assertTrue(expected.statusLine.equals(actual.statusLine));
        assertTrue(expected.headers.equals(actual.headers));
        assertTrue(Arrays.equals(expected.body, actual.body));
    }

}
