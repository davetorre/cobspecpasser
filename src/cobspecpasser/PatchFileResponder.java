package cobspecpasser;

import httpserver.Responder;
import httpserver.HTTPRequest;
import httpserver.HTTPResponse;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PatchFileResponder implements Responder {
    Path filePath;

    public void doPatch(byte[] content) {
        try {
            Files.write(filePath, content);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public byte[] addNewLineAtEnd(byte[] input) {
        return (new String(input) + "\n").getBytes();
    }

    /* Gets the contents of the file at filePath, adds newline char,
       returns lowercase String of the SHA-1 encryption. */
    public String getEtag(Path filePath) throws Exception {
        byte[] content = Files.readAllBytes(filePath);
        content = addNewLineAtEnd(content);

        java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
        digest.update(content);

        return DatatypeConverter.printHexBinary(digest.digest()).toLowerCase();
    }

    public boolean isMatched(String etagFromRequest) {
        boolean answer;
        try {
            answer = getEtag(filePath).equals(etagFromRequest.toLowerCase());
        } catch (Exception e) {
            answer = false;
        }
        return answer;
    }

    public HTTPResponse respond(HTTPRequest request) {
        HTTPResponse response = new HTTPResponse("HTTP/1.1 400 Bad Request",
                                                 new HashMap<String, String>(),
                                                 new byte[0]);

        if (request.headers.containsKey("If-Match")) {
            if (isMatched(request.headers.get("If-Match"))) {
                doPatch(request.body);
                response.statusLine = "HTTP/1.1 204 No Content\n";
            } else {
                response.statusLine = "HTTP/1.1 412 Conflicting Modification";
            }
        }

        return response;
    }

    public PatchFileResponder(Path filePath) {
        this.filePath = filePath;
    }
}
