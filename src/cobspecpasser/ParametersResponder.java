package cobspecpasser;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;
import httpserver.Responder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class ParametersResponder implements Responder {

    public String decode(String input) {
        input = input.replace("&", "\n");
        input = input.replace("=", " = ");
        try {
            input = URLDecoder.decode(input, "UTF-8");
        } catch (Exception e) {
            input = "There was a problem decoding parameters.";
        }
        return input;
    }

    public HTTPResponse respond(HTTPRequest request) {
        String decodedParams = decode(request.parameters);

        return new HTTPResponse("HTTP/1.1 200 OK",
                                new HashMap<String, String>(),
                                decodedParams.getBytes());
    }
}
