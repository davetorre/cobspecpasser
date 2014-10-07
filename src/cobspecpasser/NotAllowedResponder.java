package cobspecpasser;

import httpserver.Responder;
import httpserver.HTTPRequest;
import httpserver.HTTPResponse;

import java.util.HashMap;

public class NotAllowedResponder implements Responder {
    public HTTPResponse respond(HTTPRequest request) {
        return new HTTPResponse("HTTP/1.1 405 Method Not Allowed",
                                new HashMap<String, String>(),
                                new byte[0]);
    }
}
