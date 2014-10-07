package cobspecpasser;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;
import httpserver.Responder;

import java.util.*;

public class OptionsResponder implements Responder {

    public HTTPResponse respond(HTTPRequest request) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Allow", "GET,HEAD,POST,OPTIONS,PUT");

        return new HTTPResponse("HTTP/1.1 200 OK",
                                headers,
                                new byte[0]);
    }
}
