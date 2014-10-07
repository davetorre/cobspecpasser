package cobspecpasser;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;
import httpserver.Responder;

import java.util.HashMap;

public class WriteFormResponder implements Responder {
    private Form form;

    public HTTPResponse respond(HTTPRequest request) {
        form.write(request.body);
        return new HTTPResponse("HTTP/1.1 200 OK",
                                new HashMap<String, String>(),
                                new byte[0]);
    }

    public WriteFormResponder(Form form) {
        this.form = form;
    }
}
