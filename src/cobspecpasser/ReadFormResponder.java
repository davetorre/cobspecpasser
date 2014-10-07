package cobspecpasser;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;
import httpserver.Responder;

import java.util.HashMap;

public class ReadFormResponder implements Responder {
    private Form form;

    public HTTPResponse respond(HTTPRequest request) {
        return new HTTPResponse("HTTP/1.1 200 OK",
                                new HashMap<String, String>(),
                                form.read());
    }

    public ReadFormResponder(Form form) {
        this.form = form;
    }
}
