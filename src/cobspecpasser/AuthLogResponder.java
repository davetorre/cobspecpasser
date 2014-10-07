package cobspecpasser;


import httpserver.Responder;
import httpserver.HTTPRequest;
import httpserver.HTTPResponse;

import java.util.Base64;
import java.util.HashMap;

public class AuthLogResponder implements Responder {
    public String auth;
    public Log logs;

    public boolean isAuthorized(HTTPRequest request) {
        boolean authorized = false;
        if (request.headers.containsKey("Authorization") &&
                request.headers.get("Authorization").equals(auth)) {
            authorized = true;
        }
        return authorized;
    }

    public HTTPResponse respond(HTTPRequest request) {
        HTTPResponse response;
        if (isAuthorized(request)) {
            response = new HTTPResponse("HTTP/1.1 200 OK",
                                        new HashMap<String, String>(),
                                        logs.getContents().getBytes());
        } else {
            response = new HTTPResponse("HTTP/1.1 401 Unauthorized",
                                        new HashMap<String, String>(),
                                        "Authentication required".getBytes());
        }
        return response;
    }

    public AuthLogResponder(Log logs, String id, String password) {
        this.logs = logs;
        byte[] idPassword = (id + ":" + password).getBytes();
        auth = "Basic " + Base64.getEncoder().encodeToString(idPassword);
    }
}
