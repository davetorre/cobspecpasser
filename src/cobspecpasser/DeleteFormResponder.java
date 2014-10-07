package cobspecpasser;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;
import httpserver.Responder;

import java.util.HashMap;

public class DeleteFormResponder implements Responder {
    private Form form;

    public HTTPResponse respond(HTTPRequest request) {
        form.delete();
        return new HTTPResponse("HTTP/1.1 200 OK",
                                new HashMap<String, String>(),
                                new byte[0]);
    }

    public DeleteFormResponder(Form form) {
        this.form = form;
    }
}
