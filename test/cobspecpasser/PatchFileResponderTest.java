package cobspecpasser;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;

public class PatchFileResponderTest {
    File file;
    PatchFileResponder responder;
    String etag;
    String wrongEtag;

    public HashMap<String, String> headersWithEtag(String tag) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("If-Match", tag);
        return headers;
    }

    @Before
    public void setUp() throws Exception {
        file = TestHelper.makeTempFile("some-file", "default content".getBytes());
        responder = new PatchFileResponder(file.toPath());
        etag = "60bb224c68b1ed765a0f84d910de58d0beea91c4";
        wrongEtag = "wrong123etag456";
    }

    @Test
    public void testGetEtag() throws Exception {
        assertTrue(etag.equals(responder.getEtag(file.toPath())));
    }

    @Test
    public void testIsMatched() throws Exception {
        assertTrue(responder.isMatched(etag));
        assertTrue(responder.isMatched(etag.toUpperCase()));
    }

    @Test
    public void testIsNotMatched() throws Exception {
        assertFalse(responder.isMatched(wrongEtag));
    }

    @Test
    public void testRespondWithCorrectEtag() throws Exception {
        HTTPRequest request = new HTTPRequest("PATCH /thing HTTP/1.1",
                                              headersWithEtag(etag),
                                              new byte[0]);
        assertTrue("HTTP/1.1 204 No Content\n".equals(
                responder.respond(request).statusLine));
    }

    @Test
    public void testRespondWithWrongEtag() throws Exception {
        HTTPRequest request = new HTTPRequest("PATCH /thing HTTP/1.1",
                                              headersWithEtag(wrongEtag),
                                              new byte[0]);
        assertTrue("HTTP/1.1 412 Conflicting Modification".equals(
                responder.respond(request).statusLine));
    }

    @Test
    public void testRespondWithNoEtag() throws Exception {
        HTTPRequest request = new HTTPRequest("PATCH /thing HTTP/1.1",
                                              new HashMap<String, String>(),
                                              new byte[0]);
        assertTrue("HTTP/1.1 400 Bad Request".equals(
                responder.respond(request).statusLine));
    }
}
