package cobspecpasser;

import httpserver.Responder;
import httpserver.HTTPRequest;
import httpserver.HTTPResponse;

import java.util.HashMap;

public class RedirectResponder implements Responder {

    public HTTPResponse respond(HTTPRequest request) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Location", "http://localhost:5000/");

        return new HTTPResponse("HTTP/1.1 307 Temporary Redirect",
                                headers,
                                new byte[0]);
    }
}
