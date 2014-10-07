package cobspecpasser;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class AuthLogResponderTest {
    Log log;
    AuthLogResponder responder;

    @Before
    public void setUp() throws Exception {
        log = new Log();
        log.writeAtEnd("Log1\nLog2\n");
        responder = new AuthLogResponder(log, "Aladdin", "open sesame");
    }

    HTTPRequest blankAuthRequest() {
        return new HTTPRequest("GET /something",
                               new HashMap<String, String>(),
                               new byte[0]);
    }

    HTTPRequest wrongAuthRequest() {
        HTTPRequest request = blankAuthRequest();
        request.headers.put("Authorization", "Basic QWxhZGRpbjpvcGVuIG5vdyE=");
        return request;
    }

    HTTPRequest correctAuthRequest() {
        HTTPRequest request = blankAuthRequest();
        request.headers.put("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        return request;
    }

    @Test
    public void testCreatesAuthFromIdAndPassword() throws Exception {
        String expectedAuth = "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==";
        assertTrue(expectedAuth.equals(responder.auth));
    }

    @Test
    public void testIsAuthorizedWithCorrectAuth() throws Exception {
        HTTPRequest request = correctAuthRequest();
        assertTrue(responder.isAuthorized(request));
    }

    @Test
    public void testIsNotAuthorizedWithWrongAuth() throws Exception {
        HTTPRequest request = wrongAuthRequest();
        assertFalse(responder.isAuthorized(request));
    }

    @Test
    public void testIsNotAuthorizedWithBlankAuth() throws Exception {
        HTTPRequest request = blankAuthRequest();
        assertFalse(responder.isAuthorized(request));
    }

    @Test
    public void testAuthorizedResponse() throws Exception {
        HTTPRequest request = correctAuthRequest();
        HTTPResponse response = responder.respond(request);

        assertTrue(response.statusLine.equals("HTTP/1.1 200 OK"));
        assertTrue(Arrays.equals(log.getContents().getBytes(), response.body));
    }

    @Test
    public void testUnauthorizedResponse() throws Exception {
        HTTPRequest request = wrongAuthRequest();
        HTTPResponse response = responder.respond(request);

        assertTrue(response.statusLine.equals("HTTP/1.1 401 Unauthorized"));
        assertTrue(Arrays.equals("Authentication required".getBytes(), response.body));
    }
}
